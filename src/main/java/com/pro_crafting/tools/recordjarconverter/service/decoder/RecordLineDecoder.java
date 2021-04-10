package com.pro_crafting.tools.recordjarconverter.service.decoder;


import com.pro_crafting.tools.recordjarconverter.service.model.Field;
import com.pro_crafting.tools.recordjarconverter.service.model.Record;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named(Names.RECORD)
public class RecordLineDecoder implements LineByLineDecoder<Record> {
    public static final String RECORD_SEPERATOR = "%%";

    private LineByLineDecoder<Field<String, String>> decoder;

    private LineByLineDecoder<List<String>> commentDecoder;

    private LineByLineDecoderEngine engine = new LineByLineDecoderEngine();

    private final Record record = new Record();

    public RecordLineDecoder() {
        // cdi needs this for proxy
    }

    @Inject
    public RecordLineDecoder(@Named(Names.FIELD) LineByLineDecoder<Field<String, String>> decoder, @Named(Names.COMMENT) LineByLineDecoder<List<String>> commentDecoder) {
        this.decoder = decoder;
        this.commentDecoder = commentDecoder;
    }

    @Override
    public void parseLine(String line) {

        if (this.record.getFields().isEmpty()) {
            List<String> comments = engine.chainNextDecoder(commentDecoder, line);
            if (comments != null) {
                this.record.setComments(comments.toArray(new String[0]));
            }
        }

        if (!commentDecoder.hasData()) {
            Field<String, String> field = engine.chainNextDecoder(decoder, line);
            if (field != null ) {
                this.record.getFields().put(field.getKey(), field.getValue());
            }
        }
    }

    @Override
    public boolean caresAboutLine(String line) {
        return  (record.getFields().isEmpty() && line.startsWith(CommentLineDecoder.COMMENT_START)) || !line.startsWith(RECORD_SEPERATOR);
    }

    @Override
    public Record gatherData() {
        if (!hasData()) {
            return null;
        }

        Record gatheredRecordLines = new Record(record);
        Field<String, String> field = decoder.gatherData();
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
        return !record.getFields().isEmpty() || decoder.hasData();
    }
}
