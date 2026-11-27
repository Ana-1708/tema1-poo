package entities;

import entities.points.DamPricesPredictionPoints;

public final class DamPricesPrediction extends DailyDocument<DamPricesPredictionPoints> {
    private String currencyUnit;
    private String priceMeasureUnit;

    public DamPricesPrediction(final String currencyUnit, final String priceMeasureUnit) {
        this.currencyUnit = currencyUnit;
        this.priceMeasureUnit = priceMeasureUnit;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(final String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getPriceMeasureUnit() {
        return priceMeasureUnit;
    }

    public void setPriceMeasureUnit(final String priceMeasureUnit) {
        this.priceMeasureUnit = priceMeasureUnit;
    }
}
