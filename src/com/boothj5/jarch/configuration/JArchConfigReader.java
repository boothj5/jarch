/* 
 * JArchConfigReader.java
 *
 * Copyright (C) 2012 James Booth <boothj5@gmail.com>
 * 
 * This file is part of JArch.
 *
 * JArch is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JArch is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JArch.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.boothj5.jarch.configuration;

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
        
        List<Element> docRuleSets = jarchConfig.getChildren("rule-set");
        List<RuleSet> ruleSets = new ArrayList<RuleSet>();
        for (Element docRuleSet : docRuleSets) {
            String ruleSetName = docRuleSet.getAttributeValue("name");
            String ruleSetBasePackage = docRuleSet.getAttributeValue("base-package");
            
            RuleSet ruleSet = new RuleSet(ruleSetName, 
                    ruleSetBasePackage, 
                    readLayerSpecs(docRuleSet), 
                    readModules(docRuleSet));
            
            ruleSets.add(ruleSet);
        }
        
        JArchConfig conf = new JArchConfig(ruleSets);
        
        return conf;
    }

    private static Map<String, LayerSpec> readLayerSpecs(Element ruleSet) {
        List<Element> docLayerSpecs = ruleSet.getChildren("layer-spec");
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

    private static List<Module> readModules(Element ruleSet) {
        List<Element> docModules = ruleSet.getChildren("module");
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
