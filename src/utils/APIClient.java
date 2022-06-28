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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Class containing (static) methods to send requests to an API via different methods (GET, POST, DELETE & PUT) using basic authorization
 */
public class APIClient {

    private static final int TIMEOUT = 15; // maximum time to wait for the request response

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    /**
     * Sends an asynchronous GET API requests to the url argument with the Content-Type set to 'application/json'
     * @param url Absolute url to the request API settings
     * @param authHeader String containing the basic authorization header
     * @return String: Response obtained from the API call
     * @throws ExecutionException Throws this exception  if the retrieval of the result is aborted because of the other exceptions
     * @throws InterruptedException Throws this exception if the thread to send the request is interrupted.
     * @throws TimeoutException Throws this exception if the server hasn't responded within the specified timeout time
     */
    public static String AsyncGET(String url, String authHeader) throws ExecutionException, InterruptedException, TimeoutException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(HttpResponse::body).get(TIMEOUT, TimeUnit.SECONDS);
    }


    /**
     * Sends an asynchronous DELETE API requests to the url argument with the Content-Type set to 'application/json'
     * @param url Absolute url to the request API settings
     * @param authHeader String containing the basic authorization header
     * @throws ExecutionException Throws this exception  if the retrieval of the result is aborted because of the other exceptions
     * @throws InterruptedException Throws this exception if the thread to send the request is interrupted.
     * @throws TimeoutException Throws this exception if the server hasn't responded within the specified timeout time
     */
    public static void AsyncDELETE(String url, String authHeader)  throws ExecutionException, InterruptedException, TimeoutException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        response.thenApply(HttpResponse::body).get(TIMEOUT, TimeUnit.SECONDS);
    }


    /**
     * Sends an asynchronous UPDATE API requests to the url argument with the Content-Type set to 'application/json' using the POST format.
     * This uses the method override to use the PUT instead of the POST
     * @param url Absolute url to the request API settings
     * @param authHeader String containing the basic authorization header
     * @param postForm Json string of the request body for updating
     * @return String: Response obtained from the API request
     * @throws ExecutionException Throws this exception  if the retrieval of the result is aborted because of the other exceptions
     * @throws InterruptedException Throws this exception if the thread to send the request is interrupted.
     * @throws TimeoutException Throws this exception if the server hasn't responded within the specified timeout time
     */
    public static String AsyncUPDATE(String url, String authHeader, String postForm) throws ExecutionException, InterruptedException, TimeoutException {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(postForm))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .header("X-HTTP-Method-Override", "PUT")
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(HttpResponse::body).get(TIMEOUT, TimeUnit.SECONDS);
    }


    /**
     * Sends an asynchronous POST API requests to the url argument using the Content-Type set to 'application/json and gives back the response
     * @param url Absolute url to the request API settings
     * @param authHeader String containing the basic authorization header
     * @param postForm Json string of the request body
     * @return String: Response obtained from the API request
     * @throws ExecutionException Throws this exception  if the retrieval of the result is aborted because of the other exceptions
     * @throws InterruptedException Throws this exception if the thread to send the request is interrupted.
     * @throws TimeoutException Throws this exception if the server hasn't responded within the specified timeout time
     */
    public static String AsyncPOST(String url, String authHeader, String postForm) throws ExecutionException, InterruptedException, TimeoutException {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(postForm))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(HttpResponse::body).get(TIMEOUT, TimeUnit.SECONDS);
    }


    /**
     * Sends an asynchronous POST API requests to the url argument using the Content-Type set to 'application/json and does not contain a body. It returns the response from the API
     * @param url Absolute url to the request API settings
     * @param authHeader String containing the basic authorization header
     * @return String: Response obtained from the API request
     * @throws ExecutionException Throws this exception  if the retrieval of the result is aborted because of the other exceptions
     * @throws InterruptedException Throws this exception if the thread to send the request is interrupted.
     * @throws TimeoutException Throws this exception if the server hasn't responded within the specified timeout time =
     */
    public static String AsyncPostWithoutData(String url, String authHeader) throws ExecutionException, InterruptedException, TimeoutException {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.thenApply(HttpResponse::body).get(2 * TIMEOUT, TimeUnit.SECONDS);
    }


    /**
     * Converts a set of credentials into authentication header based on the Base64 format. This converts into the standard string format for the Basic authorization.
     * @param username Username for the basic auth
     * @param password Password for the given username used in basic auth
     * @return String: Base64 string for basic authentication of the user
     */
    public static String generateAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }


    /**
     * Serializes the data into a json string using the jackson module.
     * @param data Data of parametrized class to serialize into json string
     * @return String: Serialized json as per the data class
     * @param <T> Class literal of the data class
     */
    public static <T> String POJOToJson(T data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(data);
    }
}
