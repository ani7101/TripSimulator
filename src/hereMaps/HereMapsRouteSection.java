package hereMaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the template of the response sent from the HERE maps routing API in the routes -> 1st element (only one route is asked at a time) -> sections field
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HereMapsRouteSection {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public final static String ID = "id";
    
    public final static String SUMMARY = "summary";

    public final static String POLYLINE = "polyline";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(ID)
    private String id;

    @JsonProperty(SUMMARY)
    private HereMapsRouteSummary summary;

    @JsonProperty(POLYLINE)
    private String polyline;


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getId() { return id; }

    public long getDuration() { return summary.getDuration(); }

    public long getBaseDuration() { return summary.getBaseDuration(); }

    public long getLength() { return summary.getLength(); }

    public String getPolyline() { return polyline; }

    //endregion

}
