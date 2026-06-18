package org.example.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RadiusValidatorTest {

    private final RadiusValidator validator = new RadiusValidator();

    @Test
    void validate_ShouldPass_ForAllowedStepValues() {
        assertDoesNotThrow(() -> validator.validate(1.0));
        assertDoesNotThrow(() -> validator.validate(2.25));
        assertDoesNotThrow(() -> validator.validate(4.0));
    }

    @Test
    void validate_ShouldThrow_ForInvalidStepOrRange() {
        assertThrows(ValidationException.class, () -> validator.validate(2.1));
        assertThrows(ValidationException.class, () -> validator.validate(4.25));
    }
}
