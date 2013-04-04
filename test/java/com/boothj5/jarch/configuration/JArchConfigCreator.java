/* 
 * JArchConfigCreator.java
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JArchConfigCreator {

    private static JArchConfig createConfig(String basePackage, String layerSpec, Layer layer1, Layer layer2, 
            Module module1, Module module2) {
        RuleSet ruleSet = RuleSetCreator.createRuleSet(basePackage, module1, module2);
        LayerSpec spec = LayerSpecCreator.createLayerSpec(layerSpec, layer1, layer2);
        Map<String, LayerSpec> layerSpecs = new HashMap<String, LayerSpec>();
        layerSpecs.put(spec.getName(), spec);
        
        return new JArchConfig(layerSpecs, Arrays.asList(ruleSet));
    }
    
    public static JArchConfig createValidConfig() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, new ArrayList<String>());
        Module thing = new Module("thing", "spring", Arrays.asList("common"));

        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }
    
    public static JArchConfig createConfigWithInvalidLayerSpec() {
        Layer controller = new Layer("controller", Arrays.asList("service", "whoops"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, new ArrayList<String>());
        Module thing = new Module("thing", "spring", Arrays.asList("common"));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }
    
    public static JArchConfig createConfigWithInvalidModuleLayerSpec() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", "whoops", new ArrayList<String>());
        Module thing = new Module("thing", "spring", Arrays.asList("common"));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }

    public static JArchConfig createConfigWithInvalidModuleDependency() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, Arrays.asList("whoops"));
        Module thing = new Module("thing", "spring", Arrays.asList("common"));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }

    public static JArchConfig createConfigWithCircularDependency() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Module common = new Module("common", null, Arrays.asList("thing"));
        Module thing = new Module("thing", "spring", Arrays.asList("common"));
        
        return createConfig("com.boothj5.jarch", "spring", controller, service, common, thing);
    }
}
