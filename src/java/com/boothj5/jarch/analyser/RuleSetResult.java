package com.boothj5.jarch.analyser;

import java.util.List;

public class RuleSetResult {

    private final String ruleSetName;
    private final List<Violation> violations;
    
    public RuleSetResult(String ruleSetName, List<Violation> violations) {
        this.ruleSetName = ruleSetName;
        this.violations = violations;
    }
    
    public String getRuleSetName() {
        return ruleSetName;
    }
    
    public List<Violation> getViolations() {
        return violations;
    }
}
