package com.camilo.util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileUtilsTest {
    @Test
    public void countNonEmptyLinesInFileShouldCountTheLinesProperly() throws IOException {
        URL resource = LogPreprocessorTest.class.getClassLoader().getResource("access.log");

        assertNotNull(resource);

        File accessLogFile = new File(resource.getFile());

        assertEquals(116484, FileUtils.countNonEmptyLinesInFile(accessLogFile));
    }
}
