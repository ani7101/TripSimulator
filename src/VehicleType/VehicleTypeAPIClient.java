package VehicleType;

import Utils.APIClient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

// Need to make a Map -> VehicleType parser in this
public class VehicleTypeAPIClient extends APIClient {
    private String authHeader;
    private String baseUrl;

    public VehicleTypeAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public Map<String, Object> getAll() throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader);
    }

    public Map<String, Object> getCount() throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/count", authHeader);
    }

    public Map<String, Object> getPreSeededFields() throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/fields", authHeader);
    }

    public Map<String, Object> getOne(String typeId) throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + typeId, authHeader);
    }

    public Map<String, Object> create(Map<String, Object> formData) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        String formDataStr = new ObjectMapper().writeValueAsString(formData);
        return AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader, formDataStr);
    }

    public Map<String, Object> create(VehicleType type) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String formDataStr = ow.writeValueAsString(type);

        return AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader, formDataStr);
    }

    public Map<String, Object> update(String typeId, Map<String, Object> formData) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        String formDataStr = new ObjectMapper().writeValueAsString(formData);
        return AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + typeId, authHeader, formDataStr);
    }

    public void delete(String typeId) throws ExecutionException, InterruptedException, TimeoutException {
        AsyncDELETE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + typeId, authHeader);
    }
}
