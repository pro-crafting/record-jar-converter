package com.pro_crafting.tools.recordjarconverter.service;

import com.pro_crafting.tools.recordjarconverter.service.decoder.LineByLineDecoder;
import com.pro_crafting.tools.recordjarconverter.service.decoder.LineByLineDecoderEngine;
import com.pro_crafting.tools.recordjarconverter.service.decoder.Names;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

@Dependent
public class RecordJarService {

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String RECORD_SEPERATOR = "%%";
    public static final String FIELD_SEPERATOR = ":";
    public static final String LINE_CONTINUATION = "\\";

    @Inject
    @Named(Names.RECORD_SEQUENCE)
    private LineByLineDecoder<List<Record>> decoder;

    @Inject
    private LineByLineDecoderEngine engine;

    @Inject
    private DecoderContext context;

    public List<Record> convert(InputStream content, String encoding) {
        if (encoding == null || encoding.isEmpty()) {
            encoding = DEFAULT_ENCODING;
        }

        Scanner scanner = new Scanner(content, encoding);
        int lineNumber = 0;
        while(scanner.hasNextLine()) {
            lineNumber++;
            context.setLineNumber(lineNumber);

            String line = scanner.nextLine();
            engine.chainNextDecoder(decoder, line);
        }
        List<Record> records = decoder.gatherData();
        if (!context.getViolations().isEmpty()) {
            throw new ViolationException(context.getViolations());
        }
        return records;
    }
}
