package organization;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import utils.APIClient;
import utils.ParseJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Accesses the organization API pertaining to users
 */
public class OrganizationAPIClient extends APIClient {
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
    public OrganizationAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }


    //endregion
    //region API Client methods
    //---------------------------------------------------------------------------------------

    /**
     * Sends a request to the IoT server instance API to add the following user to the default organization.
     * @param userIds ID of the user to be added to the organization
     * @return String: Response status
     */
    public String addUsersToOrganization(ArrayList<String> userIds) {
        return addUsersToOrganization("ORA_DEFAULT_ORG", userIds);
    }



    /**
     * Sends a request to the IoT server instance API to get all organizations.
     * @return ArrayList(Organization): List of all organizations
     */
    public ArrayList<Organization> getAll() {
        return getAll(100);
    }

    /**
     * Sends a request to the IoT server instance API to get all organizations.
     * @param limit Maximum number of devices to be queried
     * @return ArrayList(Organization): List of all organizations
     */
    public ArrayList<Organization> getAll(int limit) {
        ArrayList<Organization> response = null;

        try {
            String json = AsyncGET(baseUrl + "/iotapps/privateclientapi/v2/orgs?limit=" + limit, authHeader);
            response = ParseJson.deserializeResponse(json, OrganizationList.class).getItems();
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while getting all organizations:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while getting all organizations:", e);
        }

        return response;
    }

    /**
     * Sends a request to the IoT server instance API to add the following user to the organization.
     * @param organizationId ID (identifier) to access the organization
     * @param userIds ID of the user to be added to the organization
     * @return String: Response status
     */
    public String addUsersToOrganization(String organizationId, ArrayList<String> userIds) {
        Map<String, Object> form = new HashMap<>();
        form.put("operation", "INSERT");
        form.put("userIds", userIds);

        String response = null;

        try {
            response = AsyncPOST(baseUrl + "/iotapps/privateclientapi/v2/orgs/" + organizationId +"/users", authHeader, POJOToJson(form));
        } catch (ExecutionException | InterruptedException e) {
            IOT_API_LOGGER.error("Exception while adding an user to organization:", e);
        } catch (TimeoutException | JsonProcessingException e) {
            IOT_API_LOGGER.warn("Exception while adding an user to organization:", e);
        }

        return response;
    }

    //endregion

}
