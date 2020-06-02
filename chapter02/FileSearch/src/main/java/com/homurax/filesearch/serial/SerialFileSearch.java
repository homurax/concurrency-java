package com.homurax.filesearch.serial;

import com.homurax.filesearch.util.Result;

import java.io.File;

public class SerialFileSearch {

    public static void searchFiles(File file, String fileName, Result result) {

        File[] contents = file.listFiles();

        if ((contents == null) || (contents.length == 0)) {
            return;
        }

        for (File content : contents) {
            if (content.isDirectory()) {
                searchFiles(content, fileName, result);
            } else {
                if (content.getName().equals(fileName)) {
                    result.setFound(true);
                    result.setPath(content.getAbsolutePath());
                    System.out.printf("Serial Search: Path: %s%n", result.getPath());
                    return;
                }
            }
            if (result.isFound()) {
                return;
            }
        }
    }
}
