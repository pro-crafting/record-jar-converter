package com.pro_crafting.tools.recordjarconverter.service;

import com.pro_crafting.tools.recordjarconverter.service.decoder.FieldLineDecoder;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

class FieldLineDecoderTest {

    private FieldLineDecoder decoder = new FieldLineDecoder();

    @Test
    void testParseLine() {
        String line = "Planet: Earth \\";
        String line2 = " of the sol system";
        String failingLine = "Sol System";

        FieldLineDecoder fieldLineDecoder = new FieldLineDecoder();
        assertEquals(0, fieldLineDecoder.getViolations().size());

        fieldLineDecoder.parseLine(line, 10);
        fieldLineDecoder.parseLine(line2, 11);
        assertEquals(0, fieldLineDecoder.getViolations().size());
        assertEquals("Planet", fieldLineDecoder.gatherData().getKey());
        assertEquals("Earth of the sol system", fieldLineDecoder.gatherData().getValue());

        fieldLineDecoder = new FieldLineDecoder();
        fieldLineDecoder.parseLine(line2, 10);
        fieldLineDecoder.parseLine(failingLine, 11);
        assertEquals(2, fieldLineDecoder.getViolations().size());
    }

    @Test
    void testCaresAboutLine() {
        String line = "Planet: Earth \\";
        String line2 = " of the sol system";
        String failingLine = "Sol System";

        FieldLineDecoder fieldLineDecoder = new FieldLineDecoder();
        assertTrue(fieldLineDecoder.caresAboutLine(line));
        assertTrue(fieldLineDecoder.caresAboutLine(line2));
        assertFalse(fieldLineDecoder.caresAboutLine(failingLine));
    }

    @Test
    void testGetViolations() {
        String failingLine = "Sol System";
        FieldLineDecoder fieldLineDecoder = new FieldLineDecoder();
        assertFalse(fieldLineDecoder.caresAboutLine(failingLine));
        assertTrue(fieldLineDecoder.getViolations().isEmpty());
    }
}