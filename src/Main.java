import hereMaps.*;
import trip.Trip;
import trip.TripAPIClient;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        // Creating a list of geo-coordinates for testing out the HERE maps route
//        ArrayList<Double> stopLatitudes = new ArrayList<Double>();
//        ArrayList<Double> stopLongitudes = new ArrayList<Double>();
//        stopLatitudes.add(52.517871);
//        stopLongitudes.add(13.434175);
//        stopLatitudes.add(52.52426);
//        stopLongitudes.add(13.43);
//        Trip.PopulateTrip tripDAO = new Trip.PopulateTrip("https://aniragha-lite.internal.iot.ocs.oraclecloud.com", "iot-cloudops_ww_grp", "Welcome1234#");
//        tripDAO.createTrip("First trip", 52.51375, 13.42462, 48.21815,16.38995, stopLatitudes, stopLongitudes);

//        TripAPIClient client = new TripAPIClient("https://aniragha-lite.internal.iot.ocs.oraclecloud.com", "iot-cloudops_ww_grp", "Welcome1234#");
//        ArrayList<Trip> trips = client.getAll();
//        System.out.println(trips.toString());

        String accessToken = "eyJhbGciOiJSUzUxMiIsImN0eSI6IkpXVCIsImlzcyI6IkhFUkUiLCJhaWQiOiJ1R1Q2T0RwTU43WjlSYU81UXJMRiIsImlhdCI6MTY1NDc4MTYwNiwiZXhwIjoxNjU0ODY4MDA2LCJraWQiOiJqMSJ9.ZXlKaGJHY2lPaUprYVhJaUxDSmxibU1pT2lKQk1qVTJRMEpETFVoVE5URXlJbjAuLnp6ZHpQb2NWYUxXRDA4YnltN0xhUmcuQTFtNUprYS02RlpNOW15RmY5cW43RW5DaWVoTkJyQXh0SkI0MHlLcXA2NDRrVzdiS2xfd3pqZFVuN25GNG5fNFdFM3lraUFzSm83NjhXTDhPZHlqMzM3TGFsYjkxUGVDellQLVpoVEF3TzN5YlEyRVQwOG0wMElhRkY2OVV2bzEwMGh5SFlERklzZ3QyZ3prZ2hpZ3NRLmx2aWlyZ1NsUER3N2R0bGcycnptby1fTEhXaWpDQ2s3WmRKeHJoZmV2MDg.FnXGeN2du5mIkvWo8fei-MRc6-VDkRx2-fLPzvacX5LwHP9mWiYWVRdY-w77ukMeO6DNeLzIbRtBCv03PmJhTynD2l2XyWfAZ-mSfvynUcbovjuYXloBj_aHDqUiNsy-_oCSPLHE-krxLtwLQMS18ZbpBn3YrztARzMQuyzFC2y_IosXiUsg_BaLCEi-BSYHSnaix_RBzfJ3N0QuNsd-vBqb4TT3X3pkW223b_cfEKRXXuLGHQrEHH1z_Q-YJu5UqpDnhO9RfQcfeb3dVxh5oibwkIr_nGvkghsuof1687XAjpXBvDtiAeWuS09L9Y-zCR0gpsKFMM_wfEtoe0F3eA";


        TripAPIClient client = new TripAPIClient("https://aniragha-lite.internal.iot.ocs.oraclecloud.com", "iot-cloudops_ww_grp", "Welcome1234#");
        ArrayList<Trip> trips = client.getAll();
        System.out.println(trips.toString());
    }
}