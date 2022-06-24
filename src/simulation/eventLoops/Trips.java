package simulation.eventLoops;

import bulkGenerators.TripBulkGenerator;
import connector.ConnectorAPIClient;

import simulation.models.TripInstance;
import simulation.models.TripModel;

import utils.CredentialManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Trips {

    private ArrayList<TripInstance> instances;

    Map<String, String> credentials = new HashMap<>();

    private final ConnectorAPIClient connectorAPIClient;

    private final String organizationId;

    private final int requiredInstances;

    private final int reportInterval;

    private final int stopTime;

    public Trips(ArrayList<TripModel> tripModels, int reportInterval, String organizationId) {
        this(tripModels.size(), reportInterval, "ORA_DEFAULT_ORG");

        for (TripModel tripModel : tripModels) {
            instances.add(new TripInstance(tripModel, reportInterval));
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

        connectorAPIClient = new ConnectorAPIClient(
                credentials.get("connectorUrl"),
                credentials.get("username"),
                credentials.get("password")
        );

        initializeTrips();
    }

    public Trips(int requiredInstances, int reportInterval) {
        this(requiredInstances, reportInterval, "ORA_DEFAULT_ORG");
    }

    public void simulation() {
        for (TripInstance instance : instances) {
            instance.start();
        }
    }

    private void getCredentials() {
        credentials.put("baseUrl", CredentialManager.get("baseUrl"));

        credentials.put("connectorUrl", CredentialManager.get("connectorUrl"));

        credentials.put("username", CredentialManager.get("username"));

        credentials.put("password", CredentialManager.get("password"));

        credentials.put("accessTokenUrl", CredentialManager.get("accessTokenUrl"));

        credentials.put("accessTokenUsername", CredentialManager.get("accessTokenUsername"));

        credentials.put("accessTokenPassword", CredentialManager.get("accessTokenPassword"));
    }

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
            instances.add(new TripInstance(tripModel, reportInterval));
        }
    }
}
