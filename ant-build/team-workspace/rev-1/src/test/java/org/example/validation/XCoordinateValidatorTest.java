package org.example.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XCoordinateValidatorTest {

    private final XCoordinateValidator validator = new XCoordinateValidator();

    @Test
    void validate_ShouldPass_ForBoundaryValues() {
        assertDoesNotThrow(() -> validator.validate(-3.0));
        assertDoesNotThrow(() -> validator.validate(5.0));
    }

    @Test
    void validate_ShouldThrow_WhenValueOutOfRange() {
        assertThrows(ValidationException.class, () -> validator.validate(-3.1));
        assertThrows(ValidationException.class, () -> validator.validate(5.1));
    }

    @Test
    void validate_ShouldThrow_WhenValueIsNullOrNaN() {
        assertThrows(ValidationException.class, () -> validator.validate(null));
        assertThrows(ValidationException.class, () -> validator.validate(Double.NaN));
    }
}
