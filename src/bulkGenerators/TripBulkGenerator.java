package bulkGenerators;

import simulation.models.TripModel;
import trip.GeoLocationRepository;
import trip.Trip;
import trip.TripAPIClient;
import trip.TripGenerator;
import trip.subclasses.TripStopRecord;
import utils.CSVParser;
import utils.Generator;
import utils.PolylineEncoderDecoder.LatLngZ;
import vehicle.Vehicle;

import java.util.ArrayList;


/**
 * Contains two static methods to create multiple trips from scratch as well as use an input vehicle type to create a vehicle and later proceed to link it to the trip.
 */
public class TripBulkGenerator {

    private static final GeoLocationRepository geoLocationRepository;

    // Loading the sources, destinations and stops to run the simulation
    static {
        geoLocationRepository = new GeoLocationRepository(
                CSVParser.parseGeoLocation("locationRepository/sources.csv"),
                CSVParser.parseGeoLocation("locationRepository/destinations.csv"),
                CSVParser.parseGeoLocation("locationRepository/stops.csv")
        );
    }


    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private TripBulkGenerator() {
        throw new AssertionError();
    }

    //region Bulk generators
    //---------------------------------------------------------------------------------------

    /**
     * Creates multiple trips and parses them into the data model required for the trip simulation.
     * @param accessTokenUrl HERE maps access token generation internal IoT server instance URL
     * @param accessTokenUsername Username for the admin user of the access token generation internal IoT server
     * @param accessTokenPassword  Password corresponding to the access token username.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param connectorUrl URL (inclusive of the complete path) to the connector. It is found in the connectors' info under the configuration options.
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param requiredTrips Number of vehicles to be made
     * @param noStops Number of stops required per trip
     * @return ArrayList(TripModel): List of the randomly created models (instance) for the simulation
     */
    public static ArrayList<TripModel> bulkCreateTrips(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String baseUrl,
            String connectorUrl,
            String username,
            String password,
            int requiredTrips,
            int noStops
    ) {
        // Initialise the API client
        TripAPIClient tripClient = new TripAPIClient(baseUrl, username, password);

        // Starting the timer
        long startTime = System.nanoTime();


        // Generating a list of unique IDs
        ArrayList<String> uniqueIds = Generator.generateRandomUUID(requiredTrips);

        // Creating the vehicles
        ArrayList<Vehicle> vehicles = VehicleBulkGenerator.bulkCreateVehicles(baseUrl, connectorUrl, username, password, uniqueIds, requiredTrips);

        ArrayList<TripModel> tripModels = new ArrayList<>(requiredTrips);

        // Timer stop 1
        long vehicleTime = System.nanoTime();
        System.out.println("The time taken to create the vehicles, users and the type: " + (vehicleTime - startTime) / 1000000000.0);

        // Creating trips
        for (int i = 0; i < requiredTrips; i++) {
            long tripStartTime = System.nanoTime();

            Trip trip = TripGenerator.randomizedTripFromVehicle(
                    accessTokenUrl, accessTokenUsername, accessTokenPassword, baseUrl, username, password, vehicles.get(i).getName(), uniqueIds.get(i), geoLocationRepository, noStops);


            // Getting and updating the trip ID from the server response
            Trip responseTrip = tripClient.create(trip);
            trip.setId(responseTrip.getId());

            tripModels.add(parseTripToTripModel(trip, vehicles.get(i).getDeviceName(), vehicles.get(i).getDeviceIdentifier()));

            long tripEndTime = System.nanoTime();
            System.out.println("Time taken to create trip: " + (tripEndTime - tripStartTime) / 1000000000.0);
        }

        long endTime = System.nanoTime();
        System.out.println("Total time to create " + requiredTrips + " trips: " + (endTime - startTime) / 1000000000.0);

        return tripModels;
    }


    /**
     * Creates multiple trips from vehicle type and parses them into the data model required for the trip simulation.
     * @param accessTokenUrl HERE maps access token generation internal IoT server instance URL
     * @param accessTokenUsername Username for the admin user of the access token generation internal IoT server
     * @param accessTokenPassword  Password corresponding to the access token username.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param connectorUrl URL (inclusive of the complete path) to the connector. It is found in the connectors' info under the configuration options.
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param vehicleType vehicle type for the creation of vehicles
     * @param requiredTrips Number of vehicles to be made
     * @param noStops Number of stops required per trip
     * @return ArrayList(TripModel): List of the randomly created models (instance) for the simulation
     */
    public static ArrayList<TripModel> bulkCreateTrips(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String baseUrl,
            String connectorUrl,
            String username,
            String password,
            String vehicleType,
            int requiredTrips,
            int noStops
    ) {
        // Initialise the API client
        TripAPIClient tripClient = new TripAPIClient(baseUrl, username, password);

        // Generating a list of unique IDs
        ArrayList<String> uniqueIds = Generator.generateRandomUUID(requiredTrips);

        // Creating the vehicles
        ArrayList<Vehicle> vehicles = VehicleBulkGenerator.bulkCreateVehiclesFromVehicleType(baseUrl, connectorUrl, username, password, vehicleType, uniqueIds, requiredTrips);

        ArrayList<TripModel> tripModels = new ArrayList<>(requiredTrips);

        // Creating trips
        for (int i = 0; i < requiredTrips; i++) {
            Trip trip = TripGenerator.randomizedTripFromVehicle(accessTokenUrl, accessTokenUsername, accessTokenPassword, baseUrl, username, password, vehicles.get(i).getName(), uniqueIds.get(i), geoLocationRepository, noStops);


            // Getting and updating the trip ID from the server response
            Trip responseTrip = tripClient.create(trip);

            trip.setId(responseTrip.getId());

            tripModels.add(parseTripToTripModel(trip, vehicles.get(i).getDeviceName(), vehicles.get(i).getDeviceIdentifier()));
        }

        return tripModels;
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    /**
     * Parses trip and device information into the model for the simulation
     * @param trip Randomly generated trip
     * @param deviceName Device ID associated with the vehicle linked to the trip
     * @param deviceIdentifier Device identifier associated with the vehicle linked to the trip
     * @return TripModel: model for the simulation based on the trip
     */
    private static TripModel parseTripToTripModel(Trip trip, String deviceName, String deviceIdentifier) {
        LatLngZ source = new LatLngZ(trip.getSource().getLatitude(), trip.getSource().getLongitude());
        LatLngZ destination = new LatLngZ(trip.getDestination().getLatitude(), trip.getDestination().getLongitude());

        ArrayList<LatLngZ> stops = new ArrayList<>();
        for (TripStopRecord stop : trip.getStops()) {
            stops.add(new LatLngZ(stop.getLatitude(), stop.getLongitude()));

        }

        return new TripModel(
                trip.getId(),
                trip.getDriver().getLoginId(),
                trip.getVehicle().getName(),
                deviceName,
                deviceIdentifier,
                source,
                destination,
                stops,
                trip.getRoute()
        );
    }

    //endregion

}
