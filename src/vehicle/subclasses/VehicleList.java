package vehicle.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import utils.IoTDeserializerList;
import vehicle.Vehicle;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleList extends IoTDeserializerList {
    public static final String ITEMS = "items";

    @JsonProperty(ITEMS)
    protected ArrayList<Vehicle> items;

    public ArrayList<Vehicle> getItems() { return items; }
}
