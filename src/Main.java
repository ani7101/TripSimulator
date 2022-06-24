import bulkGenerators.TripBulkGenerator;
import simulation.models.TripInstance;
import simulation.models.TripModel;
import utils.CredentialManager;
import utils.LocalStorage;

import java.util.ArrayList;
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

        System.out.println(credentials.get("accessTokenUrl"));
        System.out.println(credentials.get("accessTokenUsername"));
        System.out.println(credentials.get("accessTokenPassword"));
        System.out.println(credentials.get("baseUrl"));
        System.out.println(credentials.get("connectorUrl"));
        System.out.println(credentials.get("username"));
        System.out.println(credentials.get("password"));

        // For creating and storing trip models locally (remove the comments)
        /*
        ArrayList<TripModel> tripModels = TripBulkGenerator.bulkCreateTrips(accessTokenUrl, username, password, baseUrl, connectorUrl, username, password, 20, 1);

        // Storing it for later use
        LocalStorage.writeTripModels(tripModels, "tripModels.ser");
         */

        TripModel tripModel = (TripModel) LocalStorage.read("tripModels.ser").get(0);
        int reportInterval = 30;

        TripInstance tripInstance = new TripInstance(tripModel, reportInterval);
    }
}