package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.google.common.base.CharMatcher;
import com.pro_crafting.tools.recordjarconverter.service.Violation;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import java.util.*;

@Dependent
public class FieldLineDecoder implements LineByLineDecoder<Map.Entry<String, String>> {
    public static final String FIELD_SEPERATOR = ":";
    public static final String LINE_CONTINUATION = "\\";

    private String name;
    private String body;
    private List<Violation> violations = new ArrayList<>();

    @Override
    public void parseLine(String line, int lineNumber) {
        line = line.trim();
        if (line.endsWith(LINE_CONTINUATION)) {
            line = line.substring(0, line.length() - 1);
        }

        if (this.name == null) {
            String[] tokens = line.split(FIELD_SEPERATOR);

            if (tokens.length < 2) {
                violations.add(new Violation(lineNumber));
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
                violations.add(new Violation(lineNumber));
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
        return line != null && ((line.contains(":") && name == null) || line.startsWith(" "));
    }

    @Override
    public Map.Entry<String, String> gatherData() {
        if (body == null || body.isEmpty()) {
            violations.add(new Violation(-1));
        }
        return hasData() ? new AbstractMap.SimpleEntry<>(name, body) : null;
    }

    @Override
    public void reset() {
        name = null;
        body = null;
        violations.clear();
    }

    @Override
    public Collection<Violation> getViolations() {
        return this.violations;
    }

    @Override
    public boolean hasData() {
        return this.name != null;
    }
}
