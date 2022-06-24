package hereMaps.accessToken;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utils.APIClient;


public class AccessTokenAPIClient extends APIClient {
    private final String authHeader;

    private final String url;


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
        } catch (Exception e) {
            e.printStackTrace();
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
    public String deserializeAccessToken(String jsonString) {
        String result = null;

        try {
            final ObjectNode node = new ObjectMapper().readValue(jsonString, ObjectNode.class);

            if (node.has("accessToken")) {
                result = node.get("accessToken").asText();
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    //endregion

}
