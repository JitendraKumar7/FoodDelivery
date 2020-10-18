
package com.rch.etawah.JsonUtil.DeliveryDetailUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryDetailJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("delivery_charges")
    @Expose
    private String deliveryCharges;
    public final static Parcelable.Creator<DeliveryDetailJson> CREATOR = new Creator<DeliveryDetailJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DeliveryDetailJson createFromParcel(Parcel in) {
            return new DeliveryDetailJson(in);
        }

        public DeliveryDetailJson[] newArray(int size) {
            return (new DeliveryDetailJson[size]);
        }

    }
    ;

    protected DeliveryDetailJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.deliveryCharges = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DeliveryDetailJson() {
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

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeValue(deliveryCharges);
    }

    public int describeContents() {
        return  0;
    }

}
