import bulkGenerators.TripBulkGenerator;
import device.DeviceAPIClient;
import simulation.eventLoops.TripSimulator;
import simulation.models.TripModel;
import trip.TripAPIClient;
import user.UserAPIClient;
import utils.*;
import vehicle.VehicleAPIClient;

import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    private static final HashMap<String, String> credentials = new HashMap<>();

    public static void main(String[] args) {
        loadCredentials();

//        createTrips(75);

        ArrayList<TripModel> tripModels = LocalStorage.read("tripModels.ser");

        // Sample simulation
        TripSimulator tripSimulator = new TripSimulator(tripModels, 10, Navigation.kmphToMilesPerHour(40));

        tripSimulator.run();

        System.out.println("Done!!");
    }


    // Utils

    public static void loadCredentials() {
        credentials.put("accessTokenUrl", CredentialManager.get("accessTokenUrl"));
        credentials.put("accessTokenUsername", CredentialManager.get("accessTokenUsername"));
        credentials.put("accessTokenPassword", CredentialManager.get("accessTokenPassword"));
        credentials.put("baseUrl", CredentialManager.get("baseUrl"));
        credentials.put("connectorUrl", CredentialManager.get("connectorUrl"));
        credentials.put("username", CredentialManager.get("username"));
        credentials.put("password", CredentialManager.get("password"));
    }

    public static void createTrips(int requiredTrips) {
        // For creating and storing trip models locally (remove the comments)
        ArrayList<TripModel> tripModels = TripBulkGenerator.bulkCreateTrips(
                credentials.get("accessTokenUrl"),
                credentials.get("accessTokenUsername"),
                credentials.get("accessTokenPassword"),
                credentials.get("baseUrl"),
                credentials.get("connectorUrl"),
                credentials.get("username"),
                credentials.get("password"),
                "ORA_DEFAULT_ORG",
                requiredTrips,
                1
        );

        // Storing it for later use
        LocalStorage.write(tripModels, "tripModels.ser");
    }

    public static void cleanUp() {
        DeviceAPIClient deviceClient = new DeviceAPIClient(credentials.get("baseUrl"), credentials.get("username"), credentials.get("password"));
        deviceClient.cleanUp();

        TripAPIClient tripClient = new TripAPIClient(credentials.get("baseUrl"), credentials.get("username"), credentials.get("password"));
        tripClient.cleanUp();

        UserAPIClient userClient = new UserAPIClient(credentials.get("baseUrl"), credentials.get("username"), credentials.get("password"));
        userClient.cleanUp();

        VehicleAPIClient vehicleClient = new VehicleAPIClient(credentials.get("baseUrl"), credentials.get("username"), credentials.get("password"));
        vehicleClient.cleanUp();
    }
}