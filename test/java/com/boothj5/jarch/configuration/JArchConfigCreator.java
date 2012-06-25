package com.boothj5.jarch.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JArchConfigCreator {

    private static JArchConfig createConfig(String basePackage, String layerSpec, Layer layer1, Layer layer2, 
            Module module1, Module module2) {
        RuleSet ruleSet = RuleSetCreator.createRuleSet(basePackage, module1, module2);
        LayerSpec spec = LayerSpecCreator.createLayerSpec(layerSpec, layer1, layer2);
        Map<String, LayerSpec> layerSpecs = new HashMap<String, LayerSpec>();
        layerSpecs.put(spec.getName(), spec);
        
        return new JArchConfig(layerSpecs, Arrays.asList(ruleSet));
    }
    
    public static JArchConfig createValidConfig() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, new ArrayList<Dependency>());
        Dependency dependency = new Dependency("common", null);
        Module thing = new Module("thing", "spring", Arrays.asList(dependency));

        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }
    
    public static JArchConfig createConfigWithInvalidLayerSpec() {
        Layer controller = new Layer("controller", Arrays.asList("service", "whoops"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, new ArrayList<Dependency>());
        Dependency dependency = new Dependency("common", null);
        Module thing = new Module("thing", "spring", Arrays.asList(dependency));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }
    
    public static JArchConfig createConfigWithInvalidModuleLayerSpec() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", "whoops", new ArrayList<Dependency>());
        Dependency dependency = new Dependency("common", null);
        Module thing = new Module("thing", "spring", Arrays.asList(dependency));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }

    public static JArchConfig createConfigWithInvalidModuleDependency() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, Arrays.asList(new Dependency("whoops", null)));
        Module thing = new Module("thing", "spring", Arrays.asList(new Dependency("common", null)));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }

    public static JArchConfig createConfigWithCircularDependency() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, Arrays.asList(new Dependency("thing", null)));
        Module thing = new Module("thing", "spring", Arrays.asList(new Dependency("common", null)));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }
}
