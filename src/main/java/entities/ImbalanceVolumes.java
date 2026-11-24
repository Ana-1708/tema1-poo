package entities;

import entities.points.ImbalacePricesPoints;
import entities.points.ImbalanceVolumesPoints;

public class ImbalanceVolumes extends DailyDocument<ImbalanceVolumesPoints> {
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
