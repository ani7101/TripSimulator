package vehicle.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleAttributeValue implements Serializable {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String DEVICE_ID = "deviceId";

    public static final String DEVICE_MODEL = "deviceModel";

    public static final String MESSAGE_FORMAT_FIELD = "messageFormatField";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(DEVICE_ID)
    private String deviceId;

    @JsonProperty(DEVICE_MODEL)
    private String deviceModel;

    @JsonProperty(MESSAGE_FORMAT_FIELD)
    private String messageFormatField;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public VehicleAttributeValue(String deviceId, String deviceModel, String messageFormatField) {
        this.deviceId = deviceId;
        this.deviceModel = deviceModel;
        this.messageFormatField = messageFormatField;
    }

    // Empty constructor for jackson to serialize/deserialize
    public VehicleAttributeValue() {}


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

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

    //endregion

}
