package vehicle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleAttributeValue {
    public static final String DEVICE_ID = "deviceId";

    public static final String DEVICE_MODEL = "deviceModel";

    public static final String MESSAGE_FORMAT_FIELD = "messageFormatField";

    @JsonProperty(DEVICE_ID)
    private String deviceId;

    @JsonProperty(DEVICE_MODEL)
    private String deviceModel;

    @JsonProperty(MESSAGE_FORMAT_FIELD)
    private String messageFormatField;

    public VehicleAttributeValue(String deviceId, String deviceModel, String messageFormatField) {
        this.deviceId = deviceId;
        this.deviceModel = deviceModel;
        this.messageFormatField = messageFormatField;
    }

    public VehicleAttributeValue() {}

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getMessageFormatField() {
        return messageFormatField;
    }

    public void setMessageFormatField(String messageFormatField) {
        this.messageFormatField = messageFormatField;
    }
}
