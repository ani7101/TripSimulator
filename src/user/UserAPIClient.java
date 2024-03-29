package user;

import com.fasterxml.jackson.core.JsonProcessingException;
import device.subclasses.DeviceList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.subclasses.UserList;
import utils.APIClient;
import utils.ParseJson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Accesses the users API of the IoT server instance.
 * The API Documentation can be referred via this
 * <a href="https://docs.oracle.com/en/cloud/saas/iot-fleet-cloud/iotfm/add-and-manage-users.html#GUID-739FA4DB-135F-48E4-BFCF-D708AE2AE419">link</a>
 */
public class UserAPIClient extends APIClient {
    private final String authHeader;

    private final String baseUrl;

    private static final Logger IOT_API_LOGGER = LoggerFactory.getLogger("iot-api");


    //region Constructors
    //---------------------------------------------------------------------------------------

    /**
     * Initializes the user API client with the input baseUrl and basic authentication username and password.
     * @param baseUrl URL (top level domain) to the IoT server instance without the path
     * @param username Username of the admin user in the given IoT server instance
     * @param password Corresponding password
     */
    public UserAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }


    //endregion
    //region API Client methods
    //---------------------------------------------------------------------------------------

    /**
     * Sends a request to the IoT server instance FM API to get all users.
     * @return ArrayList(User): List of all users
     */
    public ArrayList<User> getAll() {
        ArrayList<User> list = null;

        try {
            String json = AsyncGET(baseUrl + "/iot/privateclientapi/v2/users", authHeader);
            list = ParseJson.deserializeResponse(json, UserList.class).getItems();
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting all users:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting all users:", e);
        }

        return list;
    }

    /**
     * Sends a request to the IoT server instance API to get all users.
     * @param query Query based on the MongoDB selector
     * @return ArrayList(User): List of all users
     */
    public ArrayList<User> getAll(String query) {
        ArrayList<User> list = null;

        try {
            String json = AsyncGET(baseUrl + "/iot/privateclientapi/v2/users?q=" + query, authHeader);
            list = ParseJson.deserializeResponse(json, UserList.class).getItems();
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting all users:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting all users:", e);
        }

        return list;
    }

    /**
     * Sends a request to the IoT server instance API to get the user with the corresponding ID.
     * @param userId ID (identifier) to access the user
     * @return User: user having the input ID
     */
    public User getOne(String userId) {
        User response = null;

        try {
            String json = AsyncGET(baseUrl + "/iot/privateclientapi/v2/users/" + userId, authHeader);
            response = ParseJson.deserializeResponse(json, User.class);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting one user:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting one user:", e);
        }

        return response;
    }

    public User getOneByLoginId(String userLoginId) {
        User response = null;

        /*
         * URI encoding reference
         * Refer to https://www.eso.org/~ndelmott/url_encode.html for more information
         * %7B - {
         * %7C - }
         * %22 - "
         * %3A - :
         */

        String query = "?q=%7B%22 %22%3A%22" + userLoginId + "%22%7D";

        try {
            String json = AsyncGET(baseUrl + "/iot/privateclientapi/v2/users" + query, authHeader);
            response = ParseJson.deserializeResponse(json, UserList.class).getItems().get(0);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting device using name:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting device using name:", e);
        }

        return response;
    }

    /**
     * Sends a request to the IoT server instance API to create a user.
     * @param user User instances to be converted to the json body
     * @return User: Response from the API call
     */
    public User create(User user) {
        User response = null;

        try {
            String json = AsyncPOST(baseUrl + "/iot/privateclientapi/v2/users", authHeader, POJOToJson(user));
            response = ParseJson.deserializeResponse(json, User.class);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while creating a user:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while creating a user:", e);
        }

        return response;
    }

    public void delete(String userId) {
        try {
            AsyncDELETE(baseUrl + "/iot/privateclientapi/v2/users/" + userId, authHeader);
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while deleting vehicle type:", e);
        } catch (TimeoutException e) {
            IOT_API_LOGGER.warn("Exception while deleting vehicle type:", e);
        }
    }

    //endregion

}
