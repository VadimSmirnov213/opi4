package org.example.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AreaChecker {

    public boolean checkHit(double x, double y, double r) {
        boolean inRectangle = x >= -r && x <= 0 && y >= -r && y <= 0;
        boolean inTriangle = x >= -r / 2 && x <= 0 && y >= 0 && y <= 2 * x + r;
        boolean inQuarterCircle = x >= 0 && y >= 0 && (x * x + y * y <= (r * r) / 4);
        return inRectangle || inTriangle || inQuarterCircle;
    }
}
