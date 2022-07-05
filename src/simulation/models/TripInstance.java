package simulation.models;

import connector.ConnectorAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payload.equipment.Payload;
import utils.Navigation;
import utils.PolylineEncoderDecoder.LatLngZ;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Contains one instance of the simulation. It is a child class of <i>Thread</i> & as a result can be run concurrently.
 * <br>
 * The general functionality is it sends a message at the given report interval with the updated position according to the vehicle speed.
 */
public class TripInstance extends Thread {

    //region Class variables
    //---------------------------------------------------------------------------------------

    private final ConnectorAPIClient connectorClient;

    private final String vehicleConnectorUrl;

    private final String equipmentConnectorUrl;

    private final TripModel tripModel;

    private final int reportInterval;

    private final int stopTime;

    private static final Logger SIMULATION_LOGGER = LoggerFactory.getLogger("simulation");

    // Probability indicates that 1 in the number given below will not make it to their final destinations
    // Instead of using a randomization for finding if there is a failure in the equipment,
    // a system where every nth device is a failure is used.
    private static final int FAILURE_PROBABILITY = 4;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public TripInstance(TripModel tripModel, int reportInterval, double vehicleSpeed,
                        String vehicleConnectorUrl, String equipmentConnectorUrl, String username, String password) {
        connectorClient = new ConnectorAPIClient(username, password);

        this.tripModel = tripModel;
        this.reportInterval = reportInterval;
        this.tripModel.setVehicleSpeed(vehicleSpeed);
        this.vehicleConnectorUrl = vehicleConnectorUrl;
        this.equipmentConnectorUrl = equipmentConnectorUrl;

        // Stop time is the smaller value among either 5 times the reportInterval or 5 minutes
        // stopTime =  reportInterval < 60 ? 300 : 5 * reportInterval;
        stopTime = 20;
    }


    //endregion
    //region Main run loop
    //---------------------------------------------------------------------------------------

