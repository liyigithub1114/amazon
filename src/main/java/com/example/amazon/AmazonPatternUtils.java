package com.example.amazon;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmazonPatternUtils {

    public static final String HIGH_PRICE_REG = "[\\.0-9\\$]+(?=[</span>]+</a>)";

    public static final String STAR_REG = "(?<=<span class=\"a-icon-alt\">).*(?= out)";

    public static final String COMMENT_REG = "(?<=>)[\\,0-9]+(?=</a>\n)";

    public static final Pattern HIGH_PRICE_PATTERN = Pattern.compile(HIGH_PRICE_REG);

    public static final Pattern STAR_PATTERN = Pattern.compile(STAR_REG);

    public static final Pattern COMMENT_PATTERN = Pattern.compile(COMMENT_REG);

    public static List<String> findHighPrice(String content) {
        List<String> result = new ArrayList<>();

        final Matcher matcher = HIGH_PRICE_PATTERN.matcher(content);
        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }

    public static List<String> findStar(String content) {
        List<String> result = new ArrayList<>();

        final Matcher matcher = STAR_PATTERN.matcher(content);
        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }

    public static List<String> findComment(String content) {
        List<String> result = new ArrayList<>();

        final Matcher matcher = COMMENT_PATTERN.matcher(content);
        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }

}
