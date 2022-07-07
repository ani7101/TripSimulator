package vehicle.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleAttributeMinimal implements Serializable {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String NAME = "name";
    public static final String TYPE = "type";

    public static final String VALUE = "value";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(NAME)
    private String name;

    @JsonProperty(TYPE)
    private String type;

    @JsonProperty(VALUE)
    private VehicleAttributeValue value;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public VehicleAttributeMinimal(String name, String type) {
        this.name = name;
        this.type = type;
        this.value = new VehicleAttributeValue();
    }

    public VehicleAttributeMinimal(String name, String type,
                                   String deviceId, String deviceModel, String messageFormatField) {
        this.name = name;
        this.type = type;
        this.value = new VehicleAttributeValue(deviceId, deviceModel, messageFormatField);
    }

    // Empty constructor for jackson to serialize/deserialize
    public VehicleAttributeMinimal() {}


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VehicleAttributeValue getValue() {
        return value;
    }

    public void setValue(VehicleAttributeValue value) {
        this.value = value;
    }

    //endregion

}
