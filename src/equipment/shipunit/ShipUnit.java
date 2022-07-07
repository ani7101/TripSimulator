package equipment.shipunit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import equipment.shipitem.ShipItem;
import equipment.subclasses.AttributeValue;
import equipment.subclasses.Tracker;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipUnit implements Serializable {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String SHIP_ORDER_NUMBERS = "shipOrderNumbers";

    public static final String SHIP_UNIT_NUMBER = "shipUnitNumber";

    public static final String TRIP_EQUIPMENT_REF = "tripEquipmentRef";

    public static final String SHIP_UNIT_KEY = "shipUnitKey";

    public static final String SHIP_UNIT_TYPE = "shipUnitType";

    public static final String COMMODITIES = "commodities";


    public static final String WEIGHT = "weight";

    public static final String VOLUME = "volume";


    public static final String PICKUP_STOP_SEQUENCE = "pickupStopSequence";

    public static final String DROP_STOP_SEQUENCE = "dropStopSequence";

    public static final String TRACKERS = "trackers";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(SHIP_ORDER_NUMBERS)
    private ArrayList<String> shipOrderNumbers;

    @JsonProperty(SHIP_UNIT_NUMBER)
    private String shipUnitNumber;

    @JsonProperty(TRIP_EQUIPMENT_REF)
    private String tripEquipmentRef;

    @JsonProperty(SHIP_UNIT_KEY)
    private String shipUnitKey;

    @JsonProperty(SHIP_UNIT_TYPE)
    private String shipUnitType;

    @JsonProperty(COMMODITIES)
    private ArrayList<String> commodities;

    @JsonProperty(WEIGHT)
    private AttributeValue weight;

    @JsonProperty(VOLUME)
    private AttributeValue volume;

    @JsonProperty(PICKUP_STOP_SEQUENCE)
    private int pickupStopSequence;

    @JsonProperty(DROP_STOP_SEQUENCE)
    private int dropStopSequence;

    @JsonProperty(TRACKERS)
    private ArrayList<Tracker> trackers;

    @JsonIgnore
    private ArrayList<ShipItem> shipItems;

    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public ArrayList<String> getShipOrderNumbers() {
        return shipOrderNumbers;
    }

    public void setShipOrderNumbers(ArrayList<String> shipOrderNumbers) {
        this.shipOrderNumbers = shipOrderNumbers;
    }

    public String getShipUnitNumber() {
        return shipUnitNumber;
    }

    public void setShipUnitNumber(String shipUnitNumber) {
        this.shipUnitNumber = shipUnitNumber;
    }

    public String getTripEquipmentRef() {
        return tripEquipmentRef;
    }

    public void setTripEquipmentRef(String tripEquipmentRef) {
        this.tripEquipmentRef = tripEquipmentRef;
    }

    public String getShipUnitKey() {
        return shipUnitKey;
    }

    public void setShipUnitKey(String shipUnitKey) {
        this.shipUnitKey = shipUnitKey;
    }

    public String getShipUnitType() {
        return shipUnitType;
    }

    public void setShipUnitType(String shipUnitType) {
        this.shipUnitType = shipUnitType;
    }

    public ArrayList<String> getCommodities() {
        return commodities;
    }

    public void setCommodities(ArrayList<String> commodities) {
        this.commodities = commodities;
    }

    public AttributeValue getWeight() {
        return weight;
    }

    public void setWeight(AttributeValue weight) {
        this.weight = weight;
    }

    public AttributeValue getVolume() {
        return volume;
    }

    public void setVolume(AttributeValue volume) {
        this.volume = volume;
    }

    public int getPickupStopSequence() {
        return pickupStopSequence;
    }

    public void setPickupStopSequence(int pickupStopSequence) {
        this.pickupStopSequence = pickupStopSequence;
    }

    public int getDropStopSequence() {
        return dropStopSequence;
    }

    public void setDropStopSequence(int dropStopSequence) {
        this.dropStopSequence = dropStopSequence;
    }

    public ArrayList<Tracker> getTrackers() {
        return trackers;
    }

    public void setTrackers (ArrayList<Tracker> trackers) {
        this.trackers = trackers;
    }

    @JsonIgnore
    public ArrayList<ShipItem> getShipItems() {
        return shipItems;
    }

    @JsonIgnore
    public void setShipItems(ArrayList<ShipItem> shipItems) {
        this.shipItems = shipItems;
    }

    //endregion

}
