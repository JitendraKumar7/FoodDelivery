
package com.haris.meal4u.JsonUtil.NotificationUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification implements Parcelable
{

    @SerializedName("isAppInFocus")
    @Expose
    private Boolean isAppInFocus;
    @SerializedName("shown")
    @Expose
    private Boolean shown;
    @SerializedName("androidNotificationId")
    @Expose
    private Integer androidNotificationId;
    @SerializedName("displayType")
    @Expose
    private Integer displayType;
    @SerializedName("payload")
    @Expose
    private Payload payload;
    public final static Creator<Notification> CREATOR = new Creator<Notification>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        public Notification[] newArray(int size) {
            return (new Notification[size]);
        }

    }
    ;

    protected Notification(Parcel in) {
        this.isAppInFocus = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.shown = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.androidNotificationId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.displayType = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.payload = ((Payload) in.readValue((Payload.class.getClassLoader())));
    }

    public Notification() {
    }

    public Boolean getIsAppInFocus() {
        return isAppInFocus;
    }

    public void setIsAppInFocus(Boolean isAppInFocus) {
        this.isAppInFocus = isAppInFocus;
    }

    public Boolean getShown() {
        return shown;
    }

    public void setShown(Boolean shown) {
        this.shown = shown;
    }

    public Integer getAndroidNotificationId() {
        return androidNotificationId;
    }

    public void setAndroidNotificationId(Integer androidNotificationId) {
        this.androidNotificationId = androidNotificationId;
    }

    public Integer getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(isAppInFocus);
        dest.writeValue(shown);
        dest.writeValue(androidNotificationId);
        dest.writeValue(displayType);
        dest.writeValue(payload);
    }

    public int describeContents() {
        return  0;
    }

}
