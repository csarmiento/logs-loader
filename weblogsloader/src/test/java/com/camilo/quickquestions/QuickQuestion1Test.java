package com.camilo.quickquestions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuickQuestion1Test {

    @Test
    public void test1() {
        String htmlString = "<b>Hi</b>,<p/> This is a <u>html</u> string";
        String expected = "Hi, This is a html string";

        assertEquals(expected, QuickQuestion1.stripHtmlTags(htmlString));
    }

    @Test
    public void test2() {
        String html = "<tag attribute=\"attr1\">Hello</tag>";
        String expected = "Hello";

        assertEquals(expected, QuickQuestion1.stripHtmlTags(html));
    }
}
