
package com.haris.meal4u.JsonUtil.OrderHistoryUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderList implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurant_id;
    @SerializedName("restaurant_logo")
    @Expose
    private String restaurantLogo;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("order_price")
    @Expose
    private String orderPrice;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("delivery_fee")
    @Expose
    private String delivery_fee;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("product_order_detail")
    @Expose
    private List<ProductOrderDetail> productOrderDetail = null;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("review_status")
    @Expose
    private String reviewStatus;
    @SerializedName("rider_review_status")
    @Expose
    private String riderReviewStatus;

    public OrderList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getRestaurant_id() {
        return restaurant_id;
    }

    public OrderList setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
        return this;
    }

    public String getRestaurantLogo() {
        return restaurantLogo;
    }

    public void setRestaurantLogo(String restaurantLogo) {
        this.restaurantLogo = restaurantLogo;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDelivery_fee() {
        return delivery_fee;
    }

    public OrderList setDelivery_fee(String delivery_fee) {
        this.delivery_fee = delivery_fee;
        return this;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public List<ProductOrderDetail> getProductOrderDetail() {
        return productOrderDetail;
    }

    public void setProductOrderDetail(List<ProductOrderDetail> productOrderDetail) {
        this.productOrderDetail = productOrderDetail;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getRiderReviewStatus() {
        return riderReviewStatus;
    }

    public void setRiderReviewStatus(String riderReviewStatus) {
        this.riderReviewStatus = riderReviewStatus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.restaurant_id);
        dest.writeString(this.restaurantLogo);
        dest.writeString(this.restaurantName);
        dest.writeString(this.deliveryDate);
        dest.writeString(this.orderPrice);
        dest.writeString(this.deliveryTime);
        dest.writeString(this.delivery_fee);
        dest.writeString(this.paymentType);
        dest.writeTypedList(this.productOrderDetail);
        dest.writeString(this.orderStatus);
        dest.writeString(this.reviewStatus);
        dest.writeString(this.riderReviewStatus);
    }

    protected OrderList(Parcel in) {
        this.id = in.readString();
        this.restaurant_id = in.readString();
        this.restaurantLogo = in.readString();
        this.restaurantName = in.readString();
        this.deliveryDate = in.readString();
        this.orderPrice = in.readString();
        this.deliveryTime = in.readString();
        this.delivery_fee = in.readString();
        this.paymentType = in.readString();
        this.productOrderDetail = in.createTypedArrayList(ProductOrderDetail.CREATOR);
        this.orderStatus = in.readString();
        this.reviewStatus = in.readString();
        this.riderReviewStatus = in.readString();
    }

    public static final Creator<OrderList> CREATOR = new Creator<OrderList>() {
        @Override
        public OrderList createFromParcel(Parcel source) {
            return new OrderList(source);
        }

        @Override
        public OrderList[] newArray(int size) {
            return new OrderList[size];
        }
    };
}
