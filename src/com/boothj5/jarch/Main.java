package com.boothj5.jarch;

import java.io.IOException;

import org.jdom2.JDOMException;

public class Main {
    private static String srcPath;
    private static String configFile;
    
    public static void main(String[] args) throws IOException, JDOMException { 

        System.out.println("");
        System.out.println("JArch - Java Architecture checker.");
        System.out.println("");
        
        validateArgs(args);
        srcPath = args[0];
        configFile = args[1];
        
        System.out.println("Source path: " + srcPath);
        System.out.println("Config file: " + configFile);
        System.out.println("");
        
        JArchConfig conf = JArchConfigReader.parse(configFile);
        Analyser analyser = new Analyser(srcPath, conf.getBasePackage(), conf.getModules(), conf.getLayerSpecs());
        analyser.analyse();
    }
    
    private static void validateArgs(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: jarch <src-path> <config-file>");
            System.exit(1);
        }
    }
}
