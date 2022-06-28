package user;

import user.subclasses.UserEmailAddress;
import utils.Generator;

import java.util.ArrayList;

import static java.util.List.of;


/**
 * Contains static methods to generate a user with randomized username, name & email-id.
 * It also includes a <b>primary</b> & <b>recovery</b> dummy email-id.
 * <br>
 * NOTE - Organization is added to a user by giving them the "<i>IoTOrganization${organization_id}</i>" role
 * <br>
 * <br>
 * Static methods:
 * <ul>
 *     <li><b>randomizedDefaultDriverUser</b> - Generates a driver user in the default organization</li>
 *     <li><b>randomizedDriverUser</b> - Generates a driver user in the given input organization</li>
 *     <li><b>randomizedUser</b> - Generates a user with the given input roles</li>
 * </ul>
 */
public class UserGenerator {
    /** Private Constructor.
     *  Suppress default constructor for non-instantiability */
    private UserGenerator() {
        throw new AssertionError();
    }


    //region Randomized generators

    /**
     * Generates a driver user in the default organization
     * @return User: randomly generated user
     */
    public static User randomizedDefaultDriverUser() {
        return randomizedUser(new ArrayList<>(of("IoTDriver", "IoTOrganization$ORA_DEFAULT_ORG")));
    }

    /**
     * Generates a driver user in the given input organization
     * @param organizationId ID for the organization where the user will be created
     * @return User: randomly generated user
     */
    public static User randomizedDriverUser(String organizationId) {
        return randomizedUser(new ArrayList<>(of("IoTDriver", "IoTOrganization$" + organizationId)));
    }

    /**
     * Generates a driver user with the given roles

     * @param roles List of roles to be assigned to the user
     * @return User: randomly generated user
     */
    public static User randomizedUser(ArrayList<String> roles) {

        String firstName = Generator.generateRandomString(5);
        String lastName = Generator.generateRandomString(5);

        User user = new User(
                "simulation-driver-" + Generator.generateRandomUUID(),
                firstName,
                lastName,
                roles
        );

        // Need to change from gmail to a dummy host later
        UserEmailAddress workEmail = new UserEmailAddress("WORK", firstName + "." + lastName + ".work@email.com", true);

        UserEmailAddress recoveryEmail = new UserEmailAddress("RECOVERY", firstName + "." + lastName + ".recovery@email.com", false);

        user.addEmailAddresses(workEmail);
        user.addEmailAddresses(recoveryEmail);

        return user;
    }

    //endregion

}
