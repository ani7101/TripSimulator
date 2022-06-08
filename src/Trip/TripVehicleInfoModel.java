package Trip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TripVehicleInfoModel {

    // Defining the mapping structure for the response parser
    public static final String NAME = "name";

    @JsonProperty(NAME)
    private String name; // Optional

    public TripVehicleInfoModel(String name) {
        this.name = name;
    }
    public TripVehicleInfoModel() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
