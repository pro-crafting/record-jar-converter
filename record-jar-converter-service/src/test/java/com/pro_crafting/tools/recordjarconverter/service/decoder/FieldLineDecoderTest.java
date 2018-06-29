package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.pro_crafting.tools.recordjarconverter.service.DecoderContext;
import com.pro_crafting.tools.recordjarconverter.service.RecordJarService;
import com.pro_crafting.tools.recordjarconverter.service.decoder.FieldLineDecoder;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@EnableWeld
class FieldLineDecoderTest {

    @WeldSetup
    private WeldInitiator weld = WeldInitiator.from(WeldInitiator.createWeld().addPackage(true, RecordJarService.class)).activate(RequestScoped.class).build();

    @Inject
    private FieldLineDecoder decoder;

    @Inject
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

        decoder = weld.select(FieldLineDecoder.class).get();
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
}