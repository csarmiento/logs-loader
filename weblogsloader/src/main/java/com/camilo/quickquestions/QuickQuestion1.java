package com.camilo.quickquestions;

public class QuickQuestion1 {

    public static String stripHtmlTags(String html) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < html.length(); i++) {
            char[] charArray = html.toCharArray();
            if (charArray[i] == '<') {
                while (charArray[i] != '>') {
                    //ignore input
                    i++;
                }
                // ignore '>'
                i++;
            }
            if (i < charArray.length) {
                sb.append(charArray[i]);
            }
        }

        return sb.toString();
    }

}