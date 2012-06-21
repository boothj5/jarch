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

public class CircularConfigTest {
    private final String configFile = "test" + File.separator + "resources" + File.separator 
            + "jarch-config-circular.xml";
    
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
        List<String> warnings = validator.getWarnings();
        
        assertEquals(1, warnings.size());
        assertTrue(warnings.contains("WARNING: Module \"common\" has a circular dependency with \"address\"."));
        
        
    }
}
