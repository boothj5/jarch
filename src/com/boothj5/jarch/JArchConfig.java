package com.boothj5.jarch;

import java.util.List;
import java.util.Map;

public class JArchConfig {
    
    private String basePackage;
    private Map<String, LayerSpec> layerSpecs;
    private List<Module> modules;
    
    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
    
    public Map<String, LayerSpec> getLayerSpecs() {
        return layerSpecs;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setLayerSpecs(Map<String, LayerSpec> layerSpecs) {
        this.layerSpecs = layerSpecs;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
