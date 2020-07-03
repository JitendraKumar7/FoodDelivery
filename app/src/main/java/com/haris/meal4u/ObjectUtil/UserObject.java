package com.haris.meal4u.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class UserObject implements Parcelable {
    public int user_id;
    public String user_name;
    public String user_picture;
    public double user_latitude;
    public double user_longitude;



    public UserObject() {
    }

    public UserObject(int user_id, String user_name, String user_picture) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_picture = user_picture;
    }

    public UserObject(int user_id, String user_name, String user_picture, double user_latitude, double user_longitude) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_picture = user_picture;
        this.user_latitude = user_latitude;
        this.user_longitude = user_longitude;
    }

    public int getUser_id() {
        return user_id;
    }

    public UserObject setUser_id(int user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getUser_name() {
        return user_name;
    }

    public UserObject setUser_name(String user_name) {
        this.user_name = user_name;
        return this;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public UserObject setUser_picture(String user_picture) {
        this.user_picture = user_picture;
        return this;
    }

    public double getUser_latitude() {
        return user_latitude;
    }

    public UserObject setUser_latitude(double user_latitude) {
        this.user_latitude = user_latitude;
        return this;
    }

    public double getUser_longitude() {
        return user_longitude;
    }

    public UserObject setUser_longitude(double user_longitude) {
        this.user_longitude = user_longitude;
        return this;
    }

    @Override
    public String toString() {
        return "UserObject{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_picture='" + user_picture + '\'' +
                ", user_latitude=" + user_latitude +
                ", user_longitude=" + user_longitude +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.user_id);
        dest.writeString(this.user_name);
        dest.writeString(this.user_picture);
        dest.writeDouble(this.user_latitude);
        dest.writeDouble(this.user_longitude);
    }

    protected UserObject(Parcel in) {
        this.user_id = in.readInt();
        this.user_name = in.readString();
        this.user_picture = in.readString();
        this.user_latitude = in.readDouble();
        this.user_longitude = in.readDouble();
    }

    public static final Creator<UserObject> CREATOR = new Creator<UserObject>() {
        @Override
        public UserObject createFromParcel(Parcel source) {
            return new UserObject(source);
        }

        @Override
        public UserObject[] newArray(int size) {
            return new UserObject[size];
        }
    };
}
