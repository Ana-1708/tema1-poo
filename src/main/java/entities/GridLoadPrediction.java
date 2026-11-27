package entities;

import entities.points.GridLoadPredictionsPoints;

public final class GridLoadPrediction extends DailyDocument<GridLoadPredictionsPoints> {
    private String quantityMeasureUnit;

    public GridLoadPrediction(final String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }

    public String getQuantityMeasureUnit() {
        return quantityMeasureUnit;
    }

    public void setQuantityMeasureUnit(final String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }
}
