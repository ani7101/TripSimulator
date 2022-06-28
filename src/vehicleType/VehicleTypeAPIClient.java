package vehicleType;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.APIClient;
import utils.ParseJson;
import vehicleType.subclasses.VehicleTypeList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Accesses the vehicleType API of the IoT server instance. The program only uses the OBD2 device models and created multiple type instances for it.
 * The API Documentation can be referred via this
 * <a href="https://docs.oracle.com/en/cloud/saas/iot-fleet-cloud/rest-api/api-vehicle-type-management.html">link</a>
 */
public class VehicleTypeAPIClient extends APIClient {
    private final String authHeader;

    private final String baseUrl;

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");


    //region Constructors
    //---------------------------------------------------------------------------------------

    /**
     * Initializes the vehicle type API client with the input baseUrl and basic authentication username and password
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     */
    public VehicleTypeAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }


    //endregion
    //region API Client methods
    //---------------------------------------------------------------------------------------

    /**
     * Sends a request to the IoT server instance FM API to get all vehicle types.
     * @return ArrayList(VehicleType): List of all vehicle types
     */
    public ArrayList<VehicleType> getAll() {
        ArrayList<VehicleType> list = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader);
            list = ParseJson.deserializeResponse(json, VehicleTypeList.class).getItems();
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting all vehicle type:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting all vehicle type:", e);
        }

        return list;
    }

    public VehicleType getOne(String typeId) {
        VehicleType response = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + typeId, authHeader);
            response = ParseJson.deserializeResponse(json, VehicleType.class);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting one vehicle type:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting one vehicle type:", e);
        }

        return response;
    }

    /**
     * Sends a request to the IoT server instance FM API to get the vehicle types count.
     * @return int: Number of types
     */
    public int getCount() {
        int count = 0;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/count", authHeader);
            count = ParseJson.deserializeCountResponse(json);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting the vehicle types count:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting the vehicle types count:", e);
        }

        return count;
    }

    /**
     * Sends a request to the IoT server instance FM API to get the pre-seeded values for all vehicle types.
     * @return String: List of pre-seeded values for the vehicle to be created
     */
    public String getPreSeededFields() {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/fields", authHeader);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting vehicle type pre seeded values:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while getting vehicle type pre seeded values:", e);
        }

        return response;
    }


    /**
     * Sends a request to the IoT server instance FM API to create a vehicle type.
     * @param vehicleType Vehicle type instances to be converted to the json body
     * @return VehicleType: Response from the API call
     */
    public VehicleType create(VehicleType vehicleType) {
        VehicleType response = null;

        try {
            String json = AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/", authHeader, POJOToJson(vehicleType));
            response = ParseJson.deserializeResponse(json, VehicleType.class);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while creating a vehicle type:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while creating a vehicle type:", e);
        }

        return response;
    }

    /**
     * Sends a request to the IoT server instance FM API to update vehicle type with input ID.
     * @param vehicleTypeId ID (identifier) to access the vehicle type
     * @param vehicleType Updated vehicle type value
     * @return VehicleType: Response from the API call
     */
    public VehicleType update(String vehicleTypeId, VehicleType vehicleType) {
        VehicleType response = null;

        try {
            String json = AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + vehicleTypeId, authHeader, POJOToJson(vehicleType));
            response = ParseJson.deserializeResponse(json, VehicleType.class);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while updating a vehicle type:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while updating a vehicle type:", e);
        }

        return response;
    }

    /**
     * Sends a request to the IoT server instance FM API to delete a vehicle type.
     * @param vehicleTypeId ID (identifier) to access the vehicle type to be deleted
     */
    public void delete(String vehicleTypeId) {
        try {
            AsyncDELETE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + vehicleTypeId, authHeader);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while deleting a vehicle type:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while deleting a vehicle type:", e);
        }
    }

    //endregion

}
