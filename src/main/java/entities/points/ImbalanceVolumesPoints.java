package entities.points;

public final class ImbalanceVolumesPoints {
    private int intervalIndex;
    private double positiveImbalanceVol;
    private double negativeImbalanceVol;

    public ImbalanceVolumesPoints(final int intervalIndex, final double positiveImbalanceVol,
                                  final double negativeImbalanceVol) {
        this.intervalIndex = intervalIndex;
        this.positiveImbalanceVol = positiveImbalanceVol;
        this.negativeImbalanceVol = negativeImbalanceVol;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(final int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public double getPositiveImbalanceVol() {
        return positiveImbalanceVol;
    }

    public void setPositiveImbalanceVol(final double positiveImbalanceVol) {
        this.positiveImbalanceVol = positiveImbalanceVol;
    }

    public double getNegativeImbalanceVol() {
        return negativeImbalanceVol;
    }

    public void setNegativeImbalanceVol(final double negativeImbalanceVol) {
        this.negativeImbalanceVol = negativeImbalanceVol;
    }
}
