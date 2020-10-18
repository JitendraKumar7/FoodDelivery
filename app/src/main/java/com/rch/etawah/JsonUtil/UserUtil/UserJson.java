package com.rch.etawah.JsonUtil.UserUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserJson {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fName")
    @Expose
    private String fName;
    @SerializedName("lName")
    @Expose
    private String lName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("pass")
    @Expose
    private String pass;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("isEnable")
    @Expose
    private String isEnable;

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("userType")
    @Expose
    private String userType;

    @SerializedName("coin")
    @Expose
    private String coin;

    @SerializedName("favouriteList")
    @Expose
    private List<FavouriteList> favouriteList = null;


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

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getUid() {
        return uid;
    }

    public UserJson setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public UserJson setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public String getCoin() {
        return coin;
    }

    public UserJson setCoin(String coin) {
        this.coin = coin;
        return this;
    }

    public String getId() {
        return id;
    }

    public UserJson setId(String id) {
        this.id = id;
        return this;
    }

    public String getfName() {
        return fName;
    }

    public UserJson setfName(String fName) {
        this.fName = fName;
        return this;
    }

    public String getlName() {
        return lName;
    }

    public UserJson setlName(String lName) {
        this.lName = lName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserJson setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public List<FavouriteList> getFavouriteList() {
        return favouriteList;
    }

    public UserJson setFavouriteList(List<FavouriteList> favouriteList) {
        this.favouriteList = favouriteList;
        return this;
    }
}
