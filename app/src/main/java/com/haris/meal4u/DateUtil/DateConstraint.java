package com.haris.meal4u.DateUtil;

public class DateConstraint {

    public static enum DateTimAction {COMPARE_DATE_TIME, FIND_DATE_TIME}

    public static enum DateTimeConstraint {
        CURRENT_DAY_SHORT_NAME, CURRENT_DAY_FULL_NAME, CURRENT_WEEK_NAME, CURRENT_MONTH_NAME, CURRENT_DATE_TIME, DATE_TIME_LONG, CUSTOM_FORMAT,
        COMPARE_TWO_CUSTOM_DATE_TIME, COMPARE_TWO_CUSTOM_TIME, COMPARE_TWO_CUSTOM_DATE, COMPARE_TWO_DATE_TIME, COMPARE_TWO_DATE, COMPARE_TWO_TIME,
        CHECK_DATE_LIES_BTW_TWO_DATES,CHECK_DATE_LIES_BTW_TWO_CUSTOM_DATES,CHECK_DATE_LIES_BTW_TWO_CUSTOM_TIMES,
        CHECK_DAY, CHECK_CUSTOM_DAY,PARSE_TIME,PARSE_DATE,PARSE_DAY
    }

    public static enum DateTimeFormatConstraint {eee, eeee, h_mm_ss_a, hh_mm_ss,hh_mm, dd_mm_yyyy, dd_mm_yyyy_h_mm_a, dd_mm_yyyy_hh_mm,yyyy_mm_dd_hh_mm_ss, milli_second}


    public static class DateTimeFormats {
        public static String ONLY_DAY_SHORT_FORMAT = "EEE";
        public static String ONLY_DAY_FULL_FORMAT = "EEEE";
        public static String ONLY_TIME_FORMAT = "HH:mm";
        public static String ONLY_STANDARD_FORMAT = "yyyy-MM-dd HH:mm";

    }

    public static class DateTimeResult {
        public static String MATCH = "match";
        public static String NOT_MATCH = "not_match";
    }

}
