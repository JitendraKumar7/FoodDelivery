
package com.rch.etawah.JsonUtil.AppOfferUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppOfferJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;

    public AppOfferJson() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public AppOfferJson setCategories(List<Category> categories) {
        this.categories = categories;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeTypedList(this.offers);
        dest.writeTypedList(this.categories);
    }

    protected AppOfferJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.offers = in.createTypedArrayList(Offer.CREATOR);
        this.categories = in.createTypedArrayList(Category.CREATOR);
    }

    public static final Creator<AppOfferJson> CREATOR = new Creator<AppOfferJson>() {
        @Override
        public AppOfferJson createFromParcel(Parcel source) {
            return new AppOfferJson(source);
        }

        @Override
        public AppOfferJson[] newArray(int size) {
            return new AppOfferJson[size];
        }
    };
}
