package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.pro_crafting.tools.recordjarconverter.service.Violation;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.Collection;

@Dependent
public class LineByLineDecoderEngine {
    private Collection<Violation> violations = new ArrayList<>();

    public <T> T chainNextDecoder(LineByLineDecoder<T> next, String line, int lineNumber) {
        T returnData = null;
        if (next.caresAboutLine(line)) {
            next.parseLine(line, lineNumber);
        } else {

            returnData = next.gatherData();

            this.getViolations().addAll(next.getViolations());
            next.reset();

            if (next.caresAboutLine(line)) {
                next.parseLine(line, lineNumber);
            }
        }

        return returnData;
    }

    public Collection<Violation> getViolations() {
        return violations;
    }
}
