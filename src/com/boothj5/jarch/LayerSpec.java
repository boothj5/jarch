package com.boothj5.jarch;

import java.util.Map;

public class LayerSpec {

    private final String name;
    private final Map<String, Layer> layers;

    public LayerSpec(String name, Map<String, Layer> layers) {
        this.name = name;
        this.layers = layers;
    }
    
    public String getName() {
        return name;
    }
    
    public Map<String, Layer> getLayers() {
        return layers;
    }

    public boolean validateDependency(String sourceLayer, String dependentLayer) {
        if (sourceLayer.equals(dependentLayer)) {
            return true;
        } else if (!layers.keySet().contains(sourceLayer)) {
            return true;
        } else if (!layers.keySet().contains(dependentLayer)) {
            return true;
        } else {
            Layer layer = layers.get(sourceLayer);
            return layer.getDependencies().contains(dependentLayer);
        }
    }
}
