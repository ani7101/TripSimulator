package hereMaps.deserializerClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HEREMapsRoutes {
    public final static String ROUTES = "routes";

    @JsonProperty(ROUTES)
    private ArrayList<HEREMapsRoute> routes;

    public ArrayList<HEREMapsRoute> getRoutes() { return routes; }
}
