package com.boothj5.jarch.configuration;

import static org.junit.Assert.* ;

import org.junit.Test;

public class RuleSetTest {

    @Test
    public void containsModuleWhenDoes() {
        RuleSet ruleSet = RuleSetCreator.createValidRuleSet();
        assertTrue(ruleSet.containsModule("common"));
    }
    
    @Test 
    public void notContainsModuleWhenDoesnt() {
        RuleSet ruleSet = RuleSetCreator.createValidRuleSet();
        assertFalse(ruleSet.containsModule("person"));
    }

    @Test
    public void getModuleReturnsModule() {
        RuleSet ruleSet = RuleSetCreator.createValidRuleSet();
        Module module = ruleSet.getModule("common");
        
        assertEquals("common", module.getName());
    }

} 