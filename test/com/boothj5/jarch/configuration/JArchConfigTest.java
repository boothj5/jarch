package com.boothj5.jarch.configuration;

import static org.junit.Assert.* ;

import org.junit.Test;

public class JArchConfigTest {

    @Test
    public void containsModuleWhenDoes() {
        JArchConfig conf = JArchConfigCreator.createValidConfig();
        assertTrue(conf.containsModule("common"));
    }
    
    @Test 
    public void notContainsModuleWhenDoesnt() {
        JArchConfig conf = JArchConfigCreator.createValidConfig();
        assertFalse(conf.containsModule("person"));
    }

    @Test
    public void getModuleReturnsModule() {
        JArchConfig conf = JArchConfigCreator.createValidConfig();
        Module module = conf.getModule("common");
        
        assertEquals("common", module.getName());
    }

} 