package Trip;

import Utils.APIClient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


public class TripAPIClient extends APIClient {
    private String authHeader;
    private String baseUrl;

    public TripAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public Map<String, Object> getAll() throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/trips/", authHeader);
    }

    public Map<String, Object> getCount() throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/trips/count", authHeader);
    }

    public Map<String, Object> getOne(String tripId) throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId, authHeader);
    }

    public Map<String, Object> create(Map<String, Object> formData) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        String formDataStr = new ObjectMapper().writeValueAsString(formData);
        return AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/trips/", authHeader, formDataStr);
    }

    public Map<String, Object> create(Trip trip) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String formDataStr = ow.writeValueAsString(trip);

        return AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/trips/", authHeader, formDataStr);
    }

    public Map<String, Object> update(String tripId, Map<String, Object> formData) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        String formDataStr = new ObjectMapper().writeValueAsString(formData);
        return AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId, authHeader, formDataStr);
    }

    public void delete(String tripId) throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        AsyncDELETE(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId, authHeader);
    }

    public Map<String, Object> getMetrics(String tripId) throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/trips/" + tripId + "/metrics", authHeader);
    }
}
