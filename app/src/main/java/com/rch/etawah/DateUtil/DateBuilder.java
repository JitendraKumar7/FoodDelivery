package com.rch.etawah.DateUtil;

import com.rch.etawah.Utility.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateBuilder {
    private String TAG = DateBuilder.class.getSimpleName();
    private DateConstraint.DateTimAction dateTimAction;
    private DateConstraint.DateTimeConstraint dateTimeConstraint;
    private DateConstraint.DateTimeFormatConstraint dateTimeFormatConstraint;
    private SimpleDateFormat simpleDateFormat;
    private String givenDateTime;
    private String fromDateTime;
    private String toDateTime;
    private String customFormat;
    private boolean isCurrentDay;


    public DateBuilder() {

    }

    public DateBuilder setDateTimAction(DateConstraint.DateTimAction dateTimAction) {
        this.dateTimAction = dateTimAction;
        return this;
    }

    public DateBuilder setDateTimeConstraint(DateConstraint.DateTimeConstraint dateTimeConstraint) {
        this.dateTimeConstraint = dateTimeConstraint;
        return this;
    }

    public DateBuilder setDateTimeFormatConstraint(DateConstraint.DateTimeFormatConstraint dateTimeFormatConstraint) {
        this.dateTimeFormatConstraint = dateTimeFormatConstraint;
        return this;
    }

    public DateBuilder setGivenDateTime(String givenDateTime) {
        this.givenDateTime = givenDateTime;
        return this;
    }

    public DateBuilder setCustomFormat(String customFormat) {
        this.customFormat = customFormat;
        return this;
    }

    public DateBuilder setFromDateTime(String fromDateTime) {
        this.fromDateTime = fromDateTime;
        return this;
    }

    public DateBuilder setToDateTime(String toDateTime) {
        this.toDateTime = toDateTime;
        return this;
    }

    public DateBuilder setCurrentDay(boolean currentDay) {
        isCurrentDay = currentDay;
        return this;
    }

    public String buildDateTime() {
        String format = null;
        String result = null;


        if (dateTimeConstraint == DateConstraint.DateTimeConstraint.CUSTOM_FORMAT)
            format = customFormat;
        else {
            format = dateTimeFormatCreator(dateTimeConstraint, dateTimeFormatConstraint);
        }

        Utility.Logger(TAG, "Format = " + format);
        simpleDateFormat = new SimpleDateFormat(format);

        if (dateTimAction == DateConstraint.DateTimAction.COMPARE_DATE_TIME) {

            if (dateTimeConstraint == DateConstraint.DateTimeConstraint.CHECK_CUSTOM_DAY) {
                result = simpleDateFormat.format(new Date());

                if (result.equalsIgnoreCase(givenDateTime)) {
                    result = DateConstraint.DateTimeResult.MATCH;
                }

            }
            else if (dateTimeConstraint == DateConstraint.DateTimeConstraint.CHECK_DATE_LIES_BTW_TWO_CUSTOM_TIMES) {

                Date fromDate = null;
                Calendar fromCalendar = null;

                Date toDate = null;
                Calendar toCalendar = null;
                Date currentDate = null;

                Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);


                Utility.Logger(TAG, "From Time = " + fromDateTime + " To Time = " + toDateTime + " Current Time = " + hour + ":" + minute + " " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(now.getTime()));

                try {

                    fromDate= new SimpleDateFormat(format).parse(fromDateTime);
                    fromCalendar = Calendar.getInstance();
                    fromCalendar.setTime(fromDate);
                    fromCalendar.add(Calendar.DATE, 1);


                    toDate = new SimpleDateFormat(format).parse(toDateTime);
                    toCalendar = Calendar.getInstance();
                    toCalendar.setTime(toDate);
                    toCalendar.add(Calendar.DATE, 1);

                    if (toCalendar.get(Calendar.HOUR_OF_DAY) < fromCalendar.get(Calendar.HOUR_OF_DAY)){
                        toCalendar.add(Calendar.DATE, 1);
                    }

                    currentDate = new SimpleDateFormat(format).parse(hourMinuteZero(hour, minute));
                    now = Calendar.getInstance();
                    now.setTime(currentDate);
                    now.add(Calendar.DATE, 1);

                    Date x = now.getTime();
                    if (x.after(fromCalendar.getTime()) && x.before(toCalendar.getTime())) {
                        //checkes whether the current time is between 14:49:00 and 20:11:13.
                        Utility.Logger(TAG, "Matched");
                        result = DateConstraint.DateTimeResult.MATCH;
                    } else
                        result = DateConstraint.DateTimeResult.NOT_MATCH;

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Utility.Logger(TAG, "Result = " + result);

            }

        }
        else if (dateTimAction == DateConstraint.DateTimAction.FIND_DATE_TIME) {

            if (dateTimeConstraint == DateConstraint.DateTimeConstraint.CURRENT_DAY_FULL_NAME) {
                result = simpleDateFormat.format(new Date());
            } else if (dateTimeConstraint == DateConstraint.DateTimeConstraint.PARSE_TIME
                    || dateTimeConstraint == DateConstraint.DateTimeConstraint.PARSE_DAY) {

                Date date = null;

                try {
                    date = simpleDateFormat.parse(givenDateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                result = new SimpleDateFormat(customFormat).format(date);

            }

        }


        return result;
    }


    private String dateTimeFormatCreator(DateConstraint.DateTimeConstraint dateTimeConstraint, DateConstraint.DateTimeFormatConstraint dateTimeFormatConstraint) {
        String format = null;

        if (dateTimeConstraint == DateConstraint.DateTimeConstraint.CURRENT_DAY_FULL_NAME
                && dateTimeFormatConstraint == DateConstraint.DateTimeFormatConstraint.eeee) {

            format = DateConstraint.DateTimeFormats.ONLY_DAY_FULL_FORMAT;

        }
        else if (dateTimeConstraint == DateConstraint.DateTimeConstraint.CURRENT_DAY_SHORT_NAME
                && dateTimeFormatConstraint == DateConstraint.DateTimeFormatConstraint.eee) {

            format = DateConstraint.DateTimeFormats.ONLY_DAY_SHORT_FORMAT;

        }
        else if (dateTimeConstraint == DateConstraint.DateTimeConstraint.CHECK_DATE_LIES_BTW_TWO_CUSTOM_TIMES
                && dateTimeFormatConstraint == DateConstraint.DateTimeFormatConstraint.hh_mm) {

            format = DateConstraint.DateTimeFormats.ONLY_TIME_FORMAT;

        }
        else if (dateTimeConstraint == DateConstraint.DateTimeConstraint.PARSE_TIME
                && dateTimeFormatConstraint == DateConstraint.DateTimeFormatConstraint.hh_mm) {

            format = DateConstraint.DateTimeFormats.ONLY_TIME_FORMAT;

        } else if (dateTimeConstraint == DateConstraint.DateTimeConstraint.PARSE_DAY
                && dateTimeFormatConstraint == DateConstraint.DateTimeFormatConstraint.eeee) {

            format = DateConstraint.DateTimeFormats.ONLY_DAY_FULL_FORMAT;
        }

        return format;
    }

    private String hourMinuteZero(int hour, int mnts) {
        String hourZero = (hour >= 10) ? Integer.toString(hour) : String.format("0%s", Integer.toString(hour));
        String minuteZero = (mnts >= 10) ? Integer.toString(mnts) : String.format("0%s", Integer.toString(mnts));
        return hourZero + ":" + minuteZero;
    }

}
