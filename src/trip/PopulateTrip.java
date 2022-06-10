package trip;

import hereMaps.HEREMapsRouteSection;
import hereMaps.HereAPIClient;
import trip.tripSubClasses.TripDriverInfoModel;
import trip.tripSubClasses.TripStopRecord;
import trip.tripSubClasses.TripVehicleInfoModel;
import utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * This class is used for creating a trip and indirectly accounting for an entity in the simulation.
 * It also has functionality for update every x's minutes
 */
public class PopulateTrip {
    private HereAPIClient hereAPIClient;

    private TripAPIClient tripAPIClient;

    private TripRepository repository;

    private Trip trip;

    public PopulateTrip(String baseUrl, String username, String password,
                        TripRepository repository,
                        Trip trip) {

        tripAPIClient = new TripAPIClient(baseUrl, username, password);
        hereAPIClient = new HereAPIClient();
        this.trip = trip;
    }

    public PopulateTrip(String baseUrl, String username, String password,
                        TripRepository repository,
                        String vehicleName,
                        String driverLoginId, String driverName
                        ) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        tripAPIClient = new TripAPIClient(baseUrl, username, password);
        hereAPIClient = new HereAPIClient();
        this.repository = repository;

        // Generating the source, destination and stops
        TripStopRecord source = new TripStopRecord(repository.getRandomSource());
        TripStopRecord destination = new TripStopRecord(repository.getRandomDestination());

        ArrayList<TripStopRecord> stops = new ArrayList<>();
        ArrayList<ArrayList<Double>> stopsLocation = repository.getRandomStop(2);

        for (ArrayList<Double> stop : stopsLocation) {
            stops.add(new TripStopRecord(stop));
        }

        trip = new Trip(
                source,
                destination,
                stops,
                new TripVehicleInfoModel(vehicleName),
                new TripDriverInfoModel(driverLoginId, driverName)
        );

        trip.setName("Trip simulator-" +Generator.generateRandomUUID());

        List<Long> hereResponse = getHEREMapsInfo(source.getGeoLocation(), destination.getGeoLocation(), stopsLocation);

