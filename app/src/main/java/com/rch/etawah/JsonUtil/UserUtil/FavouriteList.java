package com.rch.etawah.JsonUtil.UserUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouriteList implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    public final static Parcelable.Creator<FavouriteList> CREATOR = new Creator<FavouriteList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FavouriteList createFromParcel(Parcel in) {
            return new FavouriteList(in);
        }

        public FavouriteList[] newArray(int size) {
            return (new FavouriteList[size]);
        }

    }
            ;

    protected FavouriteList(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.restaurantId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public FavouriteList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(restaurantId);
    }

    public int describeContents() {
        return 0;
    }

}