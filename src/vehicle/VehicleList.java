package vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import utils.IotDeserializerList;

import java.util.ArrayList;

public class VehicleList extends IotDeserializerList {
    public static final String ITEMS = "items";

    @JsonProperty(ITEMS)
    protected ArrayList<Vehicle> items;

    public ArrayList<Vehicle> getItems() { return items; }
}
