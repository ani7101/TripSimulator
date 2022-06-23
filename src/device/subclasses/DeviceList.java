package device.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import device.Device;
import utils.IoTDeserializerList;

import java.util.ArrayList;

/**
 * List using Device as items and based on the IoTDeserializerList class to deserialize a list response from the IoT API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceList extends IoTDeserializerList {
    public static final String ITEMS = "items";

    @JsonProperty(ITEMS)
    protected ArrayList<Device> items;

    public ArrayList<Device> getItems() { return items; }
}
