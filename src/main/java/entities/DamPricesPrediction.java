package entities;

import entities.points.DamPricesPredictionPoints;

public class DamPricesPrediction extends DailyDocument<DamPricesPredictionPoints> {
    private String currencyUnit;
    private String priceMeasureUnit;

    public DamPricesPrediction(String currencyUnit, String priceMeasureUnit) {
        this.currencyUnit = currencyUnit;
        this.priceMeasureUnit = priceMeasureUnit;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getPriceMeasureUnit() {
        return priceMeasureUnit;
    }

    public void setPriceMeasureUnit(String priceMeasureUnit) {
        this.priceMeasureUnit = priceMeasureUnit;
    }
}
