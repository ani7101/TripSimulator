package vehicle;

import utils.APIClient;
import utils.IotDeserializerList;
import utils.ParseJson;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

// FIXME: 09/06/2022 Implement custom parsers for the metrics and the OBD2 parameters queries
public class VehicleAPIClient extends APIClient {
    private String authHeader;

    private String baseUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public VehicleAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public ArrayList<Vehicle> getAll() {
        ArrayList<Vehicle> list = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/", authHeader);
            list = ParseJson.deserializeIoTResponse(json, new IotDeserializerList<Vehicle>()).getItems();
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }

        return list;
    }

    public int getCount() {
        int count = 0;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/count", authHeader);
            count = ParseJson.deserializeCountResponse(json);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }

        return count;
    }

    public Vehicle getOne(String vehicleId) {
        Vehicle response = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + vehicleId, authHeader);
            response = ParseJson.deserializeResponse(json, Vehicle.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }

        return response;
    }

    public String getMetrics(String vehicleId) {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId + "/metrics", authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }
        return response;
    }

    public String getOBD2Parameters(String vehicleId) {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId + "/parameters/devices", authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }
        return response;
    }

    public Vehicle create(Map<String, Object> formData) {
        Vehicle response = null;

        try {
            String formDataStr = new ObjectMapper().writeValueAsString(formData);

            String json = AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader, formDataStr);

            response = ParseJson.deserializeResponse(json, Vehicle.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }
        return response;
    }

    public Vehicle create(Vehicle vehicle) {
        Vehicle response = null;

        try {
            String json = AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/", authHeader, POJOtoJson(vehicle));

            response = ParseJson.deserializeResponse(json, Vehicle.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }
        return response;
    }

    public Vehicle update(String vehicleId, Map<String, Object> formData) {
        Vehicle response = null;

        try {
            String formDataStr = new ObjectMapper().writeValueAsString(formData);

            String json = AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId, authHeader, formDataStr);

            response = ParseJson.deserializeResponse(json, Vehicle.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }
        return response;
    }

    public Vehicle update(String vehicleId, Vehicle updatedVehicle) {
        Vehicle response = null;

        try {
            String json = AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId, authHeader, POJOtoJson(updatedVehicle));

            response = ParseJson.deserializeResponse(json, Vehicle.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }
        return response;
    }

    public void delete(String vehicleId) {
        try {
            AsyncDELETE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId, authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }
    }
}
