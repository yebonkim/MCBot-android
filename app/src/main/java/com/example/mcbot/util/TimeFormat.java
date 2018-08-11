package com.example.mcbot.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by yebonkim on 2018. 8. 12..
 */

public class TimeFormat {
    private static String DATE_FORMAT = "M월 d일";


    public static String timestampToString(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.KOREAN);
        return sdf.format(timestamp);
    }
}
