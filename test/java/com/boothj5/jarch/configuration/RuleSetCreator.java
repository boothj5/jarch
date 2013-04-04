/* 
 * RuleSetCreator.java
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
package com.boothj5.jarch.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RuleSetCreator {
    
    public static RuleSet createRuleSet(String basePackage, Module module1, Module module2) {
        List<Module> modules = new ArrayList<Module>();
        modules.add(module1);
        modules.add(module2);
        
        return new RuleSet("test", basePackage, modules);
    }
    
    public static RuleSet createValidRuleSet() {
        Module common = new Module("common", null, new ArrayList<String>());
        Module thing = new Module("thing", "spring", Arrays.asList("common"));

        return createRuleSet("com.boothj5.jarch", common, thing);
    }
}
