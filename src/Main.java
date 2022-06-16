import hereMaps.*;

import device.*;
import trip.*;
import user.*;
import vehicle.*;
import utils.*;
import device.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class Main {
    public static void main(String[] args) {

        String baseUrl = "https://aniragha-lite.internal.iot.ocs.oraclecloud.com";
        String username = "iot-cloudops_ww_grp";
        String password = "Welcome1234#";
        String vehicleType = "43SSFN3W2JR0";
        String connectorUrl = "https://aniragha-lite.device.internal.iot.ocs.oraclecloud.com/cgw/TripSimulatorController";


//        TripAPIClient tripAPIClient = new TripAPIClient(baseUrl, username, password);
//
//        GeneratedOBD2Vehicle populateVehicle = new GeneratedOBD2Vehicle(baseUrl, username, password, "43QA7DF42VF0");
//        Vehicle v1 = populateVehicle.sendQuery();
//        System.out.println(v1.getId());
//
//        System.out.println("Done");
//
//        GeneratedUser populateUser = new GeneratedUser(baseUrl, username, password);
//        populateUser.sendQuery();

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


        System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
        System.setProperty("http.proxyPort", "80");

        System.setProperty("https.proxyHost", "www-proxy.us.oracle.com");
        System.setProperty("https.proxyPort", "80");

        HEREMapsAPIClient client = new HEREMapsAPIClient();

        client.generateToken();
        System.out.println(client.getAccessToken());
    }
}