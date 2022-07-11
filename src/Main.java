import bulkGenerators.TripBulkGenerator;
import simulation.TripSimulator;
import simulation.models.TripModel;
import utils.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static final HashMap<String, String> credentials = new HashMap<>();

    public static void main(String[] args) {
        // Demonstration of creating trips
        createTrips(2);

        // The saved trips can also be loaded later on.
        ArrayList<TripModel> tripModels = LocalStorage.read("tripModels.ser");

        // Sample simulation
        TripSimulator tripSimulator = new TripSimulator(
                tripModels,                         // Trip models stored locally
                20,                                 // Report interval
                Navigation.kmphToMilesPerHour(50)   // Default vehicle speed (in mph)
        );

        // Another alternative call to the trip simulator is to pass number of instances instead of an array of TripModels
        // The following commented call does the same
        /*
            TripSimulator tripSimulator1 = new TripSimulator(
                    10,             // Number of trip instances to be created
                    20,             // Report interval
                    50              // Default vehicle speed (in mph)
            );
         */

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
        // Loading the credentials from the credentials.properties file
        loadCredentials();

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

        // Storing it for later use
        LocalStorage.write(tripModels, "tripModels.ser");

        return tripModels;
    }

    //endregion
}