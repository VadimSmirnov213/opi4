package org.example.monitoring;

public interface PointCountersMBean {
    long getTotalPoints();

    long getHitPoints();

    long getOutOfBoundsEvents();
}
