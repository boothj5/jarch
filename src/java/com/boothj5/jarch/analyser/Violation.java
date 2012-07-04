package com.boothj5.jarch.analyser;

public class Violation {

    private final String message;
    private final String clazz;
    private final int lineNumber;
    private final String line;
    
    public Violation(String message, String clazz, int lineNumber, String line) {
        this.message = message;
        this.clazz = clazz;
        this.lineNumber = lineNumber;
        this.line = line;
    }

    public String getMessage() {
        return message;
    }
    
    public String getClazz() {
        return clazz;
    }
    
    public int getLineNumber() {
        return lineNumber;
    }

    public String getLine() {
        return line;
    }
    
    @Override 
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof Violation)) {
            return false;
        }
        
        Violation other = (Violation) o;

        return this.message.equals(other.getMessage())
                && this.clazz.equals(other.getClazz())
                && this.lineNumber == other.getLineNumber()
                && this.line.equals(other.getLine());
    }
}
