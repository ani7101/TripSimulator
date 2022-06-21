package trip.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import trip.Trip;
import utils.IoTDeserializerList;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripList extends IoTDeserializerList {
    public static final String ITEMS = "items";

    @JsonProperty(ITEMS)
    protected ArrayList<Trip> items;

    public ArrayList<Trip> getItems() { return items; }
}
