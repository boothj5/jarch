/* 
 * ArgumentValidatorTest.java
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
package com.boothj5.jarch.util;

import static org.junit.Assert.* ;

import org.junit.Test;

import static com.boothj5.jarch.util.ArgumentValidator.*;

public class ArgumentValidatorTest {

    @Test
    public void notNullFails() {
        try {
            notNull(null, "Something should not be null");
        } catch (IllegalArgumentException e) {
            assertEquals("Something should not be null", e.getMessage());
        }
    }

    @Test
    public void notNullSucceeds() {
        notNull(new Integer(12), "Something should not be null");
        assertTrue(true);
    }
    
    @Test
    public void notNullOrEmptyFails() {
        try {
            notNullOrEmpty("", "Should not be empty");
        } catch (IllegalArgumentException e) {
            assertEquals("Should not be empty", e.getMessage());
        }
    }

    @Test
    public void notNullOrEmptySucceeds() {
        notNullOrEmpty("ha", "Should not be empty");
        assertTrue(true);
    }
}
