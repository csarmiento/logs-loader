package com.camilo.util;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class LogPreprocessorTest {
    @Test
    public void writeAccessLogAsCsvForLoad_generates_correctOutput() throws Exception {
        URL resource = LogPreprocessorTest.class.getClassLoader().getResource("testAccess.log");

        assertNotNull(resource);

        File accessLogFile = new File(resource.getFile());

        File csvForLoad = LogPreprocessor.writeAccessLogAsCsvForLoad(accessLogFile, "|");

        ImmutableList<String> expectedLines = ImmutableList.of(
                "2017-01-01 00:00:11.763,192.168.234.82",
                "2017-01-01 00:00:21.164,192.168.234.82",
                "2017-01-01 00:00:23.003,192.168.169.194",
                "2017-01-01 00:00:40.554,192.168.234.82",
                "2017-01-01 00:00:54.583,192.168.169.194",
                "2017-01-01 00:00:54.639,192.168.234.82",
                "2017-01-01 00:00:59.410,192.168.169.194",
                "2017-01-01 00:01:02.113,192.168.247.138",
                "2017-01-01 00:01:04.033,192.168.54.139",
                "2017-01-01 00:01:04.678,192.168.162.248"
        );

        List<String> resultFileLines = Files.readAllLines(csvForLoad.toPath());

        assertEquals(expectedLines, resultFileLines);
    }
}