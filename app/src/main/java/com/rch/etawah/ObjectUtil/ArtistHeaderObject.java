package com.rch.etawah.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

public class ArtistHeaderObject implements Parcelable {
    private String id;
    private String authorName;
    private String authorPicture;
    private String authorWork;
    private String authorDescription;
    private String postCount;
    private String downloadCount;
    private String reviewCount;
    private String loginType;
    private boolean isFollowed = false;
    private boolean isProfile = false;


    public boolean isProfile() {
        return isProfile;
    }

    public ArtistHeaderObject setProfile(boolean profile) {
        isProfile = profile;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getLoginType() {
        return loginType;
    }

    public ArtistHeaderObject setLoginType(String loginType) {
        this.loginType = loginType;
        return this;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public ArtistHeaderObject setFollowed(boolean followed) {
        isFollowed = followed;
        return this;
    }

    public String getArtistId() {
        return id;
    }

    public ArtistHeaderObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public ArtistHeaderObject setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getAuthorPicture() {
        return authorPicture;
    }

    public ArtistHeaderObject setAuthorPicture(String authorPicture) {
        this.authorPicture = authorPicture;
        return this;
    }

    public String getAuthorWork() {
        return authorWork;
    }

    public ArtistHeaderObject setAuthorWork(String authorWork) {
        this.authorWork = authorWork;
        return this;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public ArtistHeaderObject setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
        return this;
    }

    public String getPostCount() {
        return postCount;
    }

    public ArtistHeaderObject setPostCount(String postCount) {
        this.postCount = postCount;
        return this;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public ArtistHeaderObject setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
        return this;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public ArtistHeaderObject setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
        return this;
    }


    public ArtistHeaderObject() {
    }


    @Override
    public String toString() {
        return "ArtistHeaderObject{" +
                "id='" + id + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorPicture='" + authorPicture + '\'' +
                ", authorWork='" + authorWork + '\'' +
                ", authorDescription='" + authorDescription + '\'' +
                ", postCount='" + postCount + '\'' +
                ", downloadCount='" + downloadCount + '\'' +
                ", reviewCount='" + reviewCount + '\'' +
                ", loginType='" + loginType + '\'' +
                ", isFollowed=" + isFollowed +
                ", isProfile=" + isProfile +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.authorName);
        dest.writeString(this.authorPicture);
        dest.writeString(this.authorWork);
        dest.writeString(this.authorDescription);
        dest.writeString(this.postCount);
        dest.writeString(this.downloadCount);
        dest.writeString(this.reviewCount);
        dest.writeString(this.loginType);
        dest.writeByte(this.isFollowed ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isProfile ? (byte) 1 : (byte) 0);
    }

    protected ArtistHeaderObject(Parcel in) {
        this.id = in.readString();
        this.authorName = in.readString();
        this.authorPicture = in.readString();
        this.authorWork = in.readString();
        this.authorDescription = in.readString();
        this.postCount = in.readString();
        this.downloadCount = in.readString();
        this.reviewCount = in.readString();
        this.loginType = in.readString();
        this.isFollowed = in.readByte() != 0;
        this.isProfile = in.readByte() != 0;
    }

    public static final Creator<ArtistHeaderObject> CREATOR = new Creator<ArtistHeaderObject>() {
        @Override
        public ArtistHeaderObject createFromParcel(Parcel source) {
            return new ArtistHeaderObject(source);
        }

        @Override
        public ArtistHeaderObject[] newArray(int size) {
            return new ArtistHeaderObject[size];
        }
    };
}
