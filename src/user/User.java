package user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import user.subclasses.UserEmailAddress;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String EMAIL_ADDRESSES = "emailAddresses";

    public static final String PHONE_NUMBERS = "phoneNumbers";

    public static final String FIRST_NAME = "firstName";

    public static final String LAST_NAME = "lastName";

    public static final String ROLES = "roles";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(ID)
    private String id;

    @JsonProperty(NAME)
    private String name;

    @JsonProperty(EMAIL_ADDRESSES)
    private ArrayList<UserEmailAddress> emailAddresses;

    @JsonProperty(PHONE_NUMBERS)
    private ArrayList<Long> phoneNumbers;

    @JsonProperty(FIRST_NAME)
    private String firstName;

    @JsonProperty(LAST_NAME)
    private String lastName;

    @JsonProperty(ROLES)
    private ArrayList<String> roles;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public User(String name,
                String firstName, String lastName) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;

        emailAddresses = new ArrayList<>();
        phoneNumbers = new ArrayList<>();
        roles = new ArrayList<>();
        addDriverRole();
        addToDefaultOrganization();
    }

    public User(String name,
                String firstName, String lastName,
                ArrayList<String> roles) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;

        emailAddresses = new ArrayList<>();
        phoneNumbers = new ArrayList<>();
    }

    // Empty constructor for jackson to serialize/deserialize
    public User() {}


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<UserEmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(ArrayList<UserEmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public void addEmailAddresses(UserEmailAddress emailAddress) {
        this.emailAddresses.add(emailAddress);
    }

    public ArrayList<Long> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<Long> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void addPhoneNumber(long phoneNumber) { this.phoneNumbers.add(phoneNumber); }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    public void addDriverRole() {
        if (!roles.contains("IoTDriver")) {
            roles.add("IoTDriver");
        }
    }

    public void addToOrganization(String organizationId) {

        boolean inOrganization = false;
        String temp = null;

        for (String role : roles) {
            if(role.contains("IoTOrganization$")) {
                inOrganization = true;
                temp = role;
                break;
            }
        }
        if (inOrganization) {
            roles.remove(temp);
        }
        roles.add("IoTOrganization$" + organizationId);
    }

    public void addToDefaultOrganization() {
        boolean inOrganization = false;
        String temp = null;

        for (String role : roles) {
            if(role.contains("IoTOrganization$")) {
                inOrganization = true;
                temp = role;
                break;
            }
        }
        if (inOrganization) {
            roles.remove(temp);
        }
        roles.add("IoTOrganization$ORA_DEFAULT_ORG");
    }

    //endregion

}
