/* 
 * PackageUtil.java
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
package com.boothj5.jarch.util;

import java.io.File;
import java.util.StringTokenizer;

public class PackageUtil {

    public static String packageToDir(String pkgName) {
        if (pkgName == null) {
            return null;
        } else {
            return pkgName.replace('.', File.separatorChar);
        }
    }
    
    public static String fileNameToQualifiedClassName(String fileName, String srcPath) {
        if (fileName == null) {
            return null;
        } else {
            String stripped;
            if (srcPath != null) {
                stripped = fileName.substring(srcPath.length() + 1);
            } else {
                stripped = fileName.substring(1);
            }
            String withoutJava = stripped.substring(0, stripped.length() - 5);
            String className = withoutJava.replace(File.separatorChar, '.');
            return className;
        }
    }
    
    public static String getLayer(String absoluteFilePath, String sourcePath, String moduleName) {
        String endStr = absoluteFilePath.substring(sourcePath.length() + 2 + moduleName.length());
        StringTokenizer tok = new StringTokenizer(endStr, String.valueOf(File.separatorChar));
        
        String layer = tok.nextToken();

        return layer;
    }
}
