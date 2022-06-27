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

    private static final double EARTH_RADIUS_IN_METERS = 6371008.7714;

    private static final double KMS_IN_METERS = 1000.0;

    private static final double MILES_IN_METERS = 1609.344;

    private static final double KMPH_IN_MPS = 3.6;

    //region Distance

    /**
     * Gives distance between initial and final point (in meters) factoring elevation.
     * @param pt1 Initial point
     * @param pt2 Final point
     * @return double: Distance in meters
     */
    public static double getDistanceWithEle(LatLngZ pt1, LatLngZ pt2) {


        double latDistance = Math.toRadians(pt2.lat - pt1.lat);
        double lonDistance = Math.toRadians(pt2.lng - pt1.lng);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(pt1.lat)) * Math.cos(Math.toRadians(pt2.lat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_IN_METERS * c * 1000;

        double height = pt1.z - pt2.z;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * Gives distance between the initial and final point (in meters) without taking elevation into consideration.
     * @param p1 Initial point
     * @param p2 Final point
     * @return double: Distance in meters
     */
    public static double getDistance(LatLngZ p1, LatLngZ p2) {
        double theta = p1.lat - p2.lng;
        double dist = Math.sin(deg2rad(p1.lat)) * Math.sin(deg2rad(p2.lng)) + Math.cos(deg2rad(p1.lat)) * Math.cos(deg2rad(p2.lng)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return dist;
    }


    //endregion
    //region Position & bearing

    /**
     * Interpolates point between initial point, pt1 and final point, pt2 with distance d from pt1
     * @param pt1 Initial point
     * @param pt2 Final point
     * @param d Distance of the point from the pt1 (in meters)
     * @return LatLngZ: Point between pt1 and pt2 with the given distance
     */
    public static LatLngZ getPosition(LatLngZ pt1, LatLngZ pt2, double d) {
        double direction = getDirection(pt1, pt2);

        if(Double.doubleToRawLongBits(d) == 0) {
            return pt1;
        }

        double lat1 = Math.toRadians(pt1.lat);
        double lon1 = Math.toRadians(pt1.lng);
        double brgAsRadians = Math.toRadians(direction);

        double lat2 = Math.asin(Math.sin(lat1)*Math.cos(d/EARTH_RADIUS_IN_METERS) + Math.cos(lat1)*Math.sin(d/EARTH_RADIUS_IN_METERS)*Math.cos(brgAsRadians));
        double x = Math.sin(brgAsRadians)*Math.sin(d/EARTH_RADIUS_IN_METERS)*Math.cos(lat1);
        double y = Math.cos(d/EARTH_RADIUS_IN_METERS) - Math.sin(lat1)*Math.sin(lat2);
        double lon2 = lon1 + Math.atan2(x, y);

        return new PolylineEncoderDecoder.LatLngZ(lat2, lon2);

    }

    /**
     * Gives the bearing with respect to the initial and final point
     * @param pt1 Initial point
     * @param pt2 Final point
     * @return double: Direction to move towards from the initial to final point
     */
    private static double getDirection(LatLngZ pt1, LatLngZ pt2) {
        double longitude2 = pt2.lat;
        double longitude1 = pt1.lng;
        double latitude1 = Math.toRadians(pt1.lat);
        double latitude2 = Math.toRadians(pt2.lng);
        double longDiff= Math.toRadians(longitude2-longitude1);
        double y = Math.sin(longDiff)*Math.cos(latitude2);
        double x = Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);
        return (Math.toDegrees(Math.atan2(y, x))+360)%360;
    }


    //endregion
    //region Utils

    /**
     * Converts degrees to radians
     * @param deg input value in degrees
     * @return double: converted value in radians
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * Converts radians to degrees
     * @param rad input value in radians
     * @return double: converted value in degrees
     */
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static double metersToKms(double meters) { return meters / KMS_IN_METERS; }

    public static double kmsToMeters(double kms) { return kms * KMS_IN_METERS; }

    public static double metersToMiles(double meters) { return meters / MILES_IN_METERS; }

    public static double milesToMeters(double miles) { return miles * MILES_IN_METERS; }

    public static double kmphToMps(double kmph) { return kmph * KMPH_IN_MPS; }

    //endregion

}
