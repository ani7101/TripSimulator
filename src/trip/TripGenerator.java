package trip;

import hereMaps.HEREMapsAPIClient;
import hereMaps.HEREMapsRouteSection;

import trip.subclasses.*;
import user.User;
import user.UserAPIClient;
import user.UserGenerator;

import vehicle.Vehicle;
import vehicle.OBD2VehicleGenerator;

import utils.CSVParser;
import vehicle.VehicleAPIClient;

import java.util.ArrayList;
import java.util.List;

import static hereMaps.HEREMapsParsers.parseHEREMapsPolyline;
import static hereMaps.HEREMapsParsers.parseHEREMapsSummary;

public class TripGenerator {
    private static final GeoLocationRepository geoLocationRepository;

    static {
        // Loading the geolocations for the source, destinations and the stops
        geoLocationRepository = new GeoLocationRepository(
                CSVParser.parseGeoLocation("sources.csv"),
                CSVParser.parseGeoLocation("destinations.csv"),
                CSVParser.parseGeoLocation("stops.csv")
        );
    }

    public static Trip randomizedTripFromVehicleDriver(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String vehicleName,
            String driverLoginId,
            String uniqueId,
            int noStops
    ) {
        HEREMapsAPIClient hereMapsClient = new HEREMapsAPIClient(accessTokenUrl, accessTokenUsername, accessTokenPassword);

        return tripCreationHelper(
                hereMapsClient,
                new TripVehicleInfoModel(vehicleName),
                new TripDriverInfoModel(driverLoginId),
                noStops,
                uniqueId
        );
    }

    public static Trip randomizedTripFromVehicle(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String baseUrl,
            String username,
            String password,
            String vehicleName,
            String uniqueId,
            int noStops
    ) {
        HEREMapsAPIClient hereMapsClient = new HEREMapsAPIClient(accessTokenUrl, accessTokenUsername, accessTokenPassword);

        UserAPIClient userClient = new UserAPIClient(baseUrl, username, password);

        User user = UserGenerator.randomizedDefaultDriverUser(baseUrl, username, password);

        user = userClient.create(user); // Creates the user in the IoT server

        TripDriverInfoModel driver = new TripDriverInfoModel(user.getName());

        return tripCreationHelper(
                hereMapsClient,
                new TripVehicleInfoModel(vehicleName),
                driver,
                noStops,
                uniqueId
        );
    }

    public static Trip randomizedTripFromVehicleType(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String baseUrl,
            String username,
            String password,
            String vehicleTypeId,
            String deviceId, // It's better to create and access deviceIds by bulk instead of creating one at a time so better to take it as an argument
            String uniqueId,
            int noStops
    ) {
        HEREMapsAPIClient hereMapsClient = new HEREMapsAPIClient(accessTokenUrl, accessTokenUsername, accessTokenPassword);

        VehicleAPIClient vehicleClient = new VehicleAPIClient(baseUrl, username, password);
        UserAPIClient userClient = new UserAPIClient(baseUrl, username, password);

        Vehicle vehicle = OBD2VehicleGenerator.randomizedVehicle(baseUrl, username, password, vehicleTypeId, deviceId);
        vehicleClient.create(vehicle);

        User user = UserGenerator.randomizedDefaultDriverUser(baseUrl, username, password);

        User responseUser = userClient.create(user); // Creates the user in the IoT server
        user.setId(responseUser.getId());

        return tripCreationHelper(
                hereMapsClient,
                new TripVehicleInfoModel(vehicle.getName()),
                new TripDriverInfoModel(user.getId()),
                noStops,
                uniqueId
        );

    }

    public static Trip randomizedTrip(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String baseUrl,
            String username,
            String password,
            String deviceId,
            String uniqueId,
            int noStops
    ) {
        HEREMapsAPIClient hereMapsClient = new HEREMapsAPIClient(accessTokenUrl, accessTokenUsername, accessTokenPassword);

        VehicleAPIClient vehicleClient = new VehicleAPIClient(baseUrl, username, password);
        UserAPIClient userClient = new UserAPIClient(baseUrl, username, password);

        Vehicle vehicle = OBD2VehicleGenerator.randomizedVehicle(baseUrl, username, password, deviceId, uniqueId);
        vehicleClient.create(vehicle);

        User user = UserGenerator.randomizedDefaultDriverUser(baseUrl, username, password);

        User responseUser = userClient.create(user); // Creates the user in the IoT server
        user.setId(responseUser.getId());

        return tripCreationHelper(
                hereMapsClient,
                new TripVehicleInfoModel(vehicle.getName()),
                new TripDriverInfoModel(user.getId()),
                noStops,
                uniqueId
        );
    }



    // Utils
    private static Trip tripCreationHelper(
            HEREMapsAPIClient hereMapsClient,
            TripVehicleInfoModel vehicleInfoModel,
            TripDriverInfoModel driverInfoModel,
            int noStops,
            String uniqueId
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

        trip.setName("Trip simulator-" + uniqueId);

        ArrayList<HEREMapsRouteSection> routeSections = getRoute(
                hereMapsClient,
                source.getLatitude(), source.getLongitude(),
                destination.getLatitude(), destination.getLongitude(),
                stops );

        List<Long> hereResponse = parseHEREMapsSummary(routeSections);
        ArrayList<String> polyline = parseHEREMapsPolyline(routeSections);

        trip.setRoute(polyline);
        trip.setPlannedDriveDistance(hereResponse.get(1));
        trip.setPlannedDriveDurationSeconds(hereResponse.get(2));
        return trip;
    }


    private static ArrayList<HEREMapsRouteSection> getRoute(
            HEREMapsAPIClient hereMapsClient,
            double sourceLat, double sourceLng,
            double destinationLat, double destinationLng,
            ArrayList<ArrayList<Double>> stops) {

        ArrayList<Double> stopsLat = new ArrayList<>();
        ArrayList<Double> stopsLong = new ArrayList<>();

        for (ArrayList<Double> stop : stops) {
            stopsLat.add(stop.get(0));
            stopsLong.add(stop.get(1));
        }

        return hereMapsClient.getRoute(sourceLat, sourceLng,
                destinationLat, destinationLng,
                stopsLat, stopsLong,
                true);
    }

}
