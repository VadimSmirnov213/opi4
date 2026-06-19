package org.example.monitoring;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class PointMetricsService {
    private static final double DISPLAY_LIMIT = 5.0;

    private final AtomicLong totalClicks = new AtomicLong(0);
    private final AtomicLong totalPoints = new AtomicLong(0);
    private final AtomicLong hitPoints = new AtomicLong(0);
    private final AtomicLong outOfBoundsEvents = new AtomicLong(0);

    private volatile OutOfBoundsNotifier outOfBoundsNotifier;

    public void setOutOfBoundsNotifier(OutOfBoundsNotifier notifier) {
        this.outOfBoundsNotifier = notifier;
    }

    public void recordSavedPoint(boolean hit) {
        totalClicks.incrementAndGet();
        totalPoints.incrementAndGet();
        if (hit) {
            hitPoints.incrementAndGet();
        }
    }

    public void recordOutOfBounds(double x, double y) {
        outOfBoundsEvents.incrementAndGet();
        OutOfBoundsNotifier notifier = outOfBoundsNotifier;
        if (notifier != null) {
            notifier.notifyOutOfBounds(x, y);
        }
    }

    public boolean isOutsideDisplay(Double x, Double y) {
        if (x == null || y == null) {
            return false;
        }
        return Math.abs(x) > DISPLAY_LIMIT || Math.abs(y) > DISPLAY_LIMIT;
    }

    public long getTotalClicks() {
        return totalClicks.get();
    }

    public long getTotalPoints() {
        return totalPoints.get();
    }

    public long getHitPoints() {
        return hitPoints.get();
    }

    public long getOutOfBoundsEvents() {
        return outOfBoundsEvents.get();
    }

    public double getHitPercent() {
        long clicks = totalClicks.get();
        if (clicks == 0) {
            return 0.0;
        }
        return (hitPoints.get() * 100.0) / clicks;
    }
}
