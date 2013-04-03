/*
 * Copyright 2013 Corelogic Ltd All Rights Reserved.
 */
package com.boothj5.jarch.ant;

import java.io.OutputStream;

import com.boothj5.jarch.analyser.RuleSetResult;

public interface Formatter {

    void openFormatter(OutputStream outputStream);

    void format(RuleSetResult result);

    void closeFormatter();

    void setOutputStream(OutputStream outputStream);

    void setType(String string);

    boolean outputStreamSupplied();

}
