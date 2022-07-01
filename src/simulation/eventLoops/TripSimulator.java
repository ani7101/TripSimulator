package simulation.eventLoops;

import bulkGenerators.TripBulkGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simulation.models.TripInstance;
import simulation.models.TripModel;

import utils.CredentialManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Overhead class to simulate multiple trip instances on multiple threads
 */
public class TripSimulator {

    private static Scanner sc = new Scanner(System.in);

    //region Class variables
    //---------------------------------------------------------------------------------------

    private final ArrayList<TripInstance> instances;

    Map<String, String> credentials = new HashMap<>();

    private final String organizationId;

    private final int requiredInstances;

    private final int reportInterval;

    private double defaultVehicleSpeed;


    private static final Logger SIMULATION_LOGGER = LoggerFactory.getLogger("simulation");


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public TripSimulator(ArrayList<TripModel> tripModels, int reportInterval, double defaultVehicleSpeed) {
        this(tripModels, reportInterval, defaultVehicleSpeed, "ORA_DEFAULT_ORG");
    }

    public TripSimulator(ArrayList<TripModel> tripModels, int reportInterval, double defaultVehicleSpeed, String organizationId) {
        this.requiredInstances = tripModels.size();
        this.defaultVehicleSpeed = defaultVehicleSpeed;
        this.reportInterval = reportInterval;
        this.organizationId = organizationId;

        instances = new ArrayList<>(requiredInstances);

        // Loading the credentials for the IoT server instance
        getCredentials();

        for (TripModel tripModel : tripModels) {
            instances.add(new TripInstance(tripModel, reportInterval, defaultVehicleSpeed, credentials.get("connectorUrl"), credentials.get("username"), credentials.get("password")));
        }
    }

    public TripSimulator(int requiredInstances, int reportInterval, double defaultVehicleSpeed) {
        this(requiredInstances, reportInterval, defaultVehicleSpeed, "ORA_DEFAULT_ORG");
    }

    public TripSimulator(int requiredInstances, int reportInterval, double defaultVehicleSpeed, String organizationId) {

        // Initialization of class variables
        this.requiredInstances = requiredInstances;
        this.defaultVehicleSpeed = defaultVehicleSpeed;
        this.reportInterval = reportInterval;
        this.organizationId = organizationId;

        instances = new ArrayList<>(requiredInstances);

        // Loading the credentials for the IoT server instance
        getCredentials();

        initializeTrips();
    }


    //endregion
    //region Simulation
    //---------------------------------------------------------------------------------------

    /**
     * Main simulation loop run for many instances each run in a different thread
     */
    public void run() {
        SIMULATION_LOGGER.info("Started simulation");
        for (TripInstance instance : instances) {
            instance.start();
        }

        // TODO: 29/06/2022 Need to include vehicle speed changes (either using CLI or GUI)
        // updateVehicleSpeed();

    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    /**
     * Obtains the credentials stored in the credentials.properties file required for the API access.
     * It later on adds the values to the credentials (hashMap).
     */
    private void getCredentials() {
        credentials.put("baseUrl", CredentialManager.get("baseUrl"));

        credentials.put("connectorUrl", CredentialManager.get("connectorUrl"));

        credentials.put("username", CredentialManager.get("username"));

        credentials.put("password", CredentialManager.get("password"));

        credentials.put("accessTokenUrl", CredentialManager.get("accessTokenUrl"));

        credentials.put("accessTokenUsername", CredentialManager.get("accessTokenUsername"));

        credentials.put("accessTokenPassword", CredentialManager.get("accessTokenPassword"));
    }

    /**
     * Creates trip instances.
     */
    private void initializeTrips() {
        ArrayList<TripModel> tripModels = TripBulkGenerator.bulkCreateTrips(
                credentials.get("accessTokenUrl"),
                credentials.get("accessTokenUsername"),
                credentials.get("accessTokenPassword"),
                credentials.get("baseUrl"),
                credentials.get("connectorUrl"),
                credentials.get("username"),
                credentials.get("password"),
                organizationId,
                requiredInstances,
                1
        );

        for (TripModel tripModel : tripModels) {
            instances.add(new TripInstance(tripModel, reportInterval, defaultVehicleSpeed, credentials.get("connectorUrl"), credentials.get("username"), credentials.get("password")));
        }

        SIMULATION_LOGGER.info("Created {} trips for simulation", requiredInstances);
    }

    private void updateVehicleSpeed() {
        System.out.print("Enter the instance whose speed you wish to change:");
        int instanceIndex = sc.nextInt();
        System.out.print("Enter the updated speed value:");
        double vehicleSpeed = sc.nextDouble();

        changeVehicleSpeed(instanceIndex, vehicleSpeed);
    }

    private void changeVehicleSpeed(int instanceIndex, double vehicleSpeed) {
        instances.get(instanceIndex).setVehicleSpeed(vehicleSpeed);
    }

    private void getVehicleSpeed(int instanceIndex) {
        instances.get(instanceIndex).getVehicleSpeed();
    }

    private void generateVehicleSpeedFile() {

    }


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public double getDefaultVehicleSpeed() { return defaultVehicleSpeed; }

    public void setDefaultVehicleSpeed(double defaultVehicleSpeed) {
        this.defaultVehicleSpeed = defaultVehicleSpeed;
    }

    //endregion

}
