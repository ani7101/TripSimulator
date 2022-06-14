package user;

import com.fasterxml.jackson.annotation.JsonProperty;
import utils.IotDeserializerList;

import java.util.ArrayList;

public class UserList extends IotDeserializerList {
    public static final String ITEMS = "items";

    @JsonProperty(ITEMS)
    protected ArrayList<User> items;

    public ArrayList<User> getItems() { return items; }
}
