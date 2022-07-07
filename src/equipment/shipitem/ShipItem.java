package equipment.shipitem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import equipment.subclasses.AttributeValue;
import equipment.subclasses.Tracker;

import java.io.Serializable;
import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipItem implements Serializable {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String SHIP_ORDER_NUMBER = "shipOrderNumber";

    public static final String SHIP_UNIT_NUMBER = "shipUnitNumber";

    public static final String TRIP_EQUIPMENT_REF = "tripEquipmentRef";

    public static final String SHIP_ITEM_KEY = "shipItemKey";

    public static final String SHIP_ITEM_NUMBER = "shipItemNumber";

    public static final String COMMODITY = "commodity";

    public static final String WEIGHT = "weight";

    public static final String QUANTITY = "quantity";

    public static final String VOLUME = "volume";

    public static final String PICKUP_STOP_SEQUENCE = "pickupStopSequence";

    public static final String DROP_STOP_SEQUENCE = "dropStopSequence";

    public static final String TRACKERS = "trackers";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(SHIP_ORDER_NUMBER)
    private String shipOrderNumber;

    @JsonProperty(SHIP_UNIT_NUMBER)
    private String shipUnitNumber;

    @JsonProperty(TRIP_EQUIPMENT_REF)
    private String tripEquipmentRef;

    @JsonProperty(SHIP_ITEM_KEY)
    private String shipItemKey;

    @JsonProperty(SHIP_ITEM_NUMBER)
    private String shipItemNumber;

    @JsonProperty(COMMODITY)
    private String commodity;


    @JsonProperty(WEIGHT)
    private AttributeValue weight;

    @JsonProperty(QUANTITY)
    private int quantity;

    @JsonProperty(VOLUME)
    private AttributeValue volume;

    @JsonProperty(PICKUP_STOP_SEQUENCE)
    private int pickupStopSequence;

    @JsonProperty(DROP_STOP_SEQUENCE)
    private int dropStopSequence;

    @JsonProperty(TRACKERS)
    private ArrayList<Tracker> trackers;

    @JsonIgnore
    private ShipOrder shipOrder;

    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------


    public String getShipOrderNumber() {
        return shipOrderNumber;
    }

    public void setShipOrderNumber(String shipOrderNumber) {
        this.shipOrderNumber = shipOrderNumber;
    }

    public String getShipUnitNumber() {
        return shipUnitNumber;
    }

    public void setShipUnitNumber(String shipUnitNumber) {
        this.shipUnitNumber = shipUnitNumber;
    }

    public String getTripEquipmentRef() { return tripEquipmentRef; }

    public void setTripEquipmentRef(String tripEquipmentRef) { this.tripEquipmentRef = tripEquipmentRef; }

    public String getShipItemKey() {
        return shipItemKey;
    }

    public void setShipItemKey(String shipItemKey) {
        this.shipItemKey = shipItemKey;
    }

    public String getShipItemNumber() {
        return shipItemNumber;
    }

    public void setShipItemNumber(String shipItemNumber) {
        this.shipItemNumber = shipItemNumber;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public AttributeValue getWeight() {
        return weight;
    }

    public void setWeight(AttributeValue weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public void setTrackers(ArrayList<Tracker> trackers) {
        this.trackers = trackers;
    }

    @JsonIgnore
    public ShipOrder getShipOrder() { return shipOrder; }

    @JsonIgnore
    public void setShipOrder(ShipOrder shipOrder) { this.shipOrder = shipOrder; }

    //endregion

}
