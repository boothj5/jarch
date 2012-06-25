package com.boothj5.jarch.configuration;

public class Dependency {

    private final String on;
    private final String viaLayerSpec;
    
    public Dependency(String on, String viaLayerSpec) {
        this.on = on;
        this.viaLayerSpec = viaLayerSpec;
    }
    
    public String getOn() {
        return on;
    }
    
    public String getViaLayerSpec() {
        return viaLayerSpec;
    }
}
