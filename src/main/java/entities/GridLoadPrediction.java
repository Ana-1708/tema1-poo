package entities;

import entities.points.GridLoadPredictionsPoints;

public class GridLoadPrediction extends DailyDocument<GridLoadPredictionsPoints>{
    private String quantityMeasureUnit;

    public GridLoadPrediction(String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }

    public String getQuantityMeasureUnit() {
        return quantityMeasureUnit;
    }

    public void setQuantityMeasureUnit(String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }
}
