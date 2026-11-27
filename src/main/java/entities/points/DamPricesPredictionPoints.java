package entities.points;

public final class DamPricesPredictionPoints {
    private int intervalIndex;
    private double predictedPriceEurMwh;
    private double spikeProbability;

    public DamPricesPredictionPoints(final int intervalIndex, final double predictedPriceEurMwh,
                                     final double spikeProbability) {
        this.intervalIndex = intervalIndex;
        this.predictedPriceEurMwh = predictedPriceEurMwh;
        this.spikeProbability = spikeProbability;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(final int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public double getPredictedPriceEurMwh() {
        return predictedPriceEurMwh;
    }

    public void setPredictedPriceEurMwh(final double predictedPriceEurMwh) {
        this.predictedPriceEurMwh = predictedPriceEurMwh;
    }

    public double getSpikeProbability() {
        return spikeProbability;
    }

    public void setSpikeProbability(final double spikeProbability) {
        this.spikeProbability = spikeProbability;
    }
}
