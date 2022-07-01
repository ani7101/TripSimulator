package payload.equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import payload.equipment.subclasses.PayloadData;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload implements Serializable {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String DEVICE_NAME = "deviceName";

    public static final String DEVICE_DESCRIPTION = "deviceDescription";

    public static final String DEVICE_TYPE = "deviceType";

    public static final String DEVICE_IDENTIFIER = "deviceIdentifier";

    public static final String DATA = "data";

    public static final String MEASUREMENT_TIME = "measurementTime";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(DEVICE_NAME)
    private String deviceName;

    @JsonProperty(DEVICE_DESCRIPTION)
    private String deviceDescription = "Follows ORA Equipment model";

    @JsonProperty(DEVICE_TYPE)
    private String deviceType = "Modular Equipment sensor";

    @JsonProperty(DEVICE_IDENTIFIER)
    private String deviceIdentifier;

    @JsonProperty(MEASUREMENT_TIME)
    private String measurementTime;

    @JsonProperty(DATA)
    private PayloadData data;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public Payload(String deviceName, String deviceIdentifier, String measurementTime, PayloadData data) {
        this.deviceName = deviceName;
        this.deviceIdentifier = deviceIdentifier;
        this.measurementTime = measurementTime;
        this.data = data;
    }

    // Empty constructor for jackson to serialize/deserialize
    public Payload() {}


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

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


    //endregion
    //region Data Getters/Setters

    // Data: Latitude
    @JsonIgnore
    public double getLatitude() {
        return data.getLatitude();
    }

    @JsonIgnore
    public void setLatitude(double latitude) {
        data.setLatitude(latitude);
    }

    // Data: Longitude
    @JsonIgnore
    public double getLongitude() {
        return data.getLongitude();
    }

    @JsonIgnore
    public void setLongitude(double longitude) {
        data.setLongitude(longitude);
    }

    // Data: Tilt
    @JsonIgnore
    public double getTilt() {
        return data.getTilt();
    }

    @JsonIgnore
    public void setTilt(double tilt) {
        data.setTilt(tilt);
    }

    // Data: Light
    @JsonIgnore
    public double getLight() {
        return data.getLight();
    }

    @JsonIgnore
    public void setLight(double light) {
        data.setLight(light);
    }

    // Data: Temperature
    @JsonIgnore
    public double getTemperature() {
        return data.getTemperature();
    }

    @JsonIgnore
    public void setTemperature(double temperature) {
        data.setTemperature(temperature);
    }

    // Data: Pressure
    @JsonIgnore
    public double getPressure() {
        return data.getPressure();
    }

    @JsonIgnore
    public void setPressure(double pressure) {
        data.setPressure(pressure);
    }

    // Data: Humidity
    @JsonIgnore
    public double getHumidity() {
        return data.getHumidity();
    }

    @JsonIgnore
    public void setHumidity(double humidity) {
        data.setHumidity(humidity);
    }

    // Data: Shock
    @JsonIgnore
    public double getShock() {
        return data.getShock();
    }

    @JsonIgnore
    public void setShock(double shock) {
        data.setShock(shock);
    }

    // Data: Ambient Temperature
    @JsonIgnore
    public double getAmbientTemperature() {
        return data.getAmbientTemperature();
    }

    @JsonIgnore
    public void setAmbientTemperature(double ambientTemperature) {
        data.setAmbientTemperature(ambientTemperature);
    }

    // Data: Tamper Detection
    @JsonIgnore
    public double getTamperDetection() {
        return data.getTamperDetection();
    }

    @JsonIgnore
    public void setTamperDetection(double tamperDetection) {
        data.setTamperDetection(tamperDetection);
    }

    //endregion

}
