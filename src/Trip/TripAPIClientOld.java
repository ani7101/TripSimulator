package Trip;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Map;
import java.util.HashMap;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This class is used to communicate with the trip API using the HttpClient for basic CRUD operations
 * It uses basic authentication with a username and a password
 * @deprecated This has been changed to the TripAPIClient class
 */
public class TripAPIClientOld {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
    private String username;
    private String password;
    private String baseUrl;

    public TripAPIClientOld(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

    public Map<String, Object> AsyncGetALlTrips() throws ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseUrl + "/fleetMonitoring/clientapi/v2/trips/"))
                .header("Content-Type", "application/json")
                .header("Authorization", generateAuthenticationHeader())
                .build();


        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        Map<String, Object> jsonMap = new ObjectMapper().readValue(jsonString, HashMap.class);
        return jsonMap;
    }

    public Map<String, Object> AsyncGetOneTrip(String tripId) throws ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId))
                .header("Content-Type", "application/json")
                .header("Authorization", generateAuthenticationHeader())
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        Map<String, Object> jsonMap = new ObjectMapper().readValue(jsonString, HashMap.class);
        return jsonMap;
    }

    public Map<String, Object> AsyncCreateTrip(Map<String, String> payload) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        String postForm = new ObjectMapper().writeValueAsString(payload);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(postForm))
                .uri(URI.create(baseUrl + "/fleetMonitoring/clientapi/v2/trips/"))
                .header("Content-Type", "application/json")
                .header("Authorization", generateAuthenticationHeader())
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        Map<String, Object> jsonMap = new ObjectMapper().readValue(jsonString, HashMap.class);
        return jsonMap;
    }

    public Map<String, Object> AsyncCreateTrip(Trip trip) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String postForm = ow.writeValueAsString(trip);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(postForm))
                .uri(URI.create(baseUrl + "/fleetMonitoring/clientapi/v2/trips/"))
                .header("Content-Type", "application/json")
                .header("Authorization", generateAuthenticationHeader())
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        Map<String, Object> jsonMap = new ObjectMapper().readValue(jsonString, HashMap.class);
        return jsonMap;
    }

    public Map<String, Object> AsyncUpdateTrip(String tripId, Map<String, String> payload) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        String postForm = new ObjectMapper().writeValueAsString(payload);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(postForm))
                .uri(URI.create(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId))
                .header("Content-Type", "application/json")
                .header("Authorization", generateAuthenticationHeader())
                .header("X-HTTP-Method-Override", "PUT")
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        Map<String, Object> jsonMap = new ObjectMapper().readValue(jsonString, HashMap.class);
        return jsonMap;
    }

    public void AsyncDeleteTrip(String tripId) throws ExecutionException, InterruptedException, TimeoutException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId))
                .header("Content-Type", "application/json")
                .header("Authorization", generateAuthenticationHeader())
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
    }

    public Map<String, Object> AsyncGetTripMetrics(String tripId) throws ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId + "/metrics"))
                .header("Content-Type", "application/json")
                .header("Authorization", generateAuthenticationHeader())
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        Map<String, Object> jsonMap = new ObjectMapper().readValue(jsonString, HashMap.class);
        return jsonMap;
    }


    // Utils
    private String generateAuthenticationHeader() {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    // Getters & Setters
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
