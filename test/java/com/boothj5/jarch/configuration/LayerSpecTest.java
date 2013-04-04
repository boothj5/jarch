/* 
 * LayerSpecTest.java
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class LayerSpecTest {

    LayerSpec spec;
    
    @Before
    public void setup() {
        Layer controller = new Layer("controller", Arrays.asList("service"));
        Layer service = new Layer("service", new ArrayList<String>());
        Map<String, Layer> layers = new HashMap<String, Layer>();
        layers.put("controller", controller);
        layers.put("service", service);
        spec = new LayerSpec("spring", layers);
    }
    
    @Test
    public void containsLayerReturnsTrue() {
        assertTrue(spec.containsLayer("service"));
    }

    @Test
    public void doesntcontainLayerReturnsFalse() {
        assertFalse(spec.containsLayer("whoops"));
    }
    
    @Test
    public void testValidDependency() {
        assertTrue(spec.validateDependency("controller", "service"));
    }

    @Test
    public void testInvalidDependency() {
        assertFalse(spec.validateDependency("service", "controller"));
    }
}
