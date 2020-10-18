package com.rch.etawah.JsonUtil.HomeUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantStatus implements Parcelable
{

    @SerializedName("day")
    @Expose
    private List<String> day = null;
    @SerializedName("fromTime")
    @Expose
    private List<String> fromTime = null;
    @SerializedName("toTime")
    @Expose
    private List<String> toTime = null;
    public final static Parcelable.Creator<RestaurantStatus> CREATOR = new Creator<RestaurantStatus>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RestaurantStatus createFromParcel(Parcel in) {
            return new RestaurantStatus(in);
        }

        public RestaurantStatus[] newArray(int size) {
            return (new RestaurantStatus[size]);
        }

    }
            ;

    protected RestaurantStatus(Parcel in) {
        in.readList(this.day, (java.lang.String.class.getClassLoader()));
        in.readList(this.fromTime, (java.lang.String.class.getClassLoader()));
        in.readList(this.toTime, (java.lang.String.class.getClassLoader()));
    }

    public RestaurantStatus() {
    }

    public List<String> getDay() {
        return day;
    }

    public void setDay(List<String> day) {
        this.day = day;
    }

    public List<String> getFromTime() {
        return fromTime;
    }

    public void setFromTime(List<String> fromTime) {
        this.fromTime = fromTime;
    }

    public List<String> getToTime() {
        return toTime;
    }

    public void setToTime(List<String> toTime) {
        this.toTime = toTime;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(day);
        dest.writeList(fromTime);
        dest.writeList(toTime);
    }

    public int describeContents() {
        return 0;
    }

}