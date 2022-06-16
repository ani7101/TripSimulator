package device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {

    public static final String DEVICE_NAME = "deviceName";

    public static final String DEVICE_DESCRIPTION = "deviceDescription";

    public static final String DEVICE_TYPE = "deviceType";

    public static final String DEVICE_IDENTIFIER = "deviceIdentifier";

    public static final String DATA = "data";

    public static final String MEASUREMENT_TIME = "measurementTime";

    @JsonProperty(DEVICE_NAME)
    private String deviceName;

    @JsonProperty(DEVICE_DESCRIPTION)
    private String deviceDescription = "Trip simulator following OBD2 device model";

    @JsonProperty(DEVICE_TYPE)
    private String deviceType = "OBD2 Modular Vehicle sensor";

    @JsonProperty(DEVICE_IDENTIFIER)
    private String deviceIdentifier;

    @JsonProperty(MEASUREMENT_TIME)
    private String measurementTime;

    @JsonProperty(DATA)
    private PayloadData data;

    public Payload(String deviceName, String deviceIdentifier, String measurementTime, PayloadData data) {
        this.deviceName = deviceName;
        this.deviceIdentifier = deviceIdentifier;
        this.measurementTime = measurementTime;
        this.data = data;
    }

    public Payload() {}

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    public String getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(String measurementTime) {
        this.measurementTime = measurementTime;
    }

    public PayloadData getData() {
        return data;
    }

    public void setData(PayloadData data) {
        this.data = data;
    }

    // Data: Latitude

    @JsonIgnore
    public double getLatitude() { return data.getLatitude(); }

    @JsonIgnore
    public void setLatitude(double latitude) { data.setLatitude(latitude); }


    // Data: Longitude
    @JsonIgnore
    public double getLongitude() { return data.getLongitude(); }

    @JsonIgnore
    public void setLongitude(double longitude) { data.setLongitude(longitude); }


    // Data: Vehicle speed
    @JsonIgnore
    public int getVehicleSpeed() { return data.getVehicleSpeed(); }

    @JsonIgnore
    public void setVehicleSpeed(int vehicleSpeed) { data.setVehicleSpeed(vehicleSpeed); }


    // Data: Engine RPM
    @JsonIgnore
    public double getEngineRPM() { return data.getEngineRPM(); }

    @JsonIgnore
    public void setEngineRPM(int engineRPM) { data.setEngineRPM(engineRPM); }


    // Data: Number of DTCs
    @JsonIgnore
    public double getNumberOfDTCs() { return data.getNumberOfDTCs(); }

    @JsonIgnore
    public void setNumberOfDTCs(double numberOfDTCs) {
        data.setNumberOfDTCs(numberOfDTCs);
    }

    // Data: Engine coolant temperature

    @JsonIgnore
    public double getEngineCoolantTemperature() { return data.getEngineCoolantTemperature(); }

    @JsonIgnore
    public void setEngineCoolantTemperature(double engineCoolantTemperature) {
        this.data.setEngineCoolantTemperature(engineCoolantTemperature);
    }


    // Data: True odometer
    @JsonIgnore
    public int getTrueOdometer() { return data.getTrueOdometer(); }

    @JsonIgnore
    public void setTrueOdometer(int trueOdometer) { data.setTrueOdometer(trueOdometer); }


    // Data: Throttle position
    @JsonIgnore
    public double getThrottlePosition() { return data.getThrottlePosition(); }

    @JsonIgnore
    public void setThrottlePosition(double throttlePosition) {
        data.setThrottlePosition(throttlePosition);
    }


    // Data: Total fuel used
    @JsonIgnore
    public int getTotalFuelUsed() {
        return data.getTotalFuelUsed();
    }

    @JsonIgnore
    public void setTotalFuelUsed(int totalFuelUsed) {
        this.data.setTotalFuelUsed(totalFuelUsed);
    }


    // Data: Runtime since engine start
    @JsonIgnore
    public long getRuntimeSinceEngineStart() {
        return data.getRuntimeSinceEngineStart();
    }

    @JsonIgnore
    public void setRuntimeSinceEngineStart(long runtimeSinceEngineStart) {
        this.data.setRuntimeSinceEngineStart(runtimeSinceEngineStart);
    }


    // Data: mass air flow
    @JsonIgnore
    public double getMassAirFlow() {
        return data.getMassAirFlow();
    }

    @JsonIgnore
    public void setMassAirFlow(double massAirFlow) {
        this.data.setMassAirFlow(massAirFlow);
    }

    // Data: Average fuel economy
    @JsonIgnore
    public double getAverageFuelEconomy() {
        return data.getAverageFuelEconomy();
    }

    @JsonIgnore
    public void setAverageFuelEconomy(double averageFuelEconomy) {
        this.setAverageFuelEconomy(averageFuelEconomy);
    }


    // Data: Distance since DTCs cleared
    @JsonIgnore
    public int getDistanceSinceDTCsCleared() {
        return getDistanceSinceDTCsCleared();
    }

    @JsonIgnore
    public void setDistanceSinceDTCsCleared(int distanceSinceDTCsCleared) {
        this.data.setDistanceSinceDTCsCleared(distanceSinceDTCsCleared);
    }
}
