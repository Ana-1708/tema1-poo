package entities;


import entities.points.DamPricesPoints;

import java.time.ZonedDateTime;

public class DamPrices extends DailyDocument<DamPricesPoints>{
    private String currencyUnit;
    private String priceMeasureUnit;

    public DamPrices(String currencyUnit, String priceMeasureUnit) {
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
