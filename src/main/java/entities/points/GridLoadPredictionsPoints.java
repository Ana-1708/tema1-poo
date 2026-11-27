package entities.points;

public final class GridLoadPredictionsPoints {
    private int intervalIndex;
    private double quantity;
    private double reservesRequiredMW;

    public GridLoadPredictionsPoints(final int intervalIndex, final double quantity,
                                     final double reservesRequiredMW) {
        this.intervalIndex = intervalIndex;
        this.quantity = quantity;
        this.reservesRequiredMW = reservesRequiredMW;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(final int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(final double quantity) {
        this.quantity = quantity;
    }

    public double getReservesRequiredMW() {
        return reservesRequiredMW;
    }

    public void setReservesRequiredMW(final double reservesRequiredMW) {
        this.reservesRequiredMW = reservesRequiredMW;
    }
}
