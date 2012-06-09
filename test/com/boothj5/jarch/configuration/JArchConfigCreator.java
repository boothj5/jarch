package com.boothj5.jarch.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JArchConfigCreator {

    private static JArchConfig createConfig(String basePackage, String layerSpec, Layer layer1, Layer layer2, 
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
        
        return new JArchConfig(basePackage, layerSpecs, modules);
    }
    
    public static JArchConfig createValidConfig() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, new ArrayList<String>());
        Module thing = new Module("thing", "spring", Arrays.asList("common"));

        return JArchConfigCreator.createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
        
    }
    
    public static JArchConfig createConfigWithInvalidLayerSpec() {
        Layer controller = new Layer("controller", Arrays.asList("service", "whoops"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, new ArrayList<String>());
        Module thing = new Module("thing", "spring", Arrays.asList("common"));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }
    
    public static JArchConfig createConfigWithInvalidModuleLayerSpec() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", "whoops", new ArrayList<String>());
        Module thing = new Module("thing", "spring", Arrays.asList("common"));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }

    public static JArchConfig createConfigWithInvalidModuleDependency() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, Arrays.asList("whoops"));
        Module thing = new Module("thing", "spring", Arrays.asList("common"));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }

    public static JArchConfig createConfigWithCircularDependency() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, Arrays.asList("thing"));
        Module thing = new Module("thing", "spring", Arrays.asList("common"));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }
}
