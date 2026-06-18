package org.example.service;

import org.example.dto.PointRequestDto;
import org.example.entity.PointEntity;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PointFactoryTest {

    @Test
    void createFromDto_ShouldUseAreaCheckerAndMapFields() throws Exception {
        PointFactory factory = new PointFactory();
        setField(factory, "areaChecker", new AreaChecker());

        PointRequestDto dto = new PointRequestDto(-1.0, -1.0, 2.0);
        PointEntity entity = factory.createFromDto(dto);

        assertEquals(-1.0, entity.getX());
        assertEquals(-1.0, entity.getY());
        assertEquals(2.0, entity.getR());
        assertTrue(entity.getHit());
    }

    @Test
    void createFromDto_ShouldHandleNullCoordinatesAsZeroForHitCalculation() throws Exception {
        PointFactory factory = new PointFactory();
        setField(factory, "areaChecker", new AreaChecker());

        PointRequestDto dto = new PointRequestDto(null, null, null);
        PointEntity entity = factory.createFromDto(dto);

        assertEquals(null, entity.getX());
        assertEquals(null, entity.getY());
        assertEquals(null, entity.getR());
        assertTrue(entity.getHit());
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
