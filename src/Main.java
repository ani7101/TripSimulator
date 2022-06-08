import Trip.*;
import Utils.PolylineEncoderDecoder;
import Vehicle.VehicleAPIClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        // Creating a trip for testing
//        Trip trip1 = new Trip();
//        trip1.setName("First ever trip!!");
//        trip1.setSource(new TripStopRecord(location1.getLatitude(), location1.getLongitude(), true));
//        trip1.setDestination(new TripStopRecord(location2.getLatitude(), location2.getLongitude(), true));
//        trip1.setDriver(new TripDriverInfoModel("testOperator100", "testOperator100", "testOperator100"));
//        trip1.setVehicle(new TripVehicleInfoModel("TestOBD2Vehicle"));

        // Creating a list of geo-coordinates for testing out the HERE maps route
//        ArrayList<Double> stopLatitudes = new ArrayList<Double>();
//        ArrayList<Double> stopLongitudes = new ArrayList<Double>();
//        stopLatitudes.add(52.517871);
//        stopLongitudes.add(13.434175);
//        stopLatitudes.add(52.52426);
//        stopLongitudes.add(13.43);
//        TripDAO tripDAO = new TripDAO("https://aniragha-lite.internal.iot.ocs.oraclecloud.com", "iot-cloudops_ww_grp", "Welcome1234#");
//        tripDAO.createTrip("First trip", 52.51375, 13.42462, 48.21815,16.38995, stopLatitudes, stopLongitudes);

        System.out.println(PolylineEncoderDecoder.decode("B2FogulkDo0-zZwvBQ_EAwCnaAwC3XAwC7VA8BjSAkDvgBA4D3hBA4DriBUwC_YAkDjcAwCrTA8BrJAkD7LAwCjIAsEzKAwH_OAsErJAwCjDAkD7GAoLUAgFoBA0F8BA8Q0FAsE8BAkmBkSAgtBwWAkS8GAwH8BU8GoBAosCgKUkhBsEA0FUA0FUAgZjDA0KgFU8GkDAsE8BAoGkDA4F8CA").getClass());
    }
}