package org.example.monitoring;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@ApplicationScoped
public class MBeanRegistrar {
    private static final String DOMAIN = "org.example.monitoring";
    private static final String POINT_COUNTERS_NAME = DOMAIN + ":type=PointCounters";
    private static final String HIT_RATIO_NAME = DOMAIN + ":type=HitRatio";

    @Inject
    private PointMetricsService metricsService;

    private volatile boolean registered;
    private MBeanServer mBeanServer;
    private ObjectName pointCountersObjectName;
    private ObjectName hitRatioObjectName;

    @PostConstruct
    public void register() {
        try {
            mBeanServer = ManagementFactory.getPlatformMBeanServer();

            PointCounters pointCounters = new PointCounters(metricsService);
            HitRatio hitRatio = new HitRatio(metricsService);

            pointCountersObjectName = new ObjectName(POINT_COUNTERS_NAME);
            hitRatioObjectName = new ObjectName(HIT_RATIO_NAME);

            registerOrReplace(pointCountersObjectName, pointCounters);
            registerOrReplace(hitRatioObjectName, hitRatio);

            metricsService.setOutOfBoundsNotifier(pointCounters::sendOutOfBoundsNotification);
            registered = true;
            System.out.println("[MBeans] Registered: " + POINT_COUNTERS_NAME + ", " + HIT_RATIO_NAME);
        } catch (Exception ignored) {
            registered = false;
            metricsService.setOutOfBoundsNotifier(null);
            System.err.println("[MBeans] Registration failed");
            ignored.printStackTrace(System.err);
        }
    }

    public void ensureRegistered() {
        if (!registered) {
            register();
        }
    }

    @PreDestroy
    public void unregister() {
        metricsService.setOutOfBoundsNotifier(null);
        if (mBeanServer == null) {
            return;
        }
        unregisterIfPresent(pointCountersObjectName);
        unregisterIfPresent(hitRatioObjectName);
    }

    private void registerOrReplace(ObjectName objectName, Object object) throws Exception {
        if (mBeanServer.isRegistered(objectName)) {
            mBeanServer.unregisterMBean(objectName);
        }
        mBeanServer.registerMBean(object, objectName);
    }

    private void unregisterIfPresent(ObjectName objectName) {
        try {
            if (objectName != null && mBeanServer.isRegistered(objectName)) {
                mBeanServer.unregisterMBean(objectName);
            }
        } catch (Exception ignored) {
        }
    }
}
