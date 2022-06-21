package user;

import user.subclasses.UserEmailAddress;
import utils.Generator;

import java.util.ArrayList;

import static java.util.List.of;

public class UserGenerator {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private UserGenerator() {
        throw new AssertionError();
    }

    public static User randomizedDefaultDriverUser(
            String baseUrl,
            String username,
            String password
    ) {
        return randomizedUser(baseUrl, username, password, new ArrayList<>(of("IoTDriver", "IoTOrganization$ORA_DEFAULT_ORG")));
    }

    public static User randomizedDriverUser(
            String baseUrl,
            String username,
            String password,
            String organizationId
    ) {
        return randomizedUser(baseUrl, username, password, new ArrayList<>(of("IoTDriver", "IoTOrganization$" + organizationId)));
    }

    public static User randomizedUser(
            String baseUrl,
            String username,
            String password,
            ArrayList<String> roles
    ) {
        String firstName = Generator.generateRandomString(5);
        String lastName = Generator.generateRandomString(5);

        User user = new User(
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

        return user;
    }
}
