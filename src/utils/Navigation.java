package utils;

import utils.PolylineEncoderDecoder.LatLngZ;

/**
 * Utility function to handle the distances and points in maps
 */
public class Navigation {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private Navigation() {
        throw new AssertionError();
    }

    private static final double EARTH_RADIUS = 6371008.7714; // in meters

    private static final double KMS_IN_METERS = 1000.0;

    private static final double MILES_PER_HOUR_TO_KMPH = 1.60934;

    private static final double MILES_PER_HOUR_TO_METERS_PER_SECOND = 2.23694;

    //region Distance

    /**
     * Calculate distance between two points in lat and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * <br>
     * <a href="https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude">Reference</a>
     * @return double: Distance in Meters
     */
    public static double getDistance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * Gives distance between the initial and final point (in meters) without taking elevation into consideration.
     * @param pt1 Initial point
     * @param pt2 Final point
     * @return double: Distance in meters
     */
    public static double getDistance(LatLngZ pt1, LatLngZ pt2) {
        return getDistance(pt1.lat, pt2.lat, pt1.lng, pt2.lng, pt1.z, pt2.z);
    }


    //endregion
    //region Position & bearing

    /**
     * Interpolates point between initial point, pt1 and final point, pt2 with distance d from pt1
     * @param point Initial point
     * @param bearing Bearing/direction from the initial point to the final point
     * @param distance Distance of the point from the pt1 (in meters)
     * @return LatLngZ: Point between pt1 and pt2 with the given distance
     */
    public static LatLngZ getPosition(LatLngZ point, double bearing, double distance) {
        distance = distance / EARTH_RADIUS;
        bearing = Math.toRadians(bearing);
        double lat1 = Math.toRadians(point.lat);
        double lon1 = Math.toRadians(point.lng);
        double lat2 = Math.asin(
                Math.sin(lat1) * Math.cos(distance) + Math.cos(lat1) * Math.sin(distance) * Math.cos(bearing)
                );
        double lon2 = lon1 + Math.atan2(
                Math.sin(bearing) * Math.sin(distance) * Math.cos(lat1),
                Math.cos(distance) - Math.sin(lat1) * Math.sin(lat2)
                );
        lon2 = (lon2 + 3 * Math.PI) % (2 * Math.PI) - Math.PI;
        return new LatLngZ(Math.toDegrees(lat2), Math.toDegrees(lon2));
    }

    /**
     * Gives the bearing with respect to the initial and final point
     * @param pt1 Initial point
     * @param pt2 Final point
     * @return double: Direction (bearing) to move towards from the initial to final point
     */
    public static double getBearing(LatLngZ pt1, LatLngZ pt2) {
        double lat1 = Math.toRadians(pt1.lat);
        double lat2 = Math.toRadians(pt2.lat);
        double deltaLon = Math.toRadians(pt2.lng - pt1.lng);
        double y = Math.sin(deltaLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLon);
        double bearing = Math.atan2(y, x);
        return (Math.toDegrees(bearing) + 360) % 360;
    }


    //endregion
    //region Conversion utils

    public static double metersToKms(double meters) { return meters / KMS_IN_METERS; }

    public static double kmsToMeters(double kms) { return kms * KMS_IN_METERS; }

    public static double milesPerHourToMetersPerSecond(double milesPerHour) {
        return milesPerHour / MILES_PER_HOUR_TO_METERS_PER_SECOND;
    }

    public static double metersPerSecondToMilesPerHour(double metersPerSecond) {
        return metersPerSecond * MILES_PER_HOUR_TO_METERS_PER_SECOND;
    }

    public static double milesPerHourToKmph(double milesPerHour) {
        return milesPerHour / MILES_PER_HOUR_TO_KMPH;
    }

    public static double kmphToMilesPerHour(double kmph) {
        return kmph * MILES_PER_HOUR_TO_KMPH;
    }

    //endregion

}
