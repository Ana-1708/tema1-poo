package entities;

import entities.points.GridLoadPoints;

public class GridLoad extends DailyDocument<GridLoadPoints> {
    String quantityMeasureUnit;

    public GridLoad(String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }

    public String getQuantityMeasureUnit() {
        return quantityMeasureUnit;
    }

    public void setQuantityMeasureUnit(String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }
}
