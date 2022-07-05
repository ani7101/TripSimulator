package shipItemsAndEquipments;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipOrder {

    //region Jackson References
    //---------------------------------------------------------------------------------------

    public static final String ORDER_NUMBER = "orderNumber";

    public static final String ORDER_KEY = "orderKey";


    //endregion
    //region Class variables
    //---------------------------------------------------------------------------------------

    @JsonProperty(ORDER_NUMBER)
    private String orderNumber;

    @JsonProperty(ORDER_KEY)
    private String orderKey;


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------

    public ShipOrder(String orderNumber, String orderKey) {
        this.orderNumber = orderNumber;
        this.orderKey = orderKey;
    }

    public ShipOrder(String uniqueId) {
        orderKey = "ORDER-KEY-" + uniqueId;
        orderNumber = "ORDER-NUMBER-" + uniqueId;
    }

    public ShipOrder() {}


    //endregion
    //region Getters/Setters
    //---------------------------------------------------------------------------------------

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    //endregion

}
