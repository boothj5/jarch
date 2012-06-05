/* 
 * JArchConfigValidator.java
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class JArchConfigValidator {
    
    private final JArchConfig conf;
    private final List<String> errors;
    private final List<String> warnings;
    private final Set<CircularDepedency> circularDependencies;
    
    public JArchConfigValidator(JArchConfig conf) {
        this.conf = conf;
        this.errors = new ArrayList<String>();
        this.warnings = new ArrayList<String>();
        this.circularDependencies = new HashSet<CircularDepedency>();
    }
    
    public void validate() {
        // check layer specs
        if (conf.getLayerSpecs() != null) {
            for (LayerSpec spec : conf.getLayerSpecs().values()) {
                for (Layer layer : spec.getLayers().values()) {
                    for (String dependentLayer : layer.getDependencies()) {
                        if (!spec.containsLayer(dependentLayer)) {
                            errors.add("ERROR: Layer \"" + layer.getName() + "\" references non-existent layer \"" 
                                    + dependentLayer + "\" in layer-spec \"" + spec.getName() + "\".");
                        }
                    }
                }
            }
        }
        
        for (Module module : conf.getModules()) {
            // check for missing layer specs
            if (module.getLayerSpec() != null && (!conf.getLayerSpecs().containsKey(module.getLayerSpec()))) {
                    errors.add("ERROR: Module \"" + module.getName() + "\" references non-existent layer-spec \"" 
                            + module.getLayerSpec() + "\".");
            }
            
            if (module.getDependencies() != null) {
                for (String dependency : module.getDependencies()) {
                    // check for missing module references
                    if (!conf.containsModule(dependency)) {
                        errors.add("ERROR: Module \"" + module.getName() + "\" depends on non-existent module \"" 
                                + dependency + "\".");
                    } else { 
                        // check for circular dependencies
                        Module module2 = conf.getModule(dependency);
                        if (module2.getDependencies().contains(module.getName())) {
                            circularDependencies.add(new CircularDepedency(module.getName(), module2.getName()));
                        }
                    }
                }
            }
        }
        
        if (circularDependencies.size() > 0) {
            for (CircularDepedency circularDepedency : circularDependencies) {
                warnings.add("WARNING: Module \"" + circularDepedency.getModuleA() + "\" has a circular dependency with \"" 
                        + circularDepedency.getModuleB() + "\".");
            }
        }
    }
    
    public List<String> getErrors() {
        if (errors.size() > 0) {
            return errors;
        } else {
            return null;
        }
    }
    
    public List<String> getWarnings() {
        if (warnings.size() > 0) {
            return warnings;
        } else {
            return null;
        }
    }
}
