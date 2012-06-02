package com.boothj5.jarch;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) { 
        System.out.println("JArch");
        
        if (args.length != 2) {
            System.out.println("Usage: jarch <src-path> <base-package>");
            System.exit(1);
        }
        
        else {
            String srcPath = args[0];
            System.out.println("Source path: " + srcPath);
            String basePackage = args[1];
            System.out.println("Base package: " + basePackage);

            String basePkgDir = packageToDir(basePackage);
            String searchPath = srcPath + "/" + basePkgDir;
        
            System.out.println("Search path: " + searchPath);

            List<File> files = getFileListing(new File(searchPath));

            for(File file : files ){
              System.out.println(file);
            }
            
            System.out.println("");
            System.out.println("Count = " + files.size());
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
