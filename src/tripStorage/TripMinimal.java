package tripStorage;

import device.Payload;
import utils.Generator;
import utils.PolylineEncoderDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to store the trip entity information.
 */
public class TripMinimal {

    private String id;

    private String driverId;

    private String vehicleName;

    private ArrayList<Double> source;

    private ArrayList<Double> destination;

    private ArrayList<ArrayList<Double>> stops;

    private String currentState;

    private ArrayList<List<PolylineEncoderDecoder.LatLngZ>> route;

    private Payload payload = new Payload();

    public TripMinimal(
            String id,
            String driverId,
            String vehicleName,
            ArrayList<Double> source,
            ArrayList<Double> destination,
            ArrayList<ArrayList<Double>> stops,
            ArrayList<String> polyline) {

        this.id = id;
        this.driverId = driverId;
        this.vehicleName = vehicleName;
        this.source = source;
        this.destination = destination;
        this.stops = stops;

        for (String sectionPolyline : polyline) {
            route.add(PolylineEncoderDecoder.decode(sectionPolyline));
        }

        // Creating and pushing general information for the payload
        createPayload();
    }

    public void createPayload() {
        String deviceName = "simulator-vehicle-" + vehicleName + "-sensor";
        String deviceIdentifier = "obd2-sensor" + Generator.generateRandomUUID();

        payload.setDeviceName(deviceName);
        payload.setDeviceIdentifier(deviceIdentifier);
        payload.setMeasurementTime(""); // Need to fix formatter
    }
}
