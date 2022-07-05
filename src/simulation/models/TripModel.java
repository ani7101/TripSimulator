package simulation.models;

import bulkGenerators.DeviceBulkGenerator;
import payload.equipment.PayloadDataGenerator;
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

    public static final long serialVersionUID = 1L;

    private final String id;

    private final String driverId;

    private final String deviceName;

    private final String deviceIdentifier;

    private final String vehicleName;

    private final PolylineEncoderDecoder.LatLngZ source;

    private final PolylineEncoderDecoder.LatLngZ destination;

    private final ArrayList<PolylineEncoderDecoder.LatLngZ> stops;

    private ArrayList<List<PolylineEncoderDecoder.LatLngZ>> route = new ArrayList<>();

    private Payload vehiclePayload;

    private ArrayList<payload.equipment.Payload> equipmentPayloads;

    private ArrayList<payload.equipment.Payload> shipUnitPayloads;

    private ArrayList<payload.equipment.Payload> shipItemPayloads;

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
            ArrayList<String> polyline,
            ArrayList<payload.equipment.Payload> equipmentPayloads,
            ArrayList<payload.equipment.Payload> shipUnitPayloads,
            ArrayList<payload.equipment.Payload> shipItemPayloads) {

        this.id = id;
        this.driverId = driverId;
        this.vehicleName = vehicleName;
        this.deviceName = deviceName;
        this.deviceIdentifier = deviceIdentifier;
        this.source = source;
        this.destination = destination;
        this.stops = stops;
        this.equipmentPayloads = equipmentPayloads;
        this.shipUnitPayloads = shipUnitPayloads;
        this.shipItemPayloads = shipItemPayloads;

        vehiclePayload = DeviceBulkGenerator.populateVehiclePayload(deviceName, deviceIdentifier);

        vehiclePayload.setLatitude(source.lat);
        vehiclePayload.setLongitude(source.lng);

        if (polyline != null) {
            for (String sectionPolyline : polyline) {
                route.add(PolylineEncoderDecoder.decode(sectionPolyline));
            }
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

    public Payload getVehiclePayload() {
        return vehiclePayload;
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

    public void setVehiclePayload(Payload vehiclePayload) {
        this.vehiclePayload = vehiclePayload;
    }

    // payload: Latitude
    public double getLatitude() { return vehiclePayload.getLatitude(); }

    public void setLatitude(double latitude) { vehiclePayload.setLatitude(latitude); }


    // payload: Longitude
    public double getLongitude() { return vehiclePayload.getLongitude(); }

    public void setLongitude(double longitude) { vehiclePayload.setLongitude(longitude); }


    // payload: Vehicle speed
    public double getVehicleSpeed() { return vehiclePayload.getVehicleSpeed(); }

    public void setVehicleSpeed(double vehicleSpeed) { vehiclePayload.setVehicleSpeed(vehicleSpeed); }


    // payload: Engine RPM
    public double getEngineRPM() { return vehiclePayload.getEngineRPM(); }

    public void setEngineRPM(int engineRPM) { vehiclePayload.setEngineRPM(engineRPM); }


    // payload: Number of DTCs
    public double getNumberOfDTCs() { return vehiclePayload.getNumberOfDTCs(); }

    public void setNumberOfDTCs(double numberOfDTCs) {
        vehiclePayload.setNumberOfDTCs(numberOfDTCs);
    }

    // payload: Engine coolant temperature

    public double getEngineCoolantTemperature() { return vehiclePayload.getEngineCoolantTemperature(); }

    public void setEngineCoolantTemperature(double engineCoolantTemperature) {
        vehiclePayload.setEngineCoolantTemperature(engineCoolantTemperature);
    }


    // payload: True odometer
    public double getTrueOdometer() { return vehiclePayload.getTrueOdometer(); }

    public void setTrueOdometer(double trueOdometer) { vehiclePayload.setTrueOdometer(trueOdometer); }


    // payload: Throttle position
    public double getThrottlePosition() { return vehiclePayload.getThrottlePosition(); }

    public void setThrottlePosition(double throttlePosition) {
        vehiclePayload.setThrottlePosition(throttlePosition);
    }


    // payload: Total fuel used
    public double getTotalFuelUsed() {
        return vehiclePayload.getTotalFuelUsed();
    }

    public void setTotalFuelUsed(double totalFuelUsed) {
        vehiclePayload.setTotalFuelUsed(totalFuelUsed);
    }


    // payload: Runtime since engine start
    public long getRuntimeSinceEngineStart() {
        return vehiclePayload.getRuntimeSinceEngineStart();
    }

    public void setRuntimeSinceEngineStart(long runtimeSinceEngineStart) {
        vehiclePayload.setRuntimeSinceEngineStart(runtimeSinceEngineStart);
    }


    // payload: mass air flow
    public double getMassAirFlow() {
        return vehiclePayload.getMassAirFlow();
    }

    public void setMassAirFlow(double massAirFlow) {
        vehiclePayload.setMassAirFlow(massAirFlow);
    }

    // payload: Average fuel economy
    public double getAverageFuelEconomy() {
        return vehiclePayload.getAverageFuelEconomy();
    }

    public void setAverageFuelEconomy(double averageFuelEconomy) {
        vehiclePayload.setAverageFuelEconomy(averageFuelEconomy);
    }


    // payload: Distance since DTCs cleared
    public int getDistanceSinceDTCsCleared() {
        return vehiclePayload.getDistanceSinceDTCsCleared();
    }

    public void setDistanceSinceDTCsCleared(int distanceSinceDTCsCleared) {
        vehiclePayload.setDistanceSinceDTCsCleared(distanceSinceDTCsCleared);
    }

    public ArrayList<payload.equipment.Payload> getEquipmentPayloads() {
        return equipmentPayloads;
    }

    public void setEquipmentPayloads(ArrayList<payload.equipment.Payload> equipmentPayloads) {
        this.equipmentPayloads = equipmentPayloads;
    }

    public ArrayList<payload.equipment.Payload> getShipUnitPayloads() {
        return shipUnitPayloads;
    }

    public void setShipUnitPayloads(ArrayList<payload.equipment.Payload> shipUnitPayloads) {
        this.shipUnitPayloads = shipUnitPayloads;
    }

    public ArrayList<payload.equipment.Payload> getShipItemPayloads() {
        return shipItemPayloads;
    }

    public void setShipItemPayloads(ArrayList<payload.equipment.Payload> shipItemPayloads) {
        this.shipItemPayloads = shipItemPayloads;
    }

    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    public void prepareVehiclePayload() {
        vehiclePayload.setMeasurementTime(DateTime.localDateTimeToIso8601(LocalDateTime.now()));

        setVehiclePayload(getRandomPayloadData(vehiclePayload));
    }

    public void prepareVehiclePayload(LocalDateTime measurementTime) {
        vehiclePayload.setMeasurementTime(DateTime.localDateTimeToIso8601(measurementTime));
        setRuntimeSinceEngineStart(
                getRuntimeSinceEngineStart() +
                Duration.between(
                        getEngineStartTime(),
                        LocalDateTime.now()).toSeconds()
        );
        setVehiclePayload(getRandomPayloadData(vehiclePayload));
    }

    public void setLocation(PolylineEncoderDecoder.LatLngZ point) {
        double lat = point.lat;
        double lng = point.lng;

        // Setting vehicle location
        setLatitude(lat);
        setLongitude(lng);
    }

    public void prepareEquipmentPayload() {
        for (payload.equipment.Payload equipmentPayload : equipmentPayloads) {
            equipmentPayload.setMeasurementTime(DateTime.localDateTimeToIso8601(LocalDateTime.now()));

            // Randomizing the payload values
            equipmentPayload.setTilt(PayloadDataGenerator.generateTilt());
            equipmentPayload.setLight(PayloadDataGenerator.generateLightSensor());
            equipmentPayload.setTemperature(PayloadDataGenerator.generateTemperature());
            equipmentPayload.setPressure(PayloadDataGenerator.generatePressure());
            equipmentPayload.setHumidity(PayloadDataGenerator.generateHumidity());
            equipmentPayload.setShock(PayloadDataGenerator.generateShock());
            equipmentPayload.setAmbientTemperature(PayloadDataGenerator.generateAmbientTemperature());
            equipmentPayload.setTamperDetection(PayloadDataGenerator.generateTamperDetection());
        }

        for (payload.equipment.Payload shipUnitPayload : shipUnitPayloads) {
            shipUnitPayload.setMeasurementTime(DateTime.localDateTimeToIso8601(LocalDateTime.now()));

            // Randomizing the payload values
            shipUnitPayload.setTilt(PayloadDataGenerator.generateTilt());
            shipUnitPayload.setLight(PayloadDataGenerator.generateLightSensor());
            shipUnitPayload.setTemperature(PayloadDataGenerator.generateTemperature());
            shipUnitPayload.setPressure(PayloadDataGenerator.generatePressure());
            shipUnitPayload.setHumidity(PayloadDataGenerator.generateHumidity());
            shipUnitPayload.setShock(PayloadDataGenerator.generateShock());
            shipUnitPayload.setAmbientTemperature(PayloadDataGenerator.generateAmbientTemperature());
            shipUnitPayload.setTamperDetection(PayloadDataGenerator.generateTamperDetection());
        }

        for (payload.equipment.Payload shipItemPayload : shipItemPayloads) {
            shipItemPayload.setMeasurementTime(DateTime.localDateTimeToIso8601(LocalDateTime.now()));

            // Randomizing the payload values
            shipItemPayload.setTilt(PayloadDataGenerator.generateTilt());
            shipItemPayload.setLight(PayloadDataGenerator.generateLightSensor());
            shipItemPayload.setTemperature(PayloadDataGenerator.generateTemperature());
            shipItemPayload.setPressure(PayloadDataGenerator.generatePressure());
            shipItemPayload.setHumidity(PayloadDataGenerator.generateHumidity());
            shipItemPayload.setShock(PayloadDataGenerator.generateShock());
            shipItemPayload.setAmbientTemperature(PayloadDataGenerator.generateAmbientTemperature());
            shipItemPayload.setTamperDetection(PayloadDataGenerator.generateTamperDetection());
        }

    }

    //endregion

}
