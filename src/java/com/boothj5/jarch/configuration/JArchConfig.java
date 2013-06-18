/*
 * JArchConfig.java
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

import java.util.List;
import java.util.Map;

public class JArchConfig {

    private final Map<String, ImportSpec> importSpecs;
    private final Map<String, LayerSpec> layerSpecs;
    private final List<RuleSet> ruleSets;

    public JArchConfig(Map<String, ImportSpec> importSpecs, Map<String, LayerSpec> layerSpecs,
    		List<RuleSet> ruleSets) {
        this.importSpecs = importSpecs;
        this.layerSpecs = layerSpecs;
        this.ruleSets = ruleSets;
    }

    public Map<String, ImportSpec> getImportSpecs() {
        return importSpecs;
    }

    public Map<String, LayerSpec> getLayerSpecs() {
        return layerSpecs;
    }

    public List<RuleSet> getRuleSets() {
        return ruleSets;
    }

}
