package com.boothj5.jarch;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Analyser {

    private final String srcPath;
    private final String basePackage;
    private final List<Module> modules;
    private final Map<String, LayerSpec> layerSpecs;
    
    public Analyser (String srcPath, String basePackage, List<Module> modules, Map<String, LayerSpec> layerSpecs) {
        this.srcPath = srcPath;
        this.basePackage = basePackage;
        this.modules = modules;
        this.layerSpecs = layerSpecs;
    }

    public void analyse() throws IOException {
        String basePackageDir = srcPath + File.separator + PackageUtil.packageToDir(basePackage);
    
        for (Module module : modules) {
            System.out.println("Analysing " + module.getName() + "...");
    
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
    
                    if (strLine.startsWith("import " + basePackage)) {
    
                        // check dependent module
                        String remain = strLine.substring(basePackage.length() + 8);
                        StringTokenizer tok = new StringTokenizer(remain, ".");
                        String dependentModule = (String) tok.nextElement();
                        
                        if (!module.validateDependency(dependentModule)) {
                            String className = PackageUtil.fileNameToQualifiedClassName(absoluteFilePath, srcPath);
                            System.out.println("-> MODULE: " + className + "(" + lineNo + "): " + strLine);
                        }
                        
                        // check layer dependencies
                        if (module.getLayerSpec() != null) {
                            LayerSpec layerSpec = layerSpecs.get(module.getLayerSpec());
                            
                            if (layerSpec != null) {
                                // check dependent layer
                                remain = strLine.substring(basePackage.length() + 8);
                                tok = new StringTokenizer(remain, ".");
                                String moduleStr = (String) tok.nextElement();
                                if (moduleStr.equals(module.getName())) {
                                    String dependentLayer = (String) tok.nextElement();
                                    if (!layerSpec.validateDependency(layer, dependentLayer)) {
                                        String className = PackageUtil.fileNameToQualifiedClassName(absoluteFilePath, srcPath);
                                        System.out.println("-> LAYER: " + className + "(" + lineNo + "): " + strLine);
                                    }
                                }
                            }
                        }
                    }
                }                   
            }
        }
    }
}
