/* 
 * JarchConsoleFormatter.java
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

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.tools.ant.Task;

import com.boothj5.jarch.analyser.RuleSetResult;
import com.boothj5.jarch.analyser.Violation;

public class JarchConsoleFormatter implements Formatter {

    private PrintWriter printWriter;
    private String type;

    @Override
    public void setOutputStream(OutputStream outputStream) {
        try {
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            printWriter = new PrintWriter(outputStreamWriter);
        } catch (final UnsupportedEncodingException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean outputStreamSupplied() {
        return printWriter != null;
    }

    @Override
    public void openFormatter(OutputStream outputStream) {
        if (printWriter == null) {
            throw new IllegalStateException("Formatter not supplied an output stream");
        }
        printWriter.println("Console Formatter[" + this.getClass().getPackage().getSpecificationVersion() + "]::");
    }

    @Override
    public void closeFormatter() {
    }

    @Override
    public void format(Task task, RuleSetResult result) {
        task.log("--> Analysing rule-set \"" + result.getRuleSetName() + "\".");
        task.log("");

        for (String warning : result.getWarnings()) {
            task.log(warning);
        }
        task.log("");

        for (Violation violation : result.getViolations()) {
            task.log(violation.getMessage());
            task.log("  -> " + violation.getClazz() + ":");
            task.log("         Line " + violation.getLineNumber() + ": " + violation.getLine());
        }
        task.log("");
    }
}
