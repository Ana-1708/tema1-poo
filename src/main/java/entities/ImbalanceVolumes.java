package entities;

import entities.points.ImbalanceVolumesPoints;

public final class ImbalanceVolumes extends DailyDocument<ImbalanceVolumesPoints> {
    private String quantityMeasureUnit;

    public ImbalanceVolumes(final String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }

    public String getQuantityMeasureUnit() {
        return quantityMeasureUnit;
    }

    public void setQuantityMeasureUnit(final String quantityMeasureUnit) {
        this.quantityMeasureUnit = quantityMeasureUnit;
    }
}
