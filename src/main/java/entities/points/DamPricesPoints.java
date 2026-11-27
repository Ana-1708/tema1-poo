package entities.points;

public final class DamPricesPoints {
    private int intervalIndex;
    private double priceAmount;

    public DamPricesPoints(final int intervalIndex, final double priceAmount) {
        this.intervalIndex = intervalIndex;
        this.priceAmount = priceAmount;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(final int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public double getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(final double priceAmount) {
        this.priceAmount = priceAmount;
    }
}
