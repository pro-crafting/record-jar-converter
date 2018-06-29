package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.pro_crafting.tools.recordjarconverter.service.DecoderContext;
import com.pro_crafting.tools.recordjarconverter.service.ErrorCode;
import com.pro_crafting.tools.recordjarconverter.service.Violation;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Dependent
@Named(Names.FIELD)
public class FieldLineDecoder implements LineByLineDecoder<Map.Entry<String, String>> {
    public static final String FIELD_SEPERATOR = ":";
    public static final String LINE_CONTINUATION = "\\";

    private String name;
    private String body;
    private List<Violation> violations = new ArrayList<>();

    @Inject
    private DecoderContext context;

    @Override
    public void parseLine(String line) {
        //TODO: Line continuations have to start with a whitespace
        //validate that
        line = line.trim();
        if (line.endsWith(LINE_CONTINUATION)) {
            line = line.substring(0, line.length() - 1);
        }

        if (this.name == null) {
            String[] tokens = line.split(FIELD_SEPERATOR);

            if (tokens.length < 2) {
                context.addViolation(line, ErrorCode.ERROR_FIELD_NO_NAME_OR_NO_BODY);
                return;
            }

            /*
               The separator MAY be surrounded on either side by any amount of
               horizontal whitespace (tab or space characters).  The normal
               convention is one space on each side.
             */
            tokens[0] = CharMatcher.whitespace().trimTrailingFrom(tokens[0]);
            tokens[1] = CharMatcher.whitespace().trimLeadingFrom(tokens[1]);

            // Whitespace characters and colon (":", %x3A) are not permitted in a field-name.
            if (tokens[0].contains(" ")) {
                context.addViolation(line, ErrorCode.ERROR_FIELD_NAME_INVALID);
                return;
            }

            this.name = tokens[0];
            this.body = tokens[1];
            return;
        }

        if (this.body != null) {
            this.body += line;
        }
    }

    @Override
    public boolean caresAboutLine(String line) {
        // Successive lines in the same field-body begin with one or more whitespace characters.
        // This decoder should be reset before it is called with a new field: body pair
        return name == null || line.startsWith(" ");
    }

    @Override
    public Map.Entry<String, String> gatherData() {
        if (Strings.isNullOrEmpty(this.name)) {
            context.addViolation(this.name, ErrorCode.ERROR_FIELD_NO_NAME_OR_NO_BODY);
        } else if (Strings.isNullOrEmpty(this.body)) {
            /*
               Note that entirely blank continuation lines are not permitted.  That
               is, this record is illegal, since the field-body [..] would
               be the empty string.
             */
            context.addViolation(this.name, ErrorCode.ERROR_FIELD_EMPTY_BODY);
        }

        if (!hasData()) {
            return null;
        }

        return new AbstractMap.SimpleEntry<>(name, body);
    }

    @Override
    public void reset() {
        name = null;
        body = null;
        violations.clear();
    }

    @Override
    public boolean hasData() {
        return this.name != null;
    }
}
