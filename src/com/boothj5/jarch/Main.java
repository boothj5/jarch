/* 
 * Main.java
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
        
        JArchConfigValidator validator = new JArchConfigValidator(conf);
        validator.validate();
        
        if (validator.getErrors() != null) {
            System.out.println("Error parsing config file.");

            for (String error : validator.getErrors()) {
                System.out.println(error);
            }

            System.out.println("");
            System.out.println("JArch exiting.");
            System.out.println("");
            
            System.exit(1);
        }

        if (validator.getWarnings() != null) {
            System.out.println("Warning parsing config file.");

            for (String warning : validator.getWarnings()) {
                System.out.println(warning);
            }
            System.out.println("");
        } 
        
        Analyser analyser = new Analyser(srcPath, conf.getBasePackage(), conf.getModules(), conf.getLayerSpecs());
        analyser.analyse();
        
        for (String error : analyser.getErrorStrings()) {
            System.out.println(error);
        }
        
        System.out.println("Module errors: " + analyser.getNumModuleErrors());
        System.out.println("Layer errors: " + analyser.getNumLayerErrors());
    }
    
    private static void validateArgs(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: jarch <src-path> <config-file>");
            System.exit(1);
        }
    }
}
