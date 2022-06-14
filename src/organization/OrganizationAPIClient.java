package organization;

import user.User;
import utils.APIClient;
import utils.ParseJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class OrganizationAPIClient extends APIClient {
    private final String authHeader;

    private final String baseUrl;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public OrganizationAPIClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.authHeader = generateAuthenticationHeader(username, password);
    }

    public String addUsersToOrganization(String organizationId, ArrayList<String> userIds) {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("operation", "INSERT");
        form.put("userIds", userIds);

        String response = null;

        try {
            response = AsyncPOST(baseUrl + "/iotapps/privateclientapi/v2/orgs/" + organizationId +"/users", authHeader, POJOToJson(form));
        } catch (Exception e) {
            LOGGER.warning("Exception @OrganizationAPIClient: " + e);
        }

        return response;
    }

    public String addUsersToOrganization(ArrayList<String> userIds) {
        return addUsersToOrganization("ORA_DEFAULT_ORG", userIds);
    }

    public ArrayList<Organization> getAll() {
        ArrayList<Organization> response = null;

        try {
            String json = AsyncGET(baseUrl + "/iotapps/privateclientapi/v2/orgs?limit=100", authHeader);
            response = ParseJson.deserializeResponse(json, OrganizationList.class).getItems();
        } catch (Exception e) {
            LOGGER.warning("Exception @OrganizationAPIClient: " + e);
        }

        return response;
    }

    public ArrayList<Organization> getAll(int limit) {
        ArrayList<Organization> response = null;

        try {
            String json = AsyncGET(baseUrl + "/iotapps/privateclientapi/v2/orgs?limit=" + limit, authHeader);
            response = ParseJson.deserializeResponse(json, OrganizationList.class).getItems();
        } catch (Exception e) {
            LOGGER.warning("Exception @OrganizationAPIClient: " + e);
        }

        return response;
    }
}
