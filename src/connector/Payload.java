package connector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import utils.DateTimeFormatter;

import java.time.LocalDateTime;

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
    private String deviceDescription;

    @JsonProperty(DEVICE_TYPE)
    private String deviceType;

    @JsonProperty(DEVICE_IDENTIFIER)
    private String deviceIdentifier;

    @JsonProperty(MEASUREMENT_TIME)
    private String measurementTime;

    @JsonProperty(DATA)
    private PayloadData data;

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

    public LocalDateTime getMeasurementTimeInLocalDateTime() {
        return DateTimeFormatter.iso8601ToLocalDateTime(measurementTime);
    }

    public void setMeasurementTime(String measurementTime) {
        this.measurementTime = measurementTime;
    }

    public void setMeasurementTime(LocalDateTime measurementTime) {
        this.measurementTime = DateTimeFormatter.localDateTimeToIso8601(measurementTime);
    }

    public PayloadData getData() {
        return data;
    }

    public void setData(PayloadData data) {
        this.data = data;
    }
}
