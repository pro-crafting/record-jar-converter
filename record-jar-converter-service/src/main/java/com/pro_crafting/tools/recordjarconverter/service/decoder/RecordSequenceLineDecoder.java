package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.google.common.collect.Lists;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Dependent
public class RecordSequenceLineDecoder implements LineByLineDecoder<List<Map<String, String>>> {
    public static final String RECORD_SEPERATOR = "%%";

    @Inject
    private LineByLineDecoder<Map<String, String>> decoder;

    @Inject
    private LineByLineDecoderEngine engine;

    private List<Map<String, String>> records = new ArrayList<>();


    @Override
    public void parseLine(String line) {
        Map<String, String> record = engine.chainNextDecoder(decoder, line);
        if (record != null) {
            this.records.add(record);
        }
    }

    @Override
    public boolean caresAboutLine(String line) {
        return line.equalsIgnoreCase(RECORD_SEPERATOR) || decoder.caresAboutLine(line);
    }

    @Override
    public List<Map<String, String>> gatherData() {
        if (!hasData()) {
            return null;
        }

        List<Map<String, String>> gatheredData = Lists.newArrayList(this.records);
        Map<String, String> record = decoder.gatherData();
        if (record == null) {
            return gatheredData;
        }
        gatheredData.add(record);
        return gatheredData;
    }

    @Override
    public void reset() {
        this.decoder.reset();
        this.records.clear();
    }

    @Override
    public boolean hasData() {
        return !records.isEmpty() || decoder.hasData();
    }
}
