/* 
 * Formatter.java
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

import org.apache.tools.ant.Task;

import com.boothj5.jarch.analyser.RuleSetResult;

public interface Formatter {

    void openFormatter(OutputStream outputStream);

    void format(Task task, RuleSetResult result);

    void closeFormatter();

    void setOutputStream(OutputStream outputStream);

    void setType(String string);

    boolean outputStreamSupplied();

}
