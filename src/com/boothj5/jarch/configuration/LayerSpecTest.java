package com.boothj5.jarch.configuration;

import static org.junit.Assert.* ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class LayerSpecTest {

    LayerSpec spec;
    
    @Before
    public void setup() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Map<String, Layer> layers = new HashMap<String, Layer>();
        layers.put("controller", controller);
        layers.put("service", service);
        spec = new LayerSpec("spring", layers);
    }
    
    @Test
    public void containsLayerReturnsTrue() {
        assertTrue(spec.containsLayer("service"));
    }

    @Test
    public void doesntcontainLayerReturnsFalse() {
        assertFalse(spec.containsLayer("whoops"));
    }
    
    @Test
    public void testValidDependency() {
        assertTrue(spec.validateDependency("controller", "service"));
    }

    @Test
    public void testInvalidDependency() {
        assertFalse(spec.validateDependency("service", "controller"));
    }
}
