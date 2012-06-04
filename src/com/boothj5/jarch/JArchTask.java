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
