package simulation;

import bulkGenerators.TripBulkGenerator;

import device.DeviceAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payload.equipment.Payload;
import simulation.models.TripInstance;
import simulation.models.TripModel;

import trip.TripAPIClient;
import utils.CSVParser;
import utils.CredentialManager;
import vehicle.Vehicle;
import vehicle.VehicleAPIClient;
import vehicleType.VehicleTypeAPIClient;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Overhead class to simulate multiple trip instances on multiple threads
 */
public class TripSimulator {

    //region Global constants

    private static final int NO_STOPS = 1;

    public static final int EQUIPMENT_PER_TRIP = 1;

    public static final int SHIP_UNITS_PER_EQUIPMENT = 2;

    public static final int SHIP_ITEMS_PER_SHIP_UNIT = 2;

    private final int MAX_INPUT_WAIT_TIME = 120;

    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    private final ArrayList<TripInstance> instances;

    Map<String, String> credentials = new HashMap<>();

    private final String organizationId;

    private final int requiredInstances;

    private final int reportInterval;

    private static final Logger SIMULATION_LOGGER = LoggerFactory.getLogger("simulation");


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public TripSimulator(ArrayList<TripModel> tripModels, int reportInterval, double defaultVehicleSpeed) {
        this(tripModels, reportInterval, defaultVehicleSpeed, "ORA_DEFAULT_ORG");
    }

    public TripSimulator(ArrayList<TripModel> tripModels, int reportInterval, double defaultVehicleSpeed, String organizationId) {
        this.requiredInstances = tripModels.size();
        this.reportInterval = reportInterval;
        this.organizationId = organizationId;

        instances = new ArrayList<>(requiredInstances);

        // Loading the credentials for the IoT server instance
        loadCredentials();

        for (TripModel tripModel : tripModels) {
            instances.add(new TripInstance(tripModel, reportInterval, defaultVehicleSpeed, credentials.get("vehicleConnectorUrl"), credentials.get("equipmentConnectorUrl"), credentials.get("username"), credentials.get("password")));
        }
    }

    public TripSimulator(int requiredInstances, int reportInterval, double defaultVehicleSpeed) {
        this(requiredInstances, reportInterval, defaultVehicleSpeed, "ORA_DEFAULT_ORG");
    }

    public TripSimulator(int requiredInstances, int reportInterval, double defaultVehicleSpeed, String organizationId) {

        // Initialization of class variables
        this.requiredInstances = requiredInstances;
        this.reportInterval = reportInterval;
        this.organizationId = organizationId;

        instances = new ArrayList<>(requiredInstances);

        // Loading the credentials for the IoT server instance
        loadCredentials();

        initializeTrips(defaultVehicleSpeed);
    }


    //endregion
    //region Simulation
    //---------------------------------------------------------------------------------------

