package trip;

import hereMaps.HereMapsAPIClient;
import hereMaps.HereMapsRouteSection;

import hereMaps.accessToken.AccessToken;
import trip.subclasses.*;
import user.User;
import user.UserAPIClient;
import user.UserGenerator;

import vehicle.Vehicle;
import vehicle.OBD2VehicleGenerator;

import vehicle.VehicleAPIClient;

import java.util.ArrayList;
import java.util.List;

import static hereMaps.HereMapsParsers.parseHEREMapsPolyline;
import static hereMaps.HereMapsParsers.parseHEREMapsSummary;

/**
 * Contains static methods to generate randomized trips with corresponding drivers and vehicles.
 * It can create the drivers as well as vehicles and later link it to a vehicle or directly link them to the input.
 * <br>
 * <br>
 * Static methods:
 * <ul>
 *  <li><b>randomizedTripFromVehicleDriver</b> - Generates a trip and links it to the input vehicle & input driver</li>
 *  <li><b>randomizedTripFromVehicle</b> - Creates a driver and finally generates a trip with the created driver & input vehicle</li>
 *  <li><b>randomizedTripFromVehicleType</b> - Starts from the vehicle type given and creates a driver and vehicle before linking to the trip</li>
 *  <li><b>randomizedTrip</b> - Generates a trip from scratch by creating all required components forehand</li>
 * </ul>
 */
public class TripGenerator {

    //region Randomized generators
    //---------------------------------------------------------------------------------------

    /**
     * Generates a trip and links it to the input vehicle & input driver
     * @param accessTokenUrl HERE maps access token generation internal IoT server instance URL
     * @param accessTokenUsername Username for the admin user of the access token generation internal IoT server
     * @param accessTokenPassword  Password corresponding to the access token username.
     * @param vehicleName Name of the vehicle to be linked to the trip
     * @param driverLoginId Driver in charge of the trip
     * @param uniqueId Unique ID for naming the trip (as per NamingConvention.MD)
     * @param noStops Required number of stops to be randomized
     * @return Trip: randomly generated trip
     */
    public static Trip randomizedTripFromVehicleDriver(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String vehicleName,
            String driverLoginId,
            String uniqueId,
            GeoLocationRepository geoLocationRepository,
            int noStops
    ) {

        return tripCreationHelper(
                accessTokenUrl,
                accessTokenUsername,
                accessTokenPassword,
                vehicleName,
                driverLoginId,
                uniqueId,
                geoLocationRepository,
                noStops
        );
    }


    /**
     * Creates a driver and finally generates a trip with the created driver & input vehicle
     * @param accessTokenUrl HERE maps access token generation internal IoT server instance URL
     * @param accessTokenUsername Username for the admin user of the access token generation internal IoT server
     * @param accessTokenPassword  Password corresponding to the access token username.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param vehicleName Name of the vehicle to be linked to the trip
     * @param uniqueId Unique ID for naming the trip (as per NamingConvention.MD)
     * @param noStops Required number of stops to be randomized
     * @return Trip: randomly generated trip
     */
    public static Trip randomizedTripFromVehicle(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String baseUrl,
            String username,
            String password,
            String vehicleName,
            String uniqueId,
            GeoLocationRepository geoLocationRepository,
            String organizationId,
            int noStops
    ) {

        UserAPIClient userClient = new UserAPIClient(baseUrl, username, password);

        User user = UserGenerator.randomizedDriverUser(organizationId);

        user = userClient.create(user); // Creates the user in the IoT server

        return tripCreationHelper(
                accessTokenUrl,
                accessTokenUsername,
                accessTokenPassword,
                vehicleName,
                user.getName(),
                uniqueId,
                geoLocationRepository,
                noStops
                );
    }

