package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void main_ShouldPrintRunHint() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(outputStream));
            Main.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String output = outputStream.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("start-jetty.sh"));
    }
}
