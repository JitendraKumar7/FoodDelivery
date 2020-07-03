
package com.haris.meal4u.JsonUtil.CouponUtil;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("coupon_id")
    @Expose
    private String couponId;
    @SerializedName("coupon_reward")
    @Expose
    private String couponReward;
    public final static Parcelable.Creator<CouponJson> CREATOR = new Creator<CouponJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CouponJson createFromParcel(Parcel in) {
            return new CouponJson(in);
        }

        public CouponJson[] newArray(int size) {
            return (new CouponJson[size]);
        }

    }
    ;

    protected CouponJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.couponId = ((String) in.readValue((String.class.getClassLoader())));
        this.couponReward = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CouponJson() {
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

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponReward() {
        return couponReward;
    }

    public void setCouponReward(String couponReward) {
        this.couponReward = couponReward;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeValue(couponId);
        dest.writeValue(couponReward);
    }

    public int describeContents() {
        return  0;
    }

}
