package com.haris.meal4u.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class TrackingObject implements Parcelable {
    public double latitude;
    public double longitude;
    public String remaining_duration;
    public String remaining_distance;


    public TrackingObject() {
    }

    public TrackingObject(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public TrackingObject(double latitude, double longitude, String remaining_duration, String remaining_distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.remaining_duration = remaining_duration;
        this.remaining_distance = remaining_distance;
    }


    public double getLatitude() {
        return latitude;
    }

    public TrackingObject setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public TrackingObject setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getRemaining_duration() {
        return remaining_duration;
    }

    public TrackingObject setRemaining_duration(String remaining_duration) {
        this.remaining_duration = remaining_duration;
        return this;
    }

    public String getRemaining_distance() {
        return remaining_distance;
    }

    public TrackingObject setRemaining_distance(String remaining_distance) {
        this.remaining_distance = remaining_distance;
        return this;
    }



    @Override
    public String toString() {
        return "TrackingObject{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", remaining_duration='" + remaining_duration + '\'' +
                ", remaining_distance='" + remaining_distance + '\'' +

                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.remaining_duration);
        dest.writeString(this.remaining_distance);

    }

    protected TrackingObject(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.remaining_duration = in.readString();
        this.remaining_distance = in.readString();

    }

    public static final Creator<TrackingObject> CREATOR = new Creator<TrackingObject>() {
        @Override
        public TrackingObject createFromParcel(Parcel source) {
            return new TrackingObject(source);
        }

        @Override
        public TrackingObject[] newArray(int size) {
            return new TrackingObject[size];
        }
    };
}
