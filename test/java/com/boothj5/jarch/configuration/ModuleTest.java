/* 
 * ModuleTest.java
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
package com.boothj5.jarch.configuration;

import static org.junit.Assert.* ;

import java.util.Arrays;

import org.junit.Test;

public class ModuleTest {

    @Test
    public void testValidDependency() {
        Module common = new Module("common", null, Arrays.asList("person"));

        assertTrue(common.validateDependency("person"));
    }

    @Test
    public void testInvalidDependency() {
        Module common = new Module("common", null, Arrays.asList("address"));

        assertFalse(common.validateDependency("person"));
    }
}
