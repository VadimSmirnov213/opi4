package org.example.i18n;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessagesTest {

    @Test
    void get_ShouldReturnExistingMessage() {
        String message = Messages.get("validation.x.null");
        assertTrue(message.contains("X"));
    }

    @Test
    void get_ShouldFormatMessageWithArguments() {
        String message = Messages.get("validation.r.range", 1.0, 4.0);
        assertTrue(message.contains("1"));
        assertTrue(message.contains("4"));
    }

    @Test
    void get_ShouldReturnPlaceholderForUnknownKey() {
        assertEquals("??missing.key??", Messages.get("missing.key"));
    }
}
