/*
 * Copyright 2013 Corelogic Ltd All Rights Reserved.
 */
package com.boothj5.jarch.ant;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
        printWriter.println("<jarch>");
    }

    @Override
    public void format(RuleSetResult result) {

        StringBuilder sb = new StringBuilder("<ruleset").append(" name=\"").append(result.getRuleSetName())
                .append("\" >\n");

        for (Violation violation : result.getViolations()) {
            sb.append("<violation")
                    .append(" message=\"").append(violation.getMessage()).append("\"")
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
