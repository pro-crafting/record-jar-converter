package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.google.common.base.CharMatcher;
import com.pro_crafting.tools.recordjarconverter.service.DecoderContext;
import com.pro_crafting.tools.recordjarconverter.service.ErrorCode;
import com.pro_crafting.tools.recordjarconverter.service.model.Field;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named(Names.FIELD)
public class FieldLineDecoder implements LineByLineDecoder<Field<String, String>> {
    public static final String FIELD_SEPERATOR = ":";
    public static final String LINE_CONTINUATION = "\\";

    private String name;
    private String body;
    private Field<String, String> field = new Field<>();

    @Inject
    DecoderContext context;

    @Override
    public void parseLine(String line) {
        line = CharMatcher.whitespace().trimTrailingFrom(line);
        if (line.endsWith(LINE_CONTINUATION)) {
            line = line.substring(0, line.length() - 1);
        }

        if (this.name == null) {
            int sepPosition = line.indexOf(FIELD_SEPERATOR);

            if (sepPosition == -1) {
                context.addViolation(line, ErrorCode.ERROR_FIELD_NO_NAME_OR_NO_BODY);
                return;
            }
            String fieldName = line.substring(0, sepPosition);
            String fieldBody = line.substring(sepPosition + 1);

            /*
               The separator MAY be surrounded on either side by any amount of
               horizontal whitespace (tab or space characters).  The normal
               convention is one space on each side.
             */
            fieldName = fieldName.trim();
            fieldBody = CharMatcher.whitespace().trimLeadingFrom(fieldBody);

            // Whitespace characters and colon (":", %x3A) are not permitted in a field-name.
            // field-name   = 1*character
            if (fieldName.contains(" ") || fieldName.isEmpty()) {
                context.addViolation(line, ErrorCode.ERROR_FIELD_NAME_INVALID);
                return;
            }

            this.name = fieldName;
            this.body = fieldBody;
            return;
        }

        if (this.body != null) {
            // Successive lines in the same field-body begin with one or more
            // whitespace characters.
            if (!line.startsWith(" ")) {
                context.addViolation(line, ErrorCode.WARNING_SUCCESSIVE_LINE_NO_LEADING_WHITESPACE);
            }
            line = CharMatcher.whitespace().trimLeadingFrom(line);
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
    public Field<String, String> gatherData() {
        if (this.name == null) {
            context.addViolation("", ErrorCode.ERROR_FIELD_NAME_INVALID);
        } else if (this.body == null || this.body.isEmpty()) {
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

        field.setKey(name);
        field.setValue(body);
        return field;
    }

    @Override
    public void reset() {
        name = null;
        body = null;
    }

    @Override
    public boolean hasData() {
        return this.name != null;
    }
}
