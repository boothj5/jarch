/* 
 * JArchTask.java
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
package com.boothj5.jarch.ant;

import java.io.IOException;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.jdom2.JDOMException;

import com.boothj5.jarch.analyser.Analyser;
import com.boothj5.jarch.configuration.JArchConfig;
import com.boothj5.jarch.configuration.JArchConfigReader;
import com.boothj5.jarch.configuration.JArchConfigValidator;

public class JArchTask extends Task {
    private String jarchConfigFile;
    private Path srcPath;
    private boolean failBuild = true;

    public void setSourcePath(String sourcePath) {
        srcPath = new Path(getProject(), sourcePath);
    }
    
    public void setFailBuild(boolean failBuild) {
        this.failBuild = failBuild;
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
            JArchConfigValidator validator = new JArchConfigValidator(conf);
            validator.validate();

            if (validator.getErrors() != null) {
                log("Error parsing JArch config file.");
                for (String error : validator.getErrors()) {
                    log(error);
                }
                throw new BuildException("JArch failed.");
            }

            if (validator.getWarnings() != null) {
                log("Warning parsing JArch config file.");
                for (String warning : validator.getWarnings()) {
                    log(warning);
                }
                log("");
            }
            
            Analyser analyser = new Analyser(srcPath.list()[0], conf.getBasePackage(), conf.getModules(), conf.getLayerSpecs());
            analyser.analyse();
            
            for (String error : analyser.getErrorStrings()) {
                log(error);
            }
            
            if ((analyser.getNumModuleErrors() > 0) || (analyser.getNumLayerErrors() > 0)) {
                if (failBuild) {
                    throw new BuildException("JArch failed, " + analyser.getNumModuleErrors() + " module errors, " + 
                            analyser.getNumLayerErrors() + " layer errors.");
                } else {
                    log("JArch report: " + analyser.getNumModuleErrors() + " module warnings, " + 
                            analyser.getNumLayerErrors() + " layer warnings.");
                    
                }
            }
            
        } catch (IOException e) {
            throw new BuildException(e);
        } catch (JDOMException e) {
            throw new BuildException(e);
        }
    }
}
