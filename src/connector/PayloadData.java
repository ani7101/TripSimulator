package connector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayloadData {

    public static final String LATITUDE = "latitude";

    public static final String LONGITUDE = "longitude";

    public static final String VEHICLE_SPEED = "vehicle_speed";

    public static final String ENGINE_RPM = "engine_rpm";

    public static final String NUMBER_OF_DTCS = "number_of_dtcs";

    public static final String ENGINE_COOLANT_TEMPERATURE = "engine_coolant_temperature";

    public static final String TRUE_ODOMETER = "true_odometer";

    public static final String THROTTLE_POSITION = "throttle_position";

    public static final String TOTAL_FUEL_USED = "total_fuel_used";

    public static final String RUNTIME_SINCE_ENGINE_START = "runtime_since_engine_start";

    public static final String MASS_AIR_FLOW = "mass_air_flow";

    public static final String AVERAGE_FUEL_ECONOMY= "average_fuel_economy";

    public static final String DISTANCE_SINCE_DTCS_CLEARED = "distance_since_dtcs_cleared";


    @JsonProperty(LATITUDE)
    private double latitude;

    @JsonProperty(LONGITUDE)
    private double longitude;

    @JsonProperty(VEHICLE_SPEED)
    private double vehicleSpeed;

    @JsonProperty(ENGINE_RPM)
    private double engineRPM;

    @JsonProperty(NUMBER_OF_DTCS)
    private double numberOfDTCs;

    @JsonProperty(ENGINE_COOLANT_TEMPERATURE)
    private double engineCoolantTemperature;

    @JsonProperty(TRUE_ODOMETER)
    private double trueOdometer;

    @JsonProperty(THROTTLE_POSITION)
    private double throttlePosition;

    @JsonProperty(TOTAL_FUEL_USED)
    private double totalFuelUsed;

    @JsonProperty(RUNTIME_SINCE_ENGINE_START)
    private double runtimeSinceEngineStart;

    @JsonProperty(MASS_AIR_FLOW)
    private double massAirFlow;

    @JsonProperty(AVERAGE_FUEL_ECONOMY)
    private double averageFuelEconomy;

    @JsonProperty(DISTANCE_SINCE_DTCS_CLEARED)
    private double distanceSinceDTCsCleared;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(double vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public double getEngineRPM() {
        return engineRPM;
    }

    public void setEngineRPM(double engineRPM) {
        this.engineRPM = engineRPM;
    }

    public double getNumberOfDTCs() {
        return numberOfDTCs;
    }

    public void setNumberOfDTCs(double numberOfDTCs) {
        this.numberOfDTCs = numberOfDTCs;
    }

    public double getEngineCoolantTemperature() {
        return engineCoolantTemperature;
    }

    public void setEngineCoolantTemperature(double engineCoolantTemperature) {
        this.engineCoolantTemperature = engineCoolantTemperature;
    }

    public double getTrueOdometer() {
        return trueOdometer;
    }

    public void setTrueOdometer(double trueOdometer) {
        this.trueOdometer = trueOdometer;
    }

    public double getThrottlePosition() {
        return throttlePosition;
    }

    public void setThrottlePosition(double throttlePosition) {
        this.throttlePosition = throttlePosition;
    }

    public double getTotalFuelUsed() {
        return totalFuelUsed;
    }

    public void setTotalFuelUsed(double totalFuelUsed) {
        this.totalFuelUsed = totalFuelUsed;
    }

    public double getRuntimeSinceEngineStart() {
        return runtimeSinceEngineStart;
    }

    public void setRuntimeSinceEngineStart(double runtimeSinceEngineStart) {
        this.runtimeSinceEngineStart = runtimeSinceEngineStart;
    }

    public double getMassAirFlow() {
        return massAirFlow;
    }

    public void setMassAirFlow(double massAirFlow) {
        this.massAirFlow = massAirFlow;
    }

    public double getAverageFuelEconomy() {
        return averageFuelEconomy;
    }

    public void setAverageFuelEconomy(double averageFuelEconomy) {
        this.averageFuelEconomy = averageFuelEconomy;
    }

    public double getDistanceSinceDTCsCleared() {
        return distanceSinceDTCsCleared;
    }

    public void setDistanceSinceDTCsCleared(double distanceSinceDTCsCleared) {
        this.distanceSinceDTCsCleared = distanceSinceDTCsCleared;
    }
}
