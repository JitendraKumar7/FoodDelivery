package com.rch.etawah.JsonUtil.OrderUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderJson implements Parcelable {

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("order_id")
    @Expose
    private String order_id;


    public String getCode() {
        return code;
    }

    public OrderJson setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public OrderJson setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getOrder_id() {
        return order_id;
    }

    public OrderJson setOrder_id(String order_id) {
        this.order_id = order_id;
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
        dest.writeString(this.order_id);
    }

    public OrderJson() {
    }

    protected OrderJson(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.order_id = in.readString();
    }

    public static final Parcelable.Creator<OrderJson> CREATOR = new Parcelable.Creator<OrderJson>() {
        @Override
        public OrderJson createFromParcel(Parcel source) {
            return new OrderJson(source);
        }

        @Override
        public OrderJson[] newArray(int size) {
            return new OrderJson[size];
        }
    };
}
