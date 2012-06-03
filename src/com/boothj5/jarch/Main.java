package com.boothj5.jarch;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom2.JDOMException;

public class Main {
    private static String srcPath;
    private static String configFile;
    
    public static void main(String[] args) throws IOException, JDOMException { 
        System.out.println("JArch");
        
        validateArgs(args);
        
        srcPath = args[0];
        configFile = args[1];
        
        JArchConfig conf = JArchConfigReader.parse(configFile);

        String absoluteBasePackageDir = srcPath + "/" + PackageUtil.packageToDir(conf.getBasePackage());

        for (Module module : conf.getModules()) {
            
            System.out.println("Analysing " + module.getName() + "...");

            FileLister fileLister = new FileLister(absoluteBasePackageDir + "/" + module.getName());
            
            List<File> moduleFiles = fileLister.getFileListing();
            for (File file : moduleFiles) {
                
                String absoluteFilePath = file.getAbsolutePath();
                String layer = PackageUtil.getLayer(absoluteFilePath, absoluteBasePackageDir, module.getName());
                
                FileInputStream fstream = new FileInputStream(file);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String strLine;
                int lineNo = 0;
                
                while ((strLine = br.readLine()) != null) {
                    
                    lineNo++;

                    if (strLine.startsWith("import " + conf.getBasePackage())) {

                        // check dependent module
                        String remain = strLine.substring(conf.getBasePackage().length() + 8);
                        StringTokenizer tok = new StringTokenizer(remain, ".");
                        String dependentModule = (String) tok.nextElement();
                        
                        if (!module.validateDependency(dependentModule)) {
                            String className = PackageUtil.fileNameToQualifiedClassName(absoluteFilePath, srcPath);
                            System.out.println("-> MODULE: " + className + "(" + lineNo + "): " + strLine);
                        }
                        
                        // check layer dependencies
                        if (module.getLayerSpec() != null) {
                            LayerSpec layerSpec = conf.getLayerSpecs().get(module.getLayerSpec());
                            
                            if (layerSpec != null) {
                                // check dependent layer
                                remain = strLine.substring(conf.getBasePackage().length() + 8);
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
    
    private static void validateArgs(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: jarch <src-path> <config-file>");
            System.exit(1);
        }
    }
}
