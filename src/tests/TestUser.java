package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import user.*;

import java.util.ArrayList;

public class TestUser {
    private UserAPIClient client;

    private User user;

    @Test(priority = 1)
    @Parameters({"baseUrl", "username", "password"})
    public void testConfigAndRandomizer(String baseUrl, String username, String password) {
        client = new UserAPIClient(baseUrl, username, password);
        User newUser = UserGenerator.randomizedDefaultDriverUser("Test user");

    }

    @Test(priority = 2)
    public void testUserFetch() {
        ArrayList<User> users = client.getAll();
        System.out.println(users.toString());
    }

    @Test(priority = 2)
    public void testUserCreation() {
        client.create(user);
    }
}
