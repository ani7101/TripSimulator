package Driver;

import org.apache.commons.logging.Log;
import utils.APIClient;
import utils.ParseJson;

import java.util.logging.Logger;

public class UserAPIClient extends APIClient {
    private String authHeader;

    private String baseUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public UserAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public User create(User user) {
        User response = null;

        try {
            String json = AsyncPOST(baseUrl + "/iot/privatewebapi/v2/users", authHeader, POJOtoJson(user));
            response = ParseJson.deserializeResponse(json, User.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @UserAPIClient: " + e);
        }

        return response;
    }
}
