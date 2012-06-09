/* 
 * LayerSpec.java
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

import java.util.Map;


public class LayerSpec {

    private final String name;
    private final Map<String, Layer> layers;

    public LayerSpec(String name, Map<String, Layer> layers) {
        this.name = name;
        this.layers = layers;
    }
    
    public String getName() {
        return name;
    }
    
    public Map<String, Layer> getLayers() {
        return layers;
    }
    
    public boolean containsLayer(String layerName) {
        return layers.keySet().contains(layerName);
    }

    public boolean validateDependency(String sourceLayer, String dependentLayer) {
        if (sourceLayer.equals(dependentLayer)) {
            return true;
        } else if (!layers.keySet().contains(sourceLayer)) {
            return true;
        } else if (!layers.keySet().contains(dependentLayer)) {
            return true;
        } else {
            Layer layer = layers.get(sourceLayer);
            return layer.getDependencies().contains(dependentLayer);
        }
    }
}
