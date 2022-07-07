import bulkGenerators.TripBulkGenerator;
import simulation.TripSimulator;
import simulation.models.TripModel;
import utils.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static final HashMap<String, String> credentials = new HashMap<>();

    public static void main(String[] args) {
        // Loading the credentials from the credentials.properties file
        loadCredentials();

        // Demonstration for creating trips
//         createTrips(50);

        // The saved trips can also be loaded later on.
//        ArrayList<TripModel> tripModels = LocalStorage.read("tripModels.ser");

        // Sample simulation
        TripSimulator tripSimulator = new TripSimulator(5, 20, Navigation.kmphToMilesPerHour(40));
        tripSimulator.run();
    }

    //region Utils
    public static void loadCredentials() {
        credentials.put("accessTokenUrl", CredentialManager.get("accessTokenUrl"));
        credentials.put("accessTokenUsername", CredentialManager.get("accessTokenUsername"));
        credentials.put("accessTokenPassword", CredentialManager.get("accessTokenPassword"));
        credentials.put("baseUrl", CredentialManager.get("baseUrl"));
        credentials.put("vehicleConnectorUrl", CredentialManager.get("vehicleConnectorUrl"));
        credentials.put("equipmentConnectorUrl", CredentialManager.get("equipmentConnectorUrl"));
        credentials.put("username", CredentialManager.get("username"));
        credentials.put("password", CredentialManager.get("password"));
    }

    public static ArrayList<TripModel> createTrips(int requiredTrips) {
        // For creating and storing trip models locally (remove the comments)
        ArrayList<TripModel> tripModels = TripBulkGenerator.bulkCreateTrips(
                credentials.get("accessTokenUrl"),
                credentials.get("accessTokenUsername"),
                credentials.get("accessTokenPassword"),
                credentials.get("baseUrl"),
                credentials.get("vehicleConnectorUrl"),
                credentials.get("equipmentConnectorUrl"),
                credentials.get("username"),
                credentials.get("password"),
                "ORA_DEFAULT_ORG",
                requiredTrips,
                1,
                2,
                2,
                1
        );

        LocalStorage.write(tripModels, "tripModels.ser");

        // Storing it for later use
        return tripModels;
    }

    //endregion
}