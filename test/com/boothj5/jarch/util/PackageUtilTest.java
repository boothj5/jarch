package com.boothj5.jarch.util;

import static org.junit.Assert.* ;

import java.io.File;

import org.junit.Test;

public class PackageUtilTest {

    char fs = File.separatorChar;
    
    @Test
    public void packageToDirNull() {
        assertNull(PackageUtil.packageToDir(null));
    }
    
    @Test
    public void packageToDirEmpty() {
        assertEquals("", PackageUtil.packageToDir(""));
    }
    
    @Test
    public void packageToDirSinglePath() {
        assertEquals("com", PackageUtil.packageToDir("com"));
    }

    @Test
    public void packageToDirFull() {
        String packageName = "com.boothj5.jarch";
        String expected = "com" + fs + 
                          "boothj5" + fs + 
                          "jarch";
        
        assertEquals(expected, PackageUtil.packageToDir(packageName));
    }

    @Test
    public void classNameFromNullFileName() {
        String sourcePath = fs + "home" + 
                            fs + "james" + 
                            fs + "projects-git" + 
                            fs + "jarch";
        String result = PackageUtil.fileNameToQualifiedClassName(null, sourcePath);
        
        assertNull(result);
    }

    @Test
    public void classNameFromFileNameNullSrcPath() {
        String fileName = fs + "home" + 
                          fs + "james" + 
                          fs + "projects-git" + 
                          fs + "jarch" + 
                          fs + "src" + 
                          fs + "com" + 
                          fs + "boothj5" + 
                          fs + "jarch" + 
                          fs + "Main.java";
        String expected = "home.james.projects-git.jarch.src.com.boothj5.jarch" +
                          ".Main";
        
        String result = PackageUtil.fileNameToQualifiedClassName(fileName, null);
        
        assertEquals(expected, result);
    }

    @Test
    public void classNameFromFileName() {
        String fileName = fs + "home" + 
                          fs + "james" + 
                          fs + "projects-git" + 
                          fs + "jarch" + 
                          fs + "src" + 
                          fs + "com" + 
                          fs + "boothj5" + 
                          fs + "jarch" + 
                          fs + "Main.java";
        String srcPath = fs + "home" +
                         fs + "james" + 
                         fs + "projects-git" + 
                         fs + "jarch" + 
                         fs + "src";
        String expected = "com.boothj5.jarch.Main";
        
        String result = PackageUtil.fileNameToQualifiedClassName(fileName, srcPath);
        
        assertEquals(expected, result);
    }
}
