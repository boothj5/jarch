package com.boothj5.jarch.configuration;

import static org.junit.Assert.* ;

import java.util.Arrays;

import org.junit.Test;

public class ModuleTest {

    @Test
    public void testValidDependency() {
        Module common = new Module("common", null, Arrays.asList(new Dependency("person", null)));

        assertTrue(common.validateDependency("person"));
    }

    @Test
    public void testInvalidDependency() {
        Module common = new Module("common", null, Arrays.asList(new Dependency("address", null)));

        assertFalse(common.validateDependency("person"));
    }
}
