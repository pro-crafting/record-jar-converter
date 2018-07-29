package com.pro_crafting.tools.recordjarconverter.service;


import com.pro_crafting.tools.recordjarconverter.service.decoder.LineByLineDecoder;
import com.pro_crafting.tools.recordjarconverter.service.decoder.LineByLineDecoderEngine;
import com.pro_crafting.tools.recordjarconverter.service.decoder.Names;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@RequestScoped
public class RecordJarService {

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String RECORD_SEPERATOR = "%%";
    public static final String FIELD_SEPERATOR = ":";
    public static final String LINE_CONTINUATION = "\\";

    @Inject
    @Named(Names.RECORD_SEQUENCE)
    private LineByLineDecoder<List<Record>> decoder;

    private LineByLineDecoderEngine engine = new LineByLineDecoderEngine();

    @Inject
    private DecoderContext context;

    public List<Record> convert(InputStream content, String encoding) {
        if (encoding == null || encoding.isEmpty()) {
            encoding = DEFAULT_ENCODING;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(content))) {
            int lineNumber = 0;
            String line = reader.readLine();
            for(;line != null;  line = reader.readLine()) {
                lineNumber++;
                context.setLineNumber(lineNumber);
                engine.chainNextDecoder(decoder, line);
            }
        } catch (IOException e) {
            context.addViolation("", "IOEXCEPTION");
        }
        List<Record> records = decoder.gatherData();
        if (!context.getViolations().isEmpty()) {
            throw new ViolationException(context.getViolations());
        }
        return records;
    }
}
