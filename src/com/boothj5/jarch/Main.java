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
            System.out.println("Source path: " + srcPath);
            String basePackage = conf.getBasePath();
            System.out.println("Base package: " + basePackage);
            String basePkgDir = packageToDir(basePackage);
            String searchPath = srcPath + "/" + basePkgDir;
            System.out.println("Search path: " + searchPath);
            
            List<File> files = getFileListing(new File(searchPath));

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
}
