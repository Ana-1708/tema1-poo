package entities;

import entities.points.GridLoadPoints;

public final class GridLoad extends DailyDocument<GridLoadPoints> {
    private String quantityMeasureUnit;

    public GridLoad(final String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }

    public String getQuantityMeasureUnit() {
        return quantityMeasureUnit;
    }

    public void setQuantityMeasureUnit(final String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }
}
