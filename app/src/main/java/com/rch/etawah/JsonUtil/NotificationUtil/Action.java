package com.rch.etawah.JsonUtil.NotificationUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Action implements Parcelable
{

    @SerializedName("type")
    @Expose
    private Integer type;
    public final static Creator<Action> CREATOR = new Creator<Action>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Action createFromParcel(Parcel in) {
            return new Action(in);
        }

        public Action[] newArray(int size) {
            return (new Action[size]);
        }

    }
    ;

    protected Action(Parcel in) {
        this.type = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Action() {
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
    }

    public int describeContents() {
        return  0;
    }

}
