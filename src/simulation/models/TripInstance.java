package simulation.models;

import connector.ConnectorAPIClient;
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

    private final ConnectorAPIClient connectorClient;

    private final TripModel tripModel;

    private final int reportInterval;

    private final int stopTime;

    // This is required in case we stop the vehicle & restart it after some time.
    private int vehicleSpeed;

    // TODO: 27/06/2022 Yet to configure the stopping condition of the vehicle.
    private Boolean shouldMove = true;


    //endregion
    //region Constructors

    public TripInstance(TripModel tripModel, int reportInterval, int vehicleSpeed,
                        String connectorUrl,String username,String password) {
        connectorClient = new ConnectorAPIClient(connectorUrl, username, password);

        this.tripModel = tripModel;
        this.reportInterval = reportInterval;
        this.tripModel.setVehicleSpeed(vehicleSpeed);
        this.vehicleSpeed = vehicleSpeed;

        // Stop time is the smaller value among either 5 times the reportInterval or 5 minutes
        stopTime =  reportInterval < 60 ? 300 : 5 * reportInterval;
    }


    //endregion
    //region Main run loop

    /**
     * Simulates the whole route between
     */
    @Override
    public void run() {
        for (int i = 0; i < tripModel.getRoute().size(); i++) {
            simulateTrip(i);
            sleep(LocalDateTime.now(), stopTime);
        }
    }


    //endregion
    //region Utils

    /**
     * Simulates one section of the route.
     * @param routeSection id to the section of the route
     */
    private void simulateTrip(int routeSection) {
        try {
            while (!interrupted()) {
                LocalDateTime startTime = LocalDateTime.now();

                // Moving the vehicle unless specified
                if (!stoppingCondition()) {
                    tripModel.setVehicleSpeed(vehicleSpeed);
                    updateVehicle(routeSection);
                } else {
                    tripModel.setVehicleSpeed(0);
                }

                // Send the updated message to the connector
                connectorClient.postPayload(tripModel.getPayload());

                sleep(startTime, reportInterval / 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the vehicle position and other values required for each report to the server
     * @param routeSection id to the section of the route
     */
    private void updateVehicle(int routeSection) {
        double distance = reportInterval * Navigation.kmphToMps(tripModel.getVehicleSpeed());
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
                LatLngZ updatedPos = Navigation.getPosition(pt1, pt2, distanceFromStart);

                // Updating the latitude and longitude (position) of the vehicle
                tripModel.setLatitude(updatedPos.lat);
                tripModel.setLongitude(updatedPos.lng);

                // Updating additional parameters such as distance travelled, fuel used, distance from start and position in the route
                tripModel.setTrueOdometer(tripModel.getTrueOdometer() + distanceInKms);
                tripModel.setTotalFuelUsed(tripModel.getTotalFuelUsed() + distanceInKms / tripModel.getAverageFuelEconomy());

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

    // TODO: 27/06/2022  
    private boolean stoppingCondition() {
        return false;
    }


    //endregion
    // region Getters/Setters

    public Boolean getShouldMove() {
        return shouldMove;
    }

    public void setShouldMove(Boolean shouldMove) {
        this.shouldMove = shouldMove;
    }

    public int getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(int vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    //endregion

}
