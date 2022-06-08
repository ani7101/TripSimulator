package Trip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TripDriverInfoModel {

    // Defining the mapping structure for the response parser
    public static final String LOGIN_ID = "loginId";

    public static final String NAME = "name";

    public static final String EXTERNAL_ID = "externalId";

    @JsonProperty(LOGIN_ID)
    private String loginId;     // Optional

    @JsonProperty(NAME)
    private String name;        // Optional

    @JsonProperty(EXTERNAL_ID)
    private String externalId;  // Optional

    public TripDriverInfoModel(String externalId, String loginId, String name) {
        this.externalId = externalId;
        this.loginId = loginId;
        this.name = name;
    }
    public TripDriverInfoModel(String loginId, String name) {
        this.loginId = loginId;
        this.name = name;
    }
    public TripDriverInfoModel() {}

    // Getters
    public String getExternalId() { return externalId; }
    public String getLoginId() { return loginId; }
    public String getName() { return name; }

    // Setters
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public void setName(String name) {
        this.name = name;
    }
}
