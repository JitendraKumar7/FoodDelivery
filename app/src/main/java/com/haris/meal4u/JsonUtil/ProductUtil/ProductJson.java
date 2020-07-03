
package com.haris.meal4u.JsonUtil.ProductUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    public final static Parcelable.Creator<ProductJson> CREATOR = new Creator<ProductJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ProductJson createFromParcel(Parcel in) {
            return new ProductJson(in);
        }

        public ProductJson[] newArray(int size) {
            return (new ProductJson[size]);
        }

    }
    ;

    protected ProductJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.products, (com.haris.meal4u.JsonUtil.ProductUtil.Product.class.getClassLoader()));
    }

    public ProductJson() {
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeList(products);
    }

    public int describeContents() {
        return  0;
    }

}
