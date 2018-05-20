package com.pro_crafting.tools.recordjarconverter.service.decoder;

import com.pro_crafting.tools.recordjarconverter.service.Violation;

import java.util.Collection;
import java.util.Map;

/**
 * A decoder is
 * @param <T>
 */
public interface LineByLineDecoder<T> {
    /**
     *
     * @param line line currently being read. Not allowed to be null.
     * @param lineNumber line number of the currently read line
     */
    void parseLine(String line, int lineNumber);

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
     * Get all produced violations by this decoder
     * @return returns all violations, or an empty Collection, if no violation was produced
     */
    Collection<Violation> getViolations();

    /**
     * Has this decoder read any data successfully
     * @return true, if data is available via {@link #gatherData()}, otherwise false
     */
    boolean hasData();
}
