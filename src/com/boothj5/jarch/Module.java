package com.boothj5.jarch;

import java.util.List;

public class Module {

    private final String name;
    private final String layerSpec;
    private final List<String> dependencies;
    
    public Module(String name, String layerSpec, List<String> dependencies) {
        this.name = name;
        this.layerSpec = layerSpec;
        this.dependencies = dependencies;
    }

    public String getName() {
        return name;
    }

    public String getLayerSpec() {
        return layerSpec;
    }
    public List<String> getDependencies() {
        return dependencies;
    }
}
