package equipmentDeviceModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.LoggerFactory;
import utils.APIClient;

import org.slf4j.Logger;
import utils.ParseJson;
import vehicle.Vehicle;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class DeviceModelAPIClient extends APIClient {

    private final String authHeader;

    private final String baseUrl;

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");


    //region Constructors
    //---------------------------------------------------------------------------------------

    /**
     * Initializes the device model API client with the input baseUrl and basic authentication username and password.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     */
    public DeviceModelAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }


    //endregion
    //region API Client methods

    /**
     * Sends a request to the IoT server instance API to get all device models.
     * @return ArrayList(String): List of all devices
     */
    public String getAll() {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/iot/api/v2/deviceModels", authHeader);

        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting all devices:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while getting all devices:", e);
        }

        return response;
    }

    /**
     * Sends a request to the IoT server instance API to get the all the devices and then returns the device with given ID. We cannot call for only one device at a time, so instead all devices are queried at once.
     * @param deviceModelUrn URN (identifier) to access the device
     * @return Device: Device having the input ID
     */
    public String getOne(String deviceModelUrn) {
        String response = null;

        try {
            response = AsyncGET(baseUrl + "/iot/api/v2/deviceModels/" + deviceModelUrn, authHeader);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting one device:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while getting one device:", e);
        }

        return response;
    }


    /**
     * Sends a request to the IoT server instance FM API to create the equipment device model.
     * It reads the payload from the EquipmentDeviceMode.json file
     * @return String: Response from the API call
     */
    public String create(String formData) {
        String response = null;


        try {
            response = AsyncPOST(baseUrl + "/iot/api/v2/deviceModels", authHeader, formData);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while creating a device model:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while creating a device model:", e);
        }

        return response;
    }

    public String createDefaultEquipment() {
        String data = null;

        try {
            data = ParseJson.readJsonFile("src/equipment/deviceModel/EquipmentDeviceModel.json");

        } catch (IOException ioe) {
            IOT_API_LOGGER.warn("Exception while creating the default equipment device model:", ioe);
        }

        return create(data);
    }

    //endregion

}
