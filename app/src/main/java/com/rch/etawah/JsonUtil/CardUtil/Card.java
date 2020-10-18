
package com.rch.etawah.JsonUtil.CardUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("card_name")
    @Expose
    private String cardName;
    @SerializedName("card_no")
    @Expose
    private String cardNo;
    @SerializedName("stripe_customer_id")
    @Expose
    private String stripeCustomerId;

    public Card() {
    }

    public String getId() {
        return id;
    }

    public Card setId(String id) {
        this.id = id;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.companyName);
        dest.writeString(this.cardName);
        dest.writeString(this.cardNo);
        dest.writeString(this.stripeCustomerId);
    }

    protected Card(Parcel in) {
        this.id = in.readString();
        this.companyName = in.readString();
        this.cardName = in.readString();
        this.cardNo = in.readString();
        this.stripeCustomerId = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
