package entities.points;

public class GridLoadPredictionsPoints {
    private int intervalIndex;
    private double quantity;
    private double reservesRequiredMW;

    public GridLoadPredictionsPoints(int intervalIndex, double quantity, double reservesRequiredMW) {
        this.intervalIndex = intervalIndex;
        this.quantity = quantity;
        this.reservesRequiredMW = reservesRequiredMW;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getReservesRequiredMW() {
        return reservesRequiredMW;
    }

    public void setReservesRequiredMW(double reservesRequiredMW) {
        this.reservesRequiredMW = reservesRequiredMW;
    }
}
