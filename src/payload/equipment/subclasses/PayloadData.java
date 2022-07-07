package payload.equipment.subclasses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Data included with their units
 * <ul>
 *     <li>Latitude (in degrees)</li>
 *     <li>Longitude (in degrees)</li>
 *     <li>Tilt</li>
 *     <li>Light</li>
 *     <li>Temperature</li>
 *     <li>Pressure</li>
 *     <li>Humidity</li>
 *     <li>Shock</li>
 *     <li>Ambient temperature</li>
 *     <li>Tamper detection</li>
 * </ul>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayloadData implements Serializable {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String LATITUDE = "latitude";

    public static final String LONGITUDE = "longitude";

    public static final String TILT = "tilt";

    public static final String LIGHT = "light";

    public static final String TEMPERATURE = "temperature";

    public static final String PRESSURE = "pressure";

    public static final String HUMIDITY = "humidity";

    public static final String SHOCK = "shock";

    public static final String AMBIENT_TEMPERATURE = "ambient_temperature";

    public static final String TAMPER_DETECTION = "tamper_detection";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(LATITUDE)
    private double latitude;

    @JsonProperty(LONGITUDE)
    private double longitude;

    @JsonProperty(TILT)
    private double tilt;

    @JsonProperty(LIGHT)
    private int light;

    @JsonProperty(TEMPERATURE)
    private double temperature;

    @JsonProperty(PRESSURE)
    private double pressure;

    @JsonProperty(HUMIDITY)
    private double humidity;

    @JsonProperty(SHOCK)
    private double shock;

    @JsonProperty(AMBIENT_TEMPERATURE)
    private double ambientTemperature;

    @JsonProperty(TAMPER_DETECTION)
    private double tamperDetection;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------


    public PayloadData(double latitude, double longitude, double tilt, int light, double temperature, double pressure, double humidity, double shock, double ambientTemperature, double tamperDetection) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.tilt = tilt;
        this.light = light;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.shock = shock;
        this.ambientTemperature = ambientTemperature;
        this.tamperDetection = tamperDetection;
    }

    // Empty constructor for jackson to serialize/deserialize
    public PayloadData() {}


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

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

    public double getTilt() {
        return tilt;
    }

    public void setTilt(double tilt) {
        this.tilt = tilt;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getShock() {
        return shock;
    }

    public void setShock(double shock) {
        this.shock = shock;
    }

    public double getAmbientTemperature() {
        return ambientTemperature;
    }

    public void setAmbientTemperature(double ambientTemperature) {
        this.ambientTemperature = ambientTemperature;
    }

    public double getTamperDetection() {
        return tamperDetection;
    }

    public void setTamperDetection(double tamperDetection) {
        this.tamperDetection = tamperDetection;
    }

    //endregion

}
