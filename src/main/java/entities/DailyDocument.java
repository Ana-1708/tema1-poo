package entities;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DailyDocument<T> {
    private String biddingZoneDomain;
    private String resolution;
    private ZonedDateTime periodEnd;
    private ZonedDateTime periodStart;
    private final List<T> points = new ArrayList<>(Collections.nCopies(96, null));

    /**
     * Returns the point stored for the given interval.
     *
     * @param intervalIndex the index of the interval, from 1 to 96
     * @return the point, or null if there is none
     */
    public final T getPoint(final int intervalIndex) {
        return points.get(intervalIndex - 1);
    }

    /**
     * Stores a point for the given interval.
     *
     * @param intervalIndex the index of the interval, from 1 to 96
     * @param point the point to store
     */
    public final void setPoint(final int intervalIndex, final T point) {
        points.set(intervalIndex - 1, point);
    }
}
