package com.boothj5.jarch;

import java.util.List;

public class Layer {
    private final String name;
    private final List<String> dependencies;

    public Layer(String name, List<String> dependencies) {
        this.name = name;
        this.dependencies = dependencies;
    }

    public String getName() {
        return name;
    }
    
    public List<String> getDependencies() {
        return dependencies;
    }
}
