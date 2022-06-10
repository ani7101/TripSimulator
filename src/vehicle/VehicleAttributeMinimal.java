package vehicle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleAttributeMinimal {
    // Defining the mapping structure for the response parser
    public static final String NAME = "name";
    public static final String TYPE = "type";


    @JsonProperty(NAME)
    private String name;

    @JsonProperty(TYPE)
    private String type;

    public VehicleAttributeMinimal(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public VehicleAttributeMinimal() {}


    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() { return type; }

    public void setType(String type) {
        this.type = type;
    }
}
