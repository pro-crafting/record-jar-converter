package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.google.common.collect.Lists;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Named(Names.RECORD_SEQUENCE)
public class RecordSequenceLineDecoder implements LineByLineDecoder<List<Record>> {
    public static final String RECORD_SEPERATOR = "%%";

    @Inject
    @Named(Names.RECORD)
    private LineByLineDecoder<Record> decoder;

    private LineByLineDecoderEngine engine = new LineByLineDecoderEngine();

    private List<Record> records = new ArrayList<>();


    @Override
    public void parseLine(String line) {
        Record record = engine.chainNextDecoder(decoder, line);
        if (record != null) {
            this.records.add(record);
        }
    }

    @Override
    public boolean caresAboutLine(String line) {
        return line.startsWith(RECORD_SEPERATOR) || decoder.caresAboutLine(line);
    }

    @Override
    public List<Record> gatherData() {
        if (!hasData()) {
            return null;
        }

        List<Record> gatheredData = Lists.newArrayList(this.records);
        Record record = decoder.gatherData();
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
