package com.pro_crafting.tools.recordjarconverter.service.decoder;

/**
 * A decoder is
 * @param <T>
 */
public interface LineByLineDecoder<T> {
    /**
     * Instructs this decoder to begin parsing of a raw line of text.
     * @param line line currently being read. Not allowed to be null.
     */
    void parseLine(String line);

    /**
     * Determines if an implemtation cares about a given line, based on the format of the line
     * @param line line currently being read
     * @return true if this decoder cares about the line, and the line should be read, otherwise false
     */
    boolean caresAboutLine(String line);

    /**
     * Gathers data parsed by this decoder and subsequent decoders.
     * Will most likely be called once {@link #caresAboutLine(String)} isn't satisfied anymore.
     * @return the parsed data. Null should only be returned if no data is available, see {@link #hasData()}.
     * Changes to this object are not passed back to the producing decoder.
     */
    T gatherData();

    /**
     * Resets this decoders internal state to its initial state. Used when the decoder is getting reused.
     */
    void reset();

    /**
     * Has this decoder read any data successfully
     * @return true, if data is available via {@link #gatherData()}, otherwise false
     */
    boolean hasData();
}
