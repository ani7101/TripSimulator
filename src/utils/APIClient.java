package utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class APIClient {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public String AsyncGET(String url, String authHeader) throws ExecutionException, InterruptedException, TimeoutException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
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

    public String AsyncUPDATE(String url, String authHeader, String postForm) throws ExecutionException, InterruptedException, TimeoutException {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(postForm))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .header("X-HTTP-Method-Override", "PUT")
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
    }

    public String AsyncPOST(String url, String authHeader, String postForm) throws ExecutionException, InterruptedException, TimeoutException {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(postForm))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(HttpResponse::body).get(10, TimeUnit.SECONDS);
    }

    /**
     * This is used to convert a set of credentials using Base64 formatting into authentication header
     * @param username Username for the basic auth
     * @param password Password for the given username used in basic auth
     * @return String for authentication of the user
     */
    public String generateAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    public <T> String POJOToJson(T data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(data);
    }
}
