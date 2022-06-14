package vehicleType;

import trip.TripList;
import utils.APIClient;
import utils.IotDeserializerList;
import utils.ParseJson;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

public class VehicleTypeAPIClient extends APIClient {
    private final String authHeader;

    private final String baseUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public VehicleTypeAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public ArrayList<VehicleType> getAll() {
        ArrayList<VehicleType> list = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader);
            list = ParseJson.deserializeResponse(json, VehicleTypeList.class).getItems();
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }
        return list;
    }

    public int getCount() {
        int count = 0;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/count", authHeader);
            count = ParseJson.deserializeCountResponse(json);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }
        return count;
    }

    // TODO: 13/06/2022 Implement parser (but most likely not required)
    public String getPreSeededFields() {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/fields", authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }
        return response;
    }

    public VehicleType getOne(String typeId) {
        VehicleType response = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + typeId, authHeader);
            response = ParseJson.deserializeResponse(json, VehicleType.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }

        return response;
    }

    public VehicleType create(Map<String, Object> formData) {
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

    public VehicleType create(VehicleType type) {
        VehicleType response = null;

        try {
            String json = AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader, POJOtoJson(type));
            response = ParseJson.deserializeResponse(json, VehicleType.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }

        return response;
    }

    public VehicleType update(String typeId, Map<String, Object> formData) {
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

    public VehicleType update(String typeId, VehicleType type) {
        VehicleType response = null;

        try {
            String json = AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + typeId, authHeader, POJOtoJson(type));
            response = ParseJson.deserializeResponse(json, VehicleType.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }

        return response;
    }

    public void delete(String typeId) {
        try {
            AsyncDELETE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + typeId, authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleTypeAPIClient: " + e);
        }
    }
}
