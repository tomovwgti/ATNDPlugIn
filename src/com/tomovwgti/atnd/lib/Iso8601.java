
package com.tomovwgti.atnd.lib;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日時の分解
 */
public class Iso8601 {
    static final String TAG = Iso8601.class.getSimpleName();

    private static final Pattern pattern = Pattern
            .compile("(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2})");

    /**
     * ISO8601日時を正規表現で分解して取得する
     */
    public static Calendar getCalendar(String iso8601) {
        Matcher matcher = pattern.matcher(iso8601);
        Calendar calendar = Calendar.getInstance();

        if (matcher.find()) {
            // yyyy-mm-dd
            calendar.set(Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)) - 1, Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)));
        }
        return calendar;
    }

    public static String getString(String iso8601) {
        Matcher matcher = pattern.matcher(iso8601);
        String daytime = null;

        if (matcher.find()) {
            // yyyy-mm-dd
            daytime = matcher.group(1) + "/" + matcher.group(2) + "/" + matcher.group(3) + " "
                    + matcher.group(4) + ":" + matcher.group(5);
        }
        return daytime;
    }
}
