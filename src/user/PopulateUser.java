package user;

import utils.Generator;

import java.util.ArrayList;

import static java.util.List.*;

// Omitted usage of the phone number in users
public class PopulateUser {
    private UserAPIClient client;

    private User user;

    public PopulateUser(String baseUrl, String username, String password,
                        User user) {

        client = new UserAPIClient(baseUrl, username, password);
        this.user = user;
    }

    public PopulateUser(String baseUrl, String username, String password,
                        ArrayList<String> roles) {

        client = new UserAPIClient(baseUrl, username, password);

        String firstName = Generator.generateRandomString(5);
        String lastName = Generator.generateRandomString(5);

        user = new User(
                "Driver-" + Generator.generateRandomUUID(),
                firstName,
                lastName,
                roles
        );

        // Need to change from gmail to a dummy host later
        UserEmailAddress workEmail = new UserEmailAddress("WORK", firstName + "." + lastName + ".work@gmail.com", true);

        UserEmailAddress recoveryEmail = new UserEmailAddress("RECOVERY", firstName + "." + lastName + ".recovery@gmail.com", true);

        user.addEmailAddresses(workEmail);
        user.addEmailAddresses(recoveryEmail);
    }

    public PopulateUser(String baseUrl, String username, String password) {
        this(baseUrl, username, password, new ArrayList<>(of("IoTDriver")));
    }

    public User sendQuery() {
        return client.create(user);
    }

    public User getUser() { return user; }
}
