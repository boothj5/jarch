package com.boothj5.jarch.analyser;

import java.util.List;

public class RuleSetResult {

    private final String ruleSetName;
    private final List<Violation> violations;
    private final List<String> warnings;
    
    public RuleSetResult(String ruleSetName, List<Violation> violations, List<String> warnings) {
        this.ruleSetName = ruleSetName;
        this.violations = violations;
        this.warnings = warnings;
    }
    
    public String getRuleSetName() {
        return ruleSetName;
    }
    
    public List<Violation> getViolations() {
        return violations;
    }
    
    public List<String> getWarnings() {
        return warnings;
    }
}
