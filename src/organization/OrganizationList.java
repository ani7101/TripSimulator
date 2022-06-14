package organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import utils.IoTDeserializerList;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationList extends IoTDeserializerList {
    public static final String ITEMS = "items";

    @JsonProperty(ITEMS)
    protected ArrayList<Organization> items;

    public ArrayList<Organization> getItems() { return items; }
}
