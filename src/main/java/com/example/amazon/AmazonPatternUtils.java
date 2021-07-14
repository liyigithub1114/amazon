package com.example.amazon;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmazonPatternUtils {

    public static final String PRICE_REGION_REG =
        "<span class='p13n-sc-price' >.*?</span>( - <span class='p13n-sc-price' >.*?</span>)?";

    public static final String HIGH_PRICE_REG = "[\\.0-9\\$]+(?=</span>$)";

    public static final String STAR_REG = "(?<=<span class=\"a-icon-alt\">).*(?= out)";

    public static final String COMMENT_REG = "(?<=>)[\\\\,0-9]+(?=</a>\\n)";

    public static final Pattern PRICE_REGION_PATTERN = Pattern.compile(PRICE_REGION_REG);

    public static final Pattern HIGH_PRICE_PATTERN = Pattern.compile(HIGH_PRICE_REG);

    public static final Pattern STAR_PATTERN = Pattern.compile(STAR_REG);

    public static final Pattern COMMENT_PATTERN = Pattern.compile(COMMENT_REG);

    public static List<String> findHighPrice(String content) {
        List<String> result = new ArrayList<>();

        final Matcher matcher = PRICE_REGION_PATTERN.matcher(content);
        while (matcher.find()) {
            final String group = matcher.group();
            final Matcher matcher1 = HIGH_PRICE_PATTERN.matcher(group);
            if (matcher1.find()) {
                result.add(matcher1.group());
            }
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
