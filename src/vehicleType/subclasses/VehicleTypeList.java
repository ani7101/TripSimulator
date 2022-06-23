package vehicleType.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import utils.IoTDeserializerList;
import vehicleType.VehicleType;

import java.util.ArrayList;

/**
 * List using VehicleType as items and based on the IoTDeserializerList class to deserialize a list response from the IoT API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleTypeList extends IoTDeserializerList {
    public static final String ITEMS = "items";

    @JsonProperty(ITEMS)
    protected ArrayList<VehicleType> items;

    public ArrayList<VehicleType> getItems() { return items; }
}
