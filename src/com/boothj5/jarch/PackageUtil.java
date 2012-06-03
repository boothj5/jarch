package com.boothj5.jarch;

import java.io.File;
import java.util.StringTokenizer;

public class PackageUtil {

    public static String packageToDir(String pkgName) {
        return pkgName.replace('.', File.separatorChar);
    }
    
    public static String fileNameToQualifiedClassName(String fileName, String srcPath) {
        String stripped = fileName.substring(srcPath.length() + 1);
        String withoutJava = stripped.substring(0, stripped.length() - 5);
        String className = withoutJava.replace(File.separatorChar, '.');
        
        return className;
    }
    
    public static String getLayer(String absoluteFilePath, String sourcePath, String moduleName) {
        String endStr = absoluteFilePath.substring(sourcePath.length() + 2 + moduleName.length());
        StringTokenizer tok = new StringTokenizer(endStr, String.valueOf(File.separatorChar));
        
        String layer = tok.nextToken();

        return layer;
    }
}
