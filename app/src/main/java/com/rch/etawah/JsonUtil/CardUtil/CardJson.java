
package com.rch.etawah.JsonUtil.CardUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cards")
    @Expose
    private List<Card> cards = null;
    public final static Parcelable.Creator<CardJson> CREATOR = new Creator<CardJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CardJson createFromParcel(Parcel in) {
            return new CardJson(in);
        }

        public CardJson[] newArray(int size) {
            return (new CardJson[size]);
        }

    }
    ;

    protected CardJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.cards, (com.rch.etawah.JsonUtil.CardUtil.Card.class.getClassLoader()));
    }

    public CardJson() {
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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeList(cards);
    }

    public int describeContents() {
        return  0;
    }

}
