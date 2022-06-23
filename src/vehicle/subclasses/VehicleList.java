package vehicle.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import utils.IoTDeserializerList;
import vehicle.Vehicle;

import java.util.ArrayList;

/**
 * List using Vehicle as items and based on the IoTDeserializerList class to deserialize a list response from the IoT API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleList extends IoTDeserializerList {
    public static final String ITEMS = "items";

    @JsonProperty(ITEMS)
    protected ArrayList<Vehicle> items;

    public ArrayList<Vehicle> getItems() { return items; }
}
