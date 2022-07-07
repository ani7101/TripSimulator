package user.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * User email address format as per the IoT server.
 * Contains the following fields - <br>
 * <ol>
 *    <li>type (String) - PRIMARY/RECOVERY</li>
 *    <li>value (String)</li>
 *    <li>primary (Boolean)</li>
 * </ol>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEmailAddress implements Serializable {

    //region Jackson References
    //---------------------------------------------------------------------------------------
    public static final String TYPE = "type";

    public static final String VALUE = "value";

    public static final String PRIMARY = "primary";

    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(TYPE)
    private String type;

    @JsonProperty(VALUE)
    private String value;

    @JsonProperty(PRIMARY)
    private boolean primary;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public UserEmailAddress(String type, String value, boolean primary) {
        this.type = type;
        this.value = value;
        this.primary = primary;
    }

    public UserEmailAddress(String type, String value) {
        this(type, value, false);
    }

    // Empty constructor for jackson to serialize/deserialize
    public UserEmailAddress() {}


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    public boolean isPrimary() { return primary; }

    public void setPrimary(boolean primary) { this.primary = primary; }

    //endregion

}
