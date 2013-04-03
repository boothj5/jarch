/*
 * Copyright 2013 Corelogic Ltd All Rights Reserved.
 */
package com.boothj5.jarch.ant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.tools.ant.BuildException;

public class FormatterFactory {

    public static enum Type {
        console() {
            @Override
            public Formatter getFormatter() {
                return new JarchConsoleFormatter();
            }
        },
        xml() {
            @Override
            public Formatter getFormatter() {
                return new JarchXMLFormatter();
            }
        };
        abstract Formatter getFormatter();
    }

    private Type type;
    private File file;

    public void setType(Type type) {
        this.type = type;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Formatter getFormatter() {
        // String allowedTypes = StringUtils.join(Type.values(), ",");
        if (type == null) {
            throw new BuildException("You must specify a formatter type");
        }

        Formatter formatter = type.getFormatter();
        formatter.setOutputStream(System.out);
        if (file != null) {
            try {
                formatter.setOutputStream(new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                throw new BuildException("Error accessing file[" + file + "]");
            }
        }
        return formatter;
    }

}
