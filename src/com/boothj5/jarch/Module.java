package com.boothj5.jarch;

import java.util.List;

public class Module {

    private final String name;
    private final boolean layered;
    private final List<String> dependencies;
    
    public Module(String name, boolean layered, List<String> dependencies) {
        this.name = name;
        this.layered = layered;
        this.dependencies = dependencies;
    }

    public String getName() {
        return name;
    }

    public boolean isLayered() {
        return layered;
    }

    public List<String> getDependencies() {
        return dependencies;
    }
}
