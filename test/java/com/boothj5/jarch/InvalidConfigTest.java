/* 
 * InvalidConfigTest.java
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
package com.boothj5.jarch;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;

import com.boothj5.jarch.configuration.JArchConfig;
import com.boothj5.jarch.configuration.JArchConfigReader;
import com.boothj5.jarch.configuration.JArchConfigValidator;

public class InvalidConfigTest {
    private final String configFile = "test" + File.separator + "resources" + File.separator 
            + "jarch-config-invalid.xml";
    
    private JArchConfig conf;
    private JArchConfigValidator validator;
    
    
    @Before
    public void setUp() throws IOException, JDOMException {
        conf = JArchConfigReader.parse(configFile);
        validator = new JArchConfigValidator(conf);
    }
    
    @Test
    public void validateReturnsNoErrors() {
        validator.validate();
        List<String> errors = validator.getErrors();
        
        assertEquals(4, errors.size());
        assertTrue(errors.contains("ERROR: Layer \"service\" references non-existent layer \"storage\" in " +
        		"layer-spec \"spring\"."));
        assertTrue(errors.contains("ERROR: Layer \"controller\" references non-existent layer \"face\" in " +
        		"layer-spec \"spring\"."));
        assertTrue(errors.contains("ERROR: Layer \"controller\" references non-existent layer \"face\" in " +
        		"layer-spec \"spring\"."));
        assertTrue(errors.contains("ERROR: Module \"address\" references non-existent layer-spec \"sprung\"."));
        assertTrue(errors.contains("ERROR: Module \"person\" depends on non-existent module \"addresses\"."));
    }
}
