package connector;

import payload.Payload;
import utils.APIClient;

import java.util.logging.Logger;

public class ConnectorAPIClient extends APIClient {
    private final String authHeader;

    private final String connectorUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ConnectorAPIClient(String connectorUrl, String username, String password) {
        this.connectorUrl = connectorUrl; // https://aniragha-lite.device.internal.iot.ocs.oraclecloud.com/cgw/TripSimulatorController
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public String postPayload(Payload payload) {
        String response = null;

        try {
            response = AsyncPOST(connectorUrl, authHeader, POJOToJson(payload));
        } catch (Exception e) {
            LOGGER.warning("Exception @ConnectorAPIClient: " + e);
        }

        return response; // Reports `Request accepted` if it's a valid call
    }
}
