package org.example.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AreaCheckerTest {

    private final AreaChecker areaChecker = new AreaChecker();

    @Test
    void checkHit_ShouldReturnTrue_ForPointInsideRectangle() {
        assertTrue(areaChecker.checkHit(-1.0, -1.0, 2.0));
    }

    @Test
    void checkHit_ShouldReturnTrue_ForPointInsideTriangle() {
        assertTrue(areaChecker.checkHit(-0.5, 0.2, 2.0));
    }

    @Test
    void checkHit_ShouldReturnTrue_ForPointInsideQuarterCircle() {
        assertTrue(areaChecker.checkHit(0.5, 0.5, 2.0));
    }

    @Test
    void checkHit_ShouldReturnFalse_ForPointOutsideAllAreas() {
        assertFalse(areaChecker.checkHit(2.0, 2.0, 2.0));
    }
}
