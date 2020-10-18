package com.rch.etawah.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class BillingObject implements Parcelable {
    private String paymentMethod;
    private String stripeCustomerToken;


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public BillingObject setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public String getStripeCustomerToken() {
        return stripeCustomerToken;
    }

    public BillingObject setStripeCustomerToken(String stripeCustomerToken) {
        this.stripeCustomerToken = stripeCustomerToken;
        return this;
    }

    @Override
    public String toString() {
        return "BillingObject{" +
                "paymentMethod='" + paymentMethod + '\'' +
                ", stripeCustomerToken='" + stripeCustomerToken + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paymentMethod);
        dest.writeString(this.stripeCustomerToken);
    }

    public BillingObject() {
    }

    protected BillingObject(Parcel in) {
        this.paymentMethod = in.readString();
        this.stripeCustomerToken = in.readString();
    }

    public static final Parcelable.Creator<BillingObject> CREATOR = new Parcelable.Creator<BillingObject>() {
        @Override
        public BillingObject createFromParcel(Parcel source) {
            return new BillingObject(source);
        }

        @Override
        public BillingObject[] newArray(int size) {
            return new BillingObject[size];
        }
    };
}
