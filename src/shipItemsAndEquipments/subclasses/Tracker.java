package shipItemsAndEquipments.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tracker {

    public static final String KEY = "key";

    public static final String VALUE = "value";

    @JsonProperty(KEY)
    private String key;

    @JsonProperty(VALUE)
    private String value;


    public Tracker(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Tracker(String value) {
        this.key = "DeviceName";
        this.value = value;
    }

    public Tracker() {}


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
