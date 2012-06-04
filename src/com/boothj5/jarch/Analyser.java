package com.boothj5.jarch;

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

public class Analyser {

    private final String srcPath;
    private final String basePackage;
    private final List<Module> modules;
    private final Map<String, LayerSpec> layerSpecs;
    private final List<String> errorStrings;
    private int numModuleErrors;
    private int numLayerErrors;
    
    public Analyser (String srcPath, String basePackage, List<Module> modules, Map<String, LayerSpec> layerSpecs) {
        this.srcPath = srcPath;
        this.basePackage = basePackage;
        this.modules = modules;
        this.layerSpecs = layerSpecs;
        this.errorStrings = new ArrayList<String>();
        this.numModuleErrors = 0;
        this.numLayerErrors = 0;
    }

    public void analyse() throws IOException {
        String basePackageDir = srcPath + File.separator + PackageUtil.packageToDir(basePackage);
    
        for (Module module : modules) {
            analyseModule(module, basePackageDir);
        }
    }
    
    public List<String> getErrorStrings() {
        return errorStrings;
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
    
                if (strLine.startsWith("import " + basePackage)) {
                    checkDependency(module, strLine, lineNo, absoluteFilePath);
                    checkLayer(module, layer, strLine, lineNo, absoluteFilePath);
                }
            }                   
        }
    }

    private void checkDependency(Module module, String strLine, int lineNo, String absoluteFilePath) {
        String remain = strLine.substring(basePackage.length() + 8);
        StringTokenizer tok = new StringTokenizer(remain, ".");
        String dependentModule = (String) tok.nextElement();
        
        if (!module.validateDependency(dependentModule)) {
            String className = PackageUtil.fileNameToQualifiedClassName(absoluteFilePath, srcPath);
            errorStrings.add("MODULE: \"" + module.getName() + "\" must not import from \"" + dependentModule + "\"");
            errorStrings.add("  -> " + className + ":");
            errorStrings.add("         Line " + lineNo + ": " + strLine);
            errorStrings.add("");
            numModuleErrors++;
        }
    }
    
    private void checkLayer(Module module, String layer, String strLine, int lineNo, String absoluteFilePath) {
        if (module.getLayerSpec() != null) {
            LayerSpec layerSpec = layerSpecs.get(module.getLayerSpec());
            
            if (layerSpec != null) {
                String remain = strLine.substring(basePackage.length() + 8);
                StringTokenizer tok = new StringTokenizer(remain, ".");
                String moduleStr = (String) tok.nextElement();
                if (moduleStr.equals(module.getName())) {
                    String dependentLayer = (String) tok.nextElement();
                    if (!layerSpec.validateDependency(layer, dependentLayer)) {
                        String className = PackageUtil.fileNameToQualifiedClassName(absoluteFilePath, srcPath);
                        errorStrings.add("LAYER: \"" + layer + "\" must not import from \"" + dependentLayer + 
                                "\" in module \"" + module.getName() + "\" according to layer-spec \"" + 
                                layerSpec.getName() + "\"");
                        errorStrings.add("  -> " + className + ":");
                        errorStrings.add("         Line " + lineNo + ": " + strLine);
                        errorStrings.add("");
                        numLayerErrors++;
                    }
                }
            }
        }
    }
}
