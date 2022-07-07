package hereMaps.deserializerClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class hereMapsRoute {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public final static String ID = "id";

    public final static String SECTIONS = "sections";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(ID)
    private String id;

    @JsonProperty(SECTIONS)
    private ArrayList<HereMapsRouteSection> sections;


    //endregion
    //region Getters
    //---------------------------------------------------------------------------------------

    public String getId() { return id; }

    public ArrayList<HereMapsRouteSection> getSections() { return sections; }

    //endregion

}
