package com.boothj5.jarch.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JArchConfigCreator {

    public static JArchConfig createValidConfig() {
        String basePackage = "com.boothj5.jarch";
        
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());

        Map<String, Layer> layers = new HashMap<String, Layer>();
        layers.put(controller.getName(), controller);
        layers.put(service.getName(), service);
        
        LayerSpec spec = new LayerSpec("spring", layers); 
                
        Map<String, LayerSpec> layerSpecs = new HashMap<String, LayerSpec>();
        layerSpecs.put(spec.getName(), spec);
        
        Module common = new Module("common", null, new ArrayList<String>());
        Module thing = new Module("thing", spec.getName(), Arrays.asList("common"));
        
        List<Module> modules = new ArrayList<Module>();

        modules.add(common);
        modules.add(thing);
        
        return new JArchConfig(basePackage, layerSpecs, modules);
    }
    
    public static JArchConfig createConfigWithInvalidLayerSpec() {
        String basePackage = "com.boothj5.jarch";
        
        Layer controller = new Layer("controller", Arrays.asList("service", "whoops"));
        Layer service = new Layer("service", new ArrayList<String>());

        Map<String, Layer> layers = new HashMap<String, Layer>();
        layers.put(controller.getName(), controller);
        layers.put(service.getName(), service);
        
        LayerSpec spec = new LayerSpec("spring", layers); 
                
        Map<String, LayerSpec> layerSpecs = new HashMap<String, LayerSpec>();
        layerSpecs.put(spec.getName(), spec);
        
        Module common = new Module("common", null, new ArrayList<String>());
        Module thing = new Module("thing", spec.getName(), Arrays.asList("common"));
        
        List<Module> modules = new ArrayList<Module>();

        modules.add(common);
        modules.add(thing);
        
        return new JArchConfig(basePackage, layerSpecs, modules);
    }
    
    public static JArchConfig createConfigWithInvalidModuleLayerSpec() {
        String basePackage = "com.boothj5.jarch";
        
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());

        Map<String, Layer> layers = new HashMap<String, Layer>();
        layers.put(controller.getName(), controller);
        layers.put(service.getName(), service);
        
        LayerSpec spec = new LayerSpec("spring", layers); 
                
        Map<String, LayerSpec> layerSpecs = new HashMap<String, LayerSpec>();
        layerSpecs.put(spec.getName(), spec);
        
        Module common = new Module("common", "whoops", new ArrayList<String>());
        Module thing = new Module("thing", spec.getName(), Arrays.asList("common"));
        
        List<Module> modules = new ArrayList<Module>();

        modules.add(common);
        modules.add(thing);
        
        return new JArchConfig(basePackage, layerSpecs, modules);
    }

    public static JArchConfig createConfigWithInvalidModuleDependency() {
        String basePackage = "com.boothj5.jarch";
        
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());

        Map<String, Layer> layers = new HashMap<String, Layer>();
        layers.put(controller.getName(), controller);
        layers.put(service.getName(), service);
        
        LayerSpec spec = new LayerSpec("spring", layers); 
                
        Map<String, LayerSpec> layerSpecs = new HashMap<String, LayerSpec>();
        layerSpecs.put(spec.getName(), spec);
        
        Module common = new Module("common", null, Arrays.asList("whoops"));
        Module thing = new Module("thing", spec.getName(), Arrays.asList("common"));
        
        List<Module> modules = new ArrayList<Module>();

        modules.add(common);
        modules.add(thing);
        
        return new JArchConfig(basePackage, layerSpecs, modules);
    }

    public static JArchConfig createConfigWithCircularDependency() {
        String basePackage = "com.boothj5.jarch";
        
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());

        Map<String, Layer> layers = new HashMap<String, Layer>();
        layers.put(controller.getName(), controller);
        layers.put(service.getName(), service);
        
        LayerSpec spec = new LayerSpec("spring", layers); 
                
        Map<String, LayerSpec> layerSpecs = new HashMap<String, LayerSpec>();
        layerSpecs.put(spec.getName(), spec);
        
        Module common = new Module("common", null, Arrays.asList("thing"));
        Module thing = new Module("thing", spec.getName(), Arrays.asList("common"));
        
        List<Module> modules = new ArrayList<Module>();

        modules.add(common);
        modules.add(thing);
        
        return new JArchConfig(basePackage, layerSpecs, modules);
    }


}
