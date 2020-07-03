package com.haris.meal4u.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FilterObject implements Parcelable {
    private String expense;
    private String rating;
    private String cusines;
    private String radius;
    private boolean isFilterApplied = false;
    private ArrayList<DataObject> dataObjects = new ArrayList<>();

    public String getRadius() {
        return radius;
    }

    public FilterObject setRadius(String radius) {
        this.radius = radius;
        return this;
    }

    public String getExpense() {
        return expense;
    }

    public FilterObject setExpense(String expense) {
        this.expense = expense;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public FilterObject setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public ArrayList<DataObject> getDataObjects() {
        return dataObjects;
    }

    public FilterObject setDataObjects(ArrayList<DataObject> dataObjects) {
        this.dataObjects = dataObjects;
        return this;
    }

    public String getCusines() {
        return cusines;
    }

    public FilterObject setCusines(String cusines) {
        this.cusines = cusines;
        return this;
    }

    public boolean isFilterApplied() {
        return isFilterApplied;
    }

    public FilterObject setFilterApplied(boolean filterApplied) {
        isFilterApplied = filterApplied;
        return this;
    }

    @Override
    public String toString() {
        return "FilterObject{" +
                "expense='" + expense + '\'' +
                ", rating='" + rating + '\'' +
                ", cusines='" + cusines + '\'' +
                ", radius='" + radius + '\'' +
                ", isFilterApplied=" + isFilterApplied +
                ", dataObjects=" + dataObjects +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.expense);
        dest.writeString(this.rating);
        dest.writeString(this.cusines);
        dest.writeString(this.radius);
        dest.writeByte(this.isFilterApplied ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.dataObjects);
    }

    public FilterObject() {
    }

    protected FilterObject(Parcel in) {
        this.expense = in.readString();
        this.rating = in.readString();
        this.cusines = in.readString();
        this.radius = in.readString();
        this.isFilterApplied = in.readByte() != 0;
        this.dataObjects = in.createTypedArrayList(DataObject.CREATOR);
    }

    public static final Parcelable.Creator<FilterObject> CREATOR = new Parcelable.Creator<FilterObject>() {
        @Override
        public FilterObject createFromParcel(Parcel source) {
            return new FilterObject(source);
        }

        @Override
        public FilterObject[] newArray(int size) {
            return new FilterObject[size];
        }
    };
}
