package simulation.models;

import bulkGenerators.DeviceConnectorBulkGenerator;
import payload.vehicle.Payload;
import utils.DateTime;
import utils.PolylineEncoderDecoder;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static payload.vehicle.PayloadDataGenerator.getRandomPayloadData;

/**
 * Stores the minimal information required for simulation of one trip
 */
public class TripModel implements Serializable {

    //region Class variables
    //---------------------------------------------------------------------------------------

    private final String id;

    private final String driverId;

    private final String deviceName;

    private final String deviceIdentifier;

    private final String vehicleName;

    private final PolylineEncoderDecoder.LatLngZ source;

    private final PolylineEncoderDecoder.LatLngZ destination;

    private final ArrayList<PolylineEncoderDecoder.LatLngZ> stops;

    private ArrayList<List<PolylineEncoderDecoder.LatLngZ>> route = new ArrayList<>();

    private Payload payload;

    private LocalDateTime engineStartTime;

    private int routePosition = 0; // This is the current position in the route ArrayList

    private double distanceFromStart = 0; // This is the excess distance the vehicle travels per route leg beyond the starting point of the leg


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public TripModel(
            String id,
            String driverId,
            String vehicleName,
            String deviceName,
            String deviceIdentifier,
            PolylineEncoderDecoder.LatLngZ source,
            PolylineEncoderDecoder.LatLngZ destination,
            ArrayList<PolylineEncoderDecoder.LatLngZ> stops,
            ArrayList<String> polyline) {

        this.id = id;
        this.driverId = driverId;
        this.vehicleName = vehicleName;
        this.deviceName = deviceName;
        this.deviceIdentifier = deviceIdentifier;
        this.source = source;
        this.destination = destination;
        this.stops = stops;

        payload = DeviceConnectorBulkGenerator.populatePayload(deviceName, deviceIdentifier);

        payload.setLatitude(source.lat);
        payload.setLongitude(source.lng);

        if (polyline != null) {
            for (String sectionPolyline : polyline) {
                route.add(PolylineEncoderDecoder.decode(sectionPolyline));
            }
        }
    }

    public TripModel(
            String id,
            String driverId,
            String vehicleName,
            String deviceName,
            String deviceIdentifier,
            PolylineEncoderDecoder.LatLngZ source,
            PolylineEncoderDecoder.LatLngZ destination,
            ArrayList<PolylineEncoderDecoder.LatLngZ> stops,
            ArrayList<String> polyline,
            Payload payload) {

        this.id = id;
        this.driverId = driverId;
        this.vehicleName = vehicleName;
        this.deviceName = deviceName;
        this.deviceIdentifier = deviceIdentifier;

        this.source = source;
        this.destination = destination;
        this.stops = stops;
        this.payload = payload;

        for (String sectionPolyline : polyline) {
            route.add(PolylineEncoderDecoder.decode(sectionPolyline));
        }
    }


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceIdentifier() { return deviceIdentifier; }

    public String getVehicleName() {
        return vehicleName;
    }

    public PolylineEncoderDecoder.LatLngZ getSource() {
        return source;
    }

    public PolylineEncoderDecoder.LatLngZ getDestination() {
        return destination;
    }

    public ArrayList<PolylineEncoderDecoder.LatLngZ> getStops() {
        return stops;
    }

    public ArrayList<List<PolylineEncoderDecoder.LatLngZ>> getRoute() {
        return route;
    }

    public Payload getPayload() {
        return payload;
    }

    public int getRoutePosition() {
        return routePosition;
    }

    public void setRoutePosition(int routePosition) {
        this.routePosition = routePosition;
    }

    public double getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(double distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public LocalDateTime getEngineStartTime() {
        return engineStartTime;
    }

    public void setEngineStartTime(LocalDateTime engineStartTime) {
        this.engineStartTime = engineStartTime;
    }

    public void setRoute(ArrayList<List<PolylineEncoderDecoder.LatLngZ>> route) {
        this.route = route;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    // payload: Latitude
    public double getLatitude() { return payload.getLatitude(); }

    public void setLatitude(double latitude) { payload.setLatitude(latitude); }


    // payload: Longitude
    public double getLongitude() { return payload.getLongitude(); }

    public void setLongitude(double longitude) { payload.setLongitude(longitude); }


    // payload: Vehicle speed
    public double getVehicleSpeed() { return payload.getVehicleSpeed(); }

    public void setVehicleSpeed(double vehicleSpeed) { payload.setVehicleSpeed(vehicleSpeed); }


    // payload: Engine RPM
    public double getEngineRPM() { return payload.getEngineRPM(); }

    public void setEngineRPM(int engineRPM) { payload.setEngineRPM(engineRPM); }


    // payload: Number of DTCs
    public double getNumberOfDTCs() { return payload.getNumberOfDTCs(); }

    public void setNumberOfDTCs(double numberOfDTCs) {
        payload.setNumberOfDTCs(numberOfDTCs);
    }

    // payload: Engine coolant temperature

    public double getEngineCoolantTemperature() { return payload.getEngineCoolantTemperature(); }

    public void setEngineCoolantTemperature(double engineCoolantTemperature) {
        payload.setEngineCoolantTemperature(engineCoolantTemperature);
    }


    // payload: True odometer
    public double getTrueOdometer() { return payload.getTrueOdometer(); }

    public void setTrueOdometer(double trueOdometer) { payload.setTrueOdometer(trueOdometer); }


    // payload: Throttle position
    public double getThrottlePosition() { return payload.getThrottlePosition(); }

    public void setThrottlePosition(double throttlePosition) {
        payload.setThrottlePosition(throttlePosition);
    }


    // payload: Total fuel used
    public double getTotalFuelUsed() {
        return payload.getTotalFuelUsed();
    }

    public void setTotalFuelUsed(double totalFuelUsed) {
        payload.setTotalFuelUsed(totalFuelUsed);
    }


    // payload: Runtime since engine start
    public long getRuntimeSinceEngineStart() {
        return payload.getRuntimeSinceEngineStart();
    }

    public void setRuntimeSinceEngineStart(long runtimeSinceEngineStart) {
        payload.setRuntimeSinceEngineStart(runtimeSinceEngineStart);
    }


    // payload: mass air flow
    public double getMassAirFlow() {
        return payload.getMassAirFlow();
    }

    public void setMassAirFlow(double massAirFlow) {
        payload.setMassAirFlow(massAirFlow);
    }

    // payload: Average fuel economy
    public double getAverageFuelEconomy() {
        return payload.getAverageFuelEconomy();
    }

    public void setAverageFuelEconomy(double averageFuelEconomy) {
        payload.setAverageFuelEconomy(averageFuelEconomy);
    }


    // payload: Distance since DTCs cleared
    public int getDistanceSinceDTCsCleared() {
        return payload.getDistanceSinceDTCsCleared();
    }

    public void setDistanceSinceDTCsCleared(int distanceSinceDTCsCleared) {
        payload.setDistanceSinceDTCsCleared(distanceSinceDTCsCleared);
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    public void preparePayload() {
        payload.setMeasurementTime(DateTime.localDateTimeToIso8601(LocalDateTime.now()));

        setPayload(getRandomPayloadData(payload));
    }

    public void preparePayload(LocalDateTime measurementTime) {
        payload.setMeasurementTime(DateTime.localDateTimeToIso8601(measurementTime));
        setRuntimeSinceEngineStart(
                getRuntimeSinceEngineStart() +
                Duration.between(
                        getEngineStartTime(),
                        LocalDateTime.now()).toSeconds()
        );
        setPayload(getRandomPayloadData(payload));
    }

    //endregion

}
