package hereMaps.accessToken;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import utils.APIClient;


public class AccessTokenAPIClient extends APIClient {
    private final String authHeader;

    private final String url;

    public AccessTokenAPIClient(String url, String username, String password) {
        this.url = url;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public String get() {
        String accessToken = null;

        try {
            String json = AsyncPostWithoutData(url, authHeader);
            accessToken = parseAccessToken(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    public String parseAccessToken(String jsonString) {
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
}
