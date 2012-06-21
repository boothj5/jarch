package com.boothj5.jarch;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;

import com.boothj5.jarch.analyser.Analyser;
import com.boothj5.jarch.configuration.JArchConfig;
import com.boothj5.jarch.configuration.JArchConfigReader;

public class InvalidTest {
    
    private final String srcPath = "test" + File.separator + "resources" + File.separator + "invalid";
    private final String configFile = "test" + File.separator + "resources" + File.separator 
            + "jarch-config.xml";
    
    private String absSrcPath;
    private JArchConfig conf;
    
    @Before
    public void setUp() throws IOException, JDOMException {
        File srcDir = new File(srcPath);
        absSrcPath = srcDir.getAbsolutePath();
        conf = JArchConfigReader.parse(configFile);
    }
    
    @Test
    public void analyserReturnsCorrectModuleErrorCount() throws IOException {
        Analyser analyser = new Analyser(absSrcPath, conf.getLayerSpecs(), conf.getRuleSets());
        analyser.analyse();
        
        assertEquals(15, analyser.getNumModuleErrors());
    }

    @Test
    public void analyserReturnsCorrectLayerErrorCount() throws IOException {
        Analyser analyser = new Analyser(absSrcPath, conf.getLayerSpecs(), conf.getRuleSets());
        analyser.analyse();
        
        assertEquals(8, analyser.getNumLayerErrors());
    }
}
