package entities;

import entities.points.ImbalacePricesPoints;

public class ImbalancePrices extends DailyDocument<ImbalacePricesPoints> {
    private String currencyUnit;
    private String priceMeasurment;

    public ImbalancePrices(String currencyUnit, String priceMeasurment) {
        this.currencyUnit = currencyUnit;
        this.priceMeasurment = priceMeasurment;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getPriceMeasurment() {
        return priceMeasurment;
    }

    public void setPriceMeasurment(String priceMeasurment) {
        this.priceMeasurment = priceMeasurment;
    }
}
