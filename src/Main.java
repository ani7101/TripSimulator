import trip.*;
import user.*;
import vehicle.*;
import utils.*;


public class Main {
    public static void main(String[] args) {

        String baseUrl = "https://aniragha-lite.internal.iot.ocs.oraclecloud.com";
        String username = "iot-cloudops_ww_grp";
        String password = "Welcome1234#";
        String vehicleType = "43SSFN3W2JR0";

        TripAPIClient tripAPIClient = new TripAPIClient(baseUrl, username, password);

        PopulateVehicle populateVehicle = new PopulateVehicle(baseUrl, username, password, "43QA7DF42VF0");
        Vehicle v1 = populateVehicle.sendQuery();
        System.out.println(v1.getId());

        System.out.println("Done");

        PopulateUser populateUser = new PopulateUser(baseUrl, username, password);
        populateUser.sendQuery();

        /*

        Trip trip = new Trip(
                new TripStopRecord(-122.26226, 37.53087),
                new TripStopRecord(-121.95267, 37.39125),
                new ArrayList<>(),
                new TripVehicleInfoModel(populateVehicle.getVehicle().getName()),
                new TripDriverInfoModel(populateUser.getUser().getName())
        );

        trip.setName("Trip simulator-" + Generator.generateRandomUUID());

        Trip response = tripAPIClient.create(trip);
        System.out.println("Done!");
         */
    }
}