package connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payload.vehicle.Payload;
import utils.APIClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


/**
 * Accesses the connector API of the IoT server instance.
 * The official documentation for connectors can be referred via this
 * <a href="https://docs.oracle.com/en/cloud/paas/iot-cloud/iotgs/create-and-configure-connectors.html#GUID-5D1505F0-6D8F-457B-B7F7-0FFC2FF079E4">link</a>
 */
public class ConnectorAPIClient extends APIClient {
    private final String authHeader;

    private final String connectorUrl;

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");


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
    public <T> String postPayload(T payload) {
        String response = null;

        try {
            response = AsyncPOST(connectorUrl, authHeader, POJOToJson(payload));
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while posting payload:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while posting payload:", e);
        }

        return response;
    }

    //endregion

}
