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
            JArchConfig conf = JArchConfig.parse(args[1]);
            String srcPath = args[0];
            String basePackage = conf.getBasePath();
            String basePkgDir = packageToDir(basePackage);
            String searchPath = srcPath + "/" + basePkgDir;
            List<File> files = getFileListing(new File(searchPath));

//            debug(conf, srcPath, basePackage, basePkgDir, searchPath, files);

            for (Module module : conf.getModules()) {
                List<File> moduleFiles = getFileListing(new File(searchPath + "/" + module.getName()));
                
                System.out.println("Analysing " + module.getName() + "...");
                for (File file : moduleFiles) {
                    FileInputStream fstream = new FileInputStream(file);
                    DataInputStream in = new DataInputStream(fstream);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    int lineNo = 0;
                    while ((strLine = br.readLine()) != null)   {
                        lineNo++;
                        if (strLine.startsWith("import " + basePackage)) {
                            String remain = strLine.substring(basePackage.length() + 8);
                            StringTokenizer tok = new StringTokenizer(remain, ".");
                            String dependentModule = (String) tok.nextElement();
                            
                            if (!module.validateDependency(dependentModule)) {
                                String classPath = file.getAbsolutePath();
                                String stripped = classPath.substring(srcPath.length() + 1);
                                String withoutJava = stripped.substring(0, stripped.length() - 5);
                                String className = withoutJava.replace("/", ".");
                                System.out.println("-> " + className + "(" + lineNo + "): " + strLine);
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
