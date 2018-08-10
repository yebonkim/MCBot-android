package com.example.mcbot.util;

import java.sql.Timestamp;

/**
 * Created by yebonkim on 2018. 8. 8..
 */

public class TimeUtil {

    public static long getNowTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return timestamp.getTime();
    }
}
