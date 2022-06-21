package user.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import user.User;
import utils.IoTDeserializerList;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserList extends IoTDeserializerList {
    public static final String ITEMS = "items";

    @JsonProperty(ITEMS)
    protected ArrayList<User> items;

    public ArrayList<User> getItems() { return items; }
}