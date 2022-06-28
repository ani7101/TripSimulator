package simulation.models;

import connector.ConnectorAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final TripModel tripModel;

    private final int reportInterval;

    private final int stopTime;

    private static final Logger SIMULATION_LOGGER = LoggerFactory.getLogger("simulation");


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public TripInstance(TripModel tripModel, int reportInterval, double vehicleSpeed,
                        String connectorUrl, String username, String password) {
        connectorClient = new ConnectorAPIClient(connectorUrl, username, password);

        this.tripModel = tripModel;
        this.reportInterval = reportInterval;
        this.tripModel.setVehicleSpeed(vehicleSpeed);

        // Stop time is the smaller value among either 5 times the reportInterval or 5 minutes
        stopTime =  reportInterval < 60 ? 300 : 5 * reportInterval;
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
        System.out.println("Number of sections in route: " + tripModel.getRoute().size());
        for (int i = 0; i < tripModel.getRoute().size(); i++) {
            simulateTrip(i);
            SIMULATION_LOGGER.info("vehicle for trip {} finished section {}", tripModel.getId(), i);
            sleep(LocalDateTime.now(), stopTime);
        }

        SIMULATION_LOGGER.info("vehicle for trip {} finished it's trip", tripModel.getId());
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
        try {

            while (!interrupted()) {

                LocalDateTime startTime = LocalDateTime.now();

                // Moving the vehicle unless specified
                updateVehicle(routeSection);


                if (stoppingCondition(routeSection)) {
                    return;
                }

                // System.out.println("Vehicle has moved and is at " + tripModel.getLatitude() + ", " + tripModel.getLongitude() + "and route position: " + tripModel.getRoutePosition());

                // Send the updated message to the connector
                connectorClient.postPayload(tripModel.getPayload());

                sleep(startTime, reportInterval);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void updateVehicle(int routeSection) {
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
                tripModel.setLatitude(updatedPos.lat);
                tripModel.setLongitude(updatedPos.lng);

                // Updating additional parameters such as distance travelled, fuel used, distance from start and position in the route
                tripModel.setTrueOdometer(tripModel.getTrueOdometer() + distanceInKms);
                tripModel.setTotalFuelUsed(tripModel.getTotalFuelUsed() + distanceInKms / tripModel.getAverageFuelEconomy());

                tripModel.setRuntimeSinceEngineStart(tripModel.getRuntimeSinceEngineStart() + reportInterval);

                tripModel.setDistanceFromStart(distanceFromStart);
                tripModel.setRoutePosition(tripModel.getRoutePosition() + 1);
                tripModel.setRoutePosition(i);

                // Generating randomized payload data for other fields
                tripModel.preparePayload();

                return;
            }
            distanceFromStart = excessDistance;
            tripModel.setRoutePosition(i);
        }
    }


    /**
     * Makes the thread sleep for a variable time to ensure reports are made consistently at the given interval.
     * @param startTime Starting time when the iteration was started.
     * @param interval time interval to sleep
     */
    private static void sleep(LocalDateTime startTime, int interval) {
        try {
            long elapsedTime = Duration.between(startTime, LocalDateTime.now()).toMillis();
            long sleepTime = interval - elapsedTime > 0 ? interval - elapsedTime : 0;
            Thread.sleep(sleepTime);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }


    /**
     * Stops the path if the stop has already been reached.
     * @param routeSection section of the route (it indicates which stop we are dealing with)
     * @return Boolean: stopping condition for the trip
     */
    private boolean stoppingCondition(int routeSection) {
        if (routeSection == tripModel.getRoute().size() - 1) {
            return tripModel.getRoutePosition() >= tripModel.getRoute().get(routeSection).size() ||
                    equal(tripModel.getLatitude(), tripModel.getDestination().lat) && equal(tripModel.getLongitude(), tripModel.getDestination().lng);
        }
        LatLngZ stop = tripModel.getStops().get(routeSection);

        return tripModel.getRoutePosition() >= tripModel.getRoute().get(routeSection).size() ||
                equal(tripModel.getLatitude(), stop.lat) && equal(tripModel.getLongitude(), stop.lng);
    }


    private static final double DISTANCE_THRESHOLD = 1e-6;

    /**
     * Checks if a value is within the proximity of another value. This radius if given bt DISTANCE_THRESHOLD global variable.
     * @return Boolean: if they lie within the given proximity
     */
    private static boolean equal(double val1, double val2) {
        return Math.abs(val2 - val1) <= DISTANCE_THRESHOLD;
    }


    //endregion

}
