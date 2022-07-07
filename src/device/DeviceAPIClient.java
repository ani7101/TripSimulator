package device;

import com.fasterxml.jackson.core.JsonProcessingException;
import device.subclasses.DeviceList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.APIClient;
import utils.ParseJson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class DeviceAPIClient extends APIClient {
    private final String authHeader;

    private final String baseUrl;

    private final int LIMIT = 500;

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");


    //region Constructors
    //---------------------------------------------------------------------------------------

    /**
     * Initializes the device API client with the input baseUrl and basic authentication username and password.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     */
    public DeviceAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }


    //endregion
    //region API Client methods
    //---------------------------------------------------------------------------------------

    /**
     * Sends a request to the IoT server instance API to get all devices.
     * @return ArrayList(Device): List of all devices
     */
    public ArrayList<Device> getAll() {
        return getAll(LIMIT);
    }

    /**
     * Sends a request to the IoT server instance API to get all devices.
     * @param limit Maximum number of devices to be queried
     * @return ArrayList(Device): List of all devices
     */
    public ArrayList<Device> getAll(int limit) {
        ArrayList<Device> response = null;

        try {
            String json = AsyncGET(baseUrl + "/iot/api/v2/devices?limit=" + limit, authHeader);
            response = ParseJson.deserializeResponse(json, DeviceList.class).getItems();
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting all devices:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting all devices:", e);
        }

        return response;
    }


    /**
     * Sends a request to the IoT server instance API to get the all the devices and then returns the device with given ID. We cannot call for only one device at a time, so instead all devices are queried at once.
     * @param deviceId ID (identifier) to access the device
     * @return Device: Device having the input ID
     */
    public Device getOne(String deviceId) {
        Device response = null;

        try {
            String json = AsyncGET(baseUrl + "/iot/api/v2/devices/" + deviceId, authHeader);
            response = ParseJson.deserializeResponse(json, Device.class);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting one device:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting one device:", e);
        }

        return response;
    }

    /**
     * Searches for a device from the device identifier (hardware ID) in the list of all devices and returns it
     * @param deviceIdentifier query device identifier
     * @return Device: Device with the queried device identifier
     */
    public Device getOneByIdentifier(String deviceIdentifier) {
        Device response = null;

        /*
         * URI encoding reference
         * Refer to https://www.eso.org/~ndelmott/url_encode.html for more information
         * %7B - {
         * %7C - }
         * %22 - "
         * %3A - :
         */

        String query = "?q=%7B%22hardwareId%22%3A%22" + deviceIdentifier + "%22%7D";

        try {
            String json = AsyncGET(baseUrl + "/iot/api/v2/devices" + query, authHeader);
            response = ParseJson.deserializeResponse(json, DeviceList.class).getItems().get(0);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting device using name:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting device using name:", e);
        }

        return response;
    }


    /**
     * Sends a request to the IoT server instance API to get the devices count.
     * @return int: Number of devices
     */
    public int getCount() {
        int count = 0;

        try {
            String json = AsyncGET(baseUrl + "/iot/api/v2/devices/count", authHeader);
            count = ParseJson.deserializeCountResponse(json);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting the devices count:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting the devices count:", e);
        }

        return count;
    }


    /**
     * Sends a request to the IoT server instance FM API to delete a device.
     * @param deviceId ID (identifier) to access the device to be deleted
     */
    public void delete(String deviceId) {
        try {
            AsyncDELETE(baseUrl + "/iot/api/v2/devices/" + deviceId, authHeader);
        }  catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while deleting a device:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while deleting a device:", e);
        }
    }


    //endregion

}
