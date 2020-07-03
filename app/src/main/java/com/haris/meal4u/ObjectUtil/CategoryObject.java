package com.haris.meal4u.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryObject implements Parcelable {
    private String id;
    private String title;
    private String picture;
    private int drawable;
    private boolean isRound = true;
    private boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public CategoryObject setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public String getId() {
        return id;
    }

    public CategoryObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CategoryObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getDrawable() {
        return drawable;
    }

    public CategoryObject setDrawable(int drawable) {
        this.drawable = drawable;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public CategoryObject setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public boolean isRound() {
        return isRound;
    }

    public CategoryObject setRound(boolean round) {
        isRound = round;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.picture);
        dest.writeInt(this.drawable);
        dest.writeByte(this.isRound ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    public CategoryObject() {
    }

    @Override
    public String toString() {
        return "CategoryObject{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", picture='" + picture + '\'' +
                ", drawable=" + drawable +
                ", isRound=" + isRound +
                ", isSelected=" + isSelected +
                '}';
    }

    protected CategoryObject(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.picture = in.readString();
        this.drawable = in.readInt();
        this.isRound = in.readByte() != 0;
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CategoryObject> CREATOR = new Parcelable.Creator<CategoryObject>() {
        @Override
        public CategoryObject createFromParcel(Parcel source) {
            return new CategoryObject(source);
        }

        @Override
        public CategoryObject[] newArray(int size) {
            return new CategoryObject[size];
        }
    };
}
