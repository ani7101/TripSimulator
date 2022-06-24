package hereMaps.deserializerClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * This is a wrapper for the HERE maps route response from the API.
 * <br>
 * NOTE - The route response can be checked out at routeResponseExample.json
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class hereMapsRoutes {
    public final static String ROUTES = "routes";

    @JsonProperty(ROUTES)
    private ArrayList<hereMapsRoute> routes;

    public ArrayList<hereMapsRoute> getRoutes() { return routes; }
}
