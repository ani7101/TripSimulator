package trip.tripSubClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripStopRecord {

    // Defining the mapping structure for the response parser
    public final static String GEO_LOCATION = "geoLocation";

    public final static String ADDRESS = "address";

    public final static String CITY = "city";

    public final static String COUNTRY = "country";

    public final static String DISTANCE_TO_NEXT_STOP = "distanceToNextStop";

    public final static String EXPECTED_DURATION_FROM_START = "expectedDurationFromStart";

    public final static String POSTAL_CODE = "postalCode";

    public final static String STATE = "state";

    public final static String STOP_DURATION_IN_SECONDS = "stopDurationInSeconds";

    public final static String STREET = "street";

    public final static String TIME_ZONE = "timeZone";


    @JsonProperty(GEO_LOCATION)
    private ArrayList<Double> geoLocation;        // Mandatory

    @JsonProperty(ADDRESS)
    private String address;                 // Optional

    @JsonProperty(CITY)
    private String city;                    // Optional

    @JsonProperty(COUNTRY)
    private String country;                 // Optional

    @JsonProperty(DISTANCE_TO_NEXT_STOP)
    private double distanceToNextStop;      // Optional

    @JsonProperty(EXPECTED_DURATION_FROM_START)
    private int expectedDurationFromStart;  // Optional

    @JsonProperty(POSTAL_CODE)
    private String postalCode;              // Optional

    @JsonProperty(STATE)
    private String state;                   // Optional

    @JsonProperty(STOP_DURATION_IN_SECONDS)
    private int stopDurationInSeconds;      // Optional

    @JsonProperty(STREET)
    private String street;                  // Optional

    @JsonProperty(TIME_ZONE)
    private String timeZone;                // Optional

    public TripStopRecord(double latitude, double longitude, ArrayList<Double> additionalElements) {
        geoLocation = new ArrayList<Double>();
        geoLocation.add(latitude);  // At index 0
        geoLocation.add(longitude); // At index 1
        for (Double element : additionalElements) {
            geoLocation.add(element);
        }
    }

    public TripStopRecord(ArrayList<Double> geoLocation) {
        this.geoLocation = geoLocation;
    }

    public TripStopRecord() {}

    public ArrayList<Double> getGeoLocation() { return geoLocation; }

    public double getLatitude() {
        return geoLocation.get(0);
    }

    public double getLongitude() {
        return geoLocation.get(1);
    }

    public void setGeoLocation(ArrayList<Double> geoLocation) { this.geoLocation = geoLocation; }

    public void setLatitude(double latitude) {
        this.geoLocation.add(0, latitude);
    }

    public void setLongitude(double longitude) {
        this.geoLocation.add(0, longitude);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getDistanceToNextStop() {
        return distanceToNextStop;
    }

    public void setDistanceToNextStop(double distanceToNextStop) {
        this.distanceToNextStop = distanceToNextStop;
    }

    public int getExpectedDurationFromStart() {
        return expectedDurationFromStart;
    }

    public void setExpectedDurationFromStart(int expectedDurationFromStart) {
        this.expectedDurationFromStart = expectedDurationFromStart;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStopDurationInSeconds() {
        return stopDurationInSeconds;
    }

    public void setStopDurationInSeconds(int stopDurationInSeconds) {
        this.stopDurationInSeconds = stopDurationInSeconds;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
