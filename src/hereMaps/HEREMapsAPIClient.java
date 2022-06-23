package hereMaps;

import hereMaps.accessToken.AccessTokenAPIClient;
import hereMaps.deserializerClasses.HEREMapsRoutes;
import utils.APIClient;
import utils.ParseJson;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Logger;

public class HEREMapsAPIClient extends APIClient {
    private String accessToken;
    private LocalDateTime lastGenerated;

    private AccessTokenAPIClient accessTokenClient;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public HEREMapsAPIClient(String accessTokenUrl, String username, String password) {
        accessTokenClient = new AccessTokenAPIClient(accessTokenUrl, username, password);

        generateToken();
    }

    public void generateToken() {
        accessToken = accessTokenClient.get();
        lastGenerated = LocalDateTime.now();
    }

    public String getGeoLocation(double latitude, double longitude) {
        if (lastGenerated == null || Duration.between(lastGenerated, LocalDateTime.now()).toHours() > 24) {
            generateToken();
        }

        String url = "https://revgeocode.search.hereapi.com/v1/revgeocode?lang=en-US&at=" + latitude + "," + longitude;

        String response = null;

        try {
            response = AsyncGET(url, "Bearer " + accessToken);
        } catch (Exception e) {
            LOGGER.warning("Exception @HEREMapsAPIClient: " + e);
        }
        return response;
    }


    public ArrayList<HEREMapsRouteSection> getRoute(double sourceLat, double sourceLong,
                                                    double destinationLat, double destinationLong,
                                                    ArrayList<Double> stopsLat, ArrayList<Double> stopsLong,
                                                    boolean requirePolyline) {
        if (lastGenerated == null || Duration.between(lastGenerated, LocalDateTime.now()).toHours() > 24) {
            generateToken();
        }

        // Generating the URL with the parameters
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://router.hereapi.com/v8/routes?transportMode=truck&lang=en-US&return=summary,elevation");
        if (requirePolyline) {
            urlBuilder.append(",polyline");
        }
        urlBuilder.append("&origin=" + sourceLat + "," + sourceLong);
        urlBuilder.append("&destination=" + destinationLat + "," + destinationLong);

        for (int i = 0; i < stopsLat.size(); i++) {
            urlBuilder.append("&via=" + stopsLat.get(i) + "," + stopsLong.get(i));
        }

        ArrayList<HEREMapsRouteSection> response = null;

        try {
            String json = AsyncGET(urlBuilder.toString(), "Bearer " + accessToken);

            HEREMapsRoutes temp = ParseJson.deserializeResponse(json, HEREMapsRoutes.class);
            response = temp.getRoutes().get(0).getSections(); // We take the first element in the routes array as only one call is being made at a time
        } catch (Exception e) {
            LOGGER.warning("Exception @HEREMapsAPIClient: " + e);
        }

        return response;
    }


    public ArrayList<HEREMapsRouteSection> getRoute(double sourceLat, double sourceLong,
                                                    double destinationLat, double destinationLong,
                                                    boolean requirePolyline) {
        if (lastGenerated == null || Duration.between(lastGenerated, LocalDateTime.now()).toHours() > 24) {
            generateToken();
        }

        // Setting up a proxy
        System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
        System.setProperty("http.proxyPort", "80");
        System.setProperty("https.proxyHost", "www-proxy.us.oracle.com");
        System.setProperty("https.proxyPort", "80");

        // Generating the URL with the parameters
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://router.hereapi.com/v8/routes?transportMode=truck&lang=en-US&return=summary,elevation");
        if (requirePolyline) {
            urlBuilder.append(",polyline");
        }
        urlBuilder.append("&origin=" + sourceLat + "," + sourceLong);
        urlBuilder.append("&destination=" + destinationLat + "," + destinationLong);

        ArrayList<HEREMapsRouteSection> response = null;

        try {
            String json = AsyncGET(urlBuilder.toString(), "Bearer " + accessToken);

            HEREMapsRoutes temp = ParseJson.deserializeResponse(json, HEREMapsRoutes.class);
            response = temp.getRoutes().get(0).getSections(); // We take the first element in the routes array as only one call is being made at a time
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warning("Exception @HEREMapsAPIClient: " + e);
        }

        // Removing the proxy
        System.clearProperty("http.proxyHost");
        System.clearProperty("https.proxyHost");
        System.clearProperty("http.proxyPort");
        System.clearProperty("https.proxyPort");

        return response;
    }

    public String getAccessToken() {
        return accessToken;
    }

    private static void pause() {
        try {
            System.out.println("Stop or start the the VPN and type a random word and ENTER");
            System.in.read();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
