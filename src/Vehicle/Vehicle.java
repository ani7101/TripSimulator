package Vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Vehicle {

    // Defining the mapping structure for the response parser
    public static final String NAME = "name";

    public static final String MODEL = "model";

    public static final String MAKE = "MAKE";

    public static final String REGISTRATION_NUMBER = "registrationNumber";

    public static final String TYPE = "type";

    public static final String VIN = "vin";

    public static final String YEAR = "year";

    public static final String DESCRIPTION = "description";

    public static final String ATTRIBUTES = "attributes";

    @JsonProperty(NAME)
    private String name;

    @JsonProperty(MODEL)
    private String model;

    @JsonProperty(MAKE)
    private String make;

    @JsonProperty(REGISTRATION_NUMBER)
    private String registrationNumber;

    @JsonProperty(TYPE)
    private String type;

    @JsonProperty(VIN)
    private String vin;

    @JsonProperty(YEAR)
    private int year;

    // Optional fields
    @JsonProperty(DESCRIPTION)
    private String description;

    @JsonProperty(ATTRIBUTES)
    private ArrayList<VehicleAttributeMinimal> attributes;

    // Constructor
    public Vehicle(String name, String model, String make, String registrationNumber, String type, String vin, int year) {
        this.name = name;
        this.model = model;
        this.make = make;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.vin = vin;
        this.year = year;
    }

    public Vehicle(String name, String model, String make, String registrationNumber, String type, String vin, int year, ArrayList<VehicleAttributeMinimal> attributes) {
        this.name = name;
        this.model = model;
        this.make = make;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.vin = vin;
        this.year = year;
        this.attributes = attributes;
    }

    public Vehicle(String name, String model, String make, String registrationNumber, String type, String vin, int year, String description, ArrayList<VehicleAttributeMinimal> attributes) {
        this.name = name;
        this.model = model;
        this.make = make;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.vin = vin;
        this.year = year;
        this.description = description;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<VehicleAttributeMinimal> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<VehicleAttributeMinimal> attributes) {
        this.attributes = attributes;
    }
}
