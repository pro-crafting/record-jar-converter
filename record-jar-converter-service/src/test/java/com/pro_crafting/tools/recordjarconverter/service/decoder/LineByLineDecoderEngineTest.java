package com.pro_crafting.tools.recordjarconverter.service.decoder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class LineByLineDecoderEngineTest {

    @Spy
    private LineByLineDecoderEngine engine;

    @Mock
    private LineByLineDecoder<Object> decoder;

    @Test
    void testChainNextDecoderCallsParserWhenCaring() {
        when(decoder.caresAboutLine(any())).thenReturn(true);
        engine.chainNextDecoder(decoder, "");
        verify(decoder).parseLine("");
    }
}