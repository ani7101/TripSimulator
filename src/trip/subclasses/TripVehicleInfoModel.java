package trip.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripVehicleInfoModel {

    // Defining the mapping structure for the response parser
    public static final String NAME = "name";

    @JsonProperty(NAME)
    private String name;

    @JsonIgnore
    private String deviceId;

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

    public String getDeviceId() { return deviceId; }

    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
}
