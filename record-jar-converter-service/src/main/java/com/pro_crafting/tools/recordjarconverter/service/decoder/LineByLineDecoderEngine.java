package com.pro_crafting.tools.recordjarconverter.service.decoder;

public class LineByLineDecoderEngine {

    public <T> T chainNextDecoder(LineByLineDecoder<T> next, String line) {
        if (next.caresAboutLine(line)) {
            next.parseLine(line);
            return null;
        }

        T returnData = next.gatherData();
        next.reset();

        if (next.caresAboutLine(line)) {
            next.parseLine(line);
        }
        return returnData;
    }
}
