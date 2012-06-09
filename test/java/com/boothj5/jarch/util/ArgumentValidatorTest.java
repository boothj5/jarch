package com.boothj5.jarch.util;

import static org.junit.Assert.* ;

import org.junit.Test;

import static com.boothj5.jarch.util.ArgumentValidator.*;

public class ArgumentValidatorTest {

    @Test
    public void notNullFails() {
        try {
            notNull(null, "Something should not be null");
        } catch (IllegalArgumentException e) {
            assertEquals("Something should not be null", e.getMessage());
        }
    }

    @Test
    public void notNullSucceeds() {
        notNull(new Integer(12), "Something should not be null");
        assertTrue(true);
    }
    
    @Test
    public void notNullOrEmptyFails() {
        try {
            notNullOrEmpty("", "Should not be empty");
        } catch (IllegalArgumentException e) {
            assertEquals("Should not be empty", e.getMessage());
        }
    }

    @Test
    public void notNullOrEmptySucceeds() {
        notNullOrEmpty("ha", "Should not be empty");
        assertTrue(true);
    }
}
