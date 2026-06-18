package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PointRequestDtoTest {

    @Test
    void init_ShouldSetDefaultRadius_WhenRadiusIsNull() {
        PointRequestDto dto = new PointRequestDto(1.0, 2.0, null);

        dto.init();

        assertEquals(2.0, dto.getR());
    }

    @Test
    void gettersAndSetters_ShouldWorkAndToStringContainValues() {
        PointRequestDto dto = new PointRequestDto();
        dto.setX(1.5);
        dto.setY(-2.0);
        dto.setR(3.25);

        assertEquals(1.5, dto.getX());
        assertEquals(-2.0, dto.getY());
        assertEquals(3.25, dto.getR());
        assertTrue(dto.toString().contains("x=1.5"));
    }
}
