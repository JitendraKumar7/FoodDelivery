
package com.rch.etawah.JsonUtil.OrderHistoryUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductOrderDetail implements Parcelable
{

    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_quantity")
    @Expose
    private String productQuantity;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    public final static Parcelable.Creator<ProductOrderDetail> CREATOR = new Creator<ProductOrderDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ProductOrderDetail createFromParcel(Parcel in) {
            return new ProductOrderDetail(in);
        }

        public ProductOrderDetail[] newArray(int size) {
            return (new ProductOrderDetail[size]);
        }

    }
    ;

    protected ProductOrderDetail(Parcel in) {
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.productQuantity = ((String) in.readValue((String.class.getClassLoader())));
        this.productPrice = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ProductOrderDetail() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(productName);
        dest.writeValue(productQuantity);
        dest.writeValue(productPrice);
    }

    public int describeContents() {
        return  0;
    }

}
