
package com.rch.etawah.JsonUtil.NotificationUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload implements Parcelable
{

    @SerializedName("notificationID")
    @Expose
    private String notificationID;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("additionalData")
    @Expose
    private AdditionalData additionalData;
    @SerializedName("lockScreenVisibility")
    @Expose
    private Integer lockScreenVisibility;
    @SerializedName("groupMessage")
    @Expose
    private String groupMessage;
    @SerializedName("fromProjectNumber")
    @Expose
    private String fromProjectNumber;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("rawPayload")
    @Expose
    private String rawPayload;
    public final static Creator<Payload> CREATOR = new Creator<Payload>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Payload createFromParcel(Parcel in) {
            return new Payload(in);
        }

        public Payload[] newArray(int size) {
            return (new Payload[size]);
        }

    }
    ;

    protected Payload(Parcel in) {
        this.notificationID = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.body = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalData = ((AdditionalData) in.readValue((AdditionalData.class.getClassLoader())));
        this.lockScreenVisibility = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.groupMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.fromProjectNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.priority = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.rawPayload = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Payload() {
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public AdditionalData getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(AdditionalData additionalData) {
        this.additionalData = additionalData;
    }

    public Integer getLockScreenVisibility() {
        return lockScreenVisibility;
    }

    public void setLockScreenVisibility(Integer lockScreenVisibility) {
        this.lockScreenVisibility = lockScreenVisibility;
    }

    public String getGroupMessage() {
        return groupMessage;
    }

    public void setGroupMessage(String groupMessage) {
        this.groupMessage = groupMessage;
    }

    public String getFromProjectNumber() {
        return fromProjectNumber;
    }

    public void setFromProjectNumber(String fromProjectNumber) {
        this.fromProjectNumber = fromProjectNumber;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getRawPayload() {
        return rawPayload;
    }

    public void setRawPayload(String rawPayload) {
        this.rawPayload = rawPayload;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(notificationID);
        dest.writeValue(title);
        dest.writeValue(body);
        dest.writeValue(additionalData);
        dest.writeValue(lockScreenVisibility);
        dest.writeValue(groupMessage);
        dest.writeValue(fromProjectNumber);
        dest.writeValue(priority);
        dest.writeValue(rawPayload);
    }

    public int describeContents() {
        return  0;
    }

}
