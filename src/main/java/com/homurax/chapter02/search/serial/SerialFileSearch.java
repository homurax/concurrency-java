package com.homurax.chapter02.search.serial;

import com.homurax.chapter02.search.util.Result;
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
                    result.setPath(content.getAbsolutePath());
                    result.setFound(true);
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
