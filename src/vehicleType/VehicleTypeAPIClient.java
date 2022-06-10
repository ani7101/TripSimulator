package vehicleType;

import trip.Trip;
import utils.APIClient;
import utils.IotDeserializerList;
import utils.ParseJson;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class VehicleTypeAPIClient extends APIClient {
    private String authHeader;

    private String baseUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public VehicleTypeAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public ArrayList<VehicleType> getAll() {
        ArrayList<VehicleType> list = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader);
            list = ParseJson.deserializeIoTResponse(json, new IotDeserializerList<VehicleType>()).getItems();
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }
        return list;
    }

    public int getCount() throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        int count = 0;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/count", authHeader);
            count = ParseJson.deserializeCountResponse(json);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }
        return count;
    }

    public String getPreSeededFields() throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/fields", authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }
        return response;
    }

    public VehicleType getOne(String typeId) throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
        VehicleType response = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + typeId, authHeader);
            response = ParseJson.deserializeResponse(json, VehicleType.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }

        return response;
    }

    public VehicleType create(Map<String, Object> formData) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        VehicleType response = null;

        try {
            String formDataStr = new ObjectMapper().writeValueAsString(formData);

            String json = AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader, formDataStr);
            response = ParseJson.deserializeResponse(json, VehicleType.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }

        return response;
    }

    public VehicleType create(VehicleType type) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        VehicleType response = null;

        try {
            String json = AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader, POJOtoJson(type));
            response = ParseJson.deserializeResponse(json, VehicleType.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }

        return response;
    }

    public VehicleType update(String typeId, Map<String, Object> formData) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        VehicleType response = null;

        try {
            String formDataStr = new ObjectMapper().writeValueAsString(formData);
            String json = AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + typeId, authHeader, formDataStr);
            response = ParseJson.deserializeResponse(json, VehicleType.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }

        return response;
    }

    public VehicleType update(String typeId, VehicleType type) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        VehicleType response = null;

        try {
            String json = AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + typeId, authHeader, POJOtoJson(type));
            response = ParseJson.deserializeResponse(json, VehicleType.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }

        return response;
    }

    public void delete(String typeId) throws ExecutionException, InterruptedException, TimeoutException {
        try {
            AsyncDELETE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + typeId, authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }
    }
}