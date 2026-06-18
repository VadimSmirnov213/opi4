package org.example.entity;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LocalDateTimeConverterTest {

    private final LocalDateTimeConverter converter = new LocalDateTimeConverter();

    @Test
    void convertToDatabaseColumn_ShouldConvertAndHandleNull() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 5, 1, 12, 30, 15);

        Timestamp timestamp = converter.convertToDatabaseColumn(dateTime);

        assertEquals(Timestamp.valueOf(dateTime), timestamp);
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void convertToEntityAttribute_ShouldConvertAndHandleNull() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(2026, 5, 1, 12, 30, 15));

        LocalDateTime dateTime = converter.convertToEntityAttribute(timestamp);

        assertEquals(timestamp.toLocalDateTime(), dateTime);
        assertNull(converter.convertToEntityAttribute(null));
    }
}
