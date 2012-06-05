package com.boothj5.jarch;

public class CircularDepedency {
    private final String moduleA;
    private final String moduleB;

    public CircularDepedency(String moduleA, String moduleB) {
        this.moduleA = moduleA;
        this.moduleB = moduleB;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CircularDepedency)) {
            return false;
        } else {
            CircularDepedency other = (CircularDepedency) o;
            
            if (this.moduleA.equals(other.moduleA) && this.moduleB.equals(other.moduleB)) {
                return true;
            } else if (this.moduleA.equals(other.moduleB) && this.moduleB.equals(other.moduleA)) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    @Override
    public int hashCode() {
        return moduleA.hashCode() + moduleB.hashCode();
    }
    
    public String getModuleA() {
        return moduleA;
    }

    public String getModuleB() {
        return moduleB;
    }
}