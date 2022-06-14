package user;

import utils.APIClient;
import utils.ParseJson;

import java.util.ArrayList;
import java.util.logging.Logger;

public class UserAPIClient extends APIClient {
    private final String authHeader;

    private final String baseUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public UserAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public User create(User user) {
        User response = null;

        try {
            String json = AsyncPOST(baseUrl + "/iot/privateclientapi/v2/users", authHeader, POJOToJson(user));
            response = ParseJson.deserializeResponse(json, User.class);
        } catch (Exception e) {
             LOGGER.warning("Exception @UserAPIClient: " + e);
        }

        return response;
    }

    public ArrayList<User> getAll() {
        ArrayList<User> list = null;

        try {
            String json = AsyncGET(baseUrl + "/iot/privateclientapi/v2/users", authHeader);
            list = ParseJson.deserializeResponse(json, UserList.class).getItems();
        } catch (Exception e) {
            LOGGER.warning("Exception @UserAPIClient: " + e);
        }

        return list;
    }

    public ArrayList<User> getAll(String query) {
        ArrayList<User> list = null;

        try {
            String json = AsyncGET(baseUrl + "/iot/privateclientapi/v2/users?q=" + query, authHeader);
            list = ParseJson.deserializeResponse(json, UserList.class).getItems();
        } catch (Exception e) {
            LOGGER.warning("Exception @UserAPIClient: " + e);
        }

        return list;
    }

    public User getOne(String userId) {
        User response = null;

        try {
            String json = AsyncGET(baseUrl + "/iot/privateclientapi/v2/users/" + userId, authHeader);
            response = ParseJson.deserializeResponse(json, User.class);
        } catch (Exception e) {
            LOGGER.warning("Exception @UserAPIClientL: " + e);
        }

        return response;
    }
}
