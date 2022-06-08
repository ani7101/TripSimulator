package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class APIClient {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public Map<String, Object> AsyncGET(String url, String authHeader) throws ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        Map<String, Object> jsonMap = new ObjectMapper().readValue(jsonString, HashMap.class);
        return jsonMap;
    }

    public void AsyncDELETE(String url, String authHeader)  throws ExecutionException, InterruptedException, TimeoutException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
    }

    public Map<String, Object> AsyncUPDATE(String url, String authHeader, String postForm) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(postForm))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .header("X-HTTP-Method-Override", "PUT")
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        Map<String, Object> jsonMap = new ObjectMapper().readValue(jsonString, HashMap.class);
        return jsonMap;
    }

    public Map<String, Object> AsyncPOST(String url, String authHeader, String postForm) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(postForm))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        Map<String, Object> jsonMap = new ObjectMapper().readValue(jsonString, HashMap.class);
        return jsonMap;
    }

    /**
     * This is used to convert a set of credentials using Base64 formatting into a authentication header
     * @param username
     * @param password
     * @return String for authentication of the user
     */
    public String generateAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
}
