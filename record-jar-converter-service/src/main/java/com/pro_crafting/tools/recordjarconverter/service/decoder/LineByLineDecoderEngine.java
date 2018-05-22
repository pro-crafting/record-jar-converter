package com.pro_crafting.tools.recordjarconverter.service.decoder;

import javax.enterprise.context.Dependent;

@Dependent
public class LineByLineDecoderEngine {

    public <T> T chainNextDecoder(LineByLineDecoder<T> next, String line) {
        T returnData = null;
        if (next.caresAboutLine(line)) {
            next.parseLine(line);
        } else {
            returnData = next.gatherData();
            next.reset();

            if (next.caresAboutLine(line)) {
                next.parseLine(line);
            }
        }

        return returnData;
    }
}
