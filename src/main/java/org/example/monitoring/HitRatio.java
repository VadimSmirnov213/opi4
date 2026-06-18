package org.example.monitoring;

public class HitRatio implements HitRatioMBean {
    private final PointMetricsService metricsService;

    public HitRatio(PointMetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public double getHitPercent() {
        return metricsService.getHitPercent();
    }

    @Override
    public long getTotalClicks() {
        return metricsService.getTotalClicks();
    }
}
