package trip.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripDriverInfoModel implements Serializable {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String LOGIN_ID = "loginId";

    public static final String NAME = "name";

    public static final String EXTERNAL_ID = "externalId";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(LOGIN_ID)
    private String loginId;     // Optional

    @JsonProperty(NAME)
    private String name;        // Optional

    @JsonProperty(EXTERNAL_ID)
    private String externalId;  // Optional


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public TripDriverInfoModel(String externalId, String loginId, String name) {
        this.externalId = externalId;
        this.loginId = loginId;
        this.name = name;
    }
    public TripDriverInfoModel(String loginId, String name) {
        this.loginId = loginId;
        this.name = name;
    }

    public TripDriverInfoModel(String loginId) {
        this.loginId = loginId;
    }

    // Empty constructor for jackson to serialize/deserialize
    public TripDriverInfoModel() {}



    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getExternalId() { return externalId; }
    public String getLoginId() { return loginId; }
    public String getName() { return name; }


    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public void setName(String name) {
        this.name = name;
    }

    //endregion

}
