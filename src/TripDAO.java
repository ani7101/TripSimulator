import Utils.*;
import Trip.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * This class is used for creating a trip and indirectly accounting for an entity in the simulation.
 * It also has functionality for update every x's minutes
 */
public class TripDAO {
    private HereAPIClient hereAPIClient;
    private TripAPIClient tripAPIClient;

    public TripDAO(String baseUrl, String username, String password) {

        tripAPIClient = new TripAPIClient(baseUrl, username, password);
        hereAPIClient = new HereAPIClient();
    }

    public Map<String, Object> createTrip(String name,
                           double originLatitude, double originLongitude,
                           double destinationLatitude, double destinationLongitude,
                           ArrayList<Double> stopLatitudes, ArrayList<Double> stopLongitudes) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        if (stopLatitudes.size() != stopLongitudes.size()) {
            return null;
        }

        // Fill basic information
        Trip trip = GenerateBasicTrip(name, originLatitude, originLongitude, destinationLatitude, destinationLongitude, stopLatitudes, stopLongitudes);
        return tripAPIClient.create(trip);
    }

    public Map<String, Object> createTrip(String name,
                           double originLatitude, double originLongitude,
                           double destinationLatitude, double destinationLongitude,
                           ArrayList<Double> stopLatitudes, ArrayList<Double> stopLongitudes,
                           String vehicleName) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        if (stopLatitudes.size() != stopLongitudes.size()) {
            return null;
        }

        Trip trip = GenerateBasicTrip(name, originLatitude, originLongitude, destinationLatitude, destinationLongitude, stopLatitudes, stopLongitudes);
        trip.setVehicle(new TripVehicleInfoModel(vehicleName));
        return tripAPIClient.create(trip);
    }

    public Map<String, Object> createTrip(String name,
                           double originLatitude, double originLongitude,
                           double destinationLatitude, double destinationLongitude,
                           ArrayList<Double> stopLatitudes, ArrayList<Double> stopLongitudes,
                           String vehicleName,
                           String driverLoginId, String driverName) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        if (stopLatitudes.size() != stopLongitudes.size()) {
            return null;
        }

        Trip trip = GenerateBasicTrip(name, originLatitude, originLongitude, destinationLatitude, destinationLongitude, stopLatitudes, stopLongitudes);
        trip.setVehicle(new TripVehicleInfoModel(vehicleName));
        trip.setDriver(new TripDriverInfoModel(driverLoginId, driverName));
        return tripAPIClient.create(trip);
    }

    private Trip GenerateBasicTrip(String name,
                                   double originLatitude, double originLongitude,
                                   double destinationLatitude, double destinationLongitude,
                                   ArrayList<Double> stopLatitudes, ArrayList<Double> stopLongitudes) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        // Representing the data in the designated classes
        TripStopRecord origin = new TripStopRecord(originLatitude, originLongitude, true);
        TripStopRecord destination = new TripStopRecord(destinationLatitude, destinationLongitude, true);

        ArrayList<GeoPosition> stopsGeoLocation = new ArrayList<GeoPosition>();
        for (int i = 0; i < stopLatitudes.size(); i++) {
            stopsGeoLocation.add(new GeoPosition(stopLatitudes.get(i), stopLongitudes.get(i), true));
        }

        // Generating TripStopRecords for trip class
        ArrayList<TripStopRecord> stops = new ArrayList<TripStopRecord>();

        for (GeoPosition stop : stopsGeoLocation) {
            stops.add(new TripStopRecord(stop.getLatitude(), stop.getLongitude(), stop.isValid()));
        }

        Trip trip = new Trip();
        trip.setName(name);
        trip.setSource(origin);
        trip.setDestination(destination);
        trip.setStops(stops);

        // Requesting the HERE maps for the route
        Map<String, Object> route = hereAPIClient.getRoute(origin.getGeoLocation(), destination.getGeoLocation(), stopsGeoLocation, true);

        // Parsing the information from the route
        long totalDuration = 0; // Maintained in case necessary later on
        long totalLength = 0;
        long totalBaseDuration = 0;

        ArrayList<Object> sections = ((ArrayList<Object>) ((LinkedHashMap<String, Object>) ((ArrayList<Object>) route.get("routes")).get(0)).get("sections"));

        for (Object section : sections) {
            LinkedHashMap<String, Object> summary = ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) section).get("summary"));
            totalDuration += ((Number) summary.get("duration")).longValue();
            totalLength += ((Number) summary.get("length")).longValue();
            totalBaseDuration += ((Number) summary.get("baseDuration")).longValue();
        }

        trip.setPlannedDriveDistance(totalLength);
        trip.setPlannedDriveDurationSeconds(totalBaseDuration);

        return trip;
    }
}
