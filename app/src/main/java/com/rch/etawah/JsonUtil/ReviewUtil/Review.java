
package com.rch.etawah.JsonUtil.ReviewUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_picture")
    @Expose
    private String userPicture;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("review_pictures")
    @Expose
    private List<String> reviewPictures = null;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    public final static Parcelable.Creator<Review> CREATOR = new Creator<Review>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return (new Review[size]);
        }

    }
    ;

    protected Review(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.userPicture = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((String) in.readValue((String.class.getClassLoader())));
        this.review = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.reviewPictures, (java.lang.String.class.getClassLoader()));
        this.dateCreated = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Review() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public List<String> getReviewPictures() {
        return reviewPictures;
    }

    public void setReviewPictures(List<String> reviewPictures) {
        this.reviewPictures = reviewPictures;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userName);
        dest.writeValue(userPicture);
        dest.writeValue(rating);
        dest.writeValue(review);
        dest.writeList(reviewPictures);
        dest.writeValue(dateCreated);
    }

    public int describeContents() {
        return  0;
    }

}
