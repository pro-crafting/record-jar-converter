package com.pro_crafting.tools.recordjarconverter.service;

import com.pro_crafting.tools.recordjarconverter.service.decoder.FieldLineDecoder;
import com.pro_crafting.tools.recordjarconverter.service.decoder.LineByLineDecoderEngine;
import com.pro_crafting.tools.recordjarconverter.service.decoder.RecordLineDecoder;
import com.pro_crafting.tools.recordjarconverter.service.decoder.RecordSequenceLineDecoder;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class, WeldJunit5Extension.class})
public class RecordLineDecoderTest {

    @WeldSetup
    private WeldInitiator weld = WeldInitiator.of(RecordLineDecoder.class, FieldLineDecoder.class, LineByLineDecoderEngine.class, RecordLineDecoder.class, RecordSequenceLineDecoder.class);

    @Inject
    private RecordLineDecoder decoder;

    @Test
    void testParseLineMultipleFields() {
        decoder.parseLine("Planet: Mercury", 1);
        decoder.parseLine("Orbital-Radius: 57,910,000 km", 2);
        decoder.parseLine("Diameter: 4,880 km", 3);
        decoder.parseLine("Mass: 3.30e23 kg", 4);

        Map<String, String> data = decoder.gatherData();
        assertEquals(4, data.size());
    }
}