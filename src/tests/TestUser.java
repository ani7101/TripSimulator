package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import user.*;

import java.util.ArrayList;

public class TestUser {
    private UserAPIClient client;
    private PopulateUser user;

    @Test(priority = 1)
    @Parameters({"baseUrl", "username", "password"})
    public void testConfigAndRandomizer(String baseUrl, String username, String password) {
        client = new UserAPIClient(baseUrl, username, password);
        user = new PopulateUser(baseUrl, username, password);

        System.out.println(user.getUser().toString());
    }

    @Test(priority = 2)
    public void testUserFetch() {
        ArrayList<User> users = client.getAll();
        System.out.println(users.toString());
    }

    @Test(priority = 2)
    public void testUserCreation() {
        user.sendQuery();
    }
}