    /**
     * Simulates the whole route between
     */
    @Override
    public void run() {
        SIMULATION_LOGGER.info("vehicle for trip {} started moving", tripModel.getId());
        System.out.println("Vehicle for trip " + tripModel.getId() + " started moving");

        for (int i = 0; i < tripModel.getRoute().size(); i++) {

            // Sending the initial payload at the source location
            LocalDateTime startTime = LocalDateTime.now();
            postInitialVehiclePayload();

            // Wait until the next report interval to begin moving the vehicle.
            sleep(startTime, reportInterval);

            simulateTrip(i);

            SIMULATION_LOGGER.info("vehicle for trip {} finished section {} and is waiting for {}", tripModel.getId(), i, stopTime);
            System.out.println("Vehicle for trip " + tripModel.getId() + " finished section " + i + " and is waiting for " + stopTime + "s");

            // Sleeping at each stop for the IoT server to finish processing
            sleep(LocalDateTime.now(), stopTime);
        }

        SIMULATION_LOGGER.info("vehicle for trip {} finished it's trip", tripModel.getId());
        System.out.println("Vehicle for trip " + tripModel.getId() + " finished it's trip");
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    /**
     * Simulates one section of the route.
     * It updates the properties of the vehicle at a given input interval & sleeps for the rest of the time.
     * @param routeSection id to the section of the route
     */
    private void simulateTrip(int routeSection) {
        System.out.println("Number of positions in route section " + routeSection + " is " + tripModel.getRoute().get(routeSection).size());
        try {
            // Main loop for iterating over route position
            while (!interrupted()) {

                LocalDateTime startTime = LocalDateTime.now();

                // Checking whether to stop the vehicle or not
                if (stoppingCondition(routeSection)) {
                    if (routeSection == tripModel.getRoute().size() - 1) {
                        this.tripModel.setLatitude(this.tripModel.getDestination().lat);
                        this.tripModel.setLatitude(this.tripModel.getDestination().lng);
                    }
                    else {
                        this.tripModel.setLatitude(this.tripModel.getStops().get(routeSection).lat);
                        this.tripModel.setLatitude(this.tripModel.getStops().get(routeSection).lng);
                    }

                    tripModel.prepareVehiclePayload();
                    connectorClient.postPayload(vehicleConnectorUrl, tripModel.getVehiclePayload());

                    // Setting route position back to start
                    tripModel.setRoutePosition(0);

                    return;
                }

                // Moving the vehicle and posting the payload
                updateVehicleAndPost(routeSection);

                System.out.println("Vehicle has moved and is at " + tripModel.getLatitude() + ", " + tripModel.getLongitude() + " and route position: " + tripModel.getRoutePosition());

                sleep(startTime, reportInterval);
            }
        } catch (Exception e) {
            SIMULATION_LOGGER.warn("Error while moving the vehicle, " + e);
            e.printStackTrace();
        }
    }

    /**
     * Posts the payload of the vehicle with the location set to the source
     */
    private void postInitialVehiclePayload() {
        // Generating randomized payload data for other fields and posting the initial message at source location
        tripModel.prepareVehiclePayload();

        // To ensure the initial point is set to the source location
        tripModel.setLocation(tripModel.getSource());

        // Posting to the connector
        connectorClient.postPayload(vehicleConnectorUrl, tripModel.getVehiclePayload());

    }


    /**
     * Updates the vehicle position and other values required for each report to the server
     * <br>
     * Configures the following values
     * <ul>
     *     <li>Latitude</li>
     *     <li>Longitude</li>
     *     <li>Distance (odometer)</li>
     *     <li>Total fuel used</li>
     *     <li>Runtime since engine start</li>
     *     <li>Position on route</li>
     * </ul>
     * @param routeSection id to the section of the route
     */
    private void updateVehicleAndPost(int routeSection) {
        double distance = reportInterval * Navigation.milesPerHourToMetersPerSecond(tripModel.getVehicleSpeed());
        double distanceInKms = Navigation.metersToKms(distance);
        double distanceFromStart = tripModel.getDistanceFromStart() + distance; // in meters
        double excessDistance; // in meters

        List<LatLngZ> route = tripModel.getRoute().get(routeSection);
        for (
                int i = tripModel.getRoutePosition();
                i < tripModel.getRoute().get(routeSection).size() - 1;
                i++
        ) {
            LatLngZ pt1 = route.get(i);
            LatLngZ pt2 = route.get(i + 1);
            double routeDistance = Navigation.getDistance(pt1, pt2); // in meters

            excessDistance = distanceFromStart > routeDistance ? distanceFromStart - routeDistance : 0.0;

            if (Double.doubleToLongBits(excessDistance) == 0) {
                // Obtaining newer position along the points pt1 and pt2, in case it can't traverse the full distance
                double bearing = Navigation.getBearing(pt1, pt2);
                LatLngZ updatedPos = Navigation.getPosition(pt1, bearing, distanceFromStart);

                // Updating the latitude and longitude (position) of the vehicle
                tripModel.setLocation(updatedPos);

                // Updating additional parameters such as distance travelled, fuel used, distance from start and position in the route
                tripModel.setTrueOdometer(tripModel.getTrueOdometer() + distanceInKms);
                tripModel.setTotalFuelUsed(tripModel.getTotalFuelUsed() + distanceInKms / tripModel.getAverageFuelEconomy());

                tripModel.setRuntimeSinceEngineStart(tripModel.getRuntimeSinceEngineStart() + reportInterval);

                tripModel.setDistanceFromStart(distanceFromStart);
                tripModel.setRoutePosition(tripModel.getRoutePosition() + 1);
                tripModel.setRoutePosition(i);

                // Generating randomized payload data for other fields
                tripModel.prepareVehiclePayload();

                // TODO: 05/07/2022 Add vehicle speed reduction for route corners

                // Send the updated vehicle payload to the connector
                connectorClient.postPayload(vehicleConnectorUrl, tripModel.getVehiclePayload());

                // Send the updated equipment payloads to the connector
                updateEquipmentAndPost(routeSection, updatedPos.lat, updatedPos.lng);

                return;
            }
            distanceFromStart = excessDistance;
            tripModel.setRoutePosition(i);

            // If the vehicle moves towards the ending of the route to turn we can slow down the vehicle in the last
        }

        // Send the updated vehicle payload to the connector
        connectorClient.postPayload(vehicleConnectorUrl, tripModel.getVehiclePayload());

        // Send the updated equipment payloads to the connector
        updateEquipmentAndPost(routeSection, tripModel.getLatitude(), tripModel.getLongitude());
    }

    /**
     * Updates the equipment and posts it to the connector.
     * @param routeSection Section of the route in which the vehicle is traversing
     * @param lat Latitude to the updated position
     * @param lng Longitude of the updated position
     */
    public void updateEquipmentAndPost(int routeSection, double lat, double lng) {
        int count = 1;

        // Updating and posting the equipment payload
        for (Payload equipment : tripModel.getEquipmentPayloads()) {
            // In case the equipment falls into the route section, then we can send and update the position.

            if (
                        (routeSection >= equipment.getPickupStopSequence() - 1 && routeSection < equipment.getDropStopSequence() - 1)
                    &&
                        !equipment.isFailure()
            ) {

                System.out.println("Equipment is moved to " + lat + ", " + lng);
                equipment.setLatitude(lat);
                equipment.setLongitude(lng);

                tripModel.prepareEquipmentPayload();
                connectorClient.postPayload(equipmentConnectorUrl, equipment);
            }
            // Handling failures in equipments
            if (count % FAILURE_PROBABILITY == 0) {
                equipment.setFailure(true);
            }
            count++;
        }

        // Updating and posting the ship unit payload
        for (Payload shipUnit : tripModel.getShipUnitPayloads()) {
            if (
                    (routeSection >= shipUnit.getPickupStopSequence() && routeSection < shipUnit.getDropStopSequence())
                            &&
                            !shipUnit.isFailure()
            ) {
                shipUnit.setLatitude(lat);
                shipUnit.setLongitude(lng);

                tripModel.prepareEquipmentPayload();
                connectorClient.postPayload(equipmentConnectorUrl, shipUnit);
            }
            // Handling failures in equipments
            if (count % FAILURE_PROBABILITY == 0) {
                shipUnit.setFailure(true);
            }
            count++;
        }

        // Updating and posting the ship item payload
        for (Payload shipItem : tripModel.getShipItemPayloads()) {
            if (
                    (routeSection >= shipItem.getPickupStopSequence() && routeSection < shipItem.getDropStopSequence())
                            &&
                            !shipItem.isFailure()
            ) {
                shipItem.setLatitude(lat);
                shipItem.setLongitude(lng);

                tripModel.prepareEquipmentPayload();
                connectorClient.postPayload(equipmentConnectorUrl, shipItem);
            }
            // Handling failures in equipments
            if (count % FAILURE_PROBABILITY == 0) {
                shipItem.setFailure(true);
            }
            count++;
        }
    }


    /**
     * Makes the thread sleep for a variable time to ensure reports are made consistently at the given interval.
     * @param startTime Starting time when the iteration was started.
     * @param interval time interval to sleep in seconds
     */
    private static void sleep(LocalDateTime startTime, int interval) {
        try {
            long elapsedTime = Duration.between(startTime, LocalDateTime.now()).toMillis();
            long sleepTime = 1000L * interval - elapsedTime > 0 ? 1000L * interval - elapsedTime : 0;
            sleep(sleepTime);
        } catch (InterruptedException ie) {
            SIMULATION_LOGGER.warn("Exception generated while vehicle is sleeping: " + ie);
        }
    }

    private static void sleepCorneringTime(int routePosition,int initialRoutePosition) {
        // For every corner we reach let us sleep for 0.1s
        try {
            long sleepTime = (routePosition - initialRoutePosition) * 100L;
            sleep(sleepTime);
        } catch (InterruptedException ie) {
            SIMULATION_LOGGER.warn("Exception generated while vehicle is sleeping: " + ie);
        }
    }


    /**
     * Stops the path if the stop has already been reached.
     * @param routeSection section of the route (it indicates which stop we are dealing with)
     * @return Boolean: stopping condition for the trip
     */
    private boolean stoppingCondition(int routeSection) {
        if (routeSection == tripModel.getRoute().size() - 1) {
            return tripModel.getRoutePosition() >= tripModel.getRoute().get(routeSection).size() - 2 ||
                    equal(tripModel.getLatitude(), tripModel.getDestination().lat) && equal(tripModel.getLongitude(), tripModel.getDestination().lng);
        }
        LatLngZ stop = tripModel.getStops().get(routeSection);

        return tripModel.getRoutePosition() >= tripModel.getRoute().get(routeSection).size() - 2 ||
                equal(tripModel.getLatitude(), stop.lat) && equal(tripModel.getLongitude(), stop.lng);
    }


    private static final double DISTANCE_THRESHOLD = 1e-4;

    /**
     * Checks if a value is within the proximity of another value. This radius if given bt DISTANCE_THRESHOLD global variable.
     * @return Boolean: if they lie within the given proximity
     */
    private static boolean equal(double val1, double val2) {
        return Math.abs(val2 - val1) <= DISTANCE_THRESHOLD;
    }


    //endregion
    //region Getters/Setters

    public void setVehicleSpeed(double vehicleSpeed) {
        tripModel.setVehicleSpeed(vehicleSpeed);
    }

    public double getVehicleSpeed() {
        return tripModel.getVehicleSpeed();
    }

    //endregion

}
