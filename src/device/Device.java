package device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {
    public static final String ID = "id";

    public static final String HARDWARE_ID = "hardwareId";

    public static final String NAME = "name";

    public static final String STATE = "state";

    public static final String ENABLED = "enabled";

    public static final String CONNECTIVITY_STATUS = "connectivityStatus";

    @JsonProperty(ID)
    private String id;

    @JsonProperty(HARDWARE_ID)
    private String hardwareId;

    @JsonProperty(NAME)
    private String name;

    @JsonProperty(STATE)
    private String state;

    @JsonProperty(ENABLED)
    private boolean enabled;

    @JsonProperty(CONNECTIVITY_STATUS)
    private String connectivityStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
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
}