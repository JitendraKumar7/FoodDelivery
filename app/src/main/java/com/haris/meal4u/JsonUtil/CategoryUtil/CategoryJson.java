
package com.haris.meal4u.JsonUtil.CategoryUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    public final static Parcelable.Creator<CategoryJson> CREATOR = new Creator<CategoryJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CategoryJson createFromParcel(Parcel in) {
            return new CategoryJson(in);
        }

        public CategoryJson[] newArray(int size) {
            return (new CategoryJson[size]);
        }

    }
    ;

    protected CategoryJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.categories, (com.haris.meal4u.JsonUtil.CategoryUtil.Category.class.getClassLoader()));
    }

    public CategoryJson() {
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeList(categories);
    }

    public int describeContents() {
        return  0;
    }

}
