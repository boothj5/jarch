/*
 * Copyright 2013 Corelogic Ltd All Rights Reserved.
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
        Violation violation = new Violation("message", "clazz", 6, "line");
        assertEquals(violation, violation);
    }

    @Test
    public void equalsWorksForCollection() {
        Violation violation = new Violation("message", "clazz", 6, "line");
        List<Violation> list = new ArrayList<Violation>();
        list.add(violation);
        assertTrue(list.contains(violation));
    }

}
