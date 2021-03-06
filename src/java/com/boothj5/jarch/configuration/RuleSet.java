/* 
 * RuleSet.java
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

import java.util.List;

public class RuleSet {
    private final String name;
    private final String basePackage;
    private final List<Module> modules;
    
    public RuleSet(String name, String basePackage, List<Module> modules) {
        this.name = name;
        this.basePackage = basePackage;
        this.modules = modules;
    }
    
    public String getName() {
        return name;
    }
    
    public String getBasePackage() {
        return basePackage;
    }

    public List<Module> getModules() {
        return modules;
    }

    public boolean containsModule(String moduleName) {
        for (Module module : modules) {
            if (module.getName().equals(moduleName)) {
                return true;
            }
        }
        return false;
    }
    
    public Module getModule(String moduleName) {
        for (Module module : modules) {
            if (module.getName().equals(moduleName)) {
                return module;
            }
        }
        
        return null;
    }

}
