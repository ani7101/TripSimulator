package vehicleType.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleAttribute implements Serializable {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String ID = "id";

    public static final String DATA_TYPE = "dataType";

    public static final String NAME = "name";

    public static final String REQUIRED = "required";

    public static final String TYPE = "type";

    public static final String ALLOWED_VALUES = "allowedValues";

    public static final String DEVICE_MODEL = "deviceModel";

    public static final String INSTRUCTIONS = "instructions";

    public static final String MESSAGE_FORMAT = "messageFormat";

    public static final String MESSAGE_FORMAT_FIELD = "messageFormatField";

    public static final String UNIQUE = "unique";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    // Mandatory fields
    @JsonProperty(ID)
    private String id;

    @JsonProperty(DATA_TYPE)
    private String dataType;

    @JsonProperty(NAME)
    private String name;

    @JsonProperty(REQUIRED)
    private boolean required;

    @JsonProperty(TYPE)
    private String type;

    // Optional fields
    @JsonProperty(ALLOWED_VALUES)
    private ArrayList<Object> allowedValues;

    @JsonProperty(DEVICE_MODEL)
    private String deviceModel;

    @JsonProperty(INSTRUCTIONS)
    private String instructions;

    @JsonProperty(MESSAGE_FORMAT)
    private String messageFormat;

    @JsonProperty(MESSAGE_FORMAT_FIELD)
    private String messageFormatField;

    @JsonProperty(UNIQUE)
    private boolean unique;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public VehicleAttribute(String dataType, String name, boolean required, String type) {
        this.dataType = dataType;
        this.name = name;
        this.required = required;
        this.type = type;
    }

    public VehicleAttribute(String name, String dataType, String deviceModel, boolean required, boolean unique, String messageFormatField, String type) {
        this.dataType = dataType;
        this.name = name;
        this.required = required;
        this.type = type;
        this.deviceModel = deviceModel;
        this.messageFormatField = messageFormatField;
        this.unique = unique;
    }

    // Empty constructor for jackson to serialize/deserialize
    public VehicleAttribute() {}


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getId() { return id; }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Object> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(ArrayList<Object> allowedValues) {
        this.allowedValues = allowedValues;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getMessageFormatField() {
        return messageFormatField;
    }

    public void setMessageFormatField(String messageFormatField) {
        this.messageFormatField = messageFormatField;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    //endregion
    
}
