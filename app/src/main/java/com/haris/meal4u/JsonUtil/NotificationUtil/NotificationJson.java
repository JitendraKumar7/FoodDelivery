
package com.haris.meal4u.JsonUtil.NotificationUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationJson implements Parcelable
{

    @SerializedName("action")
    @Expose
    private Action action;
    @SerializedName("notification")
    @Expose
    private Notification notification;
    public final static Creator<NotificationJson> CREATOR = new Creator<NotificationJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NotificationJson createFromParcel(Parcel in) {
            return new NotificationJson(in);
        }

        public NotificationJson[] newArray(int size) {
            return (new NotificationJson[size]);
        }

    }
    ;

    protected NotificationJson(Parcel in) {
        this.action = ((Action) in.readValue((Action.class.getClassLoader())));
        this.notification = ((Notification) in.readValue((Notification.class.getClassLoader())));
    }

    public NotificationJson() {
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(action);
        dest.writeValue(notification);
    }

    public int describeContents() {
        return  0;
    }

}
