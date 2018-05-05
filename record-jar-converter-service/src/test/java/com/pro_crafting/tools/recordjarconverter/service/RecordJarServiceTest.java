package com.pro_crafting.tools.recordjarconverter.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * All examples are taken from either RFC5646 or the art of unix programming
 */
public class RecordJarServiceTest {
    public static final String ENCODING = "UTF-8";

    @Test
    void testConvertSingleFieldSingleRecord() {
        String line = "Planet: Mercury";

        RecordJarService service = new RecordJarService();
        List<Map<String, String>> records = service.convert(new ByteArrayInputStream(line.getBytes(Charset.forName(ENCODING))), ENCODING);

        assertEquals(1, records.size());
        assertEquals(1, records.get(0).size());
        assertTrue(records.get(0).containsKey("Planet"));
        assertEquals("Mercury", records.get(0).get("Planet"));
    }

    @Test
    void testConvertMultipleFieldsSingleRecord() {
        String line = String.join(System.lineSeparator(),
                "Planet: Mercury",
                "Orbital-Radius: 57,910,000 km",
                "Diameter: 4,880 km",
                "Mass: 3.30e23 kg");

        RecordJarService service = new RecordJarService();
        List<Map<String, String>> records = service.convert(new ByteArrayInputStream(line.getBytes(Charset.forName(ENCODING))), ENCODING);

        assertEquals(1, records.size());
        assertEquals(4, records.get(0).size());
        assertEquals("Mercury", records.get(0).get("Planet"));
        assertEquals("57,910,000 km", records.get(0).get("Orbital-Radius"));
        assertEquals("4,880 km", records.get(0).get("Diameter"));
        assertEquals("3.30e23 kg", records.get(0).get("Mass"));
    }

    @Test
    void testConvertMultipleFieldsMultipleRecords() {
        String line = String.join(System.lineSeparator(),
                "Planet: Mercury",
                "Orbital-Radius: 57,910,000 km",
                "Diameter: 4,880 km",
                "Mass: 3.30e23 kg",
                "%%",
                "Planet: Venus",
                "Orbital-Radius: 108,200,000 km",
                "Diameter: 12,103.6 km",
                "Mass: 4.869e24 kg",
                "%%",
                "Planet: Earth",
                "Orbital-Radius: 149,600,000 km",
                "Diameter: 12,756.3 km",
                "Mass: 5.972e24 kg",
                "Moons: Luna");

        RecordJarService service = new RecordJarService();
        List<Map<String, String>> records = service.convert(new ByteArrayInputStream(line.getBytes(Charset.forName(ENCODING))), ENCODING);

        assertEquals(3, records.size());
        assertEquals(4, records.get(0).size());
        assertEquals("Mercury", records.get(0).get("Planet"));
        assertEquals("57,910,000 km", records.get(0).get("Orbital-Radius"));
        assertEquals("4,880 km", records.get(0).get("Diameter"));
        assertEquals("3.30e23 kg", records.get(0).get("Mass"));
        assertEquals(4, records.get(1).size());
        assertEquals("Venus", records.get(1).get("Planet"));
        assertEquals("108,200,000 km", records.get(1).get("Orbital-Radius"));
        assertEquals("12,103.6 km", records.get(1).get("Diameter"));
        assertEquals("4.869e24 kg", records.get(1).get("Mass"));
        assertEquals(5, records.get(2).size());
        assertEquals("Earth", records.get(2).get("Planet"));
        assertEquals("149,600,000 km", records.get(2).get("Orbital-Radius"));
        assertEquals("12,756.3 km", records.get(2).get("Diameter"));
        assertEquals("5.972e24 kg", records.get(2).get("Mass"));
        assertEquals("Luna", records.get(2).get("Moons"));
    }

    @Test
    public void testConvertMultipleMultilineFieldSingleRecord() {
        String line = String.join(System.lineSeparator(),
                "Type: language",
                "Subtag: ia",
                "Description: Interlingua (International Auxiliary Language \\",
                " Association)",
                "Added: 2005-08-16");

        RecordJarService service = new RecordJarService();
        List<Map<String, String>> records = service.convert(new ByteArrayInputStream(line.getBytes(Charset.forName(ENCODING))), ENCODING);

        assertEquals(1, records.size());
        assertEquals(4, records.get(0).size());
        assertEquals("language", records.get(0).get("Type"));
        assertEquals("ia", records.get(0).get("Subtag"));
        assertEquals("2005-08-16", records.get(0).get("Added"));
        assertEquals("Interlingua (International Auxiliary Language Association)", records.get(0).get("Description"));
    }

    @Test
    public void testConvertMultipleMultilineFieldMultipleRecords() {
        String line = String.join(System.lineSeparator(),
                "%%",
                "Type: language",
                "Subtag: ia",
                "Description: Interlingua (International Auxiliary Language \\",
                " Association)",
                "Added: 2005-08-16",
                "%%",
                "Type: language",
                "Subtag: id",
                "Description: Indonesian",
                "Added: 2005-08-16",
                "Suppress-Script: Latn",
                "%%",
                "Type: language",
                "Subtag: nb",
                "Description: Norwegian Bokm&#xE5;l",
                "Added: 2005-08-16",
                "Suppress-Script: Latn",
                "%%"
                );

        RecordJarService service = new RecordJarService();
        List<Map<String, String>> records = service.convert(new ByteArrayInputStream(line.getBytes(Charset.forName(ENCODING))), ENCODING);

        assertEquals(3, records.size());
        assertEquals(4, records.get(0).size());
        assertEquals("language", records.get(0).get("Type"));
        assertEquals("ia", records.get(0).get("Subtag"));
        assertEquals("2005-08-16", records.get(0).get("Added"));
        assertEquals("Interlingua (International Auxiliary Language Association)", records.get(0).get("Description"));
        assertEquals(5, records.get(1).size());
        assertEquals(5, records.get(2).size());
    }
}