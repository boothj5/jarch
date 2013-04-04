/* 
 * Violation.java
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
package com.boothj5.jarch.analyser;

public class Violation {

    private final String message;
    private final String clazz;
    private final int lineNumber;
    private final String line;
    private final ViolationType type;

    public Violation(final String message, final String clazz, final int lineNumber, final String line, final ViolationType type) {
        this.message = message;
        this.clazz = clazz;
        this.lineNumber = lineNumber;
        this.line = line;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public String getClazz() {
        return clazz;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getLine() {
        return line;
    }

    public ViolationType getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) { return false; }
        if (!(o instanceof Violation)) { return false; }

        Violation other = (Violation) o;
        return this.message.equals(other.getMessage())
                && this.clazz.equals(other.getClazz())
                && this.lineNumber == other.getLineNumber()
                && this.line.equals(other.getLine())
                && this.type.equals(other.getType());
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + (this.message == null ? 0 : this.message.hashCode());
        hash = hash * 3 + (this.clazz == null ? 0 : this.clazz.hashCode());
        hash = hash * 5 + this.lineNumber;
        hash = hash * 13 + (this.line == null ? 0 : this.line.hashCode());
        hash = hash * 3 + (this.type == null ? 0 : this.type.hashCode());

        return hash;
    }

}
