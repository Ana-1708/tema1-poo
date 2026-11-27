package entities;

import entities.points.ImbalacePricesPoints;

public final class ImbalancePrices extends DailyDocument<ImbalacePricesPoints> {
    private String currencyUnit;
    private String priceMeasurment;

    public ImbalancePrices(final String currencyUnit, final String priceMeasurment) {
        this.currencyUnit = currencyUnit;
        this.priceMeasurment = priceMeasurment;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(final String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getPriceMeasurment() {
        return priceMeasurment;
    }

    public void setPriceMeasurment(final String priceMeasurment) {
        this.priceMeasurment = priceMeasurment;
    }
}
