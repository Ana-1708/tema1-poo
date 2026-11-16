package entities.points;

public class DamPricesPoints {
    private int intervalIndex;
    private double priceAmount;

    public DamPricesPoints(int intervalIndex, double priceAmount) {
        this.intervalIndex = intervalIndex;
        this.priceAmount = priceAmount;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public double getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(double priceAmount) {
        this.priceAmount = priceAmount;
    }
}
