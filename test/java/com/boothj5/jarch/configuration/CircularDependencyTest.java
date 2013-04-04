/* 
 * CircularDependencyTest.java
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

import org.junit.Test;

public class CircularDependencyTest {
    
    @Test(expected=IllegalArgumentException.class)
    public void nullModuleA() {
        new CircularDepedency(null, "moduleB");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void nullModuleB() {
        new CircularDepedency("moduleA", null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void emptyModuleA() {
        new CircularDepedency("", "moduleB");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void emptyModuleB() {
        new CircularDepedency("moduleA", "");
    }
    
    @Test
    public void moduleA() {
        CircularDepedency dep = new CircularDepedency("moduleA", "moduleB");
        assertEquals("moduleA", dep.getModuleA());
    }

    @Test
    public void moduleB() {
        CircularDepedency dep = new CircularDepedency("moduleA", "moduleB");
        assertEquals("moduleB", dep.getModuleB());
    }
    
    @Test 
    public void equalsWhenSame() {
        CircularDepedency dep1 = new CircularDepedency("moduleA", "moduleB");
        CircularDepedency dep2 = new CircularDepedency("moduleA", "moduleB");
        
        assertTrue(dep1.equals(dep2));
    }

    @Test 
    public void equalsWhenSameOtherWayRound() {
        CircularDepedency dep1 = new CircularDepedency("moduleA", "moduleB");
        CircularDepedency dep2 = new CircularDepedency("moduleB", "moduleA");
        
        assertTrue(dep1.equals(dep2));
    }
    
    @Test
    public void notEqualsWhenDifferent() {
        CircularDepedency dep1 = new CircularDepedency("moduleA", "moduleB");
        CircularDepedency dep2 = new CircularDepedency("moduleA", "moduleC");
        
        assertFalse(dep1.equals(dep2));
    }
    
    @Test
    public void notEqualWhenSecondNull() {
        CircularDepedency dep1 = new CircularDepedency("moduleA", "moduleB");

        assertFalse(dep1.equals(null));
    }

    @Test
    public void notEqualWhenSecondSomethingElse() {
        CircularDepedency dep1 = new CircularDepedency("moduleA", "moduleB");

        assertFalse(dep1.equals(new Integer(12)));
    }
    
    @Test 
    public void hashCodesEqualWhenSame() {
        CircularDepedency dep1 = new CircularDepedency("moduleA", "moduleB");
        CircularDepedency dep2 = new CircularDepedency("moduleA", "moduleB");

        assertTrue(dep1.hashCode() == dep2.hashCode());
    }

    @Test 
    public void hashCodesEqualWhenSameOtherWayRound() {
        CircularDepedency dep1 = new CircularDepedency("moduleA", "moduleB");
        CircularDepedency dep2 = new CircularDepedency("moduleB", "moduleA");

        assertTrue(dep1.hashCode() == dep2.hashCode());
    }

    @Test 
    public void hashCodesNotEqualWhenDifferent() {
        CircularDepedency dep1 = new CircularDepedency("moduleA", "moduleB");
        CircularDepedency dep2 = new CircularDepedency("moduleA", "moduleC");

        assertFalse(dep1.hashCode() == dep2.hashCode());
    }


}
