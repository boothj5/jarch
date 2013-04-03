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
        printWriter.println("Console Formatter::");
    }

    @Override
    public void closeFormatter() {
    }

    @Override
    public void format(RuleSetResult result) {
        printWriter.println("--> Analysing rule-set \"" + result.getRuleSetName() + "\".");
        printWriter.println("");

        for (String warning : result.getWarnings()) {
            printWriter.println(warning);
        }
        printWriter.println("");

        for (Violation violation : result.getViolations()) {
            printWriter.println(violation.getMessage());
            printWriter.println("  -> " + violation.getClazz() + ":");
            printWriter.println("         Line " + violation.getLineNumber() + ": " + violation.getLine());
        }
        printWriter.println("");

    }

}
