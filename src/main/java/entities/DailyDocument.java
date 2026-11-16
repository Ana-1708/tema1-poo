package entities;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class DailyDocument<T> {
    private String biddingZoneDomain;
    private String resolution;
    private ZonedDateTime periodEnd;
    private ZonedDateTime periodStart;
    private final List<T> points = new ArrayList<>(Collections.nCopies(96, null));
    public T getPoint(int intervalIndex){
        return points.get(intervalIndex - 1);
    }
    public void setPoint(int intervalIndex, T point){
        points.set(intervalIndex - 1, point);
    }
}
