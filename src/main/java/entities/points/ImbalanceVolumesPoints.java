package entities.points;

public class ImbalanceVolumesPoints {
    private int intervalIndex;
    private double positiveImbalanceVol;
    private double negativeImbalanceVol;

    public ImbalanceVolumesPoints(int intervalIndex, double positiveImbalanceVol, double negativeImbalanceVol) {
        this.intervalIndex = intervalIndex;
        this.positiveImbalanceVol = positiveImbalanceVol;
        this.negativeImbalanceVol = negativeImbalanceVol;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public double getPositiveImbalanceVol() {
        return positiveImbalanceVol;
    }

    public void setPositiveImbalanceVol(double positiveImbalanceVol) {
        this.positiveImbalanceVol = positiveImbalanceVol;
    }

    public double getNegativeImbalanceVol() {
        return negativeImbalanceVol;
    }

    public void setNegativeImbalanceVol(double negativeImbalanceVol) {
        this.negativeImbalanceVol = negativeImbalanceVol;
    }
}
