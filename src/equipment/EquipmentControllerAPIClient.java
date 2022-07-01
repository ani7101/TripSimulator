package equipment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.APIClient;

public class EquipmentControllerAPIClient extends APIClient {
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
    public EquipmentControllerAPIClient(String connectorUrl, String username, String password) {
        this.connectorUrl = connectorUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }


    //endregion
    //region API Client methods
    //---------------------------------------------------------------------------------------
}
