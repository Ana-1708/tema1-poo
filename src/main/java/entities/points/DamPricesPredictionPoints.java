package entities.points;

public class DamPricesPredictionPoints {
    private int intervalIndex;
    private double predictedPriceEurMwh;
    private double spikeProbability;

    public DamPricesPredictionPoints(int intervalIndex, double predictedPriceEurMwh, double spikeProbability) {
        this.intervalIndex = intervalIndex;
        this.predictedPriceEurMwh = predictedPriceEurMwh;
        this.spikeProbability = spikeProbability;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public double getPredictedPriceEurMwh() {
        return predictedPriceEurMwh;
    }

    public void setPredictedPriceEurMwh(double predictedPriceEurMwh) {
        this.predictedPriceEurMwh = predictedPriceEurMwh;
    }

    public double getSpikeProbability() {
        return spikeProbability;
    }

    public void setSpikeProbability(double spikeProbability) {
        this.spikeProbability = spikeProbability;
    }
}
