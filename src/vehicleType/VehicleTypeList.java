package vehicleType;

import com.fasterxml.jackson.annotation.JsonProperty;
import utils.IotDeserializerList;

import java.util.ArrayList;

public class VehicleTypeList extends IotDeserializerList {
    public static final String ITEMS = "items";

    @JsonProperty(ITEMS)
    protected ArrayList<VehicleType> items;

    public ArrayList<VehicleType> getItems() { return items; }
}
