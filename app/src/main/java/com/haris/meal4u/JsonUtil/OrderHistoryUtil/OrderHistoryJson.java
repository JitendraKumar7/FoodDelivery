
package com.haris.meal4u.JsonUtil.OrderHistoryUtil;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistoryJson implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("orderList")
    @Expose
    private List<OrderList> orderList = null;
    public final static Parcelable.Creator<OrderHistoryJson> CREATOR = new Creator<OrderHistoryJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OrderHistoryJson createFromParcel(Parcel in) {
            return new OrderHistoryJson(in);
        }

        public OrderHistoryJson[] newArray(int size) {
            return (new OrderHistoryJson[size]);
        }

    }
    ;

    protected OrderHistoryJson(Parcel in) {
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.orderList, (com.haris.meal4u.JsonUtil.OrderHistoryUtil.OrderList.class.getClassLoader()));
    }

    public OrderHistoryJson() {
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

    public List<OrderList> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderList> orderList) {
        this.orderList = orderList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeList(orderList);
    }

    public int describeContents() {
        return  0;
    }

}
