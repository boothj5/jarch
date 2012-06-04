package com.boothj5.jarch;

import java.util.ArrayList;
import java.util.List;

public class JArchConfigValidator {
    
    private final JArchConfig conf;
    
    public JArchConfigValidator(JArchConfig conf) {
        this.conf = conf;
    }
    
    public List<String> validate() {
        List<String> errors = new ArrayList<String>();
        
        // check for missing layer specs
        for (Module module : conf.getModules()) {
            if (module.getLayerSpec() != null && (!conf.getLayerSpecs().containsKey(module.getLayerSpec()))) {
                    errors.add("Module \"" + module.getName() + "\" references non-existent layer-spec \"" 
                            + module.getLayerSpec() + "\".");
            }
        }
        
        if (errors.size() > 0) {
            return errors;
        } else {
            return null;
        }
    }
}
