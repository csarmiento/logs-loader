package com.camilo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class LogPreprocessor {
    private static final Logger logger = LoggerFactory.getLogger(LogPreprocessor.class);

    private static final String TMP_FILE_SUFFIX = ".tmp";
    private static final String TMP_FILE_PREFIX = "web-server-access";

    public static File writeAccessLogAsCsvForLoad(File accessLogFile, String delimiter) throws IOException {
        File loadFile = File.createTempFile(TMP_FILE_PREFIX, TMP_FILE_SUFFIX);
        loadFile.deleteOnExit();

        // As split method requires a regex, here it is quoted as a safety measure
        String quotedDelimiter = Pattern.quote(delimiter);

        logger.info("Processing file: {}", accessLogFile.getAbsolutePath());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(accessLogFile));
             FileWriter fw = new FileWriter(loadFile)) {

            String line = bufferedReader.readLine();
            while (line != null) {
                String[] parts = line.split(quotedDelimiter);

                // take only timestamp and IP
                fw.write(String.format("%s,%s\n", parts[0], parts[1]));
                line = bufferedReader.readLine();
            }
        }

        return loadFile;
    }
}