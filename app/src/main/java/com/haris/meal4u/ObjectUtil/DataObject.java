package com.haris.meal4u.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.JsonUtil.AppOfferUtil.AppOfferJson;
import com.haris.meal4u.JsonUtil.AppOfferUtil.Category;
import com.haris.meal4u.JsonUtil.AppOfferUtil.Offer;
import com.haris.meal4u.JsonUtil.CardUtil.Card;
import com.haris.meal4u.JsonUtil.CardUtil.CardJson;
import com.haris.meal4u.JsonUtil.CategoryUtil.CategoryJson;
import com.haris.meal4u.JsonUtil.CouponUtil.CouponJson;
import com.haris.meal4u.JsonUtil.DeliveryDetailUtil.DeliveryDetailJson;
import com.haris.meal4u.JsonUtil.HomeUtil.Featured;
import com.haris.meal4u.JsonUtil.HomeUtil.HomeJson;
import com.haris.meal4u.JsonUtil.HomeUtil.Nearest;
import com.haris.meal4u.JsonUtil.HomeUtil.RestaurantStatus;
import com.haris.meal4u.JsonUtil.HomeUtil.Trending;
import com.haris.meal4u.JsonUtil.OrderHistoryUtil.OrderHistoryJson;
import com.haris.meal4u.JsonUtil.OrderHistoryUtil.OrderList;
import com.haris.meal4u.JsonUtil.OrderHistoryUtil.ProductOrderDetail;
import com.haris.meal4u.JsonUtil.OrderUtil.OrderJson;
import com.haris.meal4u.JsonUtil.ProductUtil.Product;
import com.haris.meal4u.JsonUtil.ProductUtil.ProductAttribute;
import com.haris.meal4u.JsonUtil.ProductUtil.ProductJson;
import com.haris.meal4u.JsonUtil.ReviewUtil.Review;
import com.haris.meal4u.JsonUtil.ReviewUtil.ReviewJson;
import com.haris.meal4u.JsonUtil.UserUtil.FavouriteList;
import com.haris.meal4u.JsonUtil.UserUtil.UserJson;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class DataObject implements Parcelable {

    /* Variable for Connection Builder */

    private String code;
    private String message;

    /* Variable for Debugging */

    private static String TAG = DataObject.class.getName();

    /* Variable for Categories */

    private String category_id;
    private String category_name;

    /* Variable for UserObject */

    private String user_id;
    private String user_fName;
    private String user_lName;
    private String user_password;
    private String login_type;
    private String user_picture;
    private String user_phone;
    private String user_email;
    private String user_sign_in;
    private String user_remember;
    private ArrayList<FavouriteList> userFavourite = new ArrayList<>();
    private String user_payment_type;

    /* Variable for payments */

    private String payment_id;
    private String payee_name;
    private String payment_card_company;
    private String payment_card_no;
    private String payment_cvv_no;
    private String payment_expiry_date;
    private String stripe_customer_no;

    /* Variable for Restaurant Detail */

    private String object_id;
    private String object_name;
    private String object_Description;
    private String object_category_name;
    private String object_min_delivery_time;
    private String object_min_order_price;
    private String object_delivery_charges;
    private String object_expense;
    private String object_rating;
    private String object_no_of_rating;
    private String object_currency;
    private String object_currency_symbol;
    private String object_status;
    private String object_latitude;
    private String object_longitude;
    private String object_address;
    private String object_picture;
    private String object_logo;
    private String object_distance;
    private String object_tags;
    private String object_date_created;
    private String paymentType;
    private String day;
    private String fromTime;
    private String toTime;
    private ArrayList<DataObject> schedule = new ArrayList<>();
    private ArrayList<String> reviewerList = new ArrayList<>();
    private ArrayList<String> paymentTypeList = new ArrayList<>();
    private boolean is_layout_01 = true;


    /* Variable for Review of Object */

    private String review_id;
    private String review_title;
    private String review_date;
    private String reviews;
    private String rating;
    private String reviewer_picture;
    private ArrayList<String> reviewPictureList = new ArrayList<>();

    /* Variable for Coupons */

    private String coupon_id;
    private String coupon_code;
    private String coupon_reward;
    private String coupon_start_date;
    private String coupon_end_date;
    private String coupon_date_created;

    /* Variable for App Offers */

    private String offer_id;
    private String offer_image;
    private String offer_latitude;
    private String offer_longitude;
    private String offer_name;

    /* Variable for Restaurant Menu */

    private String post_id;
    private String post_name;
    private String post_image;
    private String post_description;
    private String post_price;
    private String post_quantity;
    private String post_attribute_id;
    private String post_attribute_name;
    private String special_instructions;
    private ArrayList<ProductAttribute> productAttribute = new ArrayList<>();

    /* Variable for Checkout item */

    private AddressObject addressObject;
    private BillingObject billingObject;
    private ScheduleObject scheduleObject;

    /* Variable for Order History */

    private String order_id;
    private String order_restaurant_name;
    private String order_delivery_date;
    private String order_delivery_time;
    private String order_price;
    private String delivery_time;
    private String delivery_fee;
    private String payment_type;
    private String order_status;
    private String review_status;
    private String rider_review_status;
    private String order_product_name;
    private String order_product_quantity;
    private String order_product_price;
    private ArrayList<DataObject> product_order_detail_list = new ArrayList<>();


    /* Variable for Data Type */

    private Constant.DATA_TYPE dataType;
    private ArrayList<DataObject> objectArrayList = new ArrayList<>();
    private ArrayList<Object> homeList = new ArrayList<>();

    /* Variable for Favourites */

    private String favourite_id;
    private boolean isFavourite = false;
    private String cart_id;


    /* Variable for Chatting */

    private String chatting;
    private String picture;
    private String date;
    private String time;
    private boolean isFrom;

    /* General Variable  */

    private boolean isLongTap;
    private boolean isFirstItem;
    private boolean isAlreadyAddedIntoCart;
    private boolean isPaymentCardSelected;
    private String noOfItemInCart;
    private String basePrice;

    private String delivery_charges;


    public String getCode() {
        return code;
    }

    public DataObject setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DataObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCategory_id() {
        return category_id;
    }

    public DataObject setCategory_id(String category_id) {
        this.category_id = category_id;
        return this;
    }

    public String getCategory_name() {
        return category_name;
    }

    public DataObject setCategory_name(String category_name) {
        this.category_name = category_name;
        return this;
    }

    public String getUser_id() {
        return user_id;
    }

    public DataObject setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getUser_fName() {
        return user_fName;
    }

    public DataObject setUser_fName(String user_fName) {
        this.user_fName = user_fName;
        return this;
    }

    public String getUser_lName() {
        return user_lName;
    }

    public DataObject setUser_lName(String user_lName) {
        this.user_lName = user_lName;
        return this;
    }

    public String getUser_password() {
        return user_password;
    }

    public DataObject setUser_password(String user_password) {
        this.user_password = user_password;
        return this;
    }

    public String getLogin_type() {
        return login_type;
    }

    public DataObject setLogin_type(String login_type) {
        this.login_type = login_type;
        return this;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public DataObject setUser_picture(String user_picture) {
        this.user_picture = user_picture;
        return this;
    }

    public String getPhone() {
        return user_phone;
    }

    public DataObject setPhone(String phone) {
        this.user_phone = phone;
        return this;
    }

    public String getUser_email() {
        return user_email;
    }

    public DataObject setUser_email(String user_email) {
        this.user_email = user_email;
        return this;
    }

    public String getUser_sign_in() {
        return user_sign_in;
    }

    public DataObject setUser_sign_in(String user_sign_in) {
        this.user_sign_in = user_sign_in;
        return this;
    }

    public String getUser_remember() {
        return user_remember;
    }

    public DataObject setUser_remember(String user_remember) {
        this.user_remember = user_remember;
        return this;
    }

    public ArrayList<FavouriteList> getUserFavourite() {
        return userFavourite;
    }

    public DataObject setUserFavourite(ArrayList<FavouriteList> userFavourite) {
        this.userFavourite = userFavourite;
        return this;
    }

    public String getUser_payment_type() {
        return user_payment_type;
    }

    public DataObject setUser_payment_type(String user_payment_type) {
        this.user_payment_type = user_payment_type;
        return this;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public DataObject setPayment_id(String payment_id) {
        this.payment_id = payment_id;
        return this;
    }

    public String getPayee_name() {
        return payee_name;
    }

    public DataObject setPayee_name(String payee_name) {
        this.payee_name = payee_name;
        return this;
    }

    public String getPayment_card_company() {
        return payment_card_company;
    }

    public DataObject setPayment_card_company(String payment_card_company) {
        this.payment_card_company = payment_card_company;
        return this;
    }

    public String getPayment_card_no() {
        return payment_card_no;
    }

    public DataObject setPayment_card_no(String payment_card_no) {
        this.payment_card_no = payment_card_no;
        return this;
    }

    public String getPayment_cvv_no() {
        return payment_cvv_no;
    }

    public DataObject setPayment_cvv_no(String payment_cvv_no) {
        this.payment_cvv_no = payment_cvv_no;
        return this;
    }

    public String getPayment_expiry_date() {
        return payment_expiry_date;
    }

    public DataObject setPayment_expiry_date(String payment_expiry_date) {
        this.payment_expiry_date = payment_expiry_date;
        return this;
    }

    public String getStripe_customer_no() {
        return stripe_customer_no;
    }

    public DataObject setStripe_customer_no(String stripe_customer_no) {
        this.stripe_customer_no = stripe_customer_no;
        return this;
    }

    public String getObject_id() {
        return object_id;
    }

    public DataObject setObject_id(String object_id) {
        this.object_id = object_id;
        return this;
    }

    public String getObject_name() {
        return object_name;
    }

    public DataObject setObject_name(String object_name) {
        this.object_name = object_name;
        return this;
    }

    public String getObject_Description() {
        return object_Description;
    }

    public DataObject setObject_Description(String object_Description) {
        this.object_Description = object_Description;
        return this;
    }

    public String getObject_category_name() {
        return object_category_name;
    }

    public DataObject setObject_category_name(String object_category_name) {
        this.object_category_name = object_category_name;
        return this;
    }

    public String getObject_min_delivery_time() {
        return object_min_delivery_time;
    }

    public DataObject setObject_min_delivery_time(String object_min_delivery_time) {
        this.object_min_delivery_time = object_min_delivery_time;
        return this;
    }

    public String getObject_min_order_price() {
        return object_min_order_price;
    }

    public DataObject setObject_min_order_price(String object_min_order_price) {
        this.object_min_order_price = object_min_order_price;
        return this;
    }

    public String getObject_delivery_charges() {
        return object_delivery_charges;
    }

    public DataObject setObject_delivery_charges(String object_delivery_charges) {
        this.object_delivery_charges = object_delivery_charges;
        return this;
    }

    public String getObject_expense() {
        return object_expense;
    }

    public DataObject setObject_expense(String object_expense) {
        this.object_expense = object_expense;
        return this;
    }

    public String getObject_rating() {
        return object_rating;
    }

    public DataObject setObject_rating(String object_rating) {
        this.object_rating = object_rating;
        return this;
    }

    public String getObject_no_of_rating() {
        return object_no_of_rating;
    }

    public DataObject setObject_no_of_rating(String object_no_of_rating) {
        this.object_no_of_rating = object_no_of_rating;
        return this;
    }

    public String getObject_currency() {
        return object_currency;
    }

    public DataObject setObject_currency(String object_currency) {
        this.object_currency = object_currency;
        return this;
    }

    public String getObject_currency_symbol() {
        return object_currency_symbol;
    }

    public DataObject setObject_currency_symbol(String object_currency_symbol) {
        this.object_currency_symbol = object_currency_symbol;
        return this;
    }

    public String getObject_status() {
        return object_status;
    }

    public DataObject setObject_status(String object_status) {
        this.object_status = object_status;
        return this;
    }

    public String getObject_latitude() {
        return object_latitude;
    }

    public DataObject setObject_latitude(String object_latitude) {
        this.object_latitude = object_latitude;
        return this;
    }

    public String getObject_longitude() {
        return object_longitude;
    }

    public DataObject setObject_longitude(String object_longitude) {
        this.object_longitude = object_longitude;
        return this;
    }

    public String getObject_address() {
        return object_address;
    }

    public DataObject setObject_address(String object_address) {
        this.object_address = object_address;
        return this;
    }

    public String getObject_picture() {
        return object_picture;
    }

    public DataObject setObject_picture(String object_picture) {
        this.object_picture = object_picture;
        return this;
    }

    public String getObject_logo() {
        return object_logo;
    }

    public DataObject setObject_logo(String object_logo) {
        this.object_logo = object_logo;
        return this;
    }

    public String getObject_distance() {
        return object_distance;
    }

    public DataObject setObject_distance(String object_distance) {
        this.object_distance = object_distance;
        return this;
    }

    public String getObject_tags() {
        return object_tags;
    }

    public DataObject setObject_tags(String object_tags) {
        this.object_tags = object_tags;
        return this;
    }

    public String getObject_date_created() {
        return object_date_created;
    }

    public DataObject setObject_date_created(String object_date_created) {
        this.object_date_created = object_date_created;
        return this;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public DataObject setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public String getDay() {
        return day;
    }

    public DataObject setDay(String day) {
        this.day = day;
        return this;
    }

    public String getFromTime() {
        return fromTime;
    }

    public DataObject setFromTime(String fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public String getToTime() {
        return toTime;
    }

    public DataObject setToTime(String toTime) {
        this.toTime = toTime;
        return this;
    }

    public ArrayList<DataObject> getSchedule() {
        return schedule;
    }

    public DataObject setSchedule(ArrayList<DataObject> schedule) {
        this.schedule = schedule;
        return this;
    }

    public ArrayList<String> getReviewerList() {
        return reviewerList;
    }

    public DataObject setReviewerList(ArrayList<String> reviewerList) {
        this.reviewerList = reviewerList;
        return this;
    }

    public ArrayList<String> getPaymentTypeList() {
        return paymentTypeList;
    }

    public DataObject setPaymentTypeList(ArrayList<String> paymentTypeList) {
        this.paymentTypeList = paymentTypeList;
        return this;
    }

    public String getReview_id() {
        return review_id;
    }

    public DataObject setReview_id(String review_id) {
        this.review_id = review_id;
        return this;
    }

    public String getReview_title() {
        return review_title;
    }

    public DataObject setReview_title(String review_title) {
        this.review_title = review_title;
        return this;
    }

    public String getReview_date() {
        return review_date;
    }

    public DataObject setReview_date(String review_date) {
        this.review_date = review_date;
        return this;
    }

    public String getReviews() {
        return reviews;
    }

    public DataObject setReviews(String reviews) {
        this.reviews = reviews;
        return this;
    }


    public String getRating() {
        return rating;
    }

    public DataObject setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getReviewer_picture() {
        return reviewer_picture;
    }

    public DataObject setReviewer_picture(String reviewer_picture) {
        this.reviewer_picture = reviewer_picture;
        return this;
    }

    public ArrayList<String> getReviewPictureList() {
        return reviewPictureList;
    }

    public DataObject setReviewPictureList(ArrayList<String> reviewPictureList) {
        this.reviewPictureList = reviewPictureList;
        return this;
    }

    public boolean isIs_layout_01() {
        return is_layout_01;
    }

    public DataObject setIs_layout_01(boolean is_layout_01) {
        this.is_layout_01 = is_layout_01;
        return this;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public DataObject setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
        return this;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public DataObject setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
        return this;
    }

    public String getCoupon_reward() {
        return coupon_reward;
    }

    public DataObject setCoupon_reward(String coupon_reward) {
        this.coupon_reward = coupon_reward;
        return this;
    }

    public String getCoupon_start_date() {
        return coupon_start_date;
    }

    public DataObject setCoupon_start_date(String coupon_start_date) {
        this.coupon_start_date = coupon_start_date;
        return this;
    }

    public String getCoupon_end_date() {
        return coupon_end_date;
    }

    public DataObject setCoupon_end_date(String coupon_end_date) {
        this.coupon_end_date = coupon_end_date;
        return this;
    }

    public String getCoupon_date_created() {
        return coupon_date_created;
    }

    public DataObject setCoupon_date_created(String coupon_date_created) {
        this.coupon_date_created = coupon_date_created;
        return this;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public DataObject setOffer_id(String offer_id) {
        this.offer_id = offer_id;
        return this;
    }

    public String getOffer_image() {
        return offer_image;
    }

    public DataObject setOffer_image(String offer_image) {
        this.offer_image = offer_image;
        return this;
    }

    public String getOffer_latitude() {
        return offer_latitude;
    }

    public DataObject setOffer_latitude(String offer_latitude) {
        this.offer_latitude = offer_latitude;
        return this;
    }

    public String getOffer_longitude() {
        return offer_longitude;
    }

    public DataObject setOffer_longitude(String offer_longitude) {
        this.offer_longitude = offer_longitude;
        return this;
    }

    public String getOffer_name() {
        return offer_name;
    }

    public DataObject setOffer_name(String offer_name) {
        this.offer_name = offer_name;
        return this;
    }

    public String getPost_id() {
        return post_id;
    }

    public DataObject setPost_id(String post_id) {
        this.post_id = post_id;
        return this;
    }

    public String getPost_name() {
        return post_name;
    }

    public DataObject setPost_name(String post_name) {
        this.post_name = post_name;
        return this;
    }

    public String getPost_image() {
        return post_image;
    }

    public DataObject setPost_image(String post_image) {
        this.post_image = post_image;
        return this;
    }

    public String getPost_description() {
        return post_description;
    }

    public DataObject setPost_description(String post_description) {
        this.post_description = post_description;
        return this;
    }

    public String getPost_price() {
        return post_price;
    }

    public DataObject setPost_price(String post_price) {
        this.post_price = post_price;
        return this;
    }

    public String getPost_quantity() {
        return post_quantity;
    }

    public DataObject setPost_quantity(String post_quantity) {
        this.post_quantity = post_quantity;
        return this;
    }

    public String getPost_attribute_id() {
        return post_attribute_id;
    }

    public DataObject setPost_attribute_id(String post_attribute_id) {
        this.post_attribute_id = post_attribute_id;
        return this;
    }

    public String getPost_attribute_name() {
        return post_attribute_name;
    }

    public DataObject setPost_attribute_name(String post_attribute_name) {
        this.post_attribute_name = post_attribute_name;
        return this;
    }

    public String getSpecial_instructions() {
        return special_instructions;
    }

    public DataObject setSpecial_instructions(String special_instructions) {
        this.special_instructions = special_instructions;
        return this;
    }

    public ArrayList<ProductAttribute> getProductAttribute() {
        return productAttribute;
    }

    public DataObject setProductAttribute(List<ProductAttribute> productAttribute) {
        this.productAttribute.clear();
        this.productAttribute.addAll(productAttribute);
        return this;
    }

    public Constant.DATA_TYPE getDataType() {
        return dataType;
    }

    public DataObject setDataType(Constant.DATA_TYPE dataType) {
        this.dataType = dataType;
        return this;
    }

    public boolean isLongTap() {
        return isLongTap;
    }

    public DataObject setLongTap(boolean longTap) {
        isLongTap = longTap;
        return this;
    }

    public boolean isFirstItem() {
        return isFirstItem;
    }

    public DataObject setFirstItem(boolean firstItem) {
        isFirstItem = firstItem;
        return this;
    }

    public boolean isAlreadyAddedIntoCart() {
        return isAlreadyAddedIntoCart;
    }

    public DataObject setAlreadyAddedIntoCart(boolean alreadyAddedIntoCart) {
        isAlreadyAddedIntoCart = alreadyAddedIntoCart;
        return this;
    }

    public boolean isPaymentCardSelected() {
        return isPaymentCardSelected;
    }

    public DataObject setPaymentCardSelected(boolean paymentCardSelected) {
        isPaymentCardSelected = paymentCardSelected;
        return this;
    }

    public String getNoOfItemInCart() {
        return noOfItemInCart;
    }

    public DataObject setNoOfItemInCart(String noOfItemInCart) {
        this.noOfItemInCart = noOfItemInCart;
        return this;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public DataObject setBasePrice(String basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public String getDelivery_charges() {
        return delivery_charges;
    }

    public DataObject setDelivery_charges(String delivery_charges) {
        this.delivery_charges = delivery_charges;
        return this;
    }

    public ArrayList<DataObject> getObjectArrayList() {
        return objectArrayList;
    }

    public DataObject setObjectArrayList(ArrayList<DataObject> objectArrayList) {
        this.objectArrayList = objectArrayList;
        return this;
    }

    public ArrayList<Object> getHomeList() {
        return homeList;
    }

    public DataObject setHomeList(ArrayList<Object> homeList) {
        this.homeList = homeList;
        return this;
    }

    public String getFavourite_id() {
        return favourite_id;
    }

    public DataObject setFavourite_id(String favourite_id) {
        this.favourite_id = favourite_id;
        return this;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public DataObject setFavourite(boolean favourite) {
        isFavourite = favourite;
        return this;
    }

    public String getCart_id() {
        return cart_id;
    }

    public DataObject setCart_id(String cart_id) {
        this.cart_id = cart_id;
        return this;
    }

    public String getChatting() {
        return chatting;
    }

    public DataObject setChatting(String chatting) {
        this.chatting = chatting;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public DataObject setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getDate() {
        return date;
    }

    public DataObject setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public DataObject setTime(String time) {
        this.time = time;
        return this;
    }

    public boolean isFrom() {
        return isFrom;
    }

    public DataObject setFrom(boolean from) {
        isFrom = from;
        return this;
    }

    public AddressObject getAddressObject() {
        return addressObject;
    }

    public DataObject setAddressObject(AddressObject addressObject) {
        this.addressObject = addressObject;
        return this;
    }

    public BillingObject getBillingObject() {
        return billingObject;
    }

    public DataObject setBillingObject(BillingObject billingObject) {
        this.billingObject = billingObject;
        return this;
    }

    public ScheduleObject getScheduleObject() {
        return scheduleObject;
    }

    public DataObject setScheduleObject(ScheduleObject scheduleObject) {
        this.scheduleObject = scheduleObject;
        return this;
    }

    public String getOrder_id() {
        return order_id;
    }

    public DataObject setOrder_id(String order_id) {
        this.order_id = order_id;
        return this;
    }

    public String getOrder_restaurant_name() {
        return order_restaurant_name;
    }

    public DataObject setOrder_restaurant_name(String order_restaurant_name) {
        this.order_restaurant_name = order_restaurant_name;
        return this;
    }

    public String getOrder_delivery_date() {
        return order_delivery_date;
    }

    public DataObject setOrder_delivery_date(String order_delivery_date) {
        this.order_delivery_date = order_delivery_date;
        return this;
    }

    public String getOrder_delivery_time() {
        return order_delivery_time;
    }

    public DataObject setOrder_delivery_time(String order_delivery_time) {
        this.order_delivery_time = order_delivery_time;
        return this;
    }

    public String getOrder_price() {
        return order_price;
    }

    public DataObject setOrder_price(String order_price) {
        this.order_price = order_price;
        return this;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public DataObject setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
        return this;
    }

    public String getDelivery_fee() {
        return delivery_fee;
    }

    public DataObject setDelivery_fee(String delivery_fee) {
        this.delivery_fee = delivery_fee;
        return this;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public DataObject setPayment_type(String payment_type) {
        this.payment_type = payment_type;
        return this;
    }

    public String getOrder_status() {
        return order_status;
    }

    public DataObject setOrder_status(String order_status) {
        this.order_status = order_status;
        return this;
    }

    public String getReview_status() {
        return review_status;
    }

    public DataObject setReview_status(String review_status) {
        this.review_status = review_status;
        return this;
    }

    public String getRider_review_status() {
        return rider_review_status;
    }

    public DataObject setRider_review_status(String rider_review_status) {
        this.rider_review_status = rider_review_status;
        return this;
    }

    public String getOrder_product_name() {
        return order_product_name;
    }

    public DataObject setOrder_product_name(String order_product_name) {
        this.order_product_name = order_product_name;
        return this;
    }

    public String getOrder_product_quantity() {
        return order_product_quantity;
    }

    public DataObject setOrder_product_quantity(String order_product_quantity) {
        this.order_product_quantity = order_product_quantity;
        return this;
    }

    public String getOrder_product_price() {
        return order_product_price;
    }

    public DataObject setOrder_product_price(String order_product_price) {
        this.order_product_price = order_product_price;
        return this;
    }

    public ArrayList<DataObject> getProduct_order_detail_list() {
        return product_order_detail_list;
    }

    public DataObject setProduct_order_detail_list(ArrayList<DataObject> product_order_detail_list) {
        this.product_order_detail_list = product_order_detail_list;
        return this;
    }

    public static DataObject getDataObject(RequestObject requestObject, Object data) {

        DataObject dataObject = null;
        String nextPage = null;

        if (requestObject.getConnection() == Constant.CONNECTION.HOME) {

            //region HomeFragment Data

            ArrayList<DataObject> restaurantList = new ArrayList<>();
            ArrayList<Object> homeList = new ArrayList<>();
            HomeJson homeJson = (HomeJson) data;
            HomeObject homeObject = null;

            /*List of Restaurants having offers*/

            for (int i = 0; i < homeJson.getFeatured().size(); i++) {

                Featured featured = homeJson.getFeatured().get(i);

                ArrayList<DataObject> scheduleList = new ArrayList<>();

                for (int j = 0; j < featured.getRestaurant_status().getDay().size(); j++) {

                    RestaurantStatus restaurantStatus = featured.getRestaurant_status();
                    String day = restaurantStatus.getDay().get(j);
                    String fromTime = restaurantStatus.getFromTime().get(j);
                    String toTime = restaurantStatus.getToTime().get(j);

                    scheduleList.add(new DataObject()
                            .setDay(day)
                            .setFromTime(fromTime)
                            .setToTime(toTime));

                }

                restaurantList.add(new DataObject()
                        .setOffer_name(featured.getProductName())
                        .setOffer_image(featured.getProductBanner())
                        .setObject_id(featured.getId())
                        .setObject_name(featured.getName())
                        .setObject_Description(featured.getDescription())
                        .setObject_category_name(featured.getCategory())
                        .setObject_logo(featured.getLogo())
                        .setObject_picture(featured.getCoverUrl())
                        .setObject_latitude(featured.getLatitude())
                        .setObject_longitude(featured.getLongitude())
                        .setObject_address(featured.getLocation())
                        .setObject_delivery_charges(featured.getDelivery_charges())
                        .setObject_min_delivery_time(featured.getDeliveryTime())
                        .setObject_min_order_price(featured.getMinOrder())
                        .setObject_expense(featured.getExpense())
                        .setObject_rating(featured.getRating())
                        .setObject_no_of_rating(featured.getNo_of_ratings())
                        .setObject_currency(featured.getCurrency())
                        .setObject_currency_symbol(featured.getCurrency_symbol())
                         .setSchedule(new ArrayList<DataObject>(scheduleList))
                        .setObject_distance(featured.getDistance())
                        .setObject_tags(featured.getTags())
                        .setObject_date_created(featured.getDate())
                        .setReviewerList(new ArrayList<String>(featured.getReviewers()))
                        .setPaymentTypeList(new ArrayList<String>(featured.getPayment_method()))
                        .setDataType(Constant.DATA_TYPE.FEATURED));

            }
            if (restaurantList.size() > 0) {
                homeObject = new HomeObject()
                        .setData_type(Constant.DATA_TYPE.FEATURED)
                        .setDataObjectArrayList(new ArrayList<DataObject>(restaurantList));
                homeList.add(homeObject);
            }
            restaurantList.clear();

            /*List of Top Brands*/

            for (int i = 0; i < homeJson.getTrending().size(); i++) {

                Trending featured = homeJson.getTrending().get(i);

                ArrayList<DataObject> scheduleList = new ArrayList<>();

                for (int j = 0; j < featured.getRestaurant_status().getDay().size(); j++) {

                    RestaurantStatus restaurantStatus = featured.getRestaurant_status();
                    String day = restaurantStatus.getDay().get(j);
                    String fromTime = restaurantStatus.getFromTime().get(j);
                    String toTime = restaurantStatus.getToTime().get(j);

                    scheduleList.add(new DataObject()
                            .setDay(day)
                            .setFromTime(fromTime)
                            .setToTime(toTime));

                }

                restaurantList.add(new DataObject()
                        .setObject_id(featured.getId())
                        .setObject_name(featured.getName())
                        .setObject_Description(featured.getDescription())
                        .setObject_category_name(featured.getCategory())
                        .setObject_logo(featured.getLogo())
                        .setObject_picture(featured.getCoverUrl())
                        .setObject_latitude(featured.getLatitude())
                        .setObject_longitude(featured.getLongitude())
                        .setObject_address(featured.getLocation())
                        .setObject_delivery_charges(featured.getDelivery_charges())
                        .setObject_min_delivery_time(featured.getDeliveryTime())
                        .setObject_min_order_price(featured.getMinOrder())
                        .setObject_expense(featured.getExpense())
                        .setObject_rating(featured.getRating())
                        .setObject_no_of_rating(featured.getNo_of_ratings())
                        .setObject_currency(featured.getCurrency())
                        .setObject_currency_symbol(featured.getCurrency_symbol())
                         .setSchedule(new ArrayList<DataObject>(scheduleList))
                        .setObject_distance(featured.getDistance())
                        .setObject_tags(featured.getTags())
                        .setObject_date_created(featured.getDate())
                        .setReviewerList(new ArrayList<String>(featured.getReviewers()))
                        .setPaymentTypeList(new ArrayList<String>(featured.getPayment_method()))
                        .setDataType(Constant.DATA_TYPE.TOP_BRANDS));

            }
            if (restaurantList.size() > 0) {
                homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(requestObject.getContext(), R.string.popular_brands))
                        .setData_type(Constant.DATA_TYPE.TOP_BRANDS)
                        .setDataObjectArrayList(new ArrayList<DataObject>(restaurantList));
                homeList.add(homeObject);
            }
            restaurantList.clear();

            /*List of Nearest Restaurants*/

            for (int i = 0; i < homeJson.getNearest().size(); i++) {

                Nearest featured = homeJson.getNearest().get(i);
                ArrayList<DataObject> scheduleList = new ArrayList<>();

                for (int j = 0; j < featured.getRestaurant_status().getDay().size(); j++) {

                    RestaurantStatus restaurantStatus = featured.getRestaurant_status();
                    String day = restaurantStatus.getDay().get(j);
                    String fromTime = restaurantStatus.getFromTime().get(j);
                    String toTime = restaurantStatus.getToTime().get(j);

                    scheduleList.add(new DataObject()
                            .setDay(day)
                            .setFromTime(fromTime)
                            .setToTime(toTime));

                }

                Utility.Logger(TAG, "Day = " + featured.getRestaurant_status().getDay().size());
                Utility.Logger(TAG,"Schedule List = "+scheduleList.size());

                restaurantList.add(new DataObject()
                        .setObject_id(featured.getId())
                        .setObject_name(featured.getName())
                        .setObject_Description(featured.getDescription())
                        .setObject_category_name(featured.getCategory())
                        .setObject_logo(featured.getLogo())
                        .setObject_picture(featured.getCoverUrl())
                        .setObject_latitude(featured.getLatitude())
                        .setObject_longitude(featured.getLongitude())
                        .setObject_address(featured.getLocation())
                        .setObject_delivery_charges(featured.getDelivery_charges())
                        .setObject_min_delivery_time(featured.getDeliveryTime())
                        .setObject_min_order_price(featured.getMinOrder())
                        .setObject_expense(featured.getExpense())
                        .setObject_rating(featured.getRating())
                        .setObject_no_of_rating(featured.getNo_of_ratings())
                        .setObject_currency(featured.getCurrency())
                        .setObject_currency_symbol(featured.getCurrency_symbol())
                         .setSchedule(new ArrayList<DataObject>(scheduleList))
                        .setObject_distance(featured.getDistance())
                        .setObject_tags(featured.getTags())
                        .setObject_date_created(featured.getDate())
                        .setReviewerList(new ArrayList<String>(featured.getReviewers()))
                        .setPaymentTypeList(new ArrayList<String>(featured.getPayment_method()))
                        .setDataType(Constant.DATA_TYPE.NEAREST));

            }
            if (restaurantList.size() > 0) {
                homeObject = new HomeObject()
                        .setLabel(Utility.getStringFromRes(requestObject.getContext(), R.string.most_nearest))
                        .setData_type(Constant.DATA_TYPE.HEADING_BAR)
                        .setDataObjectArrayList(new ArrayList<DataObject>(restaurantList));
                homeList.add(homeObject);
            }


            //endregion

            dataObject = new DataObject()
                    .setCode(homeJson.getCode())
                    .setMessage(homeJson.getMessage())
                    .setObjectArrayList(restaurantList)
                    .setHomeList(homeList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.CONFIGURATION) {

            //region App Configuration

            ArrayList<DataObject> offerList = new ArrayList<>();
            ArrayList<Object> categoryList = new ArrayList<>();
            AppOfferJson appOfferJson = (AppOfferJson) data;

            for (int i = 0; i < appOfferJson.getOffers().size(); i++) {

                Offer offer = appOfferJson.getOffers().get(i);
                offerList.add(new DataObject()
                        .setOffer_id(offer.getId())
                        .setOffer_name(offer.getName())
                        .setOffer_image(offer.getBannerUrl())
                        .setOffer_latitude(offer.getLatitude())
                        .setOffer_longitude(offer.getLongitude()));

            }

            for (int i = 0; i < appOfferJson.getCategories().size(); i++) {

                Category category = appOfferJson.getCategories().get(i);
                categoryList.add(new DataObject()
                        .setCategory_id(category.getId())
                        .setCategory_name(category.getName()));

            }

            //endregion

            dataObject = new DataObject()
                    .setCode(appOfferJson.getCode())
                    .setMessage(appOfferJson.getMessage())
                    .setObjectArrayList(offerList)
                    .setHomeList(categoryList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.NEAREST) {

            //region Nearby Restaurants

            ArrayList<DataObject> restaurantList = new ArrayList<>();
            ArrayList<Object> homeList = new ArrayList<>();
            HomeJson homeJson = (HomeJson) data;
            HomeObject homeObject = null;


            /*List of Nearest Restaurants*/

            for (int i = 0; i < homeJson.getNearest().size(); i++) {

                Nearest featured = homeJson.getNearest().get(i);
                ArrayList<DataObject> scheduleList = new ArrayList<>();

                for (int j = 0; j < featured.getRestaurant_status().getDay().size(); j++) {

                    RestaurantStatus restaurantStatus = featured.getRestaurant_status();
                    String day = restaurantStatus.getDay().get(j);
                    String fromTime = restaurantStatus.getFromTime().get(j);
                    String toTime = restaurantStatus.getToTime().get(j);

                    scheduleList.add(new DataObject()
                            .setDay(day)
                            .setFromTime(fromTime)
                            .setToTime(toTime));

                }

                restaurantList.add(new DataObject()
                        .setObject_id(featured.getId())
                        .setObject_name(featured.getName())
                        .setObject_Description(featured.getDescription())
                        .setObject_category_name(featured.getCategory())
                        .setObject_logo(featured.getLogo())
                        .setObject_picture(featured.getCoverUrl())
                        .setObject_latitude(featured.getLatitude())
                        .setObject_longitude(featured.getLongitude())
                        .setObject_address(featured.getLocation())
                        .setObject_delivery_charges(featured.getDelivery_charges())
                        .setObject_min_delivery_time(featured.getDeliveryTime())
                        .setObject_min_order_price(featured.getMinOrder())
                        .setObject_expense(featured.getExpense())
                        .setObject_rating(featured.getRating())
                        .setObject_no_of_rating(featured.getNo_of_ratings())
                        .setObject_currency(featured.getCurrency())
                        .setObject_currency_symbol(featured.getCurrency_symbol())
                         .setSchedule(new ArrayList<DataObject>(scheduleList))
                        .setObject_distance(featured.getDistance())
                        .setObject_tags(featured.getTags())
                        .setObject_date_created(featured.getDate())
                        .setReviewerList(new ArrayList<String>(featured.getReviewers()))
                        .setPaymentTypeList(new ArrayList<String>(featured.getPayment_method()))
                        .setDataType(Constant.DATA_TYPE.NEAREST));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(homeJson.getCode())
                    .setMessage(homeJson.getMessage())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.ALL_CATEGORIES) {

            //region All Cuisines

            ArrayList<DataObject> restaurantList = new ArrayList<>();
            AppOfferJson appOfferJson = (AppOfferJson) data;
            HomeObject homeObject = null;


            /*List of All Categories*/

            for (int i = 0; i < appOfferJson.getCategories().size(); i++) {

                Category category = appOfferJson.getCategories().get(i);
                restaurantList.add(new DataObject()
                        .setCategory_id(category.getId())
                        .setCategory_name(category.getName())
                        .setDataType(Constant.DATA_TYPE.CATEGORIES));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(appOfferJson.getCode())
                    .setMessage(appOfferJson.getMessage())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.SEARCH) {

            //region Nearby Restaurants

            ArrayList<DataObject> restaurantList = new ArrayList<>();
            ArrayList<Object> homeList = new ArrayList<>();
            HomeJson homeJson = (HomeJson) data;
            HomeObject homeObject = null;


            /*List of Nearest Restaurants*/

            for (int i = 0; i < homeJson.getNearest().size(); i++) {

                Nearest featured = homeJson.getNearest().get(i);
                ArrayList<DataObject> scheduleList = new ArrayList<>();

                for (int j = 0; j < featured.getRestaurant_status().getDay().size(); j++) {

                    RestaurantStatus restaurantStatus = featured.getRestaurant_status();
                    String day = restaurantStatus.getDay().get(j);
                    String fromTime = restaurantStatus.getFromTime().get(j);
                    String toTime = restaurantStatus.getToTime().get(j);

                    scheduleList.add(new DataObject()
                            .setDay(day)
                            .setFromTime(fromTime)
                            .setToTime(toTime));

                }

                restaurantList.add(new DataObject()
                        .setObject_id(featured.getId())
                        .setObject_name(featured.getName())
                        .setObject_Description(featured.getDescription())
                        .setObject_category_name(featured.getCategory())
                        .setObject_logo(featured.getLogo())
                        .setObject_picture(featured.getCoverUrl())
                        .setObject_latitude(featured.getLatitude())
                        .setObject_longitude(featured.getLongitude())
                        .setObject_address(featured.getLocation())
                        .setObject_delivery_charges(featured.getDelivery_charges())
                        .setObject_min_delivery_time(featured.getDeliveryTime())
                        .setObject_min_order_price(featured.getMinOrder())
                        .setObject_expense(featured.getExpense())
                        .setObject_rating(featured.getRating())
                        .setObject_no_of_rating(featured.getNo_of_ratings())
                        .setObject_currency(featured.getCurrency())
                        .setObject_currency_symbol(featured.getCurrency_symbol())
                         .setSchedule(new ArrayList<DataObject>(scheduleList))
                        .setObject_distance(featured.getDistance())
                        .setObject_tags(featured.getTags())
                        .setObject_date_created(featured.getDate())
                        .setReviewerList(new ArrayList<String>(featured.getReviewers()))
                        .setPaymentTypeList(new ArrayList<String>(featured.getPayment_method()))
                        .setDataType(Constant.DATA_TYPE.NEAREST));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(homeJson.getCode())
                    .setMessage(homeJson.getMessage())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.RESTAURANT_DETAIL) {

            //region Menu Categories

            ArrayList<DataObject> restaurantList = new ArrayList<>();
            CategoryJson categoryJson = (CategoryJson) data;


            /*List of Restaurant Menu Categories*/

            for (int i = 0; i < categoryJson.getCategories().size(); i++) {

                com.haris.meal4u.JsonUtil.CategoryUtil.Category category =
                        categoryJson.getCategories().get(i);

                restaurantList.add(new DataObject()
                        .setCategory_id(category.getId())
                        .setCategory_name(category.getName())
                        .setDataType(Constant.DATA_TYPE.CATEGORIES));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(categoryJson.getCode())
                    .setMessage(categoryJson.getMessage())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.PRODUCT_MENU) {

            //region Menu Products

            ArrayList<DataObject> restaurantList = new ArrayList<>();
            ProductJson productJson = (ProductJson) data;

            /* List of Restaurant Menu Products */

            for (int i = 0; i < productJson.getProducts().size(); i++) {

                Product product = productJson.getProducts().get(i);
                Utility.Logger(TAG, "Size = " + product.getProductAttribute().size());
                restaurantList.add(new DataObject()
                        .setPost_id(product.getId())
                        .setPost_name(product.getName())
                        .setPost_description(product.getDescription())
                        .setPost_image(product.getImage())
                        .setPost_price(product.getPrice())
                        .setProductAttribute(product.getProductAttribute())
                        .setDataType(Constant.DATA_TYPE.PRODUCT));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(productJson.getCode())
                    .setMessage(productJson.getMessage())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.REDEEM_COUPON) {

            ArrayList<DataObject> restaurantList = new ArrayList<>();
            CouponJson couponJson = (CouponJson) data;


            dataObject = new DataObject()
                    .setCode(couponJson.getCode())
                    .setMessage(couponJson.getMessage())
                    .setCoupon_reward(couponJson.getCouponReward())
                    .setCoupon_id(couponJson.getCouponId())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.PAYMENT_CARDS
                || requestObject.getConnection() == Constant.CONNECTION.ADD_CARD) {

            ArrayList<DataObject> cardList = new ArrayList<>();
            CardJson cardJson = (CardJson) data;

            for (int i = 0; i < cardJson.getCards().size(); i++) {
                Card card = cardJson.getCards().get(i);
                cardList.add(new DataObject()
                        .setPayment_id(card.getId())
                        .setPayment_card_company(card.getCardName())
                        .setPayment_card_no(card.getCardNo())
                        .setStripe_customer_no(card.getStripeCustomerId()));
            }

            dataObject = new DataObject()
                    .setCode(cardJson.getCode())
                    .setMessage(cardJson.getMessage())
                    .setObjectArrayList(cardList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.CHECK_OUT) {

            //region Check out

            OrderJson orderJson = (OrderJson) data;
            dataObject = new DataObject()
                    .setCode(orderJson.getCode())
                    .setMessage(orderJson.getMessage())
                    .setOrder_id(orderJson.getOrder_id());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.ALL_FAVOURITES) {

            //region Retrieve All Favourites

            ArrayList<DataObject> restaurantList = new ArrayList<>();
            ArrayList<Object> homeList = new ArrayList<>();
            HomeJson homeJson = (HomeJson) data;
            HomeObject homeObject = null;


            /*List of Nearest Restaurants*/

            for (int i = 0; i < homeJson.getNearest().size(); i++) {

                Nearest featured = homeJson.getNearest().get(i);
                ArrayList<DataObject> scheduleList = new ArrayList<>();

                for (int j = 0; j < featured.getRestaurant_status().getDay().size(); j++) {

                    RestaurantStatus restaurantStatus = featured.getRestaurant_status();
                    String day = restaurantStatus.getDay().get(j);
                    String fromTime = restaurantStatus.getFromTime().get(j);
                    String toTime = restaurantStatus.getToTime().get(j);

                    scheduleList.add(new DataObject()
                            .setDay(day)
                            .setFromTime(fromTime)
                            .setToTime(toTime));

                }

                restaurantList.add(new DataObject()
                        .setObject_id(featured.getId())
                        .setObject_name(featured.getName())
                        .setObject_Description(featured.getDescription())
                        .setObject_category_name(featured.getCategory())
                        .setObject_logo(featured.getLogo())
                        .setObject_picture(featured.getCoverUrl())
                        .setObject_latitude(featured.getLatitude())
                        .setObject_longitude(featured.getLongitude())
                        .setObject_address(featured.getLocation())
                        .setObject_delivery_charges(featured.getDelivery_charges())
                        .setObject_min_delivery_time(featured.getDeliveryTime())
                        .setObject_min_order_price(featured.getMinOrder())
                        .setObject_expense(featured.getExpense())
                        .setObject_rating(featured.getRating())
                        .setObject_no_of_rating(featured.getNo_of_ratings())
                        .setObject_currency(featured.getCurrency())
                        .setObject_currency_symbol(featured.getCurrency_symbol())
                         .setSchedule(new ArrayList<DataObject>(scheduleList))
                        .setObject_distance(featured.getDistance())
                        .setObject_tags(featured.getTags())
                        .setObject_date_created(featured.getDate())
                        .setReviewerList(new ArrayList<String>(featured.getReviewers()))
                        .setDataType(Constant.DATA_TYPE.NEAREST));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(homeJson.getCode())
                    .setMessage(homeJson.getMessage())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.ALL_COMMENT) {

            //region Retrieve All Reviews

            ArrayList<DataObject> reviewList = new ArrayList<>();
            ReviewJson reviewJson = (ReviewJson) data;

            /*List of Reviews */

            for (int i = 0; i < reviewJson.getReviews().size(); i++) {

                Review review = reviewJson.getReviews().get(i);

                DateTimeObject dateTimeObject = Utility.parseTimeDate(new DateTimeObject()
                        .setDatetimeType(Constant.DATETIME.BOTH12)
                        .setDatetime(review.getDateCreated()));

                reviewList.add(new DataObject()
                        .setReview_id(review.getId())
                        .setReview_title(review.getUserName())
                        .setReviewer_picture(review.getUserPicture())
                        .setReviews(review.getReview())
                        .setRating(review.getRating())
                        .setReview_date(dateTimeObject.getDate())
                        .setReviewPictureList(new ArrayList<String>(review.getReviewPictures()))
                        .setDataType(Constant.DATA_TYPE.REVIEW));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(reviewJson.getCode())
                    .setMessage(reviewJson.getMessage())
                    .setObjectArrayList(reviewList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.SIGN_UP) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage())
                    .setUser_id(userJson.getId())
                    .setUser_fName(userJson.getFName())
                    .setUser_lName(userJson.getLName())
                    .setUser_email(userJson.getEmail())
                    .setUser_password(userJson.getPass())
                    .setPhone(userJson.getPhone())
                    .setLogin_type(userJson.getUserType())
                    .setUser_picture(userJson.getAvatar());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.LOGIN) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage())
                    .setUser_id(userJson.getId())
                    .setUser_fName(userJson.getFName())
                    .setUser_lName(userJson.getLName())
                    .setUser_email(userJson.getEmail())
                    .setUser_password(userJson.getPass())
                    .setPhone(userJson.getPhone())
                    .setLogin_type(userJson.getUserType())
                    .setUser_picture(userJson.getAvatar())
                    .setUserFavourite(new ArrayList<FavouriteList>(userJson.getFavouriteList()));

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.FORGOT) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.UPDATE) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage());

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.SOCIAL_LOGIN) {

            UserJson userJson = (UserJson) data;
            dataObject = new DataObject()
                    .setCode(userJson.getCode())
                    .setMessage(userJson.getMessage())
                    .setUser_id(userJson.getId())
                    .setUser_fName(userJson.getFName())
                    .setUser_lName(userJson.getLName())
                    .setUser_email(userJson.getEmail())
                    .setUser_password(userJson.getPass())
                    .setPhone(userJson.getPhone())
                    .setLogin_type(userJson.getUserType())
                    .setUser_picture(userJson.getAvatar())
                    .setUserFavourite(new ArrayList<FavouriteList>(userJson.getFavouriteList()));

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.ORDER_HISTORY) {

            //region Retrieve Order

            ArrayList<DataObject> orderList = new ArrayList<>();
            OrderHistoryJson orderHistoryJson = (OrderHistoryJson) data;

            /*List of Order List */

            for (int i = 0; i < orderHistoryJson.getOrderList().size(); i++) {

                OrderList orderHistory = orderHistoryJson.getOrderList().get(i);

                DateTimeObject dateTimeObject = Utility.parseTimeDate(new DateTimeObject()
                        .setDatetimeType(Constant.DATETIME.BOTH12)
                        .setDatetime(orderHistory.getDeliveryDate()));

                ArrayList<DataObject> productDetailList = new ArrayList<>();

                for (int j = 0; j < orderHistory.getProductOrderDetail().size(); j++) {

                    ProductOrderDetail productOrderDetail = orderHistory.getProductOrderDetail().get(j);
                    productDetailList.add(new DataObject()
                            .setOrder_product_name(productOrderDetail.getProductName())
                            .setOrder_product_price(productOrderDetail.getProductPrice())
                            .setOrder_product_quantity(productOrderDetail.getProductQuantity()));

                }

                orderList.add(new DataObject()
                        .setOrder_id(orderHistory.getId())
                        .setObject_id(orderHistory.getRestaurant_id())
                        .setObject_logo(orderHistory.getRestaurantLogo())
                        .setOrder_restaurant_name(orderHistory.getRestaurantName())
                        .setOrder_price(orderHistory.getOrderPrice())
                        .setOrder_delivery_date(dateTimeObject.getDate())
                        .setOrder_delivery_time(dateTimeObject.getTime())
                        .setDelivery_time(orderHistory.getDeliveryTime())
                        .setDelivery_fee(orderHistory.getDelivery_fee())
                        .setPaymentType(orderHistory.getPaymentType())
                        .setOrder_status(orderHistory.getOrderStatus())
                        .setReview_status(Utility.isEmptyString(orderHistory.getReviewStatus()) ?
                                "1" : orderHistory.getReviewStatus())
                        .setRider_review_status(Utility.isEmptyString(orderHistory.getRiderReviewStatus()) ?
                                "1" : orderHistory.getRiderReviewStatus())
                        .setProduct_order_detail_list(productDetailList));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(orderHistoryJson.getCode())
                    .setMessage(orderHistoryJson.getMessage())
                    .setObjectArrayList(orderList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.SPECIFIC_RESTAURANT) {

            //region Specific Restaurant

            ArrayList<DataObject> restaurantList = new ArrayList<>();
            ArrayList<Object> homeList = new ArrayList<>();
            HomeJson homeJson = (HomeJson) data;
            HomeObject homeObject = null;


            /*List of Nearest Restaurants*/

            for (int i = 0; i < homeJson.getNearest().size(); i++) {

                Nearest featured = homeJson.getNearest().get(i);
                ArrayList<DataObject> scheduleList = new ArrayList<>();

                for (int j = 0; j < featured.getRestaurant_status().getDay().size(); j++) {

                    RestaurantStatus restaurantStatus = featured.getRestaurant_status();
                    String day = restaurantStatus.getDay().get(j);
                    String fromTime = restaurantStatus.getFromTime().get(j);
                    String toTime = restaurantStatus.getToTime().get(j);

                    scheduleList.add(new DataObject()
                            .setDay(day)
                            .setFromTime(fromTime)
                            .setToTime(toTime));

                }

                restaurantList.add(new DataObject()
                        .setObject_id(featured.getId())
                        .setObject_name(featured.getName())
                        .setObject_Description(featured.getDescription())
                        .setObject_category_name(featured.getCategory())
                        .setObject_logo(featured.getLogo())
                        .setObject_picture(featured.getCoverUrl())
                        .setObject_latitude(featured.getLatitude())
                        .setObject_longitude(featured.getLongitude())
                        .setObject_address(featured.getLocation())
                        .setObject_delivery_charges(featured.getDelivery_charges())
                        .setObject_min_delivery_time(featured.getDeliveryTime())
                        .setObject_min_order_price(featured.getMinOrder())
                        .setObject_expense(featured.getExpense())
                        .setObject_rating(featured.getRating())
                        .setObject_no_of_rating(featured.getNo_of_ratings())
                        .setObject_currency(featured.getCurrency())
                        .setObject_currency_symbol(featured.getCurrency_symbol())
                         .setSchedule(new ArrayList<DataObject>(scheduleList))
                        .setObject_distance(featured.getDistance())
                        .setObject_tags(featured.getTags())
                        .setObject_date_created(featured.getDate())
                        .setReviewerList(new ArrayList<String>(featured.getReviewers()))
                        .setPaymentTypeList(new ArrayList<String>(featured.getPayment_method()))
                        .setDataType(Constant.DATA_TYPE.NEAREST));

            }


            //endregion

            dataObject = new DataObject()
                    .setCode(homeJson.getCode())
                    .setMessage(homeJson.getMessage())
                    .setObjectArrayList(restaurantList);

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.DELIVERY_CHARGES) {

            DeliveryDetailJson deliveryDetailJson = (DeliveryDetailJson) data;
            dataObject = new DataObject()
                    .setCode(deliveryDetailJson.getCode())
                    .setMessage(deliveryDetailJson.getMessage())
                    .setDelivery_charges(deliveryDetailJson.getDeliveryCharges());

        }


        return dataObject;

    }


    @Override
    public String toString() {
        return "DataObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", category_id='" + category_id + '\'' +
                ", category_name='" + category_name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_fName='" + user_fName + '\'' +
                ", user_lName='" + user_lName + '\'' +
                ", user_password='" + user_password + '\'' +
                ", login_type='" + login_type + '\'' +
                ", user_picture='" + user_picture + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_sign_in='" + user_sign_in + '\'' +
                ", user_remember='" + user_remember + '\'' +
                ", userFavourite=" + userFavourite +
                ", user_payment_type='" + user_payment_type + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", payee_name='" + payee_name + '\'' +
                ", payment_card_company='" + payment_card_company + '\'' +
                ", payment_card_no='" + payment_card_no + '\'' +
                ", payment_cvv_no='" + payment_cvv_no + '\'' +
                ", payment_expiry_date='" + payment_expiry_date + '\'' +
                ", stripe_customer_no='" + stripe_customer_no + '\'' +
                ", object_id='" + object_id + '\'' +
                ", object_name='" + object_name + '\'' +
                ", object_Description='" + object_Description + '\'' +
                ", object_category_name='" + object_category_name + '\'' +
                ", object_min_delivery_time='" + object_min_delivery_time + '\'' +
                ", object_min_order_price='" + object_min_order_price + '\'' +
                ", object_delivery_charges='" + object_delivery_charges + '\'' +
                ", object_expense='" + object_expense + '\'' +
                ", object_rating='" + object_rating + '\'' +
                ", object_no_of_rating='" + object_no_of_rating + '\'' +
                ", object_currency='" + object_currency + '\'' +
                ", object_currency_symbol='" + object_currency_symbol + '\'' +
                ", object_status='" + object_status + '\'' +
                ", object_latitude='" + object_latitude + '\'' +
                ", object_longitude='" + object_longitude + '\'' +
                ", object_address='" + object_address + '\'' +
                ", object_picture='" + object_picture + '\'' +
                ", object_logo='" + object_logo + '\'' +
                ", object_distance='" + object_distance + '\'' +
                ", object_tags='" + object_tags + '\'' +
                ", object_date_created='" + object_date_created + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", reviewerList=" + reviewerList +
                ", paymentTypeList=" + paymentTypeList +
                ", is_layout_01=" + is_layout_01 +
                ", review_id='" + review_id + '\'' +
                ", review_title='" + review_title + '\'' +
                ", review_date='" + review_date + '\'' +
                ", reviews='" + reviews + '\'' +
                ", rating='" + rating + '\'' +
                ", reviewer_picture='" + reviewer_picture + '\'' +
                ", reviewPictureList=" + reviewPictureList +
                ", coupon_id='" + coupon_id + '\'' +
                ", coupon_code='" + coupon_code + '\'' +
                ", coupon_reward='" + coupon_reward + '\'' +
                ", coupon_start_date='" + coupon_start_date + '\'' +
                ", coupon_end_date='" + coupon_end_date + '\'' +
                ", coupon_date_created='" + coupon_date_created + '\'' +
                ", offer_id='" + offer_id + '\'' +
                ", offer_image='" + offer_image + '\'' +
                ", offer_latitude='" + offer_latitude + '\'' +
                ", offer_longitude='" + offer_longitude + '\'' +
                ", offer_name='" + offer_name + '\'' +
                ", post_id='" + post_id + '\'' +
                ", post_name='" + post_name + '\'' +
                ", post_image='" + post_image + '\'' +
                ", post_description='" + post_description + '\'' +
                ", post_price='" + post_price + '\'' +
                ", post_quantity='" + post_quantity + '\'' +
                ", post_attribute_id='" + post_attribute_id + '\'' +
                ", post_attribute_name='" + post_attribute_name + '\'' +
                ", special_instructions='" + special_instructions + '\'' +
                ", productAttribute=" + productAttribute +
                ", addressObject=" + addressObject +
                ", billingObject=" + billingObject +
                ", scheduleObject=" + scheduleObject +
                ", order_id='" + order_id + '\'' +
                ", order_restaurant_name='" + order_restaurant_name + '\'' +
                ", order_delivery_date='" + order_delivery_date + '\'' +
                ", order_delivery_time='" + order_delivery_time + '\'' +
                ", order_price='" + order_price + '\'' +
                ", delivery_time='" + delivery_time + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", order_status='" + order_status + '\'' +
                ", dataType=" + dataType +
                ", objectArrayList=" + objectArrayList +
                ", homeList=" + homeList +
                ", favourite_id='" + favourite_id + '\'' +
                ", isFavourite=" + isFavourite +
                ", cart_id='" + cart_id + '\'' +
                ", chatting='" + chatting + '\'' +
                ", picture='" + picture + '\'' +
                ", isFrom=" + isFrom +
                ", isLongTap=" + isLongTap +
                ", isFirstItem=" + isFirstItem +
                ", isAlreadyAddedIntoCart=" + isAlreadyAddedIntoCart +
                ", isPaymentCardSelected=" + isPaymentCardSelected +
                ", noOfItemInCart='" + noOfItemInCart + '\'' +
                ", basePrice='" + basePrice + '\'' +
                '}';
    }

    public DataObject() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeString(this.category_id);
        dest.writeString(this.category_name);
        dest.writeString(this.user_id);
        dest.writeString(this.user_fName);
        dest.writeString(this.user_lName);
        dest.writeString(this.user_password);
        dest.writeString(this.login_type);
        dest.writeString(this.user_picture);
        dest.writeString(this.user_phone);
        dest.writeString(this.user_email);
        dest.writeString(this.user_sign_in);
        dest.writeString(this.user_remember);
        dest.writeTypedList(this.userFavourite);
        dest.writeString(this.user_payment_type);
        dest.writeString(this.payment_id);
        dest.writeString(this.payee_name);
        dest.writeString(this.payment_card_company);
        dest.writeString(this.payment_card_no);
        dest.writeString(this.payment_cvv_no);
        dest.writeString(this.payment_expiry_date);
        dest.writeString(this.stripe_customer_no);
        dest.writeString(this.object_id);
        dest.writeString(this.object_name);
        dest.writeString(this.object_Description);
        dest.writeString(this.object_category_name);
        dest.writeString(this.object_min_delivery_time);
        dest.writeString(this.object_min_order_price);
        dest.writeString(this.object_delivery_charges);
        dest.writeString(this.object_expense);
        dest.writeString(this.object_rating);
        dest.writeString(this.object_no_of_rating);
        dest.writeString(this.object_currency);
        dest.writeString(this.object_currency_symbol);
        dest.writeString(this.object_status);
        dest.writeString(this.object_latitude);
        dest.writeString(this.object_longitude);
        dest.writeString(this.object_address);
        dest.writeString(this.object_picture);
        dest.writeString(this.object_logo);
        dest.writeString(this.object_distance);
        dest.writeString(this.object_tags);
        dest.writeString(this.object_date_created);
        dest.writeString(this.paymentType);
        dest.writeString(this.day);
        dest.writeString(this.fromTime);
        dest.writeString(this.toTime);
        dest.writeTypedList(this.schedule);
        dest.writeStringList(this.reviewerList);
        dest.writeStringList(this.paymentTypeList);
        dest.writeByte(this.is_layout_01 ? (byte) 1 : (byte) 0);
        dest.writeString(this.review_id);
        dest.writeString(this.review_title);
        dest.writeString(this.review_date);
        dest.writeString(this.reviews);
        dest.writeString(this.rating);
        dest.writeString(this.reviewer_picture);
        dest.writeStringList(this.reviewPictureList);
        dest.writeString(this.coupon_id);
        dest.writeString(this.coupon_code);
        dest.writeString(this.coupon_reward);
        dest.writeString(this.coupon_start_date);
        dest.writeString(this.coupon_end_date);
        dest.writeString(this.coupon_date_created);
        dest.writeString(this.offer_id);
        dest.writeString(this.offer_image);
        dest.writeString(this.offer_latitude);
        dest.writeString(this.offer_longitude);
        dest.writeString(this.offer_name);
        dest.writeString(this.post_id);
        dest.writeString(this.post_name);
        dest.writeString(this.post_image);
        dest.writeString(this.post_description);
        dest.writeString(this.post_price);
        dest.writeString(this.post_quantity);
        dest.writeString(this.post_attribute_id);
        dest.writeString(this.post_attribute_name);
        dest.writeString(this.special_instructions);
        dest.writeTypedList(this.productAttribute);
        dest.writeParcelable(this.addressObject, flags);
        dest.writeParcelable(this.billingObject, flags);
        dest.writeParcelable(this.scheduleObject, flags);
        dest.writeString(this.order_id);
        dest.writeString(this.order_restaurant_name);
        dest.writeString(this.order_delivery_date);
        dest.writeString(this.order_delivery_time);
        dest.writeString(this.order_price);
        dest.writeString(this.delivery_time);
        dest.writeString(this.delivery_fee);
        dest.writeString(this.payment_type);
        dest.writeString(this.order_status);
        dest.writeString(this.review_status);
        dest.writeString(this.rider_review_status);
        dest.writeString(this.order_product_name);
        dest.writeString(this.order_product_quantity);
        dest.writeString(this.order_product_price);
        dest.writeTypedList(this.product_order_detail_list);
        dest.writeInt(this.dataType == null ? -1 : this.dataType.ordinal());
        dest.writeTypedList(this.objectArrayList);
        dest.writeList(this.homeList);
        dest.writeString(this.favourite_id);
        dest.writeByte(this.isFavourite ? (byte) 1 : (byte) 0);
        dest.writeString(this.cart_id);
        dest.writeString(this.chatting);
        dest.writeString(this.picture);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeByte(this.isFrom ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isLongTap ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFirstItem ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAlreadyAddedIntoCart ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isPaymentCardSelected ? (byte) 1 : (byte) 0);
        dest.writeString(this.noOfItemInCart);
        dest.writeString(this.basePrice);
        dest.writeString(this.delivery_charges);
    }

    protected DataObject(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.category_id = in.readString();
        this.category_name = in.readString();
        this.user_id = in.readString();
        this.user_fName = in.readString();
        this.user_lName = in.readString();
        this.user_password = in.readString();
        this.login_type = in.readString();
        this.user_picture = in.readString();
        this.user_phone = in.readString();
        this.user_email = in.readString();
        this.user_sign_in = in.readString();
        this.user_remember = in.readString();
        this.userFavourite = in.createTypedArrayList(FavouriteList.CREATOR);
        this.user_payment_type = in.readString();
        this.payment_id = in.readString();
        this.payee_name = in.readString();
        this.payment_card_company = in.readString();
        this.payment_card_no = in.readString();
        this.payment_cvv_no = in.readString();
        this.payment_expiry_date = in.readString();
        this.stripe_customer_no = in.readString();
        this.object_id = in.readString();
        this.object_name = in.readString();
        this.object_Description = in.readString();
        this.object_category_name = in.readString();
        this.object_min_delivery_time = in.readString();
        this.object_min_order_price = in.readString();
        this.object_delivery_charges = in.readString();
        this.object_expense = in.readString();
        this.object_rating = in.readString();
        this.object_no_of_rating = in.readString();
        this.object_currency = in.readString();
        this.object_currency_symbol = in.readString();
        this.object_status = in.readString();
        this.object_latitude = in.readString();
        this.object_longitude = in.readString();
        this.object_address = in.readString();
        this.object_picture = in.readString();
        this.object_logo = in.readString();
        this.object_distance = in.readString();
        this.object_tags = in.readString();
        this.object_date_created = in.readString();
        this.paymentType = in.readString();
        this.day = in.readString();
        this.fromTime = in.readString();
        this.toTime = in.readString();
        this.schedule = in.createTypedArrayList(DataObject.CREATOR);
        this.reviewerList = in.createStringArrayList();
        this.paymentTypeList = in.createStringArrayList();
        this.is_layout_01 = in.readByte() != 0;
        this.review_id = in.readString();
        this.review_title = in.readString();
        this.review_date = in.readString();
        this.reviews = in.readString();
        this.rating = in.readString();
        this.reviewer_picture = in.readString();
        this.reviewPictureList = in.createStringArrayList();
        this.coupon_id = in.readString();
        this.coupon_code = in.readString();
        this.coupon_reward = in.readString();
        this.coupon_start_date = in.readString();
        this.coupon_end_date = in.readString();
        this.coupon_date_created = in.readString();
        this.offer_id = in.readString();
        this.offer_image = in.readString();
        this.offer_latitude = in.readString();
        this.offer_longitude = in.readString();
        this.offer_name = in.readString();
        this.post_id = in.readString();
        this.post_name = in.readString();
        this.post_image = in.readString();
        this.post_description = in.readString();
        this.post_price = in.readString();
        this.post_quantity = in.readString();
        this.post_attribute_id = in.readString();
        this.post_attribute_name = in.readString();
        this.special_instructions = in.readString();
        this.productAttribute = in.createTypedArrayList(ProductAttribute.CREATOR);
        this.addressObject = in.readParcelable(AddressObject.class.getClassLoader());
        this.billingObject = in.readParcelable(BillingObject.class.getClassLoader());
        this.scheduleObject = in.readParcelable(ScheduleObject.class.getClassLoader());
        this.order_id = in.readString();
        this.order_restaurant_name = in.readString();
        this.order_delivery_date = in.readString();
        this.order_delivery_time = in.readString();
        this.order_price = in.readString();
        this.delivery_time = in.readString();
        this.delivery_fee = in.readString();
        this.payment_type = in.readString();
        this.order_status = in.readString();
        this.review_status = in.readString();
        this.rider_review_status = in.readString();
        this.order_product_name = in.readString();
        this.order_product_quantity = in.readString();
        this.order_product_price = in.readString();
        this.product_order_detail_list = in.createTypedArrayList(DataObject.CREATOR);
        int tmpDataType = in.readInt();
        this.dataType = tmpDataType == -1 ? null : Constant.DATA_TYPE.values()[tmpDataType];
        this.objectArrayList = in.createTypedArrayList(DataObject.CREATOR);
        this.homeList = new ArrayList<Object>();
        in.readList(this.homeList, Object.class.getClassLoader());
        this.favourite_id = in.readString();
        this.isFavourite = in.readByte() != 0;
        this.cart_id = in.readString();
        this.chatting = in.readString();
        this.picture = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.isFrom = in.readByte() != 0;
        this.isLongTap = in.readByte() != 0;
        this.isFirstItem = in.readByte() != 0;
        this.isAlreadyAddedIntoCart = in.readByte() != 0;
        this.isPaymentCardSelected = in.readByte() != 0;
        this.noOfItemInCart = in.readString();
        this.basePrice = in.readString();
        this.delivery_charges = in.readString();
    }

    public static final Creator<DataObject> CREATOR = new Creator<DataObject>() {
        @Override
        public DataObject createFromParcel(Parcel source) {
            return new DataObject(source);
        }

        @Override
        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };
}
