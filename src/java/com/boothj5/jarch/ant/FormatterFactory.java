/* 
 * FormatterFactory.java
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
