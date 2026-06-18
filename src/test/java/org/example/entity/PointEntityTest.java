package org.example.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PointEntityTest {

    @Test
    void constructorsAndAccessors_ShouldInitializeAndUpdateFields() {
        PointEntity entity = new PointEntity(1.0, 2.0, 3.0, true);

        assertEquals(1.0, entity.getX());
        assertEquals(2.0, entity.getY());
        assertEquals(3.0, entity.getR());
        assertTrue(entity.getHit());
        assertNotNull(entity.getTimestamp());

        entity.setId(10L);
        entity.setX(-1.0);
        entity.setY(-2.0);
        entity.setR(1.25);
        entity.setHit(false);
        LocalDateTime now = LocalDateTime.now();
        entity.setTimestamp(now);

        assertEquals(10L, entity.getId());
        assertEquals(-1.0, entity.getX());
        assertEquals(-2.0, entity.getY());
        assertEquals(1.25, entity.getR());
        assertFalse(entity.getHit());
        assertEquals(now, entity.getTimestamp());
        assertTrue(entity.toString().contains("id=10"));
    }

    @Test
    void hitTextAndFormattedTime_ShouldReturnExpectedValues() {
        PointEntity nullHit = new PointEntity(0.0, 0.0, 1.0, null);
        assertEquals("Мимо", nullHit.getHitText());

        PointEntity hit = new PointEntity(0.0, 0.0, 1.0, true);
        assertEquals("Попадание", hit.getHitText());
        assertFalse(hit.getFormattedTime().isEmpty());
    }

    @Test
    void equalsAndHashCode_ShouldUseId() {
        PointEntity first = new PointEntity();
        PointEntity second = new PointEntity();
        first.setId(7L);
        second.setId(7L);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}
