package device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String ID = "id";

    public static final String DEVICE_IDENTIFIER = "hardwareId";

    public static final String NAME = "name";

    public static final String STATE = "state";

    public static final String ENABLED = "enabled";

    public static final String CONNECTIVITY_STATUS = "connectivityStatus";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(ID)
    private String id;

    @JsonProperty(DEVICE_IDENTIFIER)
    private String deviceIdentifier;

    @JsonProperty(NAME)
    private String name;

    @JsonProperty(STATE)
    private String state;

    @JsonProperty(ENABLED)
    private boolean enabled;

    @JsonProperty(CONNECTIVITY_STATUS)
    private String connectivityStatus;

    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String isConnectivityStatus() {
        return connectivityStatus;
    }

    public void setConnectivityStatus(String connectivityStatus) {
        this.connectivityStatus = connectivityStatus;
    }

    //endregion

}
