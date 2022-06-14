package trip;

import hereMaps.HEREMapsRouteSection;
import hereMaps.HEREMapsAPIClient;
import trip.tripSubClasses.TripDriverInfoModel;
import trip.tripSubClasses.TripStopRecord;
import trip.tripSubClasses.TripVehicleInfoModel;
import user.PopulateUser;
import utils.*;
import vehicle.PopulateVehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for creating a trip and indirectly accounting for an entity in the simulation.
 * Types of constructors:
 *  1. Trip class is given as an argument and is taken as support
 *  2. vehicle name (which is unique) and driver details are provided and the remaining data is generated
 *  3. vehicle name is provided and the remaining is generated (driver is ignored) (WILL BE DEPRECIATED SOON!)
 *  4. Nothing is provided and using PopulateUser and PopulateVehicle, a vehicle and a user are created and used
 */
public class PopulateTrip {
    private HEREMapsAPIClient hereAPIClient;

    private TripAPIClient tripAPIClient;

    private TripRepository repository;

    private Trip trip;

    private PopulateVehicle populateVehicle;

    private PopulateUser populateUser;


    public PopulateTrip(String baseUrl, String username, String password,
                        Trip trip) {

        tripAPIClient = new TripAPIClient(baseUrl, username, password);
        hereAPIClient = new HEREMapsAPIClient();
        this.trip = trip;
    }

    public PopulateTrip(String baseUrl, String username, String password,
                        TripRepository repository,
                        String vehicleName,
                        String driverLoginId, String driverName) {

        tripAPIClient = new TripAPIClient(baseUrl, username, password);
        hereAPIClient = new HEREMapsAPIClient();
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

        trip.setName("Trip simulator-" + Generator.generateRandomUUID());

        ArrayList<HEREMapsRouteSection> route = getRoute(source.getGeoLocation(), destination.getGeoLocation(), stopsLocation);


        List<Long> hereResponse = parseHEREMapsSummary(route);
        ArrayList<String> polyline = parseHEREMapsPolyline(route);

        trip.setRoute(polyline);
        trip.setPlannedDriveDistance(hereResponse.get(1));
        trip.setPlannedDriveDurationSeconds(hereResponse.get(2));
    }

    public PopulateTrip(String baseUrl, String username, String password,
                        TripRepository repository,
                        String vehicleName) {

        tripAPIClient = new TripAPIClient(baseUrl, username, password);
        hereAPIClient = new HEREMapsAPIClient();
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

        trip.setName("Trip simulator-" + Generator.generateRandomUUID());

        ArrayList<HEREMapsRouteSection> route = getRoute(source.getGeoLocation(), destination.getGeoLocation(), stopsLocation);


        List<Long> hereResponse = parseHEREMapsSummary(route);
        ArrayList<String> polyline = parseHEREMapsPolyline(route);

        trip.setRoute(polyline);
        trip.setPlannedDriveDistance(hereResponse.get(1));
        trip.setPlannedDriveDurationSeconds(hereResponse.get(2));
    }

    public PopulateTrip(String baseUrl, String username, String password,
                        TripRepository repository, String vehicleTypeId, boolean populate) {

        // populate argument is a dummy to distinguish it from the vehicleName variant

        tripAPIClient = new TripAPIClient(baseUrl, username, password);
        populateVehicle = new PopulateVehicle(baseUrl, username, password, vehicleTypeId);
        populateUser = new PopulateUser(baseUrl, username, password);

        hereAPIClient = new HEREMapsAPIClient();

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
                new TripVehicleInfoModel(populateVehicle.getVehicle().getName()),
                new TripDriverInfoModel(populateUser.getUser().getId(), populateUser.getUser().getName()));

        trip.setName("Trip simulator-" + Generator.generateRandomUUID());

        ArrayList<HEREMapsRouteSection> route = getRoute(source.getGeoLocation(), destination.getGeoLocation(), stopsLocation);


        List<Long> hereResponse = parseHEREMapsSummary(route);
        ArrayList<String> polyline = parseHEREMapsPolyline(route);

        trip.setRoute(polyline);
        trip.setPlannedDriveDistance(hereResponse.get(1));
        trip.setPlannedDriveDurationSeconds(hereResponse.get(2));
    }

    public Trip sendQuery() {
        return tripAPIClient.create(trip);
    }

    public Trip getTrip() {
        return trip;
    }

    // Utils
    private ArrayList<HEREMapsRouteSection> getRoute(ArrayList<Double> source, ArrayList<Double> destination, ArrayList<ArrayList<Double>> stops) {

        ArrayList<Double> stopsLat = new ArrayList<Double>();
        ArrayList<Double> stopsLong = new ArrayList<Double>();

        for (int i = 0; i < stops.size(); i++) {
            stopsLat.add(stops.get(i).get(0));
            stopsLong.add(stops.get(i).get(1));
        }

        return hereAPIClient.getRoute(source.get(0), source.get(1),
                destination.get(0), destination.get(1),
                stopsLat, stopsLong,
                true);
    }

    private List<Long> parseHEREMapsSummary(ArrayList<HEREMapsRouteSection> route) {
        long totalDuration = 0; // Maintained in case necessary later on
        long totalLength = 0;
        long totalBaseDuration = 0;

        for (HEREMapsRouteSection section : route) {
            totalDuration += section.getDuration();
            totalBaseDuration += section.getBaseDuration();
            totalLength += section.getLength();
        }

        return List.of(totalDuration, totalLength, totalBaseDuration);
    }

    private ArrayList<String> parseHEREMapsPolyline(ArrayList<HEREMapsRouteSection> route) {
        ArrayList<String> polyline = new ArrayList<>();

        for (HEREMapsRouteSection section : route) {
            polyline.add(section.getPolyline());
        }
        return polyline;
    }
}