package hereMaps.deserializerClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import hereMaps.HEREMapsRouteSection;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HEREMapsRoute {
    public final static String ID = "id";

    public final static String SECTIONS = "sections";

    @JsonProperty(ID)
    private String id;

    @JsonProperty(SECTIONS)
    private ArrayList<HEREMapsRouteSection> sections;

    public String getId() { return id; }

    public ArrayList<HEREMapsRouteSection> getSections() { return sections; }
}
