/* 
 * RuleSetAnalyser.java
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
package com.boothj5.jarch.analyser;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.boothj5.jarch.configuration.LayerSpec;
import com.boothj5.jarch.configuration.Module;
import com.boothj5.jarch.configuration.RuleSet;
import com.boothj5.jarch.util.FileLister;
import com.boothj5.jarch.util.PackageUtil;

public class RuleSetAnalyser {
    
    private final String srcPath;
    private final RuleSet ruleSet;
    private final Map<String, LayerSpec> layerSpecs;
    private final RuleSetResult result;
    private int numModuleErrors;
    private int numLayerErrors;
    
    public RuleSetAnalyser(String srcPath, RuleSet ruleSet, Map<String, LayerSpec> layerSpecs) {
        this.srcPath = srcPath;
        this.ruleSet = ruleSet;
        this.layerSpecs = layerSpecs;
        this.result = new RuleSetResult(this.ruleSet.getName(), new ArrayList<Violation>());
        this.numModuleErrors = 0;
        this.numLayerErrors = 0;
    }

    public RuleSetResult analyse() throws IOException {
        String basePackageDir = srcPath + File.separator + PackageUtil.packageToDir(ruleSet.getBasePackage());
    
        for (Module module : ruleSet.getModules()) {
            analyseModule(module, basePackageDir);
        }
        
        return result;
    }
    
    public int getNumModuleErrors() {
        return numModuleErrors;
    }
    
    public int getNumLayerErrors() {
        return numLayerErrors;
    }
    
    private void analyseModule(Module module, String basePackageDir) throws IOException {
        
        FileLister fileLister = new FileLister(basePackageDir + File.separator + module.getName());
        
        List<File> moduleFiles = fileLister.getFileListing();
        
        for (File file : moduleFiles) {
            
            String absoluteFilePath = file.getAbsolutePath();
            String layer = PackageUtil.getLayer(absoluteFilePath, basePackageDir, module.getName());
            
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
    
            String strLine;
            int lineNo = 0;
            
            while ((strLine = br.readLine()) != null) {
                lineNo++;
    
                if (strLine.startsWith("import " + ruleSet.getBasePackage())) {
                    checkDependency(module, strLine, lineNo, absoluteFilePath);
                    checkLayer(module, layer, strLine, lineNo, absoluteFilePath);
                }
            }                   

            br.close();
        }
    }

    private void checkDependency(Module module, String strLine, int lineNo, String absoluteFilePath) {
        String remain = strLine.substring(ruleSet.getBasePackage().length() + 8);
        StringTokenizer tok = new StringTokenizer(remain, ".");
        String dependentModule = (String) tok.nextElement();
        
        if (!module.validateDependency(dependentModule)) {
            String className = PackageUtil.fileNameToQualifiedClassName(absoluteFilePath, srcPath);
            String message = "MODULE: \"" + module.getName() + "\" must not import from \"" + dependentModule + "\"";
            Violation violation = new Violation(message, className, lineNo, strLine);
            
            result.getViolations().add(violation);
            
            numModuleErrors++;
        }
    }
    
    private void checkLayer(Module module, String layer, String strLine, int lineNo, String absoluteFilePath) {
        if (module.getLayerSpec() != null) {
            LayerSpec layerSpec = layerSpecs.get(module.getLayerSpec());
            
            if (layerSpec != null) {
                String remain = strLine.substring(ruleSet.getBasePackage().length() + 8);
                StringTokenizer tok = new StringTokenizer(remain, ".");
                String moduleStr = (String) tok.nextElement();
                if (moduleStr.equals(module.getName())) {
                    String dependentLayer = (String) tok.nextElement();
                    if (!layerSpec.validateDependency(layer, dependentLayer)) {
                        String className = PackageUtil.fileNameToQualifiedClassName(absoluteFilePath, srcPath);
                        String message = "LAYER: \"" + layer + "\" must not import from \"" + dependentLayer + 
                                "\" in module \"" + module.getName() + "\" according to layer-spec \"" + 
                                layerSpec.getName() + "\"";
                        Violation violation = new Violation(message, className, lineNo, strLine);

                        result.getViolations().add(violation);
                        
                        numLayerErrors++;
                    }
                }
            }
        }
    }

}
