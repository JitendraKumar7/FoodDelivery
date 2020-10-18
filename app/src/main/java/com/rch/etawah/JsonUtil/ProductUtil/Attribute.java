
package com.rch.etawah.JsonUtil.ProductUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attribute implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;

    private String currencySymbol;
    private boolean isSelected;
    private boolean isRadio;
    private int identificationNo;

    public Attribute() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public Attribute setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Attribute setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public boolean isRadio() {
        return isRadio;
    }

    public Attribute setRadio(boolean radio) {
        isRadio = radio;
        return this;
    }

    public int getIdentificationNo() {
        return identificationNo;
    }

    public Attribute setIdentificationNo(int identificationNo) {
        this.identificationNo = identificationNo;
        return this;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", currencySymbol='" + currencySymbol + '\'' +
                ", isSelected=" + isSelected +
                ", isRadio=" + isRadio +
                ", identificationNo=" + identificationNo +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.currencySymbol);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRadio ? (byte) 1 : (byte) 0);
        dest.writeInt(this.identificationNo);
    }

    protected Attribute(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.currencySymbol = in.readString();
        this.isSelected = in.readByte() != 0;
        this.isRadio = in.readByte() != 0;
        this.identificationNo = in.readInt();
    }

    public static final Creator<Attribute> CREATOR = new Creator<Attribute>() {
        @Override
        public Attribute createFromParcel(Parcel source) {
            return new Attribute(source);
        }

        @Override
        public Attribute[] newArray(int size) {
            return new Attribute[size];
        }
    };
}
