package entities.points;

public class ImbalacePricesPoints {

    private int intervalIndex;
    private double positiveImbalancePrice;
    private double negativeImbalancePrice;

    public ImbalacePricesPoints(int intervalIndex, double positiveImbalancePrice, double negativeImbalancePrice) {
        this.intervalIndex = intervalIndex;
        this.positiveImbalancePrice = positiveImbalancePrice;
        this.negativeImbalancePrice = negativeImbalancePrice;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public double getPositiveImbalancePrice() {
        return positiveImbalancePrice;
    }

    public void setPositiveImbalancePrice(double positiveImbalancePrice) {
        this.positiveImbalancePrice = positiveImbalancePrice;
    }

    public double getNegativeImbalancePrice() {
        return negativeImbalancePrice;
    }

    public void setNegativeImbalancePrice(double negativeImbalancePrice) {
        this.negativeImbalancePrice = negativeImbalancePrice;
    }
}
