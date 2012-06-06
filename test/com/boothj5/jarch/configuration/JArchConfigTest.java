package com.boothj5.jarch.configuration;

import static org.junit.Assert.* ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class JArchConfigTest {

    @Test
    public void containsModuleWhenDoes() {
        String basePackage = "com.boothj5.jarch";
        
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", null);

        Map<String, Layer> layers = new HashMap<String, Layer>();
        layers.put(controller.getName(), controller);
        layers.put(service.getName(), service);
        
        LayerSpec spec = new LayerSpec("spring", layers); 
                
        Map<String, LayerSpec> layerSpecs = new HashMap<String, LayerSpec>();
        layerSpecs.put(spec.getName(), spec);
        
        Module common = new Module("common", null, null);
        Module thing = new Module("thing", spec.getName(), Arrays.asList("common"));
        
        List<Module> modules = new ArrayList<Module>();

        modules.add(common);
        modules.add(thing);
        
        JArchConfig conf = new JArchConfig(basePackage, layerSpecs, modules);
        
        assertTrue(conf.containsModule("common"));
    }
} 