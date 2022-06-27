import simulation.models.TripInstance;
import simulation.models.TripModel;
import utils.*;

import java.util.HashMap;

public class Main {
    private static HashMap<String, String> credentials = new HashMap<>();

    public static void main(String[] args) {
        credentials.put("accessTokenUrl", CredentialManager.get("accessTokenUrl"));
        credentials.put("accessTokenUsername", CredentialManager.get("accessTokenUsername"));
        credentials.put("accessTokenPassword", CredentialManager.get("accessTokenPassword"));
        credentials.put("baseUrl", CredentialManager.get("baseUrl"));
        credentials.put("connectorUrl", CredentialManager.get("connectorUrl"));
        credentials.put("username", CredentialManager.get("username"));
        credentials.put("password", CredentialManager.get("password"));

        // For creating and storing trip models locally (remove the comments)
        /*
        ArrayList<TripModel> tripModels = TripBulkGenerator.bulkCreateTrips(
                credentials.get("accessTokenUrl"),
                credentials.get("accessTokenUsername"),
                credentials.get("accessTokenPassword"),
                credentials.get("baseUrl"),
                credentials.get("connectorUrl"),
                credentials.get("username"),
                credentials.get("password"),
                1,
                1
        );

        // Storing it for later use
        LocalStorage.writeTripModels(tripModels, "tripModels.ser");
         */


        // Sample simulation
        TripModel tripModel = (TripModel) LocalStorage.read("tripModels.ser").get(0);

        TripInstance tripInstance = new TripInstance(tripModel, 100, 120, credentials.get("connectorUrl"), credentials.get("username"), credentials.get("password"));

        tripInstance.run();


        System.out.println("Trip completed!!");
    }
}