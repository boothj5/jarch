/* 
 * JArchConfigValidatorTest.java
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

import static org.junit.Assert.* ;

import java.util.List;

import org.junit.Test;

public class JArchConfigValidatorTest {

    @Test
    public void validConfigContainsNoWarnings() {
        JArchConfig conf = JArchConfigCreator.createValidConfig();
        JArchConfigValidator validator = new JArchConfigValidator(conf);
        
        validator.validate();

        assertNull(validator.getWarnings());
    }
    
    @Test
    public void nonExistentLayerSpecLayerReferenceAddsError() {
        JArchConfig conf = JArchConfigCreator.createConfigWithInvalidLayerSpec();
        JArchConfigValidator validator = new JArchConfigValidator(conf);
        
        validator.validate();
        
        List<String> errors = validator.getErrors();
        String error = errors.get(0);
                
        assertEquals("ERROR: Layer \"controller\" references non-existent layer " + 
                "\"whoops\" in layer-spec \"spring\".", error);
    }
    
    @Test
    public void nonExistentLayerSpecModuleReferenceAddsError() {
        JArchConfig conf = JArchConfigCreator.createConfigWithInvalidModuleLayerSpec();
        JArchConfigValidator validator = new JArchConfigValidator(conf);
        
        validator.validate();
        
        List<String> errors = validator.getErrors();
        String error = errors.get(0);
                
        assertEquals("ERROR: Module \"common\" references non-existent layer-spec \"" 
                + "whoops\".", error);
    }
    
    @Test
    public void nonExistentModuleDependencyAddsError() {
        JArchConfig conf = JArchConfigCreator.createConfigWithInvalidModuleDependency();
        JArchConfigValidator validator = new JArchConfigValidator(conf);
        
        validator.validate();
        
        List<String> errors = validator.getErrors();
        String error = errors.get(0);
                
        assertEquals("ERROR: Module \"common\" depends on non-existent module \"" 
                + "whoops\".", error);
    }

    @Test
    public void circularDependencyAddsWarning() {
        JArchConfig conf = JArchConfigCreator.createConfigWithCircularDependency();
        JArchConfigValidator validator = new JArchConfigValidator(conf);
        
        validator.validate();
        
        List<String> warnings = validator.getWarnings();
        String warning = warnings.get(0);
                
        assertEquals("WARNING: Module \"common\" has a circular dependency with \"" 
                + "thing\".", warning);
    }

}
