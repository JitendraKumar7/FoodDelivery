
package com.haris.meal4u.JsonUtil.ProductUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductAttribute implements Parcelable
{

    @SerializedName("attribute_tagline")
    @Expose
    private String attributeTagline;
    @SerializedName("attribute_description")
    @Expose
    private String attributeDescription;
    @SerializedName("attribute_nature")
    @Expose
    private String attributeNature;
    @SerializedName("attribute_type")
    @Expose
    private String attributeType;
    @SerializedName("attribute_selector")
    @Expose
    private String attributeSelector;
    @SerializedName("attribute")
    @Expose
    private List<Attribute> attribute = null;

    public ProductAttribute() {
    }

    public String getAttributeTagline() {
        return attributeTagline;
    }

    public void setAttributeTagline(String attributeTagline) {
        this.attributeTagline = attributeTagline;
    }

    public String getAttributeDescription() {
        return attributeDescription;
    }

    public void setAttributeDescription(String attributeDescription) {
        this.attributeDescription = attributeDescription;
    }

    public String getAttributeNature() {
        return attributeNature;
    }

    public void setAttributeNature(String attributeNature) {
        this.attributeNature = attributeNature;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeSelector() {
        return attributeSelector;
    }

    public void setAttributeSelector(String attributeSelector) {
        this.attributeSelector = attributeSelector;
    }

    public List<Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<Attribute> attribute) {
        this.attribute = attribute;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.attributeTagline);
        dest.writeString(this.attributeDescription);
        dest.writeString(this.attributeNature);
        dest.writeString(this.attributeType);
        dest.writeString(this.attributeSelector);
        dest.writeTypedList(this.attribute);
    }

    protected ProductAttribute(Parcel in) {
        this.attributeTagline = in.readString();
        this.attributeDescription = in.readString();
        this.attributeNature = in.readString();
        this.attributeType = in.readString();
        this.attributeSelector = in.readString();
        this.attribute = in.createTypedArrayList(Attribute.CREATOR);
    }

    public static final Creator<ProductAttribute> CREATOR = new Creator<ProductAttribute>() {
        @Override
        public ProductAttribute createFromParcel(Parcel source) {
            return new ProductAttribute(source);
        }

        @Override
        public ProductAttribute[] newArray(int size) {
            return new ProductAttribute[size];
        }
    };
}
