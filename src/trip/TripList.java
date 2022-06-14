package trip;

import com.fasterxml.jackson.annotation.JsonProperty;
import utils.IotDeserializerList;

import java.util.ArrayList;

public class TripList extends IotDeserializerList {
    public static final String ITEMS = "items";

    @JsonProperty(ITEMS)
    protected ArrayList<Trip> items;

    public ArrayList<Trip> getItems() { return items; }
}
