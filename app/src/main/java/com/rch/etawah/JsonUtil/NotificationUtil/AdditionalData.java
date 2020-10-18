
package com.rch.etawah.JsonUtil.NotificationUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdditionalData implements Parcelable
{

    @SerializedName("post_id")
    @Expose
    private String postId;
    public final static Creator<AdditionalData> CREATOR = new Creator<AdditionalData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AdditionalData createFromParcel(Parcel in) {
            return new AdditionalData(in);
        }

        public AdditionalData[] newArray(int size) {
            return (new AdditionalData[size]);
        }

    }
    ;

    protected AdditionalData(Parcel in) {
        this.postId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AdditionalData() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(postId);
    }

    public int describeContents() {
        return  0;
    }

}
