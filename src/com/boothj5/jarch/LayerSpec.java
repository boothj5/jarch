package com.boothj5.jarch;

import java.util.List;

public class LayerSpec {

    private final String name;
    private final List<Layer> layers;

    public LayerSpec(String name, List<Layer> layers) {
        this.name = name;
        this.layers = layers;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Layer> getLayers() {
        return layers;
    }
}
