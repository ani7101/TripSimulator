package hereMaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HEREMapsRouteSummary {
    public final static String DURATION = "duration";

    public final static String LENGTH = "length";

    public final static String BASE_DURATION = "baseDuration";

    @JsonProperty(DURATION)
    private long duration;

    @JsonProperty(LENGTH)
    private long length;

    @JsonProperty(BASE_DURATION)
    private long baseDuration;

    public long getDuration() { return duration; }

    public long getLength() { return length; }

    public long getBaseDuration() { return baseDuration; }
}
