package com.pro_crafting.tools.recordjarconverter.service;

import com.pro_crafting.tools.recordjarconverter.service.decoder.LineByLineDecoder;
import com.pro_crafting.tools.recordjarconverter.service.decoder.LineByLineDecoderEngine;
import com.pro_crafting.tools.recordjarconverter.service.decoder.RecordLineDecoder;
import com.pro_crafting.tools.recordjarconverter.service.decoder.RecordSequenceLineDecoder;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.io.InputStream;
import java.util.*;

@Dependent
public class RecordJarService {

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String RECORD_SEPERATOR = "%%";
    public static final String FIELD_SEPERATOR = ":";
    public static final String LINE_CONTINUATION = "\\";

    @Inject
    private LineByLineDecoder<List<Map<String, String>>> decoder;

    @Inject
    private LineByLineDecoderEngine engine;

    public List<Map<String, String>> convert(InputStream content, String encoding) {
        if (encoding == null || encoding.isEmpty()) {
            encoding = DEFAULT_ENCODING;
        }

        Scanner scanner = new Scanner(content, encoding);
        int lineNumber = 0;
        while(scanner.hasNextLine()) {
            lineNumber++;

            String line = scanner.nextLine();
            engine.chainNextDecoder(decoder, line, lineNumber);
        }
        List<Map<String, String>> records = decoder.gatherData();
        if (!engine.getViolations().isEmpty()) {
            throw new ViolationException(engine.getViolations());
        }
        return records;
    }
}
