package utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * General class model for deserializing the links' response from the IoT server
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IoTDeserializerLinks {
    public static final String HREF = "href";

    public static final String REL = "rel";

    @JsonProperty(HREF)
    private String href;

    @JsonProperty(REL)
    private String rel;

    public String getHref() { return href; }

    public String getRel() { return rel; }
}