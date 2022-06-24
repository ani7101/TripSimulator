package device;

import device.subclasses.DeviceList;
import utils.APIClient;
import utils.ParseJson;

import java.util.ArrayList;
import java.util.logging.Logger;

public class DeviceAPIClient extends APIClient {
    private final String authHeader;

    private final String baseUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    //region Constructors
    //---------------------------------------------------------------------------------------

    /**
     * Initializes the user API client with the input baseUrl and basic authentication username and password.
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
        return getAll(100);
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
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warning("Exception @DeviceAPIClient: " + e);
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
        } catch (Exception e) {
            LOGGER.warning("Exception @DeviceAPIClient: " + e);
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
        } catch (Exception e) {
            LOGGER.warning("Exception @DeviceAPIClient: " + e);
        }

        return count;
    }

    //endregion

}
