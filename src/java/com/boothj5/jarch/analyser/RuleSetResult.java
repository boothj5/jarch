/* 
 * RuleSetResult.java
 *
 * Copyright (C) 2012 James Booth <boothj5@gmail.com>
 * 
 * This file is part of JArch.
 *
 * JArch is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JArch is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JArch.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
