package hereMaps;

import com.here.account.oauth2.HereAccessTokenProvider;
import hereMaps.deserializerClasses.HEREMapsRoutes;
import utils.APIClient;
import utils.ParseJson;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class HEREMapsAPIClient extends APIClient {
    private String accessToken;
    private LocalDateTime lastGenerated;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public HEREMapsAPIClient() {
        generateToken();
    }

    public void generateToken() {
        HereAccessTokenProvider accessTokens = HereAccessTokenProvider.builder().build();
        accessToken = accessTokens.getAccessToken();
        lastGenerated = LocalDateTime.now();
    }

    public String getGeoLocation(double latitude, double longitude) {
        if (lastGenerated == null || Duration.between(lastGenerated, LocalDateTime.now()).toHours() > 24) {
            generateToken();
        }

        String url = new StringBuilder().append("https://revgeocode.search.hereapi.com/v1/revgeocode?lang=en-US&at=").append(latitude).append(",").append(longitude).toString();

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
        urlBuilder.append("&origin=%s,%s".formatted(sourceLat, sourceLong));
        urlBuilder.append("&destination=%s,%s".formatted(destinationLat, destinationLong));

        for (int i = 0; i < stopsLat.size(); i++) {
            urlBuilder.append("&via=%s,%s".formatted(stopsLat.get(i), stopsLong.get(i)));
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

        // Generating the URL with the parameters
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://router.hereapi.com/v8/routes?transportMode=truck&lang=en-US&return=summary,elevation");
        if (requirePolyline) {
            urlBuilder.append(",polyline");
        }
        urlBuilder.append("&origin=%s,%s".formatted(sourceLat, sourceLong));
        urlBuilder.append("&destination=%s,%s".formatted(destinationLat, destinationLong));

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

    public String getAccessToken() { return accessToken; }
}
