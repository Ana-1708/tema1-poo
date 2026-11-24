package entities.points;

import java.math.BigDecimal;

public class GridLoadPoints {
    private int intervalIndex;
    private Double quantity;
    private double reservesRequiredMW;

    public GridLoadPoints(Double quantity, int intervalIndex, double reservesRequiredMW) {
        this.quantity = quantity;
        this.intervalIndex = intervalIndex;
        this.reservesRequiredMW = reservesRequiredMW;
    }

    public int getIntervalIndex() {
        return intervalIndex;
    }

    public void setIntervalIndex(int intervalIndex) {
        this.intervalIndex = intervalIndex;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public double getReservesRequiredMW() {
        return reservesRequiredMW;
    }

    public void setReservesRequiredMW(double reservesRequiredMW) {
        this.reservesRequiredMW = reservesRequiredMW;
    }
}
