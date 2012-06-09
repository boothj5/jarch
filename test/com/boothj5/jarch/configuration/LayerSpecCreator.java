package com.boothj5.jarch.configuration;

import java.util.HashMap;
import java.util.Map;

public class LayerSpecCreator {

    public static LayerSpec createLayerSpec(String layerSpec, Layer layer1, Layer layer2) {
        Map<String, Layer> layers = new HashMap<String, Layer>();
        layers.put(layer1.getName(), layer1);
        layers.put(layer2.getName(), layer2);
        
        return new LayerSpec(layerSpec, layers);
    }
}
