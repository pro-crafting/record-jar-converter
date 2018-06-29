package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.pro_crafting.tools.recordjarconverter.service.model.Record;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Dependent
@Named(Names.RECORD)
public class RecordLineDecoder implements LineByLineDecoder<Record> {
    public static final String RECORD_SEPERATOR = "%%";

    @Inject
    @Named(Names.FIELD)
    private LineByLineDecoder<Map.Entry<String, String>> decoder;

    @Inject
    @Named(Names.COMMENT)
    private LineByLineDecoder<List<String>> commentDecoder;

    @Inject
    private LineByLineDecoderEngine engine;

    private final Record record = new Record();

    @Override
    public void parseLine(String line) {
        Map.Entry<String, String> field = engine.chainNextDecoder(decoder, line);
        if (field != null) {
            this.record.getFields().put(field.getKey(), field.getValue());
        }

        List<String> comments = engine.chainNextDecoder(commentDecoder, line);
        if (comments != null) {
            this.record.setComments(comments);
        }
    }

    @Override
    public boolean caresAboutLine(String line) {
        return  (record.isEmpty() && line.startsWith(CommentLineDecoder.COMMENT_START)) || !line.startsWith(RECORD_SEPERATOR);
    }

    @Override
    public Record gatherData() {
        if (!hasData()) {
            return null;
        }

        Record gatheredRecordLines = Record.of(record);
        Map.Entry<String, String> field = decoder.gatherData();
        if (field == null) {
            return gatheredRecordLines;
        }
        gatheredRecordLines.getFields().put(field.getKey(), field.getValue());
        return gatheredRecordLines;
    }

    @Override
    public void reset() {
        this.decoder.reset();
        this.record.clear();
    }

    @Override
    public boolean hasData() {
        return !record.isEmpty() || decoder.hasData();
    }
}
