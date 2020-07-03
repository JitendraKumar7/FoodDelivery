package com.haris.meal4u.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class RiderTrackingObject implements Parcelable {
    public UserObject userObject;
    public RiderObject riderObject;
    public TrackingObject trackingObject;
    public UserChattingObject userChattingObject;
    public TypingObject typingObject;


    public RiderTrackingObject() {
    }


    public RiderTrackingObject(UserObject userObject, RiderObject riderObject, TrackingObject trackingObject, TypingObject typingObject) {
        this.userObject = userObject;
        this.riderObject = riderObject;
        this.trackingObject = trackingObject;
        this.typingObject = typingObject;
    }

    public RiderTrackingObject(UserObject userObject, RiderObject riderObject, TrackingObject trackingObject, UserChattingObject userChattingObject, TypingObject typingObject) {
        this.userObject = userObject;
        this.riderObject = riderObject;
        this.trackingObject = trackingObject;
        this.userChattingObject = userChattingObject;
        this.typingObject = typingObject;
    }

    public UserObject getUserObject() {
        return userObject;
    }

    public RiderTrackingObject setUserObject(UserObject userObject) {
        this.userObject = userObject;
        return this;
    }

    public RiderObject getRiderObject() {
        return riderObject;
    }

    public RiderTrackingObject setRiderObject(RiderObject riderObject) {
        this.riderObject = riderObject;
        return this;
    }

    public TrackingObject getTrackingObject() {
        return trackingObject;
    }

    public RiderTrackingObject setTrackingObject(TrackingObject trackingObject) {
        this.trackingObject = trackingObject;
        return this;
    }


    public UserChattingObject getUserChattingObject() {
        return userChattingObject;
    }

    public RiderTrackingObject setUserChattingObject(UserChattingObject userChattingObject) {
        this.userChattingObject = userChattingObject;
        return this;
    }

    public TypingObject getTypingObject() {
        return typingObject;
    }

    public RiderTrackingObject setTypingObject(TypingObject typingObject) {
        this.typingObject = typingObject;
        return this;
    }


    @Override
    public String toString() {
        return "RiderTrackingObject{" +
                "userObject=" + userObject +
                ", riderObject=" + riderObject +
                ", trackingObject=" + trackingObject +
                ", userChattingObject=" + userChattingObject +
                ", typingObject=" + typingObject +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.userObject, flags);
        dest.writeParcelable(this.riderObject, flags);
        dest.writeParcelable(this.trackingObject, flags);
        dest.writeParcelable(this.userChattingObject, flags);
        dest.writeParcelable(this.typingObject, flags);
    }

    protected RiderTrackingObject(Parcel in) {
        this.userObject = in.readParcelable(UserObject.class.getClassLoader());
        this.riderObject = in.readParcelable(RiderObject.class.getClassLoader());
        this.trackingObject = in.readParcelable(TrackingObject.class.getClassLoader());
        this.userChattingObject = in.readParcelable(UserChattingObject.class.getClassLoader());
        this.typingObject = in.readParcelable(TypingObject.class.getClassLoader());
    }

    public static final Creator<RiderTrackingObject> CREATOR = new Creator<RiderTrackingObject>() {
        @Override
        public RiderTrackingObject createFromParcel(Parcel source) {
            return new RiderTrackingObject(source);
        }

        @Override
        public RiderTrackingObject[] newArray(int size) {
            return new RiderTrackingObject[size];
        }
    };
}
