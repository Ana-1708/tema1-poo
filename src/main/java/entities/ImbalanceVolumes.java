package entities;

import entities.points.ImbalacePricesPoints;

public class ImbalanceVolumes extends DailyDocument<ImbalacePricesPoints> {
    private String quantityMeasureUnit;

    public ImbalanceVolumes(String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }

    public String getQuantityMeasureUnit() {
        return quantityMeasureUnit;
    }

    public void setQuantityMeasureUnit(String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }
}