    /**
     * Main simulation loop run for many instances each run in a different thread
     */
    public void run() {

        setVehicleSpeeds();

        SIMULATION_LOGGER.info("Started simulation");
        for (TripInstance instance : instances) {
            instance.start();
        }

        System.out.println("Should the entities be delete on the server?");

        Scanner scan = new Scanner(System.in);
        boolean toClean = scan.nextBoolean();

        if (toClean) {
            cleanUp();
        }

    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    /**
     * Obtains the credentials stored in the credentials.properties file required for the API access.
     * It later on adds the values to the credentials (hashMap).
     */
    private void loadCredentials() {
        credentials.put("accessTokenUrl", CredentialManager.get("accessTokenUrl"));

        credentials.put("accessTokenUsername", CredentialManager.get("accessTokenUsername"));

        credentials.put("accessTokenPassword", CredentialManager.get("accessTokenPassword"));

        credentials.put("baseUrl", CredentialManager.get("baseUrl"));

        credentials.put("vehicleConnectorUrl", CredentialManager.get("vehicleConnectorUrl"));

        credentials.put("equipmentConnectorUrl", CredentialManager.get("equipmentConnectorUrl"));

        credentials.put("username", CredentialManager.get("username"));

        credentials.put("password", CredentialManager.get("password"));
    }

    /**
     * Creates trip instances
     */
    private void initializeTrips(double defaultVehicleSpeed) {
        ArrayList<TripModel> tripModels = TripBulkGenerator.bulkCreateTrips(
                credentials.get("accessTokenUrl"),
                credentials.get("accessTokenUsername"),
                credentials.get("accessTokenPassword"),
                credentials.get("baseUrl"),
                credentials.get("vehicleConnectorUrl"),
                credentials.get("equipmentConnectorUrl"),
                credentials.get("username"),
                credentials.get("password"),
                organizationId,
                requiredInstances,
                EQUIPMENT_PER_TRIP,
                SHIP_UNITS_PER_EQUIPMENT,
                SHIP_ITEMS_PER_SHIP_UNIT,
                NO_STOPS
        );

        for (TripModel tripModel : tripModels) {
            instances.add(new TripInstance(tripModel, reportInterval, defaultVehicleSpeed,credentials.get("vehicleConnectorUrl"), credentials.get("equipmentConnectorUrl"), credentials.get("username"), credentials.get("password")));
        }

        SIMULATION_LOGGER.info("Created {} trips for simulation", requiredInstances);
    }


    //region Vehicle speed (CSV)

    /**
     * Creates a CSV file having all the vehicle speeds and waits for the user to change it.
     * These updated values are then used for the vehicles.
     */
    public void setVehicleSpeeds() {
        // Create a csv file for speeds
        generateVehicleSpeedCSV(requiredInstances);

        // Wait for user prompt (time limit)
        waitForChanges();

        // Read and update the vehicle speeds as per CSV file
        updateVehicleSpeedsFromCSV();
    }

    private void changeVehicleSpeed(int instanceIndex, double vehicleSpeed) {
        instances.get(instanceIndex).setVehicleSpeed(vehicleSpeed);
    }

    private double getVehicleSpeed(int instanceIndex) {
        return instances.get(instanceIndex).getVehicleSpeed();
    }

    private void generateVehicleSpeedCSV(int requiredInstances) {
        List<String[]> vehicleSpeeds = new ArrayList<>();

        for (int i = 0; i < requiredInstances; i++) {
            vehicleSpeeds.add(new String[]
                    { "vehicle-" + (i + 1), String.valueOf(getVehicleSpeed(i))});
        }

        File VehicleSpeedCSV = new File("vehicleSpeeds.csv");
        try (PrintWriter pw = new PrintWriter(VehicleSpeedCSV)) {
            vehicleSpeeds.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            SIMULATION_LOGGER.warn(e.getMessage());
        }

        SIMULATION_LOGGER.info("Vehicle speeds CSV created");
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    // Reading vehicle speeds from CSV file
    public void updateVehicleSpeedsFromCSV() {
        ArrayList<Double> vehicleSpeeds = CSVParser.parseVehicleSpeeds("vehicleSpeeds.csv");
        for (int i = 0; i < vehicleSpeeds.size(); i++) {
            changeVehicleSpeed(i, vehicleSpeeds.get(i));
        }
        SIMULATION_LOGGER.info("Updated vehicle speeds as per CSV file");
    }


    //endregion
    //region User prompt (time limited)

    public void waitForChanges()
    {
        System.out.println("Update the vehicle speeds in the CSV file and give the input \"Done\" after altering it.");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        long startTime = System.currentTimeMillis();
        try {
            while ((System.currentTimeMillis() - startTime) < MAX_INPUT_WAIT_TIME * 1000
                    && !in.ready()) {
            }

            if (in.ready()) {
                System.out.println("Proceeding to the simulation with the given speed values");
                SIMULATION_LOGGER.info("Proceeding to the simulation with the given speed values");
            } else {
                System.out.println("User has not given a response, so thia will automatically start the response.");
                SIMULATION_LOGGER.info("User has not given a response, so thia will automatically start the response.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //endregion

    public void cleanUp() {
        for(TripInstance instance : instances) {
            //Setting up the clients
            VehicleTypeAPIClient vehicleTypeClient = new VehicleTypeAPIClient(
                    credentials.get("baseUrl"),
                    credentials.get("username"),
                    credentials.get("password")
            );
            VehicleAPIClient vehicleClient = new VehicleAPIClient(
                    credentials.get("baseUrl"),
                    credentials.get("username"),
                    credentials.get("password")
            );
            TripAPIClient tripClient = new TripAPIClient(
                    credentials.get("baseUrl"),
                    credentials.get("username"),
                    credentials.get("password")
            );
            DeviceAPIClient deviceClient = new DeviceAPIClient(
                    credentials.get("baseUrl"),
                    credentials.get("username"),
                    credentials.get("password")
            );

            TripModel tripModel = instance.getTripModel();
            Vehicle vehicle = vehicleClient.getOneByName(tripModel.getVehicleName());

            // Deleting the vehicle type
            vehicleTypeClient.delete(vehicle.getType());

            // Deleting the vehicle
            vehicleClient.delete(vehicle.getId());

            // Deleting the vehicle device
            deviceClient.delete(deviceClient.getOneByIdentifier(tripModel.getVehiclePayload().getDeviceIdentifier()).getId());

            // Deleting the equipment devices
            for (Payload equipment : tripModel.getEquipmentPayloads()) {
                deviceClient.delete(deviceClient.getOneByIdentifier(equipment.getDeviceIdentifier()).getId());
            }

            // Deleting the ship unit devices
            for (Payload shipUnit : tripModel.getShipUnitPayloads()) {
                deviceClient.delete(deviceClient.getOneByIdentifier(shipUnit.getDeviceIdentifier()).getId());
            }

            // Deleting the ship item devices
            for (Payload shipItem : tripModel.getShipItemPayloads()) {
                deviceClient.delete(deviceClient.getOneByIdentifier(shipItem.getDeviceIdentifier()).getId());
            }

            // Deleting the trip
            tripClient.delete(tripModel.getId());
        }
    }
    //endregion

}
