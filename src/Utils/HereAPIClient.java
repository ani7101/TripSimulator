package Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.here.account.oauth2.HereAccessTokenProvider;
import Trip.GeoPosition;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class HereAPIClient {
    private String accessToken;
    private LocalDateTime lastGenerated;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

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

    public Map<String, Object> getGeoLocation(double latitude, double longitude) throws ExecutionException, InterruptedException, TimeoutException, IOException {
        if (lastGenerated == null || Duration.between(lastGenerated, LocalDateTime.now()).toHours() > 24) {
            generateToken();
        }

        String url = new StringBuilder().append("https://revgeocode.search.hereapi.com/v1/revgeocode?lang=en-US&at=").append(latitude).append(",").append(longitude).toString();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        Map<String, Object> responseMap = new ObjectMapper().readValue(responseBody, HashMap.class);
        return responseMap;
    }

    public Map<String, Object> getRoute(GeoPosition origin, GeoPosition destination, ArrayList<GeoPosition> stops, boolean requirePolyline) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        if (lastGenerated == null || Duration.between(lastGenerated, LocalDateTime.now()).toHours() > 24) {
            generateToken();
        }

        // Generating the URL with the parameters
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://router.hereapi.com/v8/routes?transportMode=truck&lang=en-US&return=summary,elevation");
        if (requirePolyline) {
            urlBuilder.append(",polyline");
        }
        urlBuilder.append("&origin=%s,%s".formatted(origin.getLatitude(), origin.getLongitude()));
        urlBuilder.append("&destination=%s,%s".formatted(destination.getLatitude(), destination.getLongitude()));

        for (GeoPosition stop : stops) {
            urlBuilder.append("&via=%s,%s".formatted(stop.getLatitude(), stop.getLongitude()));
        }

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlBuilder.toString()))
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        Map<String, Object> responseMap = new ObjectMapper().readValue(responseBody, HashMap.class);

        return responseMap;
    }

    public String getAccessToken() { return accessToken; }
}
