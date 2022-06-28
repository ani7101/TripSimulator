package vehicle;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.APIClient;
import utils.ParseJson;
import vehicle.subclasses.VehicleList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Accesses the vehicle API of the IoT server instance.
 * The API Documentation can be referred via this
 * <a href="https://docs.oracle.com/en/cloud/saas/iot-fleet-cloud/rest-api/api-vehicle-management.html">link</a>
 */
public class VehicleAPIClient extends APIClient {
    private final String authHeader;

    private final String baseUrl;

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");


    //region Constructors
    //---------------------------------------------------------------------------------------

    /**
     * Initializes the vehicle API client with the input baseUrl and basic authentication username and password.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     */
    public VehicleAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }


    //endregion
    //region API Client methods
    //---------------------------------------------------------------------------------------

    /**
     * Sends a request to the IoT server instance FM API to get all vehicles.
     * @return ArrayList(Vehicle): List of all vehicles
     */
    public ArrayList<Vehicle> getAll() {
        ArrayList<Vehicle> list = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/", authHeader);
            list = ParseJson.deserializeResponse(json, VehicleList.class).getItems();
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting all vehicles:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting all vehicles:", e);
        }

        return list;
    }


    /**
     * Sends a request to the IoT server instance FM API to get the vehicle with the corresponding ID.
     * @param vehicleId ID (identifier) to access the vehicle
     * @return Vehicle: Vehicle having the input ID
     */
    public Vehicle getOne(String vehicleId) {
        Vehicle response = null;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicleTypes/" + vehicleId, authHeader);
            response = ParseJson.deserializeResponse(json, Vehicle.class);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting one vehicle:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting one vehicle:", e);
        }

        return response;
    }


    /**
     * Sends a request to the IoT server instance FM API to get the vehicle count.
     * @return int: Number of vehicles
     */
    public int getCount() {
        int count = 0;

        try {
            String json = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/count", authHeader);
            count = ParseJson.deserializeCountResponse(json);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting the vehicles count:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting the vehicles count:", e);
        }

        return count;
    }


    /**
     * Sends a request to the IoT server instance FM API to get the metrics of a vehicle.
     * @param vehicleId ID to the vehicle whose metrics are to be retrieved
     * @return String: metrics json body
     */
    public String getMetrics(String vehicleId) {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId + "/metrics", authHeader);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting vehicle metrics:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while getting vehicle metrics:", e);
        }

        return response;
    }


    /**
     * Sends a request to retrieve the OBD2 parameter payload for the vehicle
     * @param vehicleId ID (identifier) to access the vehicle
     * @return Strings: OBD2 parameter response json body
     */
    public String getOBD2Parameters(String vehicleId) {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId + "/parameters/devices", authHeader);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting vehicle OBD2 parameters:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while getting vehicle OBD2 parameters:", e);
        }

        return response;
    }


    /**
     * Sends a request to the IoT server instance FM API to create a vehicle.
     * @param vehicle Vehicle instances to be converted to the json body
     * @return Vehicle: Response from the API call
     */
    public Vehicle create(Vehicle vehicle) {
        Vehicle response = null;

        try {
            String json = AsyncPOST(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/", authHeader, POJOToJson(vehicle));
            response = ParseJson.deserializeResponse(json, Vehicle.class);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while creating a vehicle:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while creating a vehicle:", e);
        }

        return response;
    }


    /**
     * Sends a request to the IoT server instance FM API to update vehicle with input ID.
     * @param vehicleId ID (identifier) to access the vehicle
     * @param updatedVehicle Updated vehicle values
     * @return Vehicle: Response from the API call
     */
    public Vehicle update(String vehicleId, Vehicle updatedVehicle) {
        Vehicle response = null;

        try {
            String json = AsyncUPDATE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId, authHeader, POJOToJson(updatedVehicle));

            response = ParseJson.deserializeResponse(json, Vehicle.class);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while updating a vehicle:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while updating a vehicle:", e);
        }

        return response;
    }

    /**
     * Sends a request to the IoT server instance FM API to delete a vehicle.
     * @param vehicleId ID (identifier) to access the vehicle to be deleted
     */
    public void delete(String vehicleId) {
        try {
            AsyncDELETE(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId, authHeader);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while deleting a vehicle:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while deleting a vehicle:", e);
        }
    }

    //endregion
    //region Utils

    public void cleanUp() {
        for (Vehicle vehicle : getAll()) {
            if (vehicle.getName().contains("simulation-vehicle")) {
                delete(vehicle.getId());
            }
        }
    }

    //endregion

}
