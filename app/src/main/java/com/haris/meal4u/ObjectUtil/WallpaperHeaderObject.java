package com.haris.meal4u.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;



public class WallpaperHeaderObject implements Parcelable {
    private String postId;
    private String postName;
    private String postDescription;
    private String postAuthorName;
    private String postDownloadCount;
    private String bookReviewCount;
    private String bookRating;
    private String postImage;
    private String postTag;
    private String postUrl;
    private String postType;
    private String postLowResImage;
    private boolean isProfile;


    public boolean isProfile() {
        return isProfile;
    }

    public WallpaperHeaderObject setProfile(boolean profile) {
        isProfile = profile;
        return this;
    }

    public String getPostLowResImage() {
        return postLowResImage;
    }

    public WallpaperHeaderObject setPostLowResImage(String postLowResImage) {
        this.postLowResImage = postLowResImage;
        return this;
    }

    public String getPostType() {
        return postType;
    }

    public WallpaperHeaderObject setPostType(String postType) {
        this.postType = postType;
        return this;
    }


    public String getPostId() {
        return postId;
    }

    public WallpaperHeaderObject setPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public String getBookName() {
        return postName;
    }

    public WallpaperHeaderObject setPostName(String postName) {
        this.postName = postName;
        return this;
    }

    public String getBookDescription() {
        return postDescription;
    }

    public WallpaperHeaderObject setPostDescription(String postDescription) {
        this.postDescription = postDescription;
        return this;
    }

    public String getBookAuthorName() {
        return postAuthorName;
    }

    public WallpaperHeaderObject setPostAuthorName(String postAuthorName) {
        this.postAuthorName = postAuthorName;
        return this;
    }

    public String getBookDownloadCount() {
        return postDownloadCount;
    }

    public WallpaperHeaderObject setPostDownloadCount(String postDownloadCount) {
        this.postDownloadCount = postDownloadCount;
        return this;
    }

    public String getBookReviewCount() {
        return bookReviewCount;
    }

    public WallpaperHeaderObject setPostReviewCount(String bookReviewCount) {
        this.bookReviewCount = bookReviewCount;
        return this;
    }

    public String getBookRating() {
        return bookRating;
    }

    public WallpaperHeaderObject setPostRating(String bookRating) {
        this.bookRating = bookRating;
        return this;
    }

    public String getBookImage() {
        return postImage;
    }

    public WallpaperHeaderObject setPostImage(String postImage) {
        this.postImage = postImage;
        return this;
    }

    public String getBookTag() {
        return postTag;
    }

    public WallpaperHeaderObject setPostTag(String postTag) {
        this.postTag = postTag;
        return this;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public WallpaperHeaderObject setPostUrl(String postUrl) {
        this.postUrl = postUrl;
        return this;
    }


    public WallpaperHeaderObject() {
    }


    @Override
    public String toString() {
        return "WallpaperHeaderObject{" +
                "postId='" + postId + '\'' +
                ", postName='" + postName + '\'' +
                ", postDescription='" + postDescription + '\'' +
                ", postAuthorName='" + postAuthorName + '\'' +
                ", postDownloadCount='" + postDownloadCount + '\'' +
                ", bookReviewCount='" + bookReviewCount + '\'' +
                ", bookRating='" + bookRating + '\'' +
                ", postImage='" + postImage + '\'' +
                ", postTag='" + postTag + '\'' +
                ", postUrl='" + postUrl + '\'' +
                ", postType='" + postType + '\'' +
                ", postLowResImage='" + postLowResImage + '\'' +

                ", isProfile=" + isProfile +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.postId);
        dest.writeString(this.postName);
        dest.writeString(this.postDescription);
        dest.writeString(this.postAuthorName);
        dest.writeString(this.postDownloadCount);
        dest.writeString(this.bookReviewCount);
        dest.writeString(this.bookRating);
        dest.writeString(this.postImage);
        dest.writeString(this.postTag);
        dest.writeString(this.postUrl);
        dest.writeString(this.postType);
        dest.writeString(this.postLowResImage);
        dest.writeByte(this.isProfile ? (byte) 1 : (byte) 0);
    }

    protected WallpaperHeaderObject(Parcel in) {
        this.postId = in.readString();
        this.postName = in.readString();
        this.postDescription = in.readString();
        this.postAuthorName = in.readString();
        this.postDownloadCount = in.readString();
        this.bookReviewCount = in.readString();
        this.bookRating = in.readString();
        this.postImage = in.readString();
        this.postTag = in.readString();
        this.postUrl = in.readString();
        this.postType = in.readString();
        this.postLowResImage = in.readString();

        this.isProfile = in.readByte() != 0;
    }

    public static final Creator<WallpaperHeaderObject> CREATOR = new Creator<WallpaperHeaderObject>() {
        @Override
        public WallpaperHeaderObject createFromParcel(Parcel source) {
            return new WallpaperHeaderObject(source);
        }

        @Override
        public WallpaperHeaderObject[] newArray(int size) {
            return new WallpaperHeaderObject[size];
        }
    };
}
