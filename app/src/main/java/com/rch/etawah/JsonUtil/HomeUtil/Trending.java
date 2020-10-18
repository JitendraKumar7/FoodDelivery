package com.rch.etawah.JsonUtil.HomeUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trending implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("cover_url")
    @Expose
    private String coverUrl;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("delivery_charges")
    @Expose
    private String delivery_charges;
    @SerializedName("min_order")
    @Expose
    private String minOrder;
    @SerializedName("expense")
    @Expose
    private String expense;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("no_of_ratings")
    @Expose
    private String no_of_ratings;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_symbol")
    @Expose
    private String currency_symbol;
    @SerializedName("restaurant_status")
    @Expose
    private RestaurantStatus restaurant_status;
    @SerializedName("sales")
    @Expose
    private String sales;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("reviewers")
    @Expose
    private List<String> reviewers = null;
    @SerializedName("payment_method")
    @Expose
    private List<String> payment_method = null;

    public Trending() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDelivery_charges() {
        return delivery_charges;
    }

    public Trending setDelivery_charges(String delivery_charges) {
        this.delivery_charges = delivery_charges;
        return this;
    }

    public String getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(String minOrder) {
        this.minOrder = minOrder;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNo_of_ratings() {
        return no_of_ratings;
    }

    public Trending setNo_of_ratings(String no_of_ratings) {
        this.no_of_ratings = no_of_ratings;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Trending setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public Trending setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
        return this;
    }

    public RestaurantStatus getRestaurant_status() {
        return restaurant_status;
    }

    public Trending setRestaurant_status(RestaurantStatus restaurant_status) {
        this.restaurant_status = restaurant_status;
        return this;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getReviewers() {
        return reviewers;
    }

    public Trending setReviewers(List<String> reviewers) {
        this.reviewers = reviewers;
        return this;
    }

    public List<String> getPayment_method() {
        return payment_method;
    }

    public Trending setPayment_method(List<String> payment_method) {
        this.payment_method = payment_method;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.category);
        dest.writeString(this.description);
        dest.writeString(this.location);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.logo);
        dest.writeString(this.coverUrl);
        dest.writeString(this.deliveryTime);
        dest.writeString(this.delivery_charges);
        dest.writeString(this.minOrder);
        dest.writeString(this.expense);
        dest.writeString(this.rating);
        dest.writeString(this.no_of_ratings);
        dest.writeString(this.currency);
        dest.writeString(this.currency_symbol);
        dest.writeParcelable(this.restaurant_status, flags);
        dest.writeString(this.sales);
        dest.writeString(this.distance);
        dest.writeString(this.tags);
        dest.writeString(this.date);
        dest.writeStringList(this.reviewers);
        dest.writeStringList(this.payment_method);
    }

    protected Trending(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.category = in.readString();
        this.description = in.readString();
        this.location = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.logo = in.readString();
        this.coverUrl = in.readString();
        this.deliveryTime = in.readString();
        this.delivery_charges = in.readString();
        this.minOrder = in.readString();
        this.expense = in.readString();
        this.rating = in.readString();
        this.no_of_ratings = in.readString();
        this.currency = in.readString();
        this.currency_symbol = in.readString();
        this.restaurant_status = in.readParcelable(RestaurantStatus.class.getClassLoader());
        this.sales = in.readString();
        this.distance = in.readString();
        this.tags = in.readString();
        this.date = in.readString();
        this.reviewers = in.createStringArrayList();
        this.payment_method = in.createStringArrayList();
    }

    public static final Creator<Trending> CREATOR = new Creator<Trending>() {
        @Override
        public Trending createFromParcel(Parcel source) {
            return new Trending(source);
        }

        @Override
        public Trending[] newArray(int size) {
            return new Trending[size];
        }
    };
}