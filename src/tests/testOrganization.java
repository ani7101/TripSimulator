package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import organization.Organization;
import organization.OrganizationAPIClient;
import user.User;

import java.util.ArrayList;

public class testOrganization {
    private OrganizationAPIClient client;

    @Test(priority = 1)
    @Parameters({"baseUrl", "username", "password"})
    public void testConfig(String baseUrl, String username, String password) {
        client = new OrganizationAPIClient(baseUrl, username, password);
    }

    @Test(priority = 2)
    public void testFetchAll() {
        ArrayList<Organization> organizations = client.getAll();
    }

    @Test(priority = 3)
    public void testAddUserToOrganization() {
        ArrayList<String> userIds = new ArrayList<String>(2);

        userIds.add("43RYGXEM2JR0");
        userIds.add("3ZKM9Z3M2JR0");

        client.addUsersToOrganization(userIds);
    }
}
