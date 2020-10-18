package com.rch.etawah.ObjectUtil;


import com.rch.etawah.ConstantUtil.Constant;

import java.util.Date;

public class DateTimeObject {
    private Constant.DATETIME datetimeType;
    private String datetime;
    private String date;
    private String time;
    private Date dateObject;
    private boolean isDateInLong;
    private long dateTimeInLong;
    private boolean isCurrentDate;
    private boolean isCustomDateObject;


    public Date getDateObject() {
        return dateObject;
    }

    public DateTimeObject setDateObject(Date dateObject) {
        this.dateObject = dateObject;
        return this;
    }

    public boolean isCustomDateObject() {
        return isCustomDateObject;
    }

    public DateTimeObject setCustomDateObject(boolean customDateObject) {
        isCustomDateObject = customDateObject;
        return this;
    }

    public Constant.DATETIME getDatetimeType() {
        return datetimeType;
    }

    public DateTimeObject setDatetimeType(Constant.DATETIME datetimeType) {
        this.datetimeType = datetimeType;
        return this;
    }

    public String getDatetime() {
        return datetime;
    }

    public DateTimeObject setDatetime(String datetime) {
        this.datetime = datetime;
        return this;
    }

    public String getDate() {
        return date;
    }

    public DateTimeObject setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public DateTimeObject setTime(String time) {
        this.time = time;
        return this;
    }

    public long getDateTimeInLong() {
        return dateTimeInLong;
    }

    public DateTimeObject setDateTimeInLong(long dateTimeInLong) {
        this.dateTimeInLong = dateTimeInLong;
        return this;
    }

    public boolean isDateInLong() {
        return isDateInLong;
    }

    public DateTimeObject setDateInLong(boolean dateInLong) {
        isDateInLong = dateInLong;
        return this;
    }

    public boolean isCurrentDate() {
        return isCurrentDate;
    }

    public DateTimeObject setCurrentDate(boolean currentDate) {
        isCurrentDate = currentDate;
        return this;
    }
}
