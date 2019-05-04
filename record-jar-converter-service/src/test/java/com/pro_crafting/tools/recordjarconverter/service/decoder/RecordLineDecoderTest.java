package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.pro_crafting.tools.recordjarconverter.service.RecordJarService;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

public class RecordLineDecoderTest {

    private RecordLineDecoder decoder;

    @BeforeEach
    public void setup() {
        decoder = new RecordLineDecoder(new FieldLineDecoder(), new CommentLineDecoder());
    }
    
    @Test
    void testParseLineMultipleFields() {
        decoder.parseLine("Planet: Mercury");
        decoder.parseLine("Orbital-Radius: 57,910,000 km");
        decoder.parseLine("Diameter: 4,880 km");
        decoder.parseLine("Mass: 3.30e23 kg");

        Record data = decoder.gatherData();
        assertEquals(4, data.getFields().size());
    }

    @Test
    void testParseLineCommentsNoFields() {
        decoder.parseLine("%% First line");
        decoder.parseLine("%% Second line");

        Record data = decoder.gatherData();
        assertNull(data);
    }

    @Test
    void testParseLineCommentsWithFields() {
        decoder.parseLine("%% First line");
        decoder.parseLine("%% Second line");

        decoder.parseLine("Planet: Mercury");
        decoder.parseLine("Orbital-Radius: 57,910,000 km");
        decoder.parseLine("Diameter: 4,880 km");
        decoder.parseLine("Mass: 3.30e23 kg");

        Record data = decoder.gatherData();
        assertNotNull(data);
        assertEquals(4, data.getFields().size());
        assertEquals(2, data.getComments().length);
    }
}