package com.haris.meal4u.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class ChattingObject implements Parcelable {
    public int index;
    public String message;
    public String time;
    public String date;
    public boolean from;
    public boolean read;

    public ChattingObject() {
    }

    public ChattingObject(String message, String time, String date, boolean from) {
        this.message = message;
        this.time = time;
        this.date = date;
        this.from = from;
    }

    public ChattingObject(String message, String time, String date, boolean from, boolean read) {
        this.message = message;
        this.time = time;
        this.date = date;
        this.from = from;
        this.read = read;
    }

    public ChattingObject(int index, String message, String time, String date, boolean from, boolean read) {
        this.index = index;
        this.message = message;
        this.time = time;
        this.date = date;
        this.from = from;
        this.read = read;
    }

    public int getIndex() {
        return index;
    }

    public ChattingObject setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ChattingObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getTime() {
        return time;
    }

    public ChattingObject setTime(String time) {
        this.time = time;
        return this;
    }

    public String getDate() {
        return date;
    }

    public ChattingObject setDate(String date) {
        this.date = date;
        return this;
    }

    public boolean from() {
        return from;
    }

    public ChattingObject setFrom(boolean from) {
        this.from = from;
        return this;
    }


    public boolean isRead() {
        return read;
    }

    public ChattingObject setRead(boolean read) {
        this.read = read;
        return this;
    }

    @Override
    public String toString() {
        return "ChattingObject{" +
                "message='" + message + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", from=" + from +
                ", read=" + read +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeString(this.time);
        dest.writeString(this.date);
        dest.writeByte(this.from ? (byte) 1 : (byte) 0);
        dest.writeByte(this.read ? (byte) 1 : (byte) 0);
    }

    protected ChattingObject(Parcel in) {
        this.message = in.readString();
        this.time = in.readString();
        this.date = in.readString();
        this.from = in.readByte() != 0;
        this.read = in.readByte() != 0;
    }

    public static final Creator<ChattingObject> CREATOR = new Creator<ChattingObject>() {
        @Override
        public ChattingObject createFromParcel(Parcel source) {
            return new ChattingObject(source);
        }

        @Override
        public ChattingObject[] newArray(int size) {
            return new ChattingObject[size];
        }
    };
}
