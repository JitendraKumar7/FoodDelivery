package com.haris.meal4u.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class ScheduleObject implements Parcelable {
    private String schedule;
    private boolean isNow;

    public boolean isNow() {
        return isNow;
    }

    public ScheduleObject setNow(boolean now) {
        isNow = now;
        return this;
    }

    public String getSchedule() {
        return schedule;
    }

    public ScheduleObject setSchedule(String schedule) {
        this.schedule = schedule;
        return this;
    }

    @Override
    public String toString() {
        return "ScheduleObject{" +
                "schedule='" + schedule + '\'' +
                ", isNow=" + isNow +
                '}';
    }

    public ScheduleObject() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.schedule);
        dest.writeByte(this.isNow ? (byte) 1 : (byte) 0);
    }

    protected ScheduleObject(Parcel in) {
        this.schedule = in.readString();
        this.isNow = in.readByte() != 0;
    }

    public static final Creator<ScheduleObject> CREATOR = new Creator<ScheduleObject>() {
        @Override
        public ScheduleObject createFromParcel(Parcel source) {
            return new ScheduleObject(source);
        }

        @Override
        public ScheduleObject[] newArray(int size) {
            return new ScheduleObject[size];
        }
    };
}
