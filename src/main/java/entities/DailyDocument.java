package entities;

import java.time.ZonedDateTime;
import java.util.List;

public class DailyDocument<T> {
    private String biddingZoneDomain;
    private String resolution;
    private ZonedDateTime periodEnd;
    private ZonedDateTime periodStart;
    private List<T> points;
}
