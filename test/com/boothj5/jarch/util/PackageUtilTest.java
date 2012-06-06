package com.boothj5.jarch.util;

import static org.junit.Assert.* ;

import java.io.File;

import org.junit.Test;

public class PackageUtilTest {

    private char fs = File.separatorChar;
    private String fileName = 
        fs + "home" + 
        fs + "james" + 
        fs + "projects-git" + 
        fs + "jarch" + 
        fs + "src" + 
        fs + "com" + 
        fs + "boothj5" + 
        fs + "jarch" + 
        fs + "configuration" + 
        fs + "controller" + 
        fs + "MyController.java";
    private String srcPath = 
            fs + "home" +
            fs + "james" + 
            fs + "projects-git" + 
            fs + "jarch" + 
            fs + "src";
    private String basePackageDir = 
            fs + "home" +
            fs + "james" + 
            fs + "projects-git" + 
            fs + "jarch" + 
            fs + "src" + 
            fs + "com" + 
            fs + "boothj5" + 
            fs + "jarch";
    
    @Test(expected=IllegalArgumentException.class)
    public void packageToDirNull() {
        PackageUtil.packageToDir(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void packageToDirEmpty() {
        PackageUtil.packageToDir("");
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

    @Test(expected=IllegalArgumentException.class)
    public void classNameFromNullFileName() {
        PackageUtil.fileNameToQualifiedClassName(null, srcPath);
    }

    @Test
    public void classNameFromFileNameNullSrcPath() {
        String expected = "home.james.projects-git.jarch.src.com.boothj5.jarch" +
                          ".configuration.controller.MyController";
        
        String result = PackageUtil.fileNameToQualifiedClassName(fileName, null);
        
        assertEquals(expected, result);
    }

    @Test
    public void classNameFromFileName() {
        String expected = "com.boothj5.jarch.configuration.controller.MyController";
        
        String result = PackageUtil.fileNameToQualifiedClassName(fileName, srcPath);
        
        assertEquals(expected, result);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void getLayerWithNullAbsoluteFilePath() {
        PackageUtil.getLayer(null, basePackageDir, "configuration");
    }

    @Test(expected=IllegalArgumentException.class)
    public void getLayerWithEmptyAbsoluteFilePath() {
        PackageUtil.getLayer("", basePackageDir, "configuration");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void getLayerWithNullSourcePath() {
        PackageUtil.getLayer(fileName, null, "configuration");
    }

    @Test(expected=IllegalArgumentException.class)
    public void getLayerWithEmptySourcePath() {
        PackageUtil.getLayer(fileName, "", "configuration");
    }

    @Test(expected=IllegalArgumentException.class)
    public void getLayerWithNullModuleName() {
        PackageUtil.getLayer(fileName, basePackageDir, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getLayerWithEmptyModuleName() {
        PackageUtil.getLayer(fileName, basePackageDir, "");
    }
    
    @Test
    public void getLayerReturnsLayer() {
        String layer = PackageUtil.getLayer(fileName, basePackageDir, "configuration");
        assertEquals("controller", layer);
    }

}
