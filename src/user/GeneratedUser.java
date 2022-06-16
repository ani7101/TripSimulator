package user;

import utils.Generator;

import java.util.ArrayList;

import static java.util.List.*;

// Omitted usage of the phone number in users
public class GeneratedUser {
    private UserAPIClient client;

    private User user;

    public GeneratedUser(String baseUrl, String username, String password,
                         User user) {

        client = new UserAPIClient(baseUrl, username, password);
        this.user = user;
    }

    public GeneratedUser(String baseUrl, String username, String password,
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

        UserEmailAddress recoveryEmail = new UserEmailAddress("RECOVERY", firstName + "." + lastName + ".recovery@gmail.com", false);

        user.addEmailAddresses(workEmail);
        user.addEmailAddresses(recoveryEmail);
    }

    public GeneratedUser(String baseUrl, String username, String password, String organizationId) {
        this(baseUrl, username, password, new ArrayList<>(of("IoTDriver", "IoTOrganization$" + organizationId)));
    }

    public GeneratedUser(String baseUrl, String username, String password) {
        this(baseUrl, username, password, new ArrayList<>(of("IoTDriver", "IoTOrganization$ORA_DEFAULT_ORG")));
    }

    public User sendQuery() {
        User response = client.create(user);
        user.setId(response.getId());
        return response;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() { return user; }
}