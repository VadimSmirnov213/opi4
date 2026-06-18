package org.example.exception;

import org.example.validation.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ExceptionsTest {

    @Test
    void validationException_ConstructorsShouldStoreMessageAndCause() {
        RuntimeException cause = new RuntimeException("cause");
        ValidationException exception = new ValidationException("validation error", cause);

        assertEquals("validation error", exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    @Test
    void repositoryException_ConstructorsShouldStoreMessageAndCause() {
        RuntimeException cause = new RuntimeException("cause");
        RepositoryException exception = new RepositoryException("repo error", cause);

        assertEquals("repo error", exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}
