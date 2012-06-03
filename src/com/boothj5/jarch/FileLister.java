package com.boothj5.jarch;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileLister {
    
    private final File path;
    
    public FileLister(String path) {
        this.path = new File(path);
    }
    
    public List<File> getFileListing() {
        List<File> result = getFileListingNoSort(path);
        Collections.sort(result);
        return result;
    }

    private List<File> getFileListingNoSort(File aStartingDir) {
        List<File> result = new ArrayList<File>();
        File[] filesAndDirs = aStartingDir.listFiles();
        List<File> filesDirs = Arrays.asList(filesAndDirs);

        for(File file : filesDirs) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                result.add(file);
            }
            if (!file.isFile()) {
                List<File> deeperList = getFileListingNoSort(file);
                result.addAll(deeperList);
            }
        }
    
        return result;
    }

}
