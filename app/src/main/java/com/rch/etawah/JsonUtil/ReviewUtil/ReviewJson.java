
package com.rch.etawah.JsonUtil.ReviewUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = null;
    public final static Parcelable.Creator<ReviewJson> CREATOR = new Creator<ReviewJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ReviewJson createFromParcel(Parcel in) {
            return new ReviewJson(in);
        }

        public ReviewJson[] newArray(int size) {
            return (new ReviewJson[size]);
        }

    }
    ;

    protected ReviewJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.reviews, (com.rch.etawah.JsonUtil.ReviewUtil.Review.class.getClassLoader()));
    }

    public ReviewJson() {
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeList(reviews);
    }

    public int describeContents() {
        return  0;
    }

}
