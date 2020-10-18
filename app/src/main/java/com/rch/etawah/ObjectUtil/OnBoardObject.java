package com.rch.etawah.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class OnBoardObject implements Parcelable {
    private int icon;
    private String title;
    private String tagline;


    public OnBoardObject(int icon, String title, String tagline) {
        this.icon = icon;
        this.title = title;
        this.tagline = tagline;
    }


    public int getIcon() {
        return icon;
    }

    public OnBoardObject setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public OnBoardObject setTitle(String title) {
        this.title = title;
        return this;
    }


    public String getTagline() {
        return tagline;
    }

    public OnBoardObject setTagline(String tagline) {
        this.tagline = tagline;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.icon);
        dest.writeString(this.title);
        dest.writeString(this.tagline);
    }

    protected OnBoardObject(Parcel in) {
        this.icon = in.readInt();
        this.title = in.readString();
        this.tagline = in.readString();
    }

    public static final Creator<OnBoardObject> CREATOR = new Creator<OnBoardObject>() {
        @Override
        public OnBoardObject createFromParcel(Parcel source) {
            return new OnBoardObject(source);
        }

        @Override
        public OnBoardObject[] newArray(int size) {
            return new OnBoardObject[size];
        }
    };
}
