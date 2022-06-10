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

public class HereAPIClient extends APIClient {
    private String accessToken;
    private LocalDateTime lastGenerated;

    public HereAPIClient(String accessToken) {
        this.accessToken = accessToken;
        lastGenerated = LocalDateTime.now();
    }

    public HereAPIClient() {}

    public void generateToken() {
        HereAccessTokenProvider accessTokens = HereAccessTokenProvider.builder().build();
        accessToken = accessTokens.getAccessToken();
        lastGenerated = LocalDateTime.now();
    }

    public String getGeoLocation(double latitude, double longitude) throws ExecutionException, InterruptedException, TimeoutException, IOException {
        if (lastGenerated == null || Duration.between(lastGenerated, LocalDateTime.now()).toHours() > 24) {
            generateToken();
        }

        String url = new StringBuilder().append("https://revgeocode.search.hereapi.com/v1/revgeocode?lang=en-US&at=").append(latitude).append(",").append(longitude).toString();

        return AsyncGET(url, "Bearer " + accessToken);
    }



    public ArrayList<HEREMapsRouteSection> getRoute(double sourceLat, double sourceLong,
                                                    double destinationLat, double destinationLong,
                                                    ArrayList<Double> stopsLat, ArrayList<Double> stopsLong,
                                                    boolean requirePolyline) throws IOException, ExecutionException, InterruptedException, TimeoutException {
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

        String json = AsyncGET(urlBuilder.toString(), "Bearer " + accessToken);

        HEREMapsRoutes temp = ParseJson.deserializeResponse(json, HEREMapsRoutes.class);
        return temp.getRoutes().get(0).getSections(); // We take the first element in the routes array as only one call is being made at a time
    }



    public ArrayList<HEREMapsRouteSection> getRoute(double sourceLat, double sourceLong,
                                                    double destinationLat, double destinationLong,
                                                    boolean requirePolyline) throws IOException, ExecutionException, InterruptedException, TimeoutException {
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

        String json = AsyncGET(urlBuilder.toString(), "Bearer " + accessToken);

        HEREMapsRoutes temp = ParseJson.deserializeResponse(json, HEREMapsRoutes.class);
        return temp.getRoutes().get(0).getSections(); // We take the first element in the routes array as only one call is being made at a time
    }

    public String getAccessToken() { return accessToken; }
}
