package com.haris.meal4u.Utility;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.JsonUtil.HomeUtil.HomeJson;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.RequestObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartObjectModal {
    private static String KEY = "CartObjectModal";
    String postId = "0";
    String postName = "0";
    String postPrice = "0";
    String postQtyPrice = "0";
    String postQuantity = "0";

    public CartObjectModal(String postId, String postName, String postPrice, String postQuantity) {
        this.postId = postId;
        this.postName = postName;
        this.postPrice = postPrice;
        this.postQuantity = postQuantity;

        this.postQtyPrice = getCalculatedPrice();
    }

    public String getPostId() {

        return postId;
    }

    public String getPostName() {

        return postName;
    }

    public String getPostPrice() {

        return postPrice;
    }

    public String getPostQtyPrice() {

        return postQtyPrice;
    }

    public String getPostQuantity() {

        return postQuantity;
    }

    public String getCurrencySymbol() {

        return "₹";
    }

    private String getCalculatedPrice() {
        try {
            int quantity = Integer.parseInt(postQuantity);
            int price = Integer.parseInt(postPrice);
            return String.valueOf(price * quantity);
        } catch (Exception ignore) {
            return postPrice;
        }
    }

    private boolean isPostQuantityEmpty() {

        return postQuantity.equalsIgnoreCase("0");
    }

    public void setPostQtyPrice(String valueOf) {

        postQtyPrice = valueOf;
    }

    public void setPostQuantity(String valueOf) {

        postQuantity = valueOf;
    }

    public static List<CartObjectModal> getList() {
        String objects = SharedPreference.getString(KEY);
        if (TextUtils.isEmpty(objects)) return new ArrayList<>();
        CartObjectModal[] objectModals = new Gson().fromJson(objects, CartObjectModal[].class);
        return new ArrayList<>(Arrays.asList(objectModals));
    }

    public static void setList(CartObjectModal objectModal) {
        List<CartObjectModal> modalList = getList();
        if (objectModal.isPostQuantityEmpty()) {
            modalList.remove(objectModal);
        } else {
            boolean isExist = false;
            for (int i = 0; i < modalList.size(); i++) {
                CartObjectModal modal = modalList.get(i);
                if (modal.cartEquals(objectModal)) {
                    modalList.set(i, objectModal);
                    isExist = true;
                }

            }
            if (!isExist) {
                modalList.add(objectModal);
            }
        }

        String objects = new Gson().toJson(modalList);
        SharedPreference.putString(KEY, objects);
    }

    public boolean cartEquals(@Nullable CartObjectModal obj) {

        return obj.getPostId().equalsIgnoreCase(postId);
    }

    public static void setList(List<CartObjectModal> modalList) {
        if (modalList == null) modalList = new ArrayList<>();
        String objects = new Gson().toJson(modalList);
        SharedPreference.putString(KEY, objects);
    }

    public static DataObject getSingleRestaurant(Context context) {
        HomeJson object = new Gson().fromJson("{\"code\":\"202\",\"message\":\"Data Retrieve Successfully\",\"featured\":[],\"trending\":[],\"nearest\":[{\"id\":\"1\",\"category\":\"Tea\",\"name\":\"RCH - Ratlam Cafe House\",\"description\":null,\"location\":\"867-C, Harsha Nagar, Chaugurji, Etawah, Uttar Pradesh, 206001.\",\"latitude\":\"26.780448\",\"longitude\":\"79.017214\",\"logo\":\"rch-header.png\",\"cover_url\":\"rch-header.png\",\"delivery_time\":\"45\",\"delivery_charges\":\"50\",\"min_order\":\"100\",\"expense\":\"1\",\"rating\":\"0\",\"no_of_ratings\":\"0\",\"currency\":\"INR\",\"currency_symbol\":\"₹\",\"restaurant_status\":{\"day\":[\"Sunday\"],\"fromTime\":[\"10:00\"],\"toTime\":[\"23:59\"]},\"distance\":null,\"tags\":\"Fast Food\",\"reviewers\":[],\"payment_method\":[\"Credit \\/ Debit Cards\",\"Cash On Delivery (COD)\"]}]}", HomeJson.class);

        RequestObject requestObject = new RequestObject();
        requestObject.setConnection(Constant.CONNECTION.HOME);
        requestObject.setContext(context);

        DataObject dataObject = DataObject.getDataObject(requestObject, object);
        return dataObject.getObjectArrayList().get(0);
    }

}


/* for (int i = 0; i < objectArrayList.size(); i++) {

            DataObject dataObject = (DataObject) objectArrayList.get(i);
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.accumulate("product_id", dataObject.getPost_id());
                jsonObject.accumulate("quantity", dataObject.getPost_quantity());
                jsonObject.accumulate("price", dataObject.getPost_price());
                jsonObject.accumulate("attribute_id", dataObject.getPost_attribute_id());
                jsonObject.accumulate("special_instruction", dataObject.getSpecial_instructions());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(jsonObject);

        }




  JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "order_product");
            jsonObject.accumulate("user_id", user_id);
            jsonObject.accumulate("restaurant_id", restaurant_id);
            jsonObject.accumulate("order_price", price);
            jsonObject.accumulate("discount_id", couponId);
            jsonObject.accumulate("delivery_time", delivery_time);
            jsonObject.accumulate("payment_type", payment_type);
            jsonObject.accumulate("stripe_token", billingObject.getStripeCustomerToken());
            jsonObject.accumulate("billing_address", addressObject.getStreetName());
            jsonObject.accumulate("latitude", addressObject.getLatitude());
            jsonObject.accumulate("longitude", addressObject.getLongitude());
            jsonObject.accumulate("building_no", addressObject.getBuildingName());
            jsonObject.accumulate("area_name", addressObject.getAreaName());
            jsonObject.accumulate("floor_name", addressObject.getFloorName());
            jsonObject.accumulate("rider_note", addressObject.getNoteRider());
            jsonObject.accumulate("delivery_date", scheduleObject.getSchedule());
            jsonObject.accumulate("order_type", scheduleObject.isNow() ? "received" : "schedule");
            jsonObject.accumulate("no_of_product", convertProductIntoArray());


        } catch (JSONException e) {
            e.printStackTrace();
        }*/