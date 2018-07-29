package com.pro_crafting.tools.recordjarconverter.service.decoder;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Named(Names.COMMENT)
public class CommentLineDecoder implements LineByLineDecoder<List<String>> {
    public static final String COMMENT_START = RecordSequenceLineDecoder.RECORD_SEPERATOR + " ";

    private List<String> comments = new ArrayList<>();

    @Override
    public void parseLine(String line) {
        comments.add(line.replace(COMMENT_START, ""));
    }

    @Override
    public boolean caresAboutLine(String line) {
        return line.startsWith(COMMENT_START);
    }

    @Override
    public List<String> gatherData() {
        return hasData() ? new ArrayList<>(this.comments) : null;
    }

    @Override
    public void reset() {
        comments.clear();
    }

    @Override
    public boolean hasData() {
        return !comments.isEmpty();
    }
}
