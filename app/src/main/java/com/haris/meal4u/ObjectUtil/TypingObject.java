package com.haris.meal4u.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class TypingObject implements Parcelable {
    public boolean from;
    public boolean to;


    public boolean isFrom() {
        return from;
    }

    public TypingObject setFrom(boolean from) {
        this.from = from;
        return this;
    }

    public boolean isTo() {
        return to;
    }

    public TypingObject setTo(boolean to) {
        this.to = to;
        return this;
    }

    @Override
    public String toString() {
        return "TypingObject{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.from ? (byte) 1 : (byte) 0);
        dest.writeByte(this.to ? (byte) 1 : (byte) 0);
    }

    public TypingObject() {
    }

    protected TypingObject(Parcel in) {
        this.from = in.readByte() != 0;
        this.to = in.readByte() != 0;
    }

    public static final Creator<TypingObject> CREATOR = new Creator<TypingObject>() {
        @Override
        public TypingObject createFromParcel(Parcel source) {
            return new TypingObject(source);
        }

        @Override
        public TypingObject[] newArray(int size) {
            return new TypingObject[size];
        }
    };
}
