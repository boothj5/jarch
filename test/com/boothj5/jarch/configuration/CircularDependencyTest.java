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
