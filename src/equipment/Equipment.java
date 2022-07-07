package equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import equipment.shipunit.ShipUnit;
import equipment.subclasses.AttributeValue;
import equipment.subclasses.Tracker;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipment implements Serializable {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String EQUIPMENT_NUMBER = "equipmentNumber";

    public static final String TRIP_EQUIPMENT_REF = "tripEquipmentRef";

    public static final String EQUIPMENT_TYPE = "equipmentType";

    public static final String PICKUP_STOP_SEQUENCE = "pickupStopSequence";

    public static final String DROP_STOP_SEQUENCE = "dropStopSequence";

    public static final String MAX_WEIGHT = "maxWeight";

    public static final String MAX_VOLUME = "maxVolume";

    public static final String TRACKERS = "trackers";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(EQUIPMENT_NUMBER)
    private String equipmentNumber;

    @JsonProperty(TRIP_EQUIPMENT_REF)
    private String tripEquipmentRef;

    @JsonProperty(EQUIPMENT_TYPE)
    private String equipmentType = "Default type";

    @JsonProperty(PICKUP_STOP_SEQUENCE)
    private int pickupStopSequence;

    @JsonProperty(DROP_STOP_SEQUENCE)
    private int dropStopSequence;

    @JsonProperty(MAX_WEIGHT)
    private AttributeValue maxWeight;

    @JsonProperty(MAX_VOLUME)
    private AttributeValue maxVolume;

    @JsonProperty(TRACKERS)
    private ArrayList<Tracker> trackers;

    @JsonIgnore
    private ArrayList<ShipUnit> shipUnits = new ArrayList<>();


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------


    public Equipment(String equipmentNumber, String tripEquipmentRef, int pickupStopSequence, int dropStopSequence, AttributeValue maxWeight, AttributeValue maxVolume, ArrayList<Tracker> trackers) {
        this.equipmentNumber = equipmentNumber;
        this.tripEquipmentRef = tripEquipmentRef;
        this.pickupStopSequence = pickupStopSequence;
        this.dropStopSequence = dropStopSequence;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.trackers = trackers;
    }

    public Equipment() {
    }


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------


    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getTripEquipmentRef() {
        return tripEquipmentRef;
    }

    public void setTripEquipmentRef(String tripEquipmentRef) {
        this.tripEquipmentRef = tripEquipmentRef;
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

    public AttributeValue getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(AttributeValue maxWeight) {
        this.maxWeight = maxWeight;
    }

    public AttributeValue getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(AttributeValue maxVolume) {
        this.maxVolume = maxVolume;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public ArrayList<Tracker> getTrackers() {
        return trackers;
    }

    public void setTrackers(ArrayList<Tracker> trackers) {
        this.trackers = trackers;
    }

    @JsonIgnore
    public ArrayList<ShipUnit> getShipUnits() {
        return shipUnits;
    }

    @JsonIgnore
    public void setShipUnits(ArrayList<ShipUnit> shipUnits) {
        this.shipUnits = shipUnits;
    }

    //endregion

}
