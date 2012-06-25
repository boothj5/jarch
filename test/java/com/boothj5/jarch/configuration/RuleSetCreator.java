package com.boothj5.jarch.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RuleSetCreator {
    
    public static RuleSet createRuleSet(String basePackage, Module module1, Module module2) {
        List<Module> modules = new ArrayList<Module>();
        modules.add(module1);
        modules.add(module2);
        
        return new RuleSet("test", basePackage, modules);
    }
    
    public static RuleSet createValidRuleSet() {
        Module common = new Module("common", null, new ArrayList<Dependency>());
        Module thing = new Module("thing", "spring", Arrays.asList(new Dependency("common", null)));

        return createRuleSet("com.boothj5.jarch", common, thing);
    }
}
