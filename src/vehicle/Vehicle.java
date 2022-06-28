package vehicle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import vehicle.subclasses.VehicleAttributeMinimal;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String MODEL = "model";

    public static final String MAKE = "make";

    public static final String REGISTRATION_NUMBER = "registrationNumber";

    public static final String TYPE = "type";

    public static final String VIN = "vin";

    public static final String YEAR = "year";

    public static final String DESCRIPTION = "description";

    public static final String ATTRIBUTES = "attributes";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(ID)
    private String id;

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

    // Unaccounted json properties
    @JsonIgnore
    private String deviceName;

    @JsonIgnore
    private String deviceIdentifier;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public Vehicle(String name, String model, String make, String type, String registrationNumber, String vin, int year) {
        this.name = name;
        this.model = model;
        this.make = make;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.vin = vin;
        this.year = year;
    }

    public Vehicle(String name, String model, String make, String type, String registrationNumber, String vin, int year, ArrayList<VehicleAttributeMinimal> attributes) {
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

    // Empty constructor for jackson to serialize/deserialize
    public Vehicle() {}


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

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceName() { return deviceName; }

    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public String getDeviceIdentifier() { return deviceIdentifier; }

    public void setDeviceIdentifier(String deviceIdentifier) { this.deviceIdentifier = deviceIdentifier; }

    //endregion

}
