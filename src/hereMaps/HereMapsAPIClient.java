package hereMaps;

import hereMaps.deserializerClasses.hereMapsRoutes;
import utils.APIClient;
import utils.ParseJson;

import java.util.ArrayList;
import java.util.logging.Logger;

public class HereMapsAPIClient extends APIClient {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    //region geo-location

    /**
     * Retrieves the information (address, pin code, etc.) of the location given by latitude and longitude.
     * @param latitude Latitude of the point
     * @param longitude Longitude of the point
     * @return String json response containing address, pin code, etc
     */
    public static String getGeoLocation(double latitude, double longitude, String accessToken) {

        String url = "https://revgeocode.search.hereapi.com/v1/revgeocode?lang=en-US&at=" + latitude + "," + longitude;

        String response = null;

        try {
            response = AsyncGET(url, "Bearer " + accessToken);
        } catch (Exception e) {
            LOGGER.warning("Exception @HereMapsAPIClient: " + e);
        }
        return response;
    }


    //endregion
    //region Routing

    /**
     * Finds the route from the source to the destination via the stop points.
     * @param sourceLat Latitude of initial point
     * @param sourceLong Longitude of the initial point
     * @param destinationLat Latitude of the final point
     * @param destinationLong Longitude of the final point
     * @param stopsLat List of the latitudes of all the stops ordered by index
     * @param stopsLong List of the longitudes of all the stops ordered by index
     * @param accessToken OAuth access token for accessing the here maps
     * @return - Route containing polyline form of the route and summary details such as length and expected time
     */
    public static ArrayList<HereMapsRouteSection> getRoute(double sourceLat, double sourceLong,
                                                           double destinationLat, double destinationLong,
                                                           ArrayList<Double> stopsLat, ArrayList<Double> stopsLong,
                                                           String accessToken
    ) {
        // Generating the URL with the parameters
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://router.hereapi.com/v8/routes?transportMode=truck&lang=en-US&return=summary,elevation,polyline");

        urlBuilder.append("&origin=" + sourceLat + "," + sourceLong);
        urlBuilder.append("&destination=" + destinationLat + "," + destinationLong);

        for (int i = 0; i < stopsLat.size(); i++) {
            urlBuilder.append("&via=" + stopsLat.get(i) + "," + stopsLong.get(i));
        }

        ArrayList<HereMapsRouteSection> response = null;

        try {
            String json = AsyncGET(urlBuilder.toString(), "Bearer " + accessToken);

            hereMapsRoutes temp = ParseJson.deserializeResponse(json, hereMapsRoutes.class);
            response = temp.getRoutes().get(0).getSections(); // We take the first element in the routes array as only one call is being made at a time
        } catch (Exception e) {
            LOGGER.warning("Exception @HereMapsAPIClient: " + e);
        }

        return response;
    }


    /**
     * Finds the route from the source to the destination via the stop points.
     * @param sourceLat Latitude of initial point
     * @param sourceLong Longitude of the initial point
     * @param destinationLat Latitude of the final point
     * @param destinationLong Longitude of the final point
     * @param accessToken OAuth access token for accessing the here maps
     * @return - Route containing polyline form of the route and summary details such as length and expected time
     */
    public static ArrayList<HereMapsRouteSection> getRoute(double sourceLat, double sourceLong,
                                                           double destinationLat, double destinationLong,
                                                           String accessToken
    ) {

        // Generating the URL with the parameters
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://router.hereapi.com/v8/routes?transportMode=truck&lang=en-US&return=summary,elevation,polyline");

        urlBuilder.append("&origin=" + sourceLat + "," + sourceLong);
        urlBuilder.append("&destination=" + destinationLat + "," + destinationLong);

        ArrayList<HereMapsRouteSection> response = null;

        try {
            String json = AsyncGET(urlBuilder.toString(), "Bearer " + accessToken);

            hereMapsRoutes temp = ParseJson.deserializeResponse(json, hereMapsRoutes.class);
            response = temp.getRoutes().get(0).getSections(); // We take the first element in the routes array as only one call is being made at a time
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warning("Exception @HereMapsAPIClient: " + e);
        }

        return response;
    }

    //endregion

}

