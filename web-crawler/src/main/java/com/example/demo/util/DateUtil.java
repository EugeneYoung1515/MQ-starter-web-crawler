package com.example.demo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtil {
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {

        @Override

        protected DateFormat initialValue() {

            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        }
    };

    public static DateFormat get(){
        return df.get();
    }

    public static void close(){
        df.remove();
    }

}
