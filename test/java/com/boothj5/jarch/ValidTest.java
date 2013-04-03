package com.boothj5.jarch;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;

import com.boothj5.jarch.analyser.Analyser;
import com.boothj5.jarch.analyser.RuleSetResult;
import com.boothj5.jarch.configuration.JArchConfig;
import com.boothj5.jarch.configuration.JArchConfigReader;

public class ValidTest {
    private final String srcPath = "test" + File.separator + "resources" + File.separator + "valid";
    private final String configFile = "test" + File.separator + "resources" + File.separator + "jarch-config.xml";

    private String absSrcPath;
    private JArchConfig conf;
    private Analyser analyser;
    private List<RuleSetResult> results;

    @Before
    public void setUp() throws IOException, JDOMException {
        File srcDir = new File(srcPath);
        absSrcPath = srcDir.getAbsolutePath();
        conf = JArchConfigReader.parse(configFile);
        analyser = new Analyser(absSrcPath, conf.getLayerSpecs(), conf.getRuleSets());
        results = analyser.analyse();
    }

    @Test
    public void analyserReturnsNoModuleErrors() throws IOException {
        assertEquals(0, analyser.getNumModuleErrors());
    }

    @Test
    public void analyserReturnsNoLayerErrors() throws IOException {
        assertEquals(0, analyser.getNumLayerErrors());
    }

    @Test
    public void warningGivenOnNonExistentModule() {
        for (RuleSetResult result : results) {
            if (result.getRuleSetName().equals("application-module-dependencies")) {
                assertEquals(1, result.getWarnings().size());
                assertTrue(result.getWarnings().contains("WARNING: Could not find module 'book'."));
            }
        }
    }

}
