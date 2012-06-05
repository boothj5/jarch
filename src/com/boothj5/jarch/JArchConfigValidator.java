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
        
        // check layer specs
        if (conf.getLayerSpecs() != null) {
            for (LayerSpec spec : conf.getLayerSpecs().values()) {
                for (Layer layer : spec.getLayers().values()) {
                    for (String dependentLayer : layer.getDependencies()) {
                        if (!spec.containsLayer(dependentLayer)) {
                            errors.add("Layer \"" + layer.getName() + "\" references non-existent layer \"" 
                                    + dependentLayer + "\" in layer-spec \"" + spec.getName() + "\".");
                        }
                    }
                }
            }
        }
        
        for (Module module : conf.getModules()) {

            // check for missing layer specs
            if (module.getLayerSpec() != null && (!conf.getLayerSpecs().containsKey(module.getLayerSpec()))) {
                    errors.add("Module \"" + module.getName() + "\" references non-existent layer-spec \"" 
                            + module.getLayerSpec() + "\".");
            }
            
            // check for missing module references
            if (module.getDependencies() != null) {
                for (String dependency : module.getDependencies()) {
                    if (!conf.containsModule(dependency)) {
                        errors.add("Module \"" + module.getName() + "\" depends on non-existent module \"" 
                                + dependency + "\".");
                    }
                }
            }
        }
        
        if (errors.size() > 0) {
            return errors;
        } else {
            return null;
        }
    }
}
