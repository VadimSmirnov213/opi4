package org.example.monitoring;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class PointCounters extends NotificationBroadcasterSupport implements PointCountersMBean {
    private static final String OUT_OF_BOUNDS_TYPE = "org.example.point.outOfBounds";
    private static final AtomicLong SEQUENCE = new AtomicLong(1);

    private final PointMetricsService metricsService;

    public PointCounters(PointMetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public long getTotalPoints() {
        return metricsService.getTotalPoints();
    }

    @Override
    public long getHitPoints() {
        return metricsService.getHitPoints();
    }

    @Override
    public long getOutOfBoundsEvents() {
        return metricsService.getOutOfBoundsEvents();
    }

    public void sendOutOfBoundsNotification(double x, double y) {
        Notification notification = new Notification(
            OUT_OF_BOUNDS_TYPE,
            this,
            SEQUENCE.getAndIncrement(),
            System.currentTimeMillis(),
            String.format("Point is outside display area: x=%.2f, y=%.2f", x, y)
        );
        notification.setUserData(buildUserData(x, y));
        sendNotification(notification);
    }

    private CompositeData buildUserData(double x, double y) {
        try {
            String[] itemNames = {"x", "y", "timestamp"};
            OpenType<?>[] itemTypes = {SimpleType.DOUBLE, SimpleType.DOUBLE, SimpleType.DATE};
            CompositeType type = new CompositeType(
                "OutOfBoundsEvent",
                "Out of bounds point event",
                itemNames,
                itemNames,
                itemTypes
            );
            Object[] itemValues = {x, y, new Date()};
            return new CompositeDataSupport(type, itemNames, itemValues);
        } catch (OpenDataException e) {
            return null;
        }
    }
}
