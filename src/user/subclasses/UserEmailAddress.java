package user.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEmailAddress {
    public static final String TYPE = "type";

    public static final String VALUE = "value";

    public static final String PRIMARY = "primary";


    @JsonProperty(TYPE)
    private String type;

    @JsonProperty(VALUE)
    private String value;

    @JsonProperty(PRIMARY)
    private boolean primary;


    public UserEmailAddress(String type, String value, boolean primary) {
        this.type = type;
        this.value = value;
        this.primary = primary;
    }

    public UserEmailAddress(String type, String value) {
        this(type, value, false);
    }

    public UserEmailAddress() {}


    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    public boolean isPrimary() { return primary; }

    public void setPrimary(boolean primary) { this.primary = primary; }

}
