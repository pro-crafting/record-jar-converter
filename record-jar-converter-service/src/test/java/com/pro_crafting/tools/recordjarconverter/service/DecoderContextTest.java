package com.pro_crafting.tools.recordjarconverter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.MissingResourceException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class DecoderContextTest {

    @Spy
    private DecoderContext context = new DecoderContext();

    @Test
    void testAddViolationWithoutColumnCallsWithColumn() {
        context.addViolation("", ErrorCode.ERROR_FIELD_EMPTY_BODY, 0);
        verify(context).addViolation(any(String.class), any(String.class), anyInt(), anyInt());
    }

    @Test
    void testAddViolationGetProperties() {
        context.addViolation("", ErrorCode.ERROR_FIELD_EMPTY_BODY, 0, 0);
        assertEquals(1, context.getViolations().size());
        assertNotNull(context.getViolations().iterator().next().getMessage());
    }

    @Test
    void testAddViolationThrowsMissingRessource() {
        Assertions.assertThrows(MissingResourceException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                context.addViolation("", "", 0, 0);
            }
        });
    }

    @Test
    void testGetViolations() {
        assertTrue(context.getViolations().isEmpty());
        context.getViolations().add(null);
        context.getViolations().add(new Violation("", "", "", 0, 0));
        assertTrue(context.getViolations().isEmpty());
    }
}