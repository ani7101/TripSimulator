package trip.subclasses;

import java.util.ArrayList;

@Deprecated
public class GeoPosition {
    private ArrayList<String> additionalElements;
    private double altitude, latitude, longitude;
    private boolean valid;

    public GeoPosition(double latitude, double longitude, boolean valid, ArrayList<String> additionalElements) {
        this.latitude = latitude;                       // Mandatory
        this.longitude = longitude;                     // Mandatory
        this.valid = valid;                             // Mandatory
        this.additionalElements = additionalElements;   // Optional
    }

    public GeoPosition(double latitude, double longitude, boolean valid) {
        this(latitude, longitude, valid, new ArrayList<String>());
    }

    // Getters
    public ArrayList<String> getAdditionalElements() {
        return additionalElements;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isValid() {
        return valid;
    }

    // Setters
    public void setAdditionalElements(ArrayList<String> additionalElements) {
        this.additionalElements = additionalElements;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
