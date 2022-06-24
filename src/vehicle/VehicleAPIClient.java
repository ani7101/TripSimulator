package vehicle;

import utils.APIClient;
import utils.ParseJson;
import vehicle.subclasses.VehicleList;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Accesses the vehicle API of the IoT server instance.
 * The API Documentation can be referred via this
 * <a href="https://docs.oracle.com/en/cloud/saas/iot-fleet-cloud/rest-api/api-vehicle-management.html">link</a>
 */
public class VehicleAPIClient extends APIClient {
    private String authHeader;

    private String baseUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


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
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
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
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
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
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }

        return count;
    }

    /**
     * Sends a request to the IoT server instance FM API to get the metrics of a vehicle.
     * @param vehicleId
     * @return String: metrics json body
     */
    public String getMetrics(String vehicleId) {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/fleetMonitoring/clientapi/v2/vehicles/" + vehicleId + "/metrics", authHeader);
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
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
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
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
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
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
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
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
        } catch (Exception e) {
            LOGGER.warning("Exception @VehicleAPIClient: " + e);
        }
    }

    //endregion

}
