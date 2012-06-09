package com.boothj5.jarch.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleSetCreator {
    
    public static RuleSet createRuleSet(String basePackage, String layerSpec, Layer layer1, Layer layer2, 
            Module module1, Module module2) {
        Map<String, Layer> layers = new HashMap<String, Layer>();
        layers.put(layer1.getName(), layer1);
        layers.put(layer2.getName(), layer2);
        
        LayerSpec spec = new LayerSpec(layerSpec, layers); 
                
        Map<String, LayerSpec> layerSpecs = new HashMap<String, LayerSpec>();
        layerSpecs.put(spec.getName(), spec);
        
        List<Module> modules = new ArrayList<Module>();

        modules.add(module1);
        modules.add(module2);
        
        return new RuleSet("test", basePackage, layerSpecs, modules);
    }
    
    public static RuleSet createValidRuleSet() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, new ArrayList<String>());
        Module thing = new Module("thing", "spring", Arrays.asList("common"));

        return createRuleSet("com.boothj5.jarch", "spring", controller, service, common, thing);
    }
}
