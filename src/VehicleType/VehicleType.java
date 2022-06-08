package VehicleType;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class VehicleType {

    // Defining the mapping structure for the response parser
    public static final String NAME = "name";
    public static final String YEARS = "years";
    public static final String ATTRIBUTES = "attributes";
    public static final String DESCRIPTION = "description";
    public static final String MAKE = "make";
    public static final String MODEL = "model";

    // Mandatory fields
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

    public VehicleType(String name, ArrayList<Integer> years) {
        this.name = name;
        this.years = years;
    }

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
}
