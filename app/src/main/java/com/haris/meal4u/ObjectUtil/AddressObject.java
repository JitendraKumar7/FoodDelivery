package com.haris.meal4u.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressObject implements Parcelable {

    private String buildingName;
    private String streetName;
    private String areaName;
    private String floorName;
    private String noteRider;
    private String longitude;
    private String latitude;

    public String getLatitude() {
        return latitude;
    }

    public AddressObject setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public AddressObject setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public AddressObject setBuildingName(String buildingName) {
        this.buildingName = buildingName;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public AddressObject setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public String getAreaName() {
        return areaName;
    }

    public AddressObject setAreaName(String areaName) {
        this.areaName = areaName;
        return this;
    }

    public String getFloorName() {
        return floorName;
    }

    public AddressObject setFloorName(String floorName) {
        this.floorName = floorName;
        return this;
    }

    public String getNoteRider() {
        return noteRider;
    }

    public AddressObject setNoteRider(String noteRider) {
        this.noteRider = noteRider;
        return this;
    }


    @Override
    public String toString() {
        return "AddressObject{" +
                "buildingName='" + buildingName + '\'' +
                ", streetName='" + streetName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", floorName='" + floorName + '\'' +
                ", noteRider='" + noteRider + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }

    public AddressObject() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.buildingName);
        dest.writeString(this.streetName);
        dest.writeString(this.areaName);
        dest.writeString(this.floorName);
        dest.writeString(this.noteRider);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
    }

    protected AddressObject(Parcel in) {
        this.buildingName = in.readString();
        this.streetName = in.readString();
        this.areaName = in.readString();
        this.floorName = in.readString();
        this.noteRider = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
    }

    public static final Creator<AddressObject> CREATOR = new Creator<AddressObject>() {
        @Override
        public AddressObject createFromParcel(Parcel source) {
            return new AddressObject(source);
        }

        @Override
        public AddressObject[] newArray(int size) {
            return new AddressObject[size];
        }
    };
}
