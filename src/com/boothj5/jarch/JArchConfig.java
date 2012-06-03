package com.boothj5.jarch;

import java.util.List;
import java.util.Map;

public class JArchConfig {
    
    private String basePath;
    private Map<String, LayerSpec> layerSpecs;
    private List<Module> modules;
    
    public String getBasePath() {
        return basePath;
    }
    
    public Map<String, LayerSpec> getLayerSpecs() {
        return layerSpecs;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setLayerSpecs(Map<String, LayerSpec> layerSpecs) {
        this.layerSpecs = layerSpecs;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
