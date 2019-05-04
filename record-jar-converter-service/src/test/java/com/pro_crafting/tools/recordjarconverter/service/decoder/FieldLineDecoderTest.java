package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.pro_crafting.tools.recordjarconverter.service.DecoderContext;
import com.pro_crafting.tools.recordjarconverter.service.ErrorCode;
import com.pro_crafting.tools.recordjarconverter.service.RecordJarService;
import com.pro_crafting.tools.recordjarconverter.service.Violation;
import com.pro_crafting.tools.recordjarconverter.service.model.Field;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
class FieldLineDecoderTest {

    @Spy
    @InjectMocks
    private FieldLineDecoder decoder;

    @Spy
    private DecoderContext context;

    @Test
    void testParseLine() {
        String line = "Planet: Earth \\";
        String line2 = " of the sol system";
        String failingLine = "Sol System";

        assertEquals(0, context.getViolations().size());

        decoder.parseLine(line);
        decoder.parseLine(line2);
        assertEquals(0, context.getViolations().size());
        assertEquals("Planet", decoder.gatherData().getKey());
        assertEquals("Earth of the sol system", decoder.gatherData().getValue());

        decoder.reset();
        decoder.parseLine(line2);
        decoder.parseLine(failingLine);
        assertEquals(2, context.getViolations().size());
    }

    @Test
    void testCaresAboutLine() {
        String line = "Planet: Earth \\";
        String line2 = " of the sol system";
        String failingLine = "Sol System";

        assertTrue(decoder.caresAboutLine(line));
        assertTrue(decoder.caresAboutLine(line2));
        assertTrue(decoder.caresAboutLine(failingLine));
    }

    @Test
    void testGetViolations() {
        String failingLine = "Sol System";
        assertTrue(decoder.caresAboutLine(failingLine));
        assertTrue(context.getViolations().isEmpty());
    }

    @Test
    void testRemovesOneLineContinuationChar() {
        String line = "Planet: Mercury \\\\";
        String line2 = "  is the greatest planet";

        decoder.parseLine(line);
        decoder.parseLine(line2);

        Field<String, String> data = decoder.gatherData();

        assertEquals("Planet", data.getKey());
        assertEquals("Mercury \\is the greatest planet", data.getValue());
    }

    @Test
    void testNoLeadingWhitespaceViolation() {
        String line = "Planet: Mercury \\\\";
        String line2 = "is the greatest planet";

        decoder.parseLine(line);
        decoder.parseLine(line2);

        Set<Violation> violations = context.getViolations();
        assertEquals(1, violations.size());
        assertEquals(ErrorCode.WARNING_SUCCESSIVE_LINE_NO_LEADING_WHITESPACE, violations.iterator().next().getErrorCode());
    }
}