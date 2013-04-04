/* 
 * JarchXMLFormatter.java
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

public class JarchXMLFormatter implements Formatter {

    private PrintWriter printWriter;
    private String type;

    public JarchXMLFormatter() {
    }
    
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
        printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        printWriter.println("<jarch version=\"" + this.getClass().getPackage().getSpecificationVersion() + "\">");
    }

    @Override
    public void format(Task task, RuleSetResult result) {

        StringBuilder sb = new StringBuilder("<ruleset").append(" name=\"").append(result.getRuleSetName())
                .append("\" >\n");

        for (Violation violation : result.getViolations()) {
            sb.append("<violation")
                    .append(" message=\"").append(violation.getMessage()).append("\"")
                    .append(" type=\"").append(violation.getType()).append("\"")
                    .append(" class=\"").append(violation.getClazz()).append("\"")
                    .append(" lineNumber=\"").append(violation.getLineNumber()).append("\"")
                    .append(" line=\"").append(violation.getLine()).append("\"").append("/>\n");
        }

        for (String warning : result.getWarnings()) {
            sb.append("<warning message=\"").append(warning).append("\" />\n");
        }

        sb.append("</ruleset>");

        printWriter.println(sb.toString());
    }

    @Override
    public void closeFormatter() {
        printWriter.println("</jarch>");
        printWriter.flush();
        printWriter = null;
    }

}
