package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.pro_crafting.tools.recordjarconverter.service.Violation;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Dependent
public class RecordLineDecoder implements LineByLineDecoder<Map<String, String>> {
    public static final String RECORD_SEPERATOR = "%%";

    @Inject
    private LineByLineDecoder<Map.Entry<String, String>> decoder;

    @Inject
    private LineByLineDecoderEngine engine;

    private final Map<String, String> record = new HashMap<>();
    private final List<Violation> violations = new ArrayList<>();

    @Override
    public void parseLine(String line) {
        Map.Entry<String, String> field = engine.chainNextDecoder(decoder, line);
        if (field != null) {
            this.record.put(field.getKey(), field.getValue());
        }
    }

    @Override
    public boolean caresAboutLine(String line) {
        return !line.contains(RECORD_SEPERATOR);
    }

    @Override
    public Map<String, String> gatherData() {
        if (!hasData()) {
            return null;
        }

        Map<String, String> gatheredRecordLines = new HashMap<>(record);
        Map.Entry<String, String> field = decoder.gatherData();
        if (field == null) {
            return gatheredRecordLines;
        }
        gatheredRecordLines.put(field.getKey(), field.getValue());
        return gatheredRecordLines;
    }

    @Override
    public void reset() {
        this.decoder.reset();
        this.record.clear();
        this.violations.clear();
    }

    @Override
    public boolean hasData() {
        return !record.isEmpty() || decoder.hasData();
    }
}
