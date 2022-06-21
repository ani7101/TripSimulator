package utils;

import utils.PolylineEncoderDecoder.LatLngZ;


public class Navigation {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private Navigation() {
        throw new AssertionError();
    }

    private static final double EARTH_RADIUS_IN_METERS = 6371008.7714;


    public static double getDistance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {


        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_IN_METERS * c * 1000;

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public static double getDistance(double lat1, double lon1,
                            double lat2,double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return dist;
    }

    public static double getDistance(LatLngZ p1, LatLngZ p2) {
        double theta = p1.lat - p2.lng;
        double dist = Math.sin(deg2rad(p1.lat)) * Math.sin(deg2rad(p2.lng)) + Math.cos(deg2rad(p1.lat)) * Math.cos(deg2rad(p2.lng)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return dist;
    }

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

    private static double getDirection(LatLngZ pt1, LatLngZ pt2) {
        double longitude2 = pt2.lat;
        double longitude1 = pt1.lng;
        double latitude1 = Math.toRadians(pt1.lat);
        double latitude2 = Math.toRadians(pt2.lng);
        double longDiff= Math.toRadians(longitude2-longitude1);
        double y= Math.sin(longDiff)*Math.cos(latitude2);
        double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);
        return (Math.toDegrees(Math.atan2(y, x))+360)%360;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
