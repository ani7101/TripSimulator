package Trip;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Map;

public class Trip {

    // Defining the mapping structure for the response parser
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

    public static final String TRIP_TEMPLATE_ID = "tripTemplateId";

    public static final String VEHICLE = "vehicle";


    @JsonProperty(DESTINATION)
    private TripStopRecord destination;         // Optional

    @JsonProperty(DRIVER)
    private TripDriverInfoModel driver;         // Optional

    @JsonProperty(EARLIEST_START_TIME)
    private int earliestStartTime;              // Optional

    @JsonProperty(EXPECTED_DURATION)
    private long expectedDuration;               // Optional

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

    @JsonProperty(TRIP_TEMPLATE_ID)
    private String tripTemplateId;              // Optional

    @JsonProperty(VEHICLE)
    private TripVehicleInfoModel vehicle;       // Optional

    private ArrayList<Object> route;            // Required for simulation

    public ArrayList<Object> getRoute() { return route; }

    public void setRoute(ArrayList<Object> route) { this.route = route; }

    public TripStopRecord getDestination() {
        return destination;
    }

    // Parsing a hashMap into a Trip class
    public Trip(Map<String, Object> tripDetails) {

    }

    public Trip() {}

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

    public String getTripTemplateId() {
        return tripTemplateId;
    }

    public void setTripTemplateId(String tripTemplateId) {
        this.tripTemplateId = tripTemplateId;
    }

    public TripVehicleInfoModel getVehicle() {
        return vehicle;
    }

    public void setVehicle(TripVehicleInfoModel vehicle) {
        this.vehicle = vehicle;
    }
}
