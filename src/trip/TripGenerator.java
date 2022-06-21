package trip;

import hereMaps.HEREMapsAPIClient;
import hereMaps.HEREMapsRouteSection;
import static hereMaps.HEREMapsParsers.*;

import trip.subclasses.*;
import user.User;
import user.UserAPIClient;
import user.UserGenerator;

import utils.Generator;
import vehicle.Vehicle;
import vehicle.OBD2VehicleGenerator;

import utils.CSVParser;
import vehicle.VehicleAPIClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // To remove upon fixing the proxy issue

public class TripGenerator {
    private static final GeoLocationRepository geoLocationRepository;

    private static final HEREMapsAPIClient hereAPIClient = new HEREMapsAPIClient();

    static {
        // Loading the geolocations for the source, destinations and the stops
        geoLocationRepository = new GeoLocationRepository(
                CSVParser.parse("sources.csv"),
                CSVParser.parse("destinations.csv"),
                CSVParser.parse("stops.csv")
        );
    }

    public static Trip randomizedTripFromVehicleDriver(
            String vehicleName,
            String driverLoginId,
            int noStops
    ) {
        return tripCreationHelper(
                new TripVehicleInfoModel(vehicleName),
                new TripDriverInfoModel(driverLoginId),
                noStops
        );
    }

    public static Trip randomizedTripFromVehicle(
            String baseUrl,
            String username,
            String password,
            String vehicleName,
            int noStops
    ) {
        UserAPIClient userClient = new UserAPIClient(baseUrl, username, password);

        User user = UserGenerator.randomizedDefaultDriverUser(baseUrl, username, password);
        userClient.create(user);

        return tripCreationHelper(
                new TripVehicleInfoModel(vehicleName),
                new TripDriverInfoModel(user.getId(), user.getName()),
                noStops
        );
    }

    public static Trip randomizedTripFromVehicleType(
            String baseUrl,
            String username,
            String password,
            String vehicleTypeId,
            String deviceId, // It's better to create and access deviceIds by bulk instead of creating one at a time so better to take it as an argument
            int noStops
    ) {
        VehicleAPIClient vehicleClient = new VehicleAPIClient(baseUrl, username, password);
        UserAPIClient userClient = new UserAPIClient(baseUrl, username, password);

        Vehicle vehicle = OBD2VehicleGenerator.randomizedVehicle(baseUrl, username, password, vehicleTypeId, deviceId);
        vehicleClient.create(vehicle);

        User user = UserGenerator.randomizedDefaultDriverUser(baseUrl, username, password);
        userClient.create(user);

        return tripCreationHelper(
                new TripVehicleInfoModel(vehicle.getName()),
                new TripDriverInfoModel(user.getId(), user.getName()),
                noStops
        );

    }

    public static Trip randomizedTrip(
            String baseUrl,
            String username,
            String password,
            String deviceId,
            int noStops
    ) {
        VehicleAPIClient vehicleClient = new VehicleAPIClient(baseUrl, username, password);
        UserAPIClient userClient = new UserAPIClient(baseUrl, username, password);

        Vehicle vehicle = OBD2VehicleGenerator.randomizedVehicle(baseUrl, username, password, deviceId);
        vehicleClient.create(vehicle);

        User user = UserGenerator.randomizedDefaultDriverUser(baseUrl, username, password);
        userClient.create(user);

        return tripCreationHelper(
                new TripVehicleInfoModel(vehicle.getName()),
                new TripDriverInfoModel(user.getId(), user.getName()),
                noStops
        );
    }



    // Utils
    private static Trip tripCreationHelper(
            TripVehicleInfoModel vehicleInfoModel,
            TripDriverInfoModel driverInfoModel,
            int noStops
    ) {
        // Picking out the source, destination and the stop geolocations
        TripStopRecord source = new TripStopRecord(geoLocationRepository.getRandomSource());
        TripStopRecord destination = new TripStopRecord(geoLocationRepository.getRandomDestination());

        ArrayList<TripStopRecord> stopRecords = new ArrayList<>();
        ArrayList<ArrayList<Double>> stops = geoLocationRepository.getRandomStop(noStops);

        for (ArrayList<Double> stop : stops) {
            stopRecords.add(new TripStopRecord(stop));
        }


        Trip trip = new Trip(
                source,
                destination,
                stopRecords,
                vehicleInfoModel,
                driverInfoModel
        );

        trip.setName("Trip simulator-" + Generator.generateRandomUUID());

        // TODO: 21/06/2022 Remove this pause after fixing proxy
        pause();

        ArrayList<HEREMapsRouteSection> routeSections = getRoute(
                source.getGeoLocation(),
                destination.getGeoLocation(),
                stops );

        List<Long> hereResponse = parseHEREMapsSummary(routeSections);
        ArrayList<String> polyline = parseHEREMapsPolyline(routeSections);

        trip.setRoute(polyline);
        trip.setPlannedDriveDistance(hereResponse.get(1));
        trip.setPlannedDriveDurationSeconds(hereResponse.get(2));
        pause();

        return trip;
    }


    private static ArrayList<HEREMapsRouteSection> getRoute(
            ArrayList<Double> source,
            ArrayList<Double> destination,
            ArrayList<ArrayList<Double>> stops) {

        ArrayList<Double> stopsLat = new ArrayList<>();
        ArrayList<Double> stopsLong = new ArrayList<>();

        for (ArrayList<Double> stop : stops) {
            stopsLat.add(stop.get(0));
            stopsLong.add(stop.get(1));
        }

        return hereAPIClient.getRoute(source.get(0), source.get(1),
                destination.get(0), destination.get(1),
                stopsLat, stopsLong,
                true);
    }

    private static void pause() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Run has paused! Finish the required tasks and click ENTER");
        reader.next();
        reader.close();
    }
}
