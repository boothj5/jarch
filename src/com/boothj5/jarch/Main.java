package com.boothj5.jarch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jdom2.JDOMException;

public class Main {
    public static void main(String[] args) throws IOException, JDOMException { 
        System.out.println("JArch");
        
        if (args.length != 2) {
            System.out.println("Usage: jarch <src-path> <config-file>");
            System.exit(1);
        }
        
        else {
            JArchConfig conf = JArchConfig.parse(args[1]);
            String srcPath = args[0];
            String basePackage = conf.getBasePath();
            String basePkgDir = packageToDir(basePackage);
            String searchPath = srcPath + "/" + basePkgDir;
            List<File> files = getFileListing(new File(searchPath));

            debug(conf, srcPath, basePackage, basePkgDir, searchPath, files);

            for (Module module : conf.getModules()) {
                List<String> errors = new ArrayList<String>();
                
                List<File> moduleFiles = getFileListing(new File(searchPath + "/" + module.getName()));
                
                System.out.println("---------------------------");
                System.out.println("Analysing " + module.getName());
                System.out.println("---------------------------");
                for (File file : moduleFiles) {
                    System.out.println(file.getName());
                }
            }
        }
    }
    
    private static String packageToDir(String pkgName) {
        return pkgName.replace('.', '/');
    }

    static public List<File> getFileListing(File aStartingDir) {
        List<File> result = getFileListingNoSort(aStartingDir);
        Collections.sort(result);
        return result;
    }

    static private List<File> getFileListingNoSort(File aStartingDir) {
        List<File> result = new ArrayList<File>();
        File[] filesAndDirs = aStartingDir.listFiles();
        List<File> filesDirs = Arrays.asList(filesAndDirs);

        for(File file : filesDirs) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                result.add(file);
            }
            if (!file.isFile()) {
                List<File> deeperList = getFileListingNoSort(file);
                result.addAll(deeperList);
            }
        }
    
        return result;
    }
    
    private static void debug(JArchConfig conf, String srcPath, String basePackage, String basePkgDir, 
            String searchPath, List<File> files) {
        System.out.println("Source path: " + srcPath);
        System.out.println("Base package: " + basePackage);
        searchPath = srcPath + "/" + basePkgDir;
        System.out.println("Search path: " + searchPath);
        
        System.out.println("");
        System.out.println("Count = " + files.size());

        for (LayerSpec spec : conf.getLayerSpecs()) {
            System.out.println("LayerSpec : " + spec.getName());
            
            for (Layer layer : spec.getLayers()) {
                System.out.println("    Layer : " + layer.getName());
                
                for (String dep : layer.getDependencies()) {
                    System.out.println("        Dependency : " + dep);
                }
            }
        }

        for (Module module : conf.getModules()) {
            System.out.println("Module : " + module.getName());
            System.out.println("    LayerSpec : " + module.getLayerSpec());
            
            for (String dep : module.getDependencies()) {
                System.out.println("        Dependency : " + dep);
            }
        }
        
    }
}
