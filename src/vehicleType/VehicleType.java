package vehicleType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import vehicleType.subclasses.VehicleAttribute;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleType {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String YEARS = "years";
    public static final String ATTRIBUTES = "attributes";
    public static final String DESCRIPTION = "description";
    public static final String MAKE = "make";
    public static final String MODEL = "model";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    // Mandatory fields
    @JsonProperty(ID)
    private String id;

    @JsonProperty(NAME)
    private String name;

    @JsonProperty(YEARS)
    private ArrayList<Integer> years;

    // Optional fields
    @JsonProperty(ATTRIBUTES)
    private ArrayList<VehicleAttribute> attributes;

    @JsonProperty(DESCRIPTION)
    private String description;

    @JsonProperty(MAKE)
    private String make;

    @JsonProperty(MODEL)
    private String model;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public VehicleType(String name, ArrayList<Integer> years) {
        this.name = name;
        this.years = years;
    }

    // Empty constructor for jackson to serialize/deserialize
    public VehicleType() {}


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getYears() {
        return years;
    }

    public void setYears(ArrayList<Integer> years) {
        this.years = years;
    }

    public ArrayList<VehicleAttribute> getAttributes() {
        return attributes;
    }

    public VehicleAttribute getAttributes(int index) {
        return attributes.get(index);
    }

    public void setAttributes(ArrayList<VehicleAttribute> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(VehicleAttribute attribute) {
        attributes.add(attribute);
    }

    public void removeAttribute(int index) {
        attributes.remove(index);
    }

    public void removeAttribute(VehicleAttribute attribute) {
        attributes.remove(attribute);
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    //endregion

}
