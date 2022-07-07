package bulkGenerators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import equipment.EquipmentDeviceGenerator;
import payload.equipment.Payload;
import equipment.Equipment;
import equipment.shipitem.ShipItem;
import equipment.shipunit.ShipUnit;
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

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");


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

    //region Bulk generator with equipment
    //---------------------------------------------------------------------------------------

    /**
     * Creates multiple trips and parses them into the data model required for the trip simulation.
     * @param accessTokenUrl HERE maps access token generation internal IoT server instance URL
     * @param accessTokenUsername Username for the admin user of the access token generation internal IoT server
     * @param accessTokenPassword  Password corresponding to the access token username.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param vehicleConnectorUrl URL (inclusive of the complete path) to the connector. It is found in the connectors' info under the configuration options
     * @param equipmentConnectorUrl Equipment connector to create devices for the equipments
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param organizationId organization in which the trip is to be created
     * @param requiredTrips Number of vehicles to be made
     * @param requiredEquipments Number of equipment required per trip
     * @param shipUnitsPerEquipment Number of ship units to be created in one equipment
     * @param shipItemsPerShipUnit Number of ship items to be created in one ship unit
     * @param noStops Number of stops required per trip
     * @return ArrayList(TripModel): List of the randomly created models (instance) for the simulation
     */
    public static ArrayList<TripModel> bulkCreateTrips(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String baseUrl,
            String vehicleConnectorUrl,
            String equipmentConnectorUrl,
            String username,
            String password,
            String organizationId,
            int requiredTrips,
            int requiredEquipments,
            int shipUnitsPerEquipment,
            int shipItemsPerShipUnit,
            int noStops
    ) {
        // Initialise the API client
        TripAPIClient tripClient = new TripAPIClient(baseUrl, username, password);

        // Starting the timer
        long startTime = System.nanoTime();


        // Generating a list of unique IDs
        ArrayList<String> uniqueIds = Generator.generateRandomUUID(requiredTrips);

        // Creating the vehicles
        ArrayList<Vehicle> vehicles = VehicleBulkGenerator.bulkCreateVehicles(baseUrl, vehicleConnectorUrl, username, password, uniqueIds, requiredTrips);

        ArrayList<TripModel> tripModels = new ArrayList<>(requiredTrips);

        // Timer stop 1
        long vehicleTime = System.nanoTime();
        IOT_API_LOGGER.info("The time taken to create {} vehicles, users and the type: {}s", requiredTrips, (vehicleTime - startTime) / 1000000000.0);
        // System.out.println("The time taken to create the vehicles, users and the type: " + (vehicleTime - startTime) / 1000000000.0);

        // Creating trips
        for (int i = 0; i < requiredTrips; i++) {
            long tripStartTime = System.nanoTime();

            try {
                Trip trip = TripGenerator.randomizedTripFromVehicleWithEquipment(
                        accessTokenUrl, accessTokenUsername, accessTokenPassword, baseUrl, equipmentConnectorUrl, username, password, vehicles.get(i).getName(), uniqueIds.get(i), geoLocationRepository, organizationId, requiredEquipments, shipUnitsPerEquipment, shipItemsPerShipUnit, noStops);

                // Getting and updating the trip ID from the server response
                Trip responseTrip = tripClient.create(trip);
                trip.setId(responseTrip.getId());
                tripModels.add(parseTripToTripModel(trip, vehicles.get(i).getDeviceName(), vehicles.get(i).getDeviceIdentifier()));

                // Logging the trip creation
                long tripEndTime = System.nanoTime();
                IOT_API_LOGGER.info("Time taken to create trip {} : {}s", trip.getId(), (tripEndTime - tripStartTime) / 1000000000.0);
                 // System.out.println("Time taken to create trip {} : " + (tripEndTime - tripStartTime) / 1000000000.0);
            } catch (Exception e) {
                i--;
                IOT_API_LOGGER.warn("Trip is not created", e);
            }
        }

        long endTime = System.nanoTime();
        IOT_API_LOGGER.info("Total time to create {} trips: {}s", requiredTrips, (endTime - startTime) / 1000000000.0);
        // System.out.println("Total time to create " + requiredTrips + " trips: " + (endTime - startTime) / 1000000000.0);

        return tripModels;
    }


    /**
     * Creates multiple trips from vehicle type and parses them into the data model required for the trip simulation.
     * @param accessTokenUrl HERE maps access token generation internal IoT server instance URL
     * @param accessTokenUsername Username for the admin user of the access token generation internal IoT server
     * @param accessTokenPassword  Password corresponding to the access token username.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param vehicleConnectorUrl
     *URL (inclusive of the complete path) to the connector. It is found in the connectors' info under the configuration options.
     * @param equipmentConnectorUrl Equipment connector to create devices for the equipments
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     * @param vehicleType vehicle type for the creation of vehicles
     * @param organizationId organization in which the trip is to be created
     * @param requiredTrips Number of vehicles to be made
     * @param requiredEquipments Number of equipment required per trip
     * @param shipUnitsPerEquipment Number of ship units to be created in one equipment
     * @param shipItemsPerShipUnit Number of ship items to be created in one ship unit
     * @param noStops Number of stops required per trip
     * @return ArrayList(TripModel): List of the randomly created models (instance) for the simulation
     */
    public static ArrayList<TripModel> bulkCreateTrips(
            String accessTokenUrl,
            String accessTokenUsername,
            String accessTokenPassword,
            String baseUrl,
            String vehicleConnectorUrl,
            String equipmentConnectorUrl,
            String username,
            String password,
            String vehicleType,
            String organizationId,
            int requiredTrips,
            int requiredEquipments,
            int shipUnitsPerEquipment,
            int shipItemsPerShipUnit,
            int noStops
    ) {
        // Initialise the API client
        TripAPIClient tripClient = new TripAPIClient(baseUrl, username, password);

        // Generating a list of unique IDs
        ArrayList<String> uniqueIds = Generator.generateRandomUUID(requiredTrips);

        // Creating the vehicles
        ArrayList<Vehicle> vehicles = VehicleBulkGenerator.bulkCreateVehiclesFromVehicleType(baseUrl, vehicleConnectorUrl, username, password, vehicleType, uniqueIds, requiredTrips);

        ArrayList<TripModel> tripModels = new ArrayList<>(requiredTrips);

        // Creating trips
        for (int i = 0; i < requiredTrips; i++) {
            Trip trip = TripGenerator.randomizedTripFromVehicleWithEquipment(accessTokenUrl, accessTokenUsername, accessTokenPassword, equipmentConnectorUrl, baseUrl, username, password, vehicles.get(i).getName(), uniqueIds.get(i), geoLocationRepository, organizationId, requiredEquipments, shipUnitsPerEquipment, shipItemsPerShipUnit, noStops);


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
        // Parsing the source and destination locations
        LatLngZ source = new LatLngZ(trip.getSource().getLatitude(), trip.getSource().getLongitude());
        LatLngZ destination = new LatLngZ(trip.getDestination().getLatitude(), trip.getDestination().getLongitude());

        // Parsing the stops
        ArrayList<LatLngZ> stops = new ArrayList<>();
        for (TripStopRecord stop : trip.getStops()) {
            stops.add(new LatLngZ(stop.getLatitude(), stop.getLongitude()));
        }

        // Parsing equipments into equipment payloads
        ArrayList<Payload> equipmentPayloads = new ArrayList<>();
        ArrayList<Payload> shipUnitPayloads = new ArrayList<>();
        ArrayList<Payload> shipItemPayloads = new ArrayList<>();

        for (Equipment equipment : trip.getEquipments()) {
            // Adding the equipment payloads
            equipmentPayloads.add(
                    EquipmentDeviceGenerator.populateEquipmentFromDeviceName(
                            equipment.getTrackers().get(0).getValue(),
                            equipment.getPickupStopSequence(),
                            equipment .getDropStopSequence()
                    )
            );

            for (ShipUnit shipUnit : equipment.getShipUnits()) {
                // Adding the ship unit payloads
                shipUnitPayloads.add(
                        EquipmentDeviceGenerator.populateEquipmentFromDeviceName(
                                shipUnit.getTrackers().get(0).getValue(),
                                shipUnit.getPickupStopSequence(),
                                shipUnit.getDropStopSequence()
                        )
                );

                for (ShipItem shipItem : shipUnit.getShipItems()) {
                    //Adding the ship item payloads
                    shipItemPayloads.add(
                            EquipmentDeviceGenerator.populateEquipmentFromDeviceName(
                                    shipItem.getTrackers().get(0).getValue(),
                                    shipItem.getPickupStopSequence(),
                                    shipItem.getDropStopSequence()
                            )
                    );
                }
            }
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
                trip.getRoute(),
                equipmentPayloads,
                shipUnitPayloads,
                shipItemPayloads
        );
    }

    //endregion

}
