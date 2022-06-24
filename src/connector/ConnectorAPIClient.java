package connector;

import payload.Payload;
import utils.APIClient;

import java.util.logging.Logger;


/**
 * Accesses the connector API of the IoT server instance.
 * The official documentation for connectors can be referred via this
 * <a href="https://docs.oracle.com/en/cloud/paas/iot-cloud/iotgs/create-and-configure-connectors.html#GUID-5D1505F0-6D8F-457B-B7F7-0FFC2FF079E4">link</a>
 */
public class ConnectorAPIClient extends APIClient {
    private final String authHeader;

    private final String connectorUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    //region Constructors
    //---------------------------------------------------------------------------------------

    /**
     *
     * @param connectorUrl URL (inclusive of the complete path) to the connector. It is found in the connectors' info under the configuration options.
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     */
    public ConnectorAPIClient(String connectorUrl, String username, String password) {
        this.connectorUrl = connectorUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }


    //endregion
    //region API Client methods
    //---------------------------------------------------------------------------------------


    /**
     * Posts the input payload to the IoT server instance.
     * @param payload Payload with the device identifier (or hardware ID) to be posted
     * @return String: Request status response. It returns "<i>Request Accepted</i>" when no exceptions are called
     */
    public String postPayload(Payload payload) {
        String response = null;

        try {
            response = AsyncPOST(connectorUrl, authHeader, POJOToJson(payload));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warning("Exception @ConnectorAPIClient: " + e);
        }

        return response;
    }

    //endregion

}
