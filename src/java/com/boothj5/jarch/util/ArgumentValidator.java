package com.boothj5.jarch.util;

public class ArgumentValidator {

    public static void notNull(Object obj, String message) {
        if (obj == null)
            throw new IllegalArgumentException(message);
    }
    
    public static void notNullOrEmpty(String str, String message) {
        notNull(str, message);
        
        if ("".equals(str))
            throw new IllegalArgumentException(message);
    }
}
