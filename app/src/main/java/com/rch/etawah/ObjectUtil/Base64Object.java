package com.rch.etawah.ObjectUtil;

import android.graphics.Bitmap;

public class Base64Object {
    private String text;
    private boolean isEncode;
    private boolean isDecode;
    private Bitmap bitmap;
    private boolean isOriginal=false;

    public Base64Object(boolean isEncode, boolean isDecode, Bitmap bitmap) {
        this.isEncode = isEncode;
        this.isDecode = isDecode;
        this.bitmap = bitmap;
    }

    public Base64Object(String text, boolean isEncode, boolean isDecode) {
        this.text = text;
        this.isEncode = isEncode;
        this.isDecode = isDecode;
    }

    public Base64Object(boolean isEncode, boolean isDecode, Bitmap bitmap, boolean isOriginal) {
        this.isEncode = isEncode;
        this.isDecode = isDecode;
        this.bitmap = bitmap;
        this.isOriginal = isOriginal;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public Base64Object setOriginal(boolean original) {
        isOriginal = original;
        return this;
    }

    public String getText() {
        return text;
    }

    public Base64Object setText(String text) {
        this.text = text;
        return this;
    }

    public boolean isEncode() {
        return isEncode;
    }

    public Base64Object setEncode(boolean encode) {
        isEncode = encode;
        return this;
    }

    public boolean isDecode() {
        return isDecode;
    }

    public Base64Object setDecode(boolean decode) {
        isDecode = decode;
        return this;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Base64Object setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        return this;
    }


    @Override
    public String toString() {
        return "Base64Object{" +
                "text='" + text + '\'' +
                ", isEncode=" + isEncode +
                ", isDecode=" + isDecode +
                ", bitmap=" + bitmap +
                '}';
    }
}
