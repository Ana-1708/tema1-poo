package entities.points;

public final class ImbalacePricesPoints {

    private int intervalIndex;
    private double positiveImbalancePrice;
    private double negativeImbalancePrice;

    public ImbalacePricesPoints(final int intervalIndex, final double positiveImbalancePrice,
                                final double negativeImbalancePrice) {
        this.intervalIndex = intervalIndex;
        this.positiveImbalancePrice = positiveImbalancePrice;
        this.negativeImbalancePrice = negativeImbalancePrice;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(final int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public double getPositiveImbalancePrice() {
        return positiveImbalancePrice;
    }

    public void setPositiveImbalancePrice(final double positiveImbalancePrice) {
        this.positiveImbalancePrice = positiveImbalancePrice;
    }

    public double getNegativeImbalancePrice() {
        return negativeImbalancePrice;
    }

    public void setNegativeImbalancePrice(final double negativeImbalancePrice) {
        this.negativeImbalancePrice = negativeImbalancePrice;
    }
}
