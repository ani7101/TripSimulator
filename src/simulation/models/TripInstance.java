package simulation.models;

import simulation.models.TripModel;
import utils.Navigation;
import utils.PolylineEncoderDecoder.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class TripInstance extends Thread {
    private TripModel tripModel;

    private final int reportInterval;

    private final int stopTime;

    public TripInstance(TripModel tripModel, int reportInterval) {
        this.tripModel = tripModel;
        this.reportInterval = reportInterval;

        // Stop time is the smaller value among either 5 times the reportInterval or 5 minutes
        stopTime =  reportInterval < 60 ? 300 : 5 * reportInterval;
    }

    @Override
    public void run() {
        for (int i = 0; i < tripModel.getRoute().size(); i++) {
            simulateTrip(i);
            sleep(LocalDateTime.now(), stopTime);
        }
    }

    private void simulateTrip(int routeSection) {
        try {
            while (interrupted()) {

                updateVehicle(routeSection);

                tripModel.preparePayload();

                if (stoppingCondition()) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void updateVehicle(int routeSection) {
        double distance = tripModel.getVehicleSpeed() * reportInterval;
        double totalExcessDistance = tripModel.getExcessDistance() + distance;
        double excessDistance = 0.0;

        List<LatLngZ> route = tripModel.getRoute().get(routeSection);

        for (
                int i = tripModel.getRoutePosition();
                i < tripModel.getRoute().get(routeSection).size() - 1;
                i++
        ) {
            LatLngZ pt1 = route.get(i);
            LatLngZ pt2 = route.get(i + 1);
            double routeDistance = Navigation.getDistance(pt1, pt2);

            if (Double.doubleToLongBits(excessDistance) == 0) {
                //Updating the newer geolocation of the vehicle
                LatLngZ updatedPos = Navigation.getPosition(pt1, pt2, distance);

                tripModel.setLatitude(updatedPos.lat);
                tripModel.setLongitude(updatedPos.lng);

                tripModel.setTrueOdometer(tripModel.getTrueOdometer() + distance);
                tripModel.setTotalFuelUsed(tripModel.getTotalFuelUsed() + distance / tripModel.getAverageFuelEconomy());

                tripModel.setExcessDistance(excessDistance);
                tripModel.setRoutePosition(tripModel.getRoutePosition() + 1);
            }
            totalExcessDistance = excessDistance;

            excessDistance = totalExcessDistance > routeDistance ? totalExcessDistance - routeDistance : 0.0;
        }
    }

    private void sleep(LocalDateTime startTime, int interval) {
        try {
            long elapsedTime = Duration.between(startTime, LocalDateTime.now()).toMillis();
            long sleepTime = interval - elapsedTime > 0 ? interval - elapsedTime : 0;
            Thread.sleep(sleepTime);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    private boolean stoppingCondition() {
        return false;
    }
}
