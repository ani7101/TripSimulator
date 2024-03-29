package organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import utils.IoTDeserializerLinks;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization implements Serializable {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String STATUS = "status";

    public static final String SYSTEM = "system";

    public static final String LINKS = "links";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(ID)
    private String id;

    @JsonProperty(NAME)
    private String name;

    @JsonProperty(DESCRIPTION)
    private String description;

    @JsonProperty(STATUS)
    private String status;

    @JsonProperty(SYSTEM)
    private boolean system;

    @JsonProperty(LINKS)
    protected ArrayList<IoTDeserializerLinks> links;


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSystem() {
        return system;
    }

    public ArrayList<IoTDeserializerLinks> getLinks() {
        return links;
    }

    //endregion

}
