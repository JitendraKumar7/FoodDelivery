package com.rch.etawah.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class UserChattingObject implements Parcelable {
    public ArrayList<ChattingObject> chattingObjectArrayList = new ArrayList<>();

    public UserChattingObject() {
    }

    public UserChattingObject(ArrayList<ChattingObject> chattingObjectArrayList) {
        this.chattingObjectArrayList = chattingObjectArrayList;
    }


    public ArrayList<ChattingObject> getChattingObjectArrayList() {
        return chattingObjectArrayList;
    }

    public UserChattingObject setChattingObjectArrayList(ArrayList<ChattingObject> chattingObjectArrayList) {
        this.chattingObjectArrayList = chattingObjectArrayList;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.chattingObjectArrayList);
    }

    protected UserChattingObject(Parcel in) {
        this.chattingObjectArrayList = in.createTypedArrayList(ChattingObject.CREATOR);
    }

    public static final Parcelable.Creator<UserChattingObject> CREATOR = new Parcelable.Creator<UserChattingObject>() {
        @Override
        public UserChattingObject createFromParcel(Parcel source) {
            return new UserChattingObject(source);
        }

        @Override
        public UserChattingObject[] newArray(int size) {
            return new UserChattingObject[size];
        }
    };
}
