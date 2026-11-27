package entities.points;

public final class GridLoadPoints {
    private int intervalIndex;
    private Double quantity;
    private double reservesRequiredMW;

    public GridLoadPoints(final Double quantity, final int intervalIndex,
                          final double reservesRequiredMW) {
        this.quantity = quantity;
        this.intervalIndex = intervalIndex;
        this.reservesRequiredMW = reservesRequiredMW;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(final int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(final Double quantity) {
        this.quantity = quantity;
    }

    public double getReservesRequiredMW() {
        return reservesRequiredMW;
    }

    public void setReservesRequiredMW(final double reservesRequiredMW) {
        this.reservesRequiredMW = reservesRequiredMW;
    }
}
