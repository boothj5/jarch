package com.boothj5.jarch;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class JArchConfigReader {

    public static JArchConfig parse(String configFilePath) throws IOException, JDOMException {
        InputStream is = new BufferedInputStream(new FileInputStream(configFilePath));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(is);
        Element jarchConfig = doc.getRootElement();

        if (!"jarch-config".equals(jarchConfig.getName())) {
            throw new RuntimeException("Error parsing config file.");
        }
        
        JArchConfig conf = new JArchConfig();
        conf.setBasePath(readBasePath(jarchConfig));
        conf.setLayerSpecs(readLayerSpecs(jarchConfig));
        conf.setModules(readModules(jarchConfig));
        
        return conf;
    }

    private static String readBasePath(Element jarchConfig) {
        List<Element> basePaths = jarchConfig.getChildren("base-path");

        if (basePaths.size() != 1) {
            throw new RuntimeException("Error parsing config file.");
        }
        
        Element basePath = basePaths.get(0);

        return basePath.getText();
    }

    private static Map<String, LayerSpec> readLayerSpecs(Element jarchConfig) {
        List<Element> docLayerSpecs = jarchConfig.getChildren("layer-spec");
        Map<String, LayerSpec> layerSpecs = new HashMap<String, LayerSpec>();
        
        for (Element docLayerSpec : docLayerSpecs) {
            String layerSpecName = docLayerSpec.getAttributeValue("name");
            List<Element> docLayers = docLayerSpec.getChildren("layer");
            Map<String, Layer> layers = new HashMap<String, Layer>();

            for (Element docLayer : docLayers) {
                String layerName = docLayer.getAttributeValue("name");
                List<Element> docDependencies = docLayer.getChildren("dependency");
                List<String> dependencies = new ArrayList<String>();
                
                for (Element docDependency : docDependencies) {
                    dependencies.add(docDependency.getAttributeValue("on"));
                }
                
                Layer newLayer = new Layer(layerName, dependencies);
                layers.put(layerName, newLayer);
            }
            
            LayerSpec newLayerSpec = new LayerSpec(layerSpecName, layers);
            layerSpecs.put(layerSpecName, newLayerSpec);
        }
        
        return layerSpecs;
    }

    private static List<Module> readModules(Element jarchConfig) {
        List<Element> docModules = jarchConfig.getChildren("module");
        List<Module> modules = new ArrayList<Module>();

        for (Element docModule : docModules) {
            String moduleName = docModule.getAttributeValue("name");
            String layerSpec = docModule.getAttributeValue("layer-spec");
            List<Element> docDependencies = docModule.getChildren("dependency");
            List<String> dependencies = new ArrayList<String>();

            for (Element docDependency : docDependencies) {
                dependencies.add(docDependency.getAttributeValue("on"));
            }
            
            Module newModule = new Module(moduleName, layerSpec, dependencies);
            modules.add(newModule);
        }

        return modules;
    }
}
