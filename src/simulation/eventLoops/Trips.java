package simulation.eventLoops;

import bulkGenerators.TripBulkGenerator;

import simulation.models.TripInstance;
import simulation.models.TripModel;

import utils.CredentialManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Overhead class to simulate multiple trip instances on multiple threads
 */
public class Trips {

    //region Class variables

    private final ArrayList<TripInstance> instances;

    Map<String, String> credentials = new HashMap<>();

    private final String organizationId;

    private final int requiredInstances;

    private final int reportInterval;

    private final int stopTime;

    // Constant value as of now
    private int VEHICLE_SPEED = 75;


    //endregion
    //region Constructors

    public Trips(ArrayList<TripModel> tripModels, int reportInterval, String organizationId) {
        this(tripModels.size(), reportInterval, "ORA_DEFAULT_ORG");

        for (TripModel tripModel : tripModels) {
            instances.add(new TripInstance(tripModel, reportInterval, VEHICLE_SPEED, credentials.get("connectorUrl"), credentials.get("username"), credentials.get("password")));
        }
    }

    public Trips(int requiredInstances, int reportInterval, String organizationId) {

        // Initialization of class variables
        this.requiredInstances = requiredInstances;
        this.reportInterval = reportInterval;
        this.organizationId = organizationId;

        instances = new ArrayList<>(requiredInstances);

        // Stop time is the smaller value among either 5 times the reportInterval or 5 minutes
        stopTime =  reportInterval < 60 ? 300 : 5 * reportInterval;

        // Loading the credentials for the IoT server instance
        getCredentials();

        initializeTrips();
    }

    public Trips(int requiredInstances, int reportInterval) {
        this(requiredInstances, reportInterval, "ORA_DEFAULT_ORG");
    }


    //endregion
    //region Simulation

    public void simulation() {
        for (TripInstance instance : instances) {
            instance.start();
        }
    }


    //endregion
    //region Utils

    /**
     * Obtains the credentials stored in the credentials.properties file required for the API access.
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
                requiredInstances,
                1
        );

        for (TripModel tripModel : tripModels) {
            instances.add(new TripInstance(tripModel, reportInterval, VEHICLE_SPEED, credentials.get("connectorUrl"), credentials.get("username"), credentials.get("password")));
        }
    }


    //endregion
    //region Getters/Setters

    public int getVEHICLE_SPEED() { return VEHICLE_SPEED; }

    public void setVEHICLE_SPEED(int VEHICLE_SPEED) {
        this.VEHICLE_SPEED = VEHICLE_SPEED;
    }

    //endregion

}
