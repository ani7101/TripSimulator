package hereMaps.accessToken;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.APIClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


public class AccessTokenAPIClient extends APIClient {
    private final String authHeader;

    private final String url;

    private static final Logger HERE_MAPS_LOGGER = LoggerFactory.getLogger("here-maps");


    //region Constructors
    //---------------------------------------------------------------------------------------

    /**
     * Initializes the user API client with the input baseUrl and basic authentication username and password
     * @param url URL with complete path to the HERE Maps OAuth access token generation
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     */
    public AccessTokenAPIClient(String url, String username, String password) {
        this.url = url;
        this.authHeader = generateAuthenticationHeader(username, password);
    }


    //endregion
    //region API Client methods
    //---------------------------------------------------------------------------------------

    /**
     * Sends a request to the internal OAuth API to get the access token for HERE Maps
     * @return String: access token for HERE Maps which is valid for 24 hours
     */
    public String get() {
        String accessToken = null;

        try {
            String json = AsyncPostWithoutData(url, authHeader);
            accessToken = deserializeAccessToken(json);
        } catch (ExecutionException | InterruptedException e) {
            HERE_MAPS_LOGGER.error("Exception while getting the access token from internal site:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            HERE_MAPS_LOGGER.warn("Exception while getting the access token from internal site:", e);
        }

        return accessToken;
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    /**
     * Deserializes the access token from the json response from the internal OAuth access token generation API
     * @param jsonString Json response from the API
     * @return String: deserialized access token
     */
    public String deserializeAccessToken(String jsonString) throws JsonProcessingException {
        String result = null;

        final ObjectNode node = new ObjectMapper().readValue(jsonString, ObjectNode.class);

        if (node.has("accessToken")) {
            result = node.get("accessToken").asText();
        }

        return result;
    }

    //endregion

}
