package com.boothj5.jarch;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom2.JDOMException;

public class Main {
    public static void main(String[] args) throws IOException, JDOMException { 
        System.out.println("JArch");
        
        if (args.length != 2) {
            System.out.println("Usage: jarch <src-path> <config-file>");
            System.exit(1);
        }
        
        else {
            JArchConfig conf = JArchConfigReader.parse(args[1]);
            String srcPath = args[0];
            String basePackage = conf.getBasePath();
            String basePkgDir = packageToDir(basePackage);
            String searchPath = srcPath + "/" + basePkgDir;

            for (Module module : conf.getModules()) {
                
                System.out.println("Analysing " + module.getName() + "...");

                FileLister fileLister = new FileLister(searchPath + "/" + module.getName());
                
                List<File> moduleFiles = fileLister.getFileListing();
                for (File file : moduleFiles) {
                    
                    String absoluteFilePath = file.getAbsolutePath();
                    String layer = getLayer(absoluteFilePath, searchPath, module.getName());
                    
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
                                String className = fileNameToQualitiedClassName(absoluteFilePath, srcPath);
                                System.out.println("-> MODULE: " + className + "(" + lineNo + "): " + strLine);
                            }
                            
                            // check layer dependencies
                            if (module.getLayerSpec() != null) {
                                LayerSpec layerSpec = conf.getLayerSpecs().get(module.getLayerSpec());
                                
                                if (layerSpec != null) {
                                    // check dependent layer
                                    remain = strLine.substring(basePackage.length() + 8);
                                    tok = new StringTokenizer(remain, ".");
                                    String moduleStr = (String) tok.nextElement();
                                    if (moduleStr.equals(module.getName())) {
                                        String dependentLayer = (String) tok.nextElement();
                                        if (!layerSpec.validateDependency(layer, dependentLayer)) {
                                            String className = fileNameToQualitiedClassName(absoluteFilePath, srcPath);
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
    
    private static String packageToDir(String pkgName) {
        return pkgName.replace('.', '/');
    }
    
    private static String fileNameToQualitiedClassName(String fileName, String srcPath) {
        String stripped = fileName.substring(srcPath.length() + 1);
        String withoutJava = stripped.substring(0, stripped.length() - 5);
        String className = withoutJava.replace("/", ".");
        
        return className;
    }
    
    private static String getLayer(String absoluteFilePath, String sourcePath, String moduleName) {
        String endStr = absoluteFilePath.substring(sourcePath.length() + 2 + moduleName.length());
        StringTokenizer tok = new StringTokenizer(endStr, "/");
        String layer = tok.nextToken();

        return layer;
    }

}
