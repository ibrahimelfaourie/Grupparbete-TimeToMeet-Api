package com.iot.timetomeet.helper;

import java.time.Instant;

public class Helper {

    public static String formatImageUrl(String imageUrl){
        return "http://" + imageUrl.substring(2);
    }

    public static String formatDate(int day, int month, int year) {
        String dayStr = String.valueOf(day);
        String monthStr = String.valueOf(month + 1);

        if (day < 10) {
            dayStr = "0" + dayStr;
        }

        if (month < 10) {
            monthStr = "0" + monthStr;
        }

        return dayStr + "/" + monthStr + "/" + year;
    }

    public static String formatFromIsoToSearchDate(String date) {
        return Instant.parse(date).toString().substring(0, 10);
    }

    public static String formatDateTime(String date) {
        String day = date.substring(0, 2);
        String month = date.substring(3, 5);
        String year = date.substring(6);

        return year + "-" + month + "-" + day + "T06:00:00Z";
    }
}
