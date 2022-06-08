package Vehicle;

import Utils.APIClient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class VehicleAPIClient extends APIClient {
    private String authHeader;
    private String baseUrl;

    public VehicleAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public Map<String, Object> getAll() throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        System.out.println(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/");
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/", authHeader);
    }

    public Map<String, Object> getCount() throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/count", authHeader);
    }

    public Map<String, Object> getOne(String vehicleId) throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + vehicleId, authHeader);
    }

    public Map<String, Object> getMetrics(String vehicleId) throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId + "/metrics", authHeader);
    }

    public Map<String, Object> getOBD2Parameters(String vehicleId) throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        return AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId + "/parameters/devices", authHeader);
    }

    public Map<String, Object> create(Map<String, Object> formData) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        String formDataStr = new ObjectMapper().writeValueAsString(formData);
        return AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader, formDataStr);
    }

    public Map<String, Object> create(Vehicle vehicle) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String formDataStr = ow.writeValueAsString(vehicle);

        return AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/", authHeader, formDataStr);
    }

    public Map<String, Object> update(String vehicleId, Map<String, Object> formData) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        String formDataStr = new ObjectMapper().writeValueAsString(formData);
        return AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId, authHeader, formDataStr);
    }

    public void delete(String vehicleId) throws ExecutionException, InterruptedException, TimeoutException {
        AsyncDELETE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId, authHeader);
    }
}
