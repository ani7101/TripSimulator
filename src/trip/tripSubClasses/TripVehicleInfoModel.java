package trip.tripSubClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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
