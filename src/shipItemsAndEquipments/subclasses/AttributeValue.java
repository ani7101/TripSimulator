package shipItemsAndEquipments.subclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeValue {

    public static final String VALUE = "value";

    public static final String UNIT_OF_MEASURE = "unitOfMeasure";

    @JsonProperty(VALUE)
    private double value;

    @JsonProperty(UNIT_OF_MEASURE)
    private String unitOfMeasure;

    public AttributeValue(double value, String unitOfMeasure) {
        this.value = value;
        this.unitOfMeasure = unitOfMeasure;
    }

    public AttributeValue() {}

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
