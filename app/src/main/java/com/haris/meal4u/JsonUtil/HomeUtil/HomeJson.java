package com.haris.meal4u.JsonUtil.HomeUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("featured")
    @Expose
    private List<Featured> featured = null;
    @SerializedName("trending")
    @Expose
    private List<Trending> trending = null;
    @SerializedName("nearest")
    @Expose
    private List<Nearest> nearest = null;
    public final static Parcelable.Creator<HomeJson> CREATOR = new Creator<HomeJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public HomeJson createFromParcel(Parcel in) {
            return new HomeJson(in);
        }

        public HomeJson[] newArray(int size) {
            return (new HomeJson[size]);
        }

    }
    ;

    protected HomeJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.featured, (com.haris.meal4u.JsonUtil.HomeUtil.Featured.class.getClassLoader()));
        in.readList(this.trending, (com.haris.meal4u.JsonUtil.HomeUtil.Trending.class.getClassLoader()));
        in.readList(this.nearest, (com.haris.meal4u.JsonUtil.HomeUtil.Nearest.class.getClassLoader()));
    }

    public HomeJson() {
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

    public List<Featured> getFeatured() {
        return featured;
    }

    public void setFeatured(List<Featured> featured) {
        this.featured = featured;
    }

    public List<Trending> getTrending() {
        return trending;
    }

    public void setTrending(List<Trending> trending) {
        this.trending = trending;
    }

    public List<Nearest> getNearest() {
        return nearest;
    }

    public void setNearest(List<Nearest> nearest) {
        this.nearest = nearest;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeList(featured);
        dest.writeList(trending);
        dest.writeList(nearest);
    }

    public int describeContents() {
        return  0;
    }

}
