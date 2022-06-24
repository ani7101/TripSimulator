package trip.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripVehicleInfoModel {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String NAME = "name";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(NAME)
    private String name;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public TripVehicleInfoModel(String name) {
        this.name = name;
    }

    // Empty constructor for jackson to serialize/deserialize
    public TripVehicleInfoModel() {}


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //endregion

}
