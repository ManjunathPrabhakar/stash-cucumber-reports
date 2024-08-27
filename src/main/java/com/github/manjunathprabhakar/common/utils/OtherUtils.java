package com.github.manjunathprabhakar.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtherUtils {

    private static final char[] escapeChars = {'<', '(', '[', '{', '\\', '^', '-', '=', '$', '!', '|', ']', '}', ')', '?', '*', '+', '.', '>'};

    private static String regexEscape(char character) {
        for (char escapeChar : escapeChars) {
            if (character == escapeChar) {
                return "\\" + character;
            }
        }
        return String.valueOf(character);
    }

    public static String quoteRegExSpecialChars(String s) {
        final String regExSpecialChars = "<([{\\^-=$!|]})?*+.>";
        final String regExSpecialCharsRE = regExSpecialChars.replaceAll(".", "\\\\$0");
        final Pattern reCharsREP = Pattern.compile("[" + regExSpecialCharsRE + "]");
        Matcher m = reCharsREP.matcher(s);
        return m.replaceAll("\\\\$0");
    }

    public static Map<String, String> getSystemProp() {
        Map<String, String> prop = new HashMap<>();
        prop.put("javahome", System.getProperty("java.home"));
        prop.put("osname", System.getProperty("os.name"));
        prop.put("osarch", System.getProperty("os.arch"));
        return prop;
    }
}

