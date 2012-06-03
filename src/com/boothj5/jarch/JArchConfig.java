package com.boothj5.jarch;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class JArchConfig {
    
    private String basePath;
    private List<LayerSpec> layerSpecs;
    private List<Module> modules;
    
    public static JArchConfig parse(String configFilePath) throws IOException, JDOMException {
        InputStream is = new BufferedInputStream(new FileInputStream(configFilePath));
        SAXBuilder builder = new SAXBuilder();
        
        Document doc = builder.build(is);
        Element jarchConfig = doc.getRootElement();
        if (!"jarch-config".equals(jarchConfig.getName())) {
            throw new RuntimeException("Error parsing config file.");
        }
        
        List<Element> basePaths = jarchConfig.getChildren("base-path");
        if (basePaths.size() != 1) {
            throw new RuntimeException("Error parsing config file.");
        }
        
        Element basePath = basePaths.get(0);
        
        JArchConfig conf = new JArchConfig();
        conf.basePath = basePath.getText();

        List<Element> docLayerSpecs = jarchConfig.getChildren("layer-spec");
        conf.layerSpecs = new ArrayList<LayerSpec>();
        for (Element docLayerSpec : docLayerSpecs) {
            String layerSpecName = docLayerSpec.getAttributeValue("name");
            List<Layer> layers = new ArrayList<Layer>();
            List<Element> docLayers = docLayerSpec.getChildren("layer");

            for (Element docLayer : docLayers) {
                String layerName = docLayer.getAttributeValue("name");
                List<String> dependencies = new ArrayList<String>();
                List<Element> docDependencies = docLayer.getChildren("dependency");
                for (Element docDependency : docDependencies) {
                    dependencies.add(docDependency.getAttributeValue("on"));
                }
                
                Layer newLayer = new Layer(layerName, dependencies);
                layers.add(newLayer);
            }
            
            LayerSpec newLayerSpec = new LayerSpec(layerSpecName, layers);
            conf.layerSpecs.add(newLayerSpec);
        }
        
        return conf;
    }
    
    public String getBasePath() {
        return basePath;
    }
    
    public List<LayerSpec> getLayerSpecs() {
        return layerSpecs;
    }
}
