package com.boothj5.jarch;

import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.jdom2.JDOMException;

public class JArchTask extends Task {
    private String jarchConfigFile;

    private Path srcPath;

    public void setSourcePath(String sourcePath) {
        srcPath = new Path(getProject(), sourcePath);
    }

    public void setSrcPathRef(Reference r) {
        srcPath = new Path(getProject());
        srcPath.setRefid(r);
    }

    public void setJArchConfigFile(String jarchConfigFile) {
        this.jarchConfigFile = jarchConfigFile;
    }

    public void execute() throws BuildException {
        try {
            log("JArch using config file " + jarchConfigFile);
            log("");

            JArchConfig conf = JArchConfigReader.parse(jarchConfigFile);
            Analyser analyser = new Analyser(srcPath.list()[0], conf.getBasePackage(), conf.getModules(), conf.getLayerSpecs());
            analyser.analyse();
            
            for (String error : analyser.getErrorStrings()) {
                log(error);
            }
            
            if ((analyser.getNumModuleErrors() > 0) || (analyser.getNumLayerErrors() > 0)) {
                throw new BuildException("JArch failed, " + analyser.getNumModuleErrors() + " module errors, " + 
                        analyser.getNumLayerErrors() + " layer errors.");
            }
        } catch (IOException e) {
            throw new BuildException(e);
        } catch (JDOMException e) {
            throw new BuildException(e);
        }
    }
}
