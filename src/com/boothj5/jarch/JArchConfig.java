package com.boothj5.jarch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<String, LayerSpec> layerSpecs;
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
        conf.layerSpecs = new HashMap<String, LayerSpec>();
        for (Element docLayerSpec : docLayerSpecs) {
            String layerSpecName = docLayerSpec.getAttributeValue("name");
            Map<String, Layer> layers = new HashMap<String, Layer>();
            List<Element> docLayers = docLayerSpec.getChildren("layer");

            for (Element docLayer : docLayers) {
                String layerName = docLayer.getAttributeValue("name");
                List<String> dependencies = new ArrayList<String>();
                List<Element> docDependencies = docLayer.getChildren("dependency");
                for (Element docDependency : docDependencies) {
                    dependencies.add(docDependency.getAttributeValue("on"));
                }
                
                Layer newLayer = new Layer(layerName, dependencies);
                layers.put(layerName, newLayer);
            }
            
            LayerSpec newLayerSpec = new LayerSpec(layerSpecName, layers);
            conf.layerSpecs.put(layerSpecName, newLayerSpec);
        }
        
        List<Element> docModules = jarchConfig.getChildren("module");
        conf.modules = new ArrayList<Module>();
        for (Element docModule : docModules) {
            String moduleName = docModule.getAttributeValue("name");
            String layerSpec = docModule.getAttributeValue("layer-spec");
            List<String> dependencies = new ArrayList<String>();
            List<Element> docDependencies = docModule.getChildren("dependency");
            for (Element docDependency : docDependencies) {
                dependencies.add(docDependency.getAttributeValue("on"));
            }
            
            Module newModule = new Module(moduleName, layerSpec, dependencies);
            conf.modules.add(newModule);
        }
        
        return conf;
    }
    
    public String getBasePath() {
        return basePath;
    }
    
    public Map<String, LayerSpec> getLayerSpecs() {
        return layerSpecs;
    }

    public List<Module> getModules() {
        return modules;
    }
}
