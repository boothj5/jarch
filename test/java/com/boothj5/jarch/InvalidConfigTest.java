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
