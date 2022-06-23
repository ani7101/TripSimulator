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

    public DeviceAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public ArrayList<Device> getAll() {
        ArrayList<Device> response = null;

        try {
            String json = AsyncGET(baseUrl + "/iot/api/v2/devices?limit=100", authHeader);
            response = ParseJson.deserializeResponse(json, DeviceList.class).getItems();
        } catch (Exception e) {
            e.printStackTrace();
//            LOGGER.warning("Exception @DeviceAPIClient: " + e);
        }

        return response;
    }

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
}
