package trip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import equipment.Equipment;
import equipment.shipitem.ShipItem;
import equipment.shipitem.ShipOrder;
import equipment.shipunit.ShipUnit;
import trip.subclasses.TripDriverInfoModel;
import trip.subclasses.TripStopRecord;
import trip.subclasses.TripVehicleInfoModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trip implements Serializable {

    public static final String TRIP_STATUS_VALUE = "IN_PROGRESS";

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String ID = "id";

    public static final String DESTINATION = "destination";

    public static final String DRIVER = "driver";

    public static final String EARLIEST_START_TIME = "earliestStartTime";

    public static final String EXPECTED_DURATION = "expectedDuration";

    public static final String LATEST_START_TIME = "latestStartTime";

    public static final String NAME = "name";

    public static final String PLANNED_DRIVE_DISTANCE = "plannedDriveDistance";

    public static final String PLANNED_DRIVE_DURATION_SECONDS = "plannedDriveDurationSeconds";

    public static final String SOURCE = "source";

    public static final String STOPS = "stops";

    public static final String VEHICLE = "vehicle";

    public static final String EQUIPMENTS = "equipments";

    public static final String SHIP_ORDERS = "shipOrders";

    public static final String SHIP_UNITS = "shipUnits";

    public static final String SHIP_ITEMS = "shipItems";

    public static final String TRIP_STATUS = "tripStatus";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(ID)
    private String id;

    @JsonProperty(DESTINATION)
    private TripStopRecord destination;         // Optional

    @JsonProperty(DRIVER)
    private TripDriverInfoModel driver;         // Optional

    @JsonProperty(EARLIEST_START_TIME)
    private int earliestStartTime;              // Optional

    @JsonProperty(EXPECTED_DURATION)
    private long expectedDuration;              // Optional

    @JsonProperty(LATEST_START_TIME)
    private int latestStartTime;                // Optional

    @JsonProperty(NAME)
    private String name;                        // Optional

    @JsonProperty(PLANNED_DRIVE_DISTANCE)
    private long plannedDriveDistance;           // Optional

    @JsonProperty(PLANNED_DRIVE_DURATION_SECONDS)
    private long plannedDriveDurationSeconds;    // Optional

    @JsonProperty(SOURCE)
    private TripStopRecord source;              // Optional

    @JsonProperty(STOPS)
    private ArrayList<TripStopRecord> stops;    // Optional

    @JsonProperty(VEHICLE)
    private TripVehicleInfoModel vehicle;       // Optional

    @JsonProperty(EQUIPMENTS)
    private ArrayList<Equipment> equipments;

    @JsonProperty(SHIP_ORDERS)
    private ArrayList<ShipOrder> shipOrders;

    @JsonProperty(SHIP_UNITS)
    private ArrayList<ShipUnit> shipUnits;

    @JsonProperty(SHIP_ITEMS)
    private ArrayList<ShipItem> shipItems;

    @JsonProperty(TRIP_STATUS)
    private String tripStatus;

    @JsonIgnore
    private ArrayList<String> route;            // Required for simulation


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public Trip(TripStopRecord source, TripStopRecord destination, ArrayList<TripStopRecord> stops) {
        this.destination = destination;
        this.source = source;
        this.stops = stops;

        tripStatus = TRIP_STATUS_VALUE;
    }

    public Trip(TripStopRecord source, TripStopRecord destination, ArrayList<TripStopRecord> stops, TripVehicleInfoModel vehicle, TripDriverInfoModel driver) {
        this.destination = destination;
        this.driver = driver;
        this.source = source;
        this.stops = stops;
        this.vehicle = vehicle;

        tripStatus = TRIP_STATUS_VALUE;
    }

    public Trip(TripStopRecord source, TripStopRecord destination, ArrayList<TripStopRecord> stops, TripVehicleInfoModel vehicle) {
        this.destination = destination;
        this.source = source;
        this.stops = stops;
        this.vehicle = vehicle;

        tripStatus = TRIP_STATUS_VALUE;
    }

    // Empty constructor for jackson to serialize/deserialize
    public Trip() {}


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    @JsonIgnore
    public ArrayList<String> getRoute() { return route; }

    @JsonIgnore
    public void setRoute(ArrayList<String> route) { this.route = route; }

    public TripStopRecord getDestination() {
        return destination;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public void setDestination(TripStopRecord destination) {
        this.destination = destination;
    }

    public TripDriverInfoModel getDriver() {
        return driver;
    }

    public void setDriver(TripDriverInfoModel driver) {
        this.driver = driver;
    }

    public int getEarliestStartTime() {
        return earliestStartTime;
    }

    public void setEarliestStartTime(int earliestStartTime) {
        this.earliestStartTime = earliestStartTime;
    }

    public long getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(long expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    public int getLatestStartTime() {
        return latestStartTime;
    }

    public void setLatestStartTime(int latestStartTime) {
        this.latestStartTime = latestStartTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPlannedDriveDistance() {
        return plannedDriveDistance;
    }

    public void setPlannedDriveDistance(long plannedDriveDistance) {
        this.plannedDriveDistance = plannedDriveDistance;
    }

    public long getPlannedDriveDurationSeconds() {
        return plannedDriveDurationSeconds;
    }

    public void setPlannedDriveDurationSeconds(long plannedDriveDurationSeconds) {
        this.plannedDriveDurationSeconds = plannedDriveDurationSeconds;
    }

    public TripStopRecord getSource() {
        return source;
    }

    public void setSource(TripStopRecord source) {
        this.source = source;
    }

    public ArrayList<TripStopRecord> getStops() {
        return stops;
    }

    public void setStops(ArrayList<TripStopRecord> stops) {
        this.stops = stops;
    }

    public TripVehicleInfoModel getVehicle() {
        return vehicle;
    }

    public void setVehicle(TripVehicleInfoModel vehicle) {
        this.vehicle = vehicle;
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(ArrayList<Equipment> equipments) {
        this.equipments = equipments;
    }

    public ArrayList<ShipOrder> getShipOrders() {
        return shipOrders;
    }

    public void setShipOrders(ArrayList<ShipOrder> shipOrders) {
        this.shipOrders = shipOrders;
    }

    public ArrayList<ShipUnit> getShipUnits() {
        return shipUnits;
    }

    public void setShipUnits(ArrayList<ShipUnit> shipUnits) {
        this.shipUnits = shipUnits;
    }

    public ArrayList<ShipItem> getShipItems() {
        return shipItems;
    }

    public void setShipItems(ArrayList<ShipItem> shipItems) {
        this.shipItems = shipItems;
    }

    public String getTripStatus() { return tripStatus; }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    //endregion

}