    /**
     * Starts from the vehicle type given and creates a driver and vehicle before linking to the trip
     * @param accessTokenUrl HERE maps access token generation internal IoT server instance URL
     * @param accessTokenUsername Username for the admin user of the access token generation internal IoT server
     * @param accessTokenPassword  Password corresponding to the access token username.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param vehicleTypeId ID of the vehicle type in which the vehicle will be created
     * @param deviceId ID to be linked to the vehicle
     * @param uniqueId Unique ID for naming the trip (as per NamingConvention.MD)
     * @param noStops Required number of stops to be randomized
     * @return Trip: randomly generated trip
     */
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
            GeoLocationRepository geoLocationRepository,
            String organizationId,
            int noStops
    ) {

        VehicleAPIClient vehicleClient = new VehicleAPIClient(baseUrl, username, password);
        UserAPIClient userClient = new UserAPIClient(baseUrl, username, password);

        Vehicle vehicle = OBD2VehicleGenerator.randomizedVehicle(baseUrl, username, password, vehicleTypeId, deviceId);
        vehicleClient.create(vehicle);

        User user = UserGenerator.randomizedDriverUser(organizationId);

        User responseUser = userClient.create(user); // Creates the user in the IoT server
        user.setId(responseUser.getId());

        return tripCreationHelper(
                accessTokenUrl,
                accessTokenUsername,
                accessTokenPassword,
                vehicle.getName(),
                user.getId(),
                uniqueId,
                geoLocationRepository,
                noStops
        );

    }


    /**
     * Generates a trip from scratch by creating all required components forehand
     * @param accessTokenUrl HERE maps access token generation internal IoT server instance URL
     * @param accessTokenUsername Username for the admin user of the access token generation internal IoT server
     * @param accessTokenPassword  Password corresponding to the access token username.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param deviceId ID to be linked to the vehicle
     * @param uniqueId Unique ID for naming the trip (as per NamingConvention.MD)
     * @param noStops Required number of stops to be randomized
     * @return Trip: randomly generated trip
     */
    public static Trip randomizedTrip(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String baseUrl,
            String username,
            String password,
            String deviceId,
            String uniqueId,
            GeoLocationRepository geoLocationRepository,
            String organizationId,
            int noStops
    ) {

        VehicleAPIClient vehicleClient = new VehicleAPIClient(baseUrl, username, password);
        UserAPIClient userClient = new UserAPIClient(baseUrl, username, password);

        Vehicle vehicle = OBD2VehicleGenerator.randomizedVehicle(baseUrl, username, password, deviceId, uniqueId);
        vehicleClient.create(vehicle);

        User user = UserGenerator.randomizedDriverUser(organizationId);

        User responseUser = userClient.create(user); // Creates the user in the IoT server
        user.setId(responseUser.getId());

        return tripCreationHelper(
                accessTokenUrl,
                accessTokenUsername,
                accessTokenPassword,
                vehicle.getName(),
                user.getId(),
                uniqueId,
                geoLocationRepository,
                noStops
        );
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    /**
     * Helper function to create the Trip instance with the route taken from HERE maps and other required information.
     * It also uses a randomizer to pick the source, destination and the stops from the repository
     * @param accessTokenUrl HERE maps access token generation internal IoT server instance URL
     * @param accessTokenUsername Username for the admin user of the access token generation internal IoT server
     * @param accessTokenPassword Password corresponding to the access token username.
     * @param vehicleName Vehicle name (identifier) to be linked to the trip
     * @param driverId driver to be linked to the trip
     * @param uniqueId Unique ID for naming the trip (as per NamingConvention.MD)
     * @param noStops Required number of stops to be randomized
     * @return Trip: randomly generated trip
     */
    private static Trip tripCreationHelper(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String vehicleName,
            String driverId,
            String uniqueId,
            GeoLocationRepository geoLocationRepository,
            int noStops
    ) {
        // Generating access token
        AccessToken accessToken = new AccessToken(accessTokenUrl, accessTokenUsername, accessTokenPassword);

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
                new TripVehicleInfoModel(vehicleName),
                new TripDriverInfoModel(driverId)
                );

        trip.setName("simulation-trip-" + uniqueId);

        ArrayList<HereMapsRouteSection> routeSections = getRoute(
                accessToken.get(),
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


    /**
     * Retrieves the route from the HERE Maps API and returns it
     * @param sourceLat Latitude of the initial point
     * @param sourceLng Longitude of the initial point
     * @param destinationLat Latitude of the final point
     * @param destinationLng Longitude of the final point
     * @param stops List of all the stop points
     * @return HereMapsRouteSection:
     */
    private static ArrayList<HereMapsRouteSection> getRoute(
            String accessToken,
            double sourceLat, double sourceLng,
            double destinationLat, double destinationLng,
            ArrayList<ArrayList<Double>> stops) {

        ArrayList<Double> stopsLat = new ArrayList<>();
        ArrayList<Double> stopsLong = new ArrayList<>();

        for (ArrayList<Double> stop : stops) {
            stopsLat.add(stop.get(0));
            stopsLong.add(stop.get(1));
        }

        return HereMapsAPIClient.getRoute(sourceLat, sourceLng,
                destinationLat, destinationLng,
                stopsLat, stopsLong, accessToken);
    }

    //endregion

}
