package com.camilo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
    public static int countNonEmptyLinesInFile(File file) throws IOException {
        int nonEmptyLinesInFile = 0;
        try (FileReader input = new FileReader(file);
             BufferedReader br = new BufferedReader(input)) {

            String line = br.readLine();
            while (line != null) {
                nonEmptyLinesInFile += (line.trim().length() > 0) ? 1 : 0;
                line = br.readLine();
            }
        }
        return nonEmptyLinesInFile;
    }
}