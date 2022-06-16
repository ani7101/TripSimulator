package trip;

import hereMaps.HEREMapsRouteSection;
import hereMaps.HEREMapsAPIClient;
import trip.tripSubClasses.TripDriverInfoModel;
import trip.tripSubClasses.TripStopRecord;
import trip.tripSubClasses.TripVehicleInfoModel;
import user.GeneratedUser;
import utils.*;
import vehicle.GeneratedOBD2Vehicle;
import vehicleType.GeneratedOBD2VehicleType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for creating a trip and indirectly accounting for an entity in the simulation.
 * Types of constructors:
 *  1. Trip class is given as an argument and is taken as support
 *  2. vehicle name (which is unique) and driver details are provided and the remaining data is generated
 *  3. vehicle name is provided and the remaining is generated (driver is ignored) (WILL BE DEPRECIATED SOON!)
 *  4. Nothing is provided and using GeneratedUser and GeneratedOBD2Vehicle, a vehicle and a user are created and used
 *
 *  As a pre-requisite for creating a device
 */
public class GeneratedTrip {
    private HEREMapsAPIClient hereAPIClient;

    private TripAPIClient tripAPIClient;

    private TripRepository repository;

    private Trip trip;

    private GeneratedOBD2Vehicle generatedOBD2Vehicle;

    private GeneratedUser generatedUser;

    private GeneratedOBD2VehicleType populateType;


    public GeneratedTrip(String baseUrl, String username, String password,
                         Trip trip) {

        tripAPIClient = new TripAPIClient(baseUrl, username, password);
        hereAPIClient = new HEREMapsAPIClient();
        this.trip = trip;
    }

    public GeneratedTrip(String baseUrl, String username, String password,
                         TripRepository repository,
                         String vehicleName,
                         String driverLoginId) {

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
                new TripDriverInfoModel(driverLoginId)
        );

        trip.setName("Trip simulator-" + Generator.generateRandomUUID());

        ArrayList<HEREMapsRouteSection> route = getRoute(source.getGeoLocation(), destination.getGeoLocation(), stopsLocation);


        List<Long> hereResponse = parseHEREMapsSummary(route);
        ArrayList<String> polyline = parseHEREMapsPolyline(route);

        trip.setRoute(polyline);
        trip.setPlannedDriveDistance(hereResponse.get(1));
        trip.setPlannedDriveDurationSeconds(hereResponse.get(2));
    }

    public GeneratedTrip(String baseUrl, String username, String password,
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

    public GeneratedTrip(String baseUrl, String username, String password,
                         TripRepository repository, String vehicleTypeId, boolean populate) {

        // populate argument is a dummy to distinguish it from the vehicleName variant

        tripAPIClient = new TripAPIClient(baseUrl, username, password);
        generatedOBD2Vehicle = new GeneratedOBD2Vehicle(baseUrl, username, password, vehicleTypeId);
        generatedUser = new GeneratedUser(baseUrl, username, password);

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
                new TripVehicleInfoModel(generatedOBD2Vehicle.getVehicle().getName()),
                new TripDriverInfoModel(generatedUser.getUser().getName()));

        trip.setName("Trip simulator-" + Generator.generateRandomUUID());

        ArrayList<HEREMapsRouteSection> route = getRoute(source.getGeoLocation(), destination.getGeoLocation(), stopsLocation);


        List<Long> hereResponse = parseHEREMapsSummary(route);
        ArrayList<String> polyline = parseHEREMapsPolyline(route);

        trip.setRoute(polyline);
        trip.setPlannedDriveDistance(hereResponse.get(1));
        trip.setPlannedDriveDurationSeconds(hereResponse.get(2));
    }

    public GeneratedTrip(String baseUrl, String username, String password,
                         TripRepository repository) {

        // populate argument is a dummy to distinguish it from the vehicleName variant

        tripAPIClient = new TripAPIClient(baseUrl, username, password);
        populateType = new GeneratedOBD2VehicleType(baseUrl, username, password);
        generatedOBD2Vehicle = new GeneratedOBD2Vehicle(baseUrl, username, password, populateType.getType().getId());
        generatedUser = new GeneratedUser(baseUrl, username, password);

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
                new TripVehicleInfoModel(generatedOBD2Vehicle.getVehicle().getName()),
                new TripDriverInfoModel(generatedUser.getUser().getId(), generatedUser.getUser().getName()));

        trip.setName("Trip simulator-" + Generator.generateRandomUUID());

        ArrayList<HEREMapsRouteSection> route = getRoute(source.getGeoLocation(), destination.getGeoLocation(), stopsLocation);


        List<Long> hereResponse = parseHEREMapsSummary(route);
        ArrayList<String> polyline = parseHEREMapsPolyline(route);

        trip.setRoute(polyline);
        trip.setPlannedDriveDistance(hereResponse.get(1));
        trip.setPlannedDriveDurationSeconds(hereResponse.get(2));
    }

    public Trip sendQuery() {
        generatedUser.sendQuery();
        generatedOBD2Vehicle.sendQuery();
        Trip response = tripAPIClient.create(trip);
        trip.setId(response.getId());

        return response;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
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