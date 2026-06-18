package org.example.monitoring;

@FunctionalInterface
public interface OutOfBoundsNotifier {
    void notifyOutOfBounds(double x, double y);
}
