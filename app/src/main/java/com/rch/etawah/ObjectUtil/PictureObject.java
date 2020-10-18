package com.rch.etawah.ObjectUtil;

public class PictureObject {
    private String picture;
    private boolean isLongTap;


    public String getPicture() {
        return picture;
    }

    public PictureObject setPicture(String picture) {
        this.picture = picture;
        return this;
    }


    public boolean isLongTap() {
        return isLongTap;
    }

    public PictureObject setLongTap(boolean longTap) {
        isLongTap = longTap;
        return this;
    }
}
