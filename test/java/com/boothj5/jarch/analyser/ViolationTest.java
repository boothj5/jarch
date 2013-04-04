/* 
 * ViolationTest.java
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ViolationTest {

    @Before
    public void setUp() {
    }

    @Test
    public void equalsReturnsTrueForSameObject() {
        Violation violation = new Violation("message", "clazz", 6, "line", ViolationType.MODULE);
        assertEquals(violation, violation);
    }

    @Test
    public void equalsWorksForCollection() {
        Violation violation = new Violation("message", "clazz", 6, "line", ViolationType.MODULE);
        List<Violation> list = new ArrayList<Violation>();
        list.add(violation);
        assertTrue(list.contains(violation));
    }

}
