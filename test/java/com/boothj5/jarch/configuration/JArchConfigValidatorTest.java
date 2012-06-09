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
