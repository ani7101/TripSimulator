package tripModel;

import device.Payload;
import trip.tripSubClasses.TripStopRecord;
import utils.PolylineEncoderDecoder;

import java.util.ArrayList;
import java.util.List;


public class TripSimulatorInstance {

    private String id;

    private String driverId;

    private String deviceId;

    private String vehicleName;

    private TripStopRecord source;

    private TripStopRecord destination;

    private ArrayList<TripStopRecord> stops;

    private ArrayList<List<PolylineEncoderDecoder.LatLngZ>> route;

    private Payload payload;

    public TripSimulatorInstance(
            String id,
            String driverId,
            String vehicleName,
            String deviceId,
            TripStopRecord source,
            TripStopRecord destination,
            ArrayList<TripStopRecord> stops,
            ArrayList<String> polyline) {

        this.id = id;
        this.driverId = driverId;
        this.vehicleName = vehicleName;
        this.deviceId = deviceId;
        this.source = source;
        this.destination = destination;
        this.stops = stops;
        payload = new Payload();

        for (String sectionPolyline : polyline) {
            route.add(PolylineEncoderDecoder.decode(sectionPolyline));
        }
    }

    public TripSimulatorInstance(
            String id,
            String driverId,
            String vehicleName,
            String deviceId,
            TripStopRecord source,
            TripStopRecord destination,
            ArrayList<TripStopRecord> stops,
            ArrayList<String> polyline,
            Payload payload) {

        this.id = id;
        this.driverId = driverId;
        this.vehicleName = vehicleName;
        this.deviceId = deviceId;
        this.source = source;
        this.destination = destination;
        this.stops = stops;
        this.payload = payload;

        for (String sectionPolyline : polyline) {
            route.add(PolylineEncoderDecoder.decode(sectionPolyline));
        }
    }
}
