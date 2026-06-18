package org.example.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class YCoordinateValidatorTest {

    private final YCoordinateValidator validator = new YCoordinateValidator();

    @Test
    void validate_ShouldPass_ForIntegerInRange() {
        assertDoesNotThrow(() -> validator.validate(-2.0));
        assertDoesNotThrow(() -> validator.validate(0.0));
        assertDoesNotThrow(() -> validator.validate(2.0));
    }

    @Test
    void validate_ShouldThrow_ForFractionalOrOutOfRangeValues() {
        assertThrows(ValidationException.class, () -> validator.validate(1.5));
        assertThrows(ValidationException.class, () -> validator.validate(-3.0));
    }
}