        trip.setPlannedDriveDistance(hereResponse.get(1));
        trip.setPlannedDriveDurationSeconds(hereResponse.get(2));
    }

    public PopulateTrip(String baseUrl, String username, String password,
                        TripRepository repository,
                        String vehicleName
    ) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        tripAPIClient = new TripAPIClient(baseUrl, username, password);
        hereAPIClient = new HereAPIClient();
        this.repository = repository;

        // Generating the source, destination and stops
        TripStopRecord source = new TripStopRecord(repository.getRandomSource());
        TripStopRecord destination = new TripStopRecord(repository.getRandomDestination());

        ArrayList<TripStopRecord> stops = new ArrayList<>();
        ArrayList<ArrayList<Double>> stopsLocation = repository.getRandomStop(2);

        for (ArrayList<Double> stop : stopsLocation) {
            stops.add(new TripStopRecord(stop));
        }

        trip = new Trip(
                source,
                destination,
                stops,
                new TripVehicleInfoModel(vehicleName)
        );

        trip.setName("Trip simulator-" +Generator.generateRandomUUID());

        List<Long> hereResponse = getHEREMapsInfo(source.getGeoLocation(), destination.getGeoLocation(), stopsLocation);

        trip.setPlannedDriveDistance(hereResponse.get(1));
        trip.setPlannedDriveDurationSeconds(hereResponse.get(2));
    }

    public Trip sendQuery() {
        return tripAPIClient.create(trip);
    }

    public Trip getTrip() { return trip; }

    // Utils
    private List<Long> getHEREMapsInfo(ArrayList<Double> source,ArrayList<Double> destination,ArrayList<ArrayList<Double>> stops) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        ArrayList<Double> stopsLat = new ArrayList<Double>();
        ArrayList<Double> stopsLong = new ArrayList<Double>();

        for(int i = 0; i < stops.size(); i++) {
            stopsLat.add(stops.get(i).get(0));
            stopsLong.add(stops.get(i).get(1));
        }

        ArrayList<HEREMapsRouteSection> route = hereAPIClient.getRoute(source.get(0), source.get(1),
                destination.get(0), destination.get(1),
                stopsLat, stopsLong,
                true);

        // Parsing the information from the route
        long totalDuration = 0; // Maintained in case necessary later on
        long totalLength = 0;
        long totalBaseDuration = 0;

        // Aim to replace this whole section by a response object
        /*  Need to uncomment after fixing the HEREMapsRouteSection class
        ArrayList<Object> sections = ((ArrayList<Object>) ((LinkedHashMap<String, Object>) ((ArrayList<Object>) route.get("routes")).get(0)).get("sections"));

        for (Object section : sections) {
            LinkedHashMap<String, Object> summary = ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) section).get("summary"));
            totalDuration += ((Number) summary.get("duration")).longValue();
            totalLength += ((Number) summary.get("length")).longValue();
            totalBaseDuration += ((Number) summary.get("baseDuration")).longValue();
        }
         */

        return List.of(totalDuration, totalLength, totalBaseDuration);
    }




    @Deprecated
    public Trip createTrip(String name,
                           ArrayList<Double> source,
                           ArrayList<Double> destination,
                           ArrayList<ArrayList<Double>> stops) throws IOException, ExecutionException, InterruptedException, TimeoutException {


        Trip trip = GenerateBasicTrip(name, source, destination, stops);
        return tripAPIClient.create(trip);
    }

    @Deprecated
    public Trip createTrip(String name,
                           ArrayList<Double> source,
                           ArrayList<Double> destination,
                           ArrayList<ArrayList<Double>> stops,
                           String vehicleName) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        Trip trip = GenerateBasicTrip(name,source, destination, stops);
        trip.setVehicle(new TripVehicleInfoModel(vehicleName));
        return tripAPIClient.create(trip);
    }

    @Deprecated
    public Trip createTrip(String name,
                           ArrayList<Double> source,
                           ArrayList<Double> destination,
                           ArrayList<ArrayList<Double>> stops,
                           String vehicleName,
                           String driverLoginId, String driverName) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        Trip trip = GenerateBasicTrip(name, source, destination, stops);
        trip.setVehicle(new TripVehicleInfoModel(vehicleName));
        trip.setDriver(new TripDriverInfoModel(driverLoginId, driverName));
        return tripAPIClient.create(trip);
    }

    @Deprecated
    private Trip GenerateBasicTrip(String name,
                                   ArrayList<Double> sourceLocation,
                                   ArrayList<Double> destinationLocation,
                                   ArrayList<ArrayList<Double>> stopsLocation) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        // Representing the data in the designated classes
        TripStopRecord source = new TripStopRecord(sourceLocation);
        TripStopRecord destination = new TripStopRecord(destinationLocation);


        // Generating TripStopRecords for trip class
        ArrayList<TripStopRecord> stops = new ArrayList<TripStopRecord>();
        TripStopRecord temp;

        ArrayList<Double> stopsLat = new ArrayList<Double>();
        ArrayList<Double> stopsLong = new ArrayList<Double>();

        for (ArrayList<Double> stop : stopsLocation) {
            temp = new TripStopRecord(stop);
            stops.add(temp);
            stopsLat.add(temp.getLatitude());
            stopsLong.add(temp.getLongitude());
        }

        Trip trip = new Trip();
        trip.setName(name);
        trip.setSource(source);
        trip.setDestination(destination);
        trip.setStops(stops);

        // Requesting the HERE maps for the route
        ArrayList<HEREMapsRouteSection> route = hereAPIClient.getRoute(source.getLatitude(), source.getLongitude(),
                destination.getLatitude(), destination.getLongitude(),
                stopsLat, stopsLong,
                true);

        // Parsing the information from the route
        long totalDuration = 0; // Maintained in case necessary later on
        long totalLength = 0;
        long totalBaseDuration = 0;

        // Aim to replace this whole section by a response object

        /*
        ArrayList<Object> sections = ((ArrayList<Object>) ((LinkedHashMap<String, Object>) ((ArrayList<Object>) route.get("routes")).get(0)).get("sections"));

        for (Object section : sections) {
            LinkedHashMap<String, Object> summary = ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) section).get("summary"));
            totalDuration += ((Number) summary.get("duration")).longValue();
            totalLength += ((Number) summary.get("length")).longValue();
            totalBaseDuration += ((Number) summary.get("baseDuration")).longValue();
        }

        trip.setPlannedDriveDistance(totalLength);
        trip.setPlannedDriveDurationSeconds(totalBaseDuration);
         */

        return trip;
    }
}
