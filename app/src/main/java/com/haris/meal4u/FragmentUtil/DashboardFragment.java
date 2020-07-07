package com.haris.meal4u.FragmentUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.haris.meal4u.ActivityUtil.Base;
import com.haris.meal4u.ActivityUtil.ListOfReviews;
import com.haris.meal4u.ActivityUtil.OnBoarding;
import com.haris.meal4u.ActivityUtil.ProductCart;
import com.haris.meal4u.ActivityUtil.RestaurantInformation;
import com.haris.meal4u.AdapterUtil.CategoriesTabPager;
import com.haris.meal4u.BuildConfig;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.CustomUtil.GlideApp;
import com.haris.meal4u.DatabaseUtil.DatabaseObject;
import com.haris.meal4u.DateUtil.DateBuilder;
import com.haris.meal4u.DateUtil.DateConstraint;
import com.haris.meal4u.FontUtil.Font;
import com.haris.meal4u.FragmentUtil.MenuFragment;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.PagerTabObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class DashboardFragment extends Fragment implements View.OnClickListener, ConnectionCallback {
    private String TAG = DashboardFragment.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private ArrayList<Object> favouriteArraylist = new ArrayList<>();
    private HashMap<String, String> favouriteMap = new HashMap<>();
    private ArrayList<Object> recordList = new ArrayList<>();
    private PrefObject prefObject;
    private ImageView imageShare;
    private ImageView imageDetail;
    private ImageView imageReport;
    private ImageView imageLogo;
    private ImageView imageCover;
    private TextView txtName;
    private TextView txtTags;
    private TextView txtStatus;
    private TextView txtDistance;
    private TextView txtRating;
    private TextView txtDeliveryTime;
    private TextView txtMinimumCharges;
    private TextView txtExpense;
    private ImageView imageFavourite;
    private ViewPager viewPagerCategories;
    private ArrayList<PagerTabObject> fragmentArrayList = new ArrayList<>();
    private CategoriesTabPager categoriesPager;
    private TabLayout layoutTab;
    private RelativeLayout scrollerComment;
    private TextView txtReviewTagline;
    private int reviewSize;
    private int totalBudgetOfCart = 0;
    private int totalNoOfItem;
    ArrayList<String> reviewList = new ArrayList<>();
    private ArrayList<Object> cartList = new ArrayList<>();
    private TextView txtCount;
    private RelativeLayout layoutViewCart;
    private TextView txtTotalBudget;
    private LinearLayout layoutComment;

    DataObject dataObject = new DataObject();


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View v) {
        dataObject.setObject_id("1");
        management = new Management(getActivity());

        imageBack = v.findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageCover = (ImageView) v.findViewById(R.id.image_cover);
        imageShare = v.findViewById(R.id.image_share);
        imageDetail = v.findViewById(R.id.image_detail);
        txtName = (TextView) v.findViewById(R.id.txt_name);
        txtTags = (TextView) v.findViewById(R.id.txt_tags);
        txtStatus = v.findViewById(R.id.txt_status);
        txtRating = (TextView) v.findViewById(R.id.txt_rating);
        txtDeliveryTime = (TextView) v.findViewById(R.id.txt_delivery_time);
        txtMinimumCharges = (TextView) v.findViewById(R.id.txt_minimum_charges);
        txtExpense = (TextView) v.findViewById(R.id.txt_expense);
        imageFavourite = (ImageView) v.findViewById(R.id.image_favourite);

        layoutViewCart = (RelativeLayout) v.findViewById(R.id.layout_view_cart);
        txtCount = (TextView) v.findViewById(R.id.txt_count);
        txtTotalBudget = (TextView) v.findViewById(R.id.txt_total_budget);

        layoutComment = v.findViewById(R.id.layout_comment);

        /*for (int i = 0; i < dataObject.getSchedule().size(); i++) {

            DataObject scheduleObject = (DataObject) dataObject.getSchedule().get(i);

            String result = new DateBuilder()
                    .setDateTimAction(DateConstraint.DateTimAction.FIND_DATE_TIME)
                    .setDateTimeConstraint(DateConstraint.DateTimeConstraint.CURRENT_DAY_FULL_NAME)
                    .setDateTimeFormatConstraint(DateConstraint.DateTimeFormatConstraint.eeee)
                    .setGivenDateTime(scheduleObject.getDay())
                    .buildDateTime();

            if (result.equalsIgnoreCase(scheduleObject.getDay())) {

                result = new DateBuilder()
                        .setDateTimAction(DateConstraint.DateTimAction.COMPARE_DATE_TIME)
                        .setDateTimeConstraint(DateConstraint.DateTimeConstraint.CHECK_DATE_LIES_BTW_TWO_CUSTOM_TIMES)
                        .setDateTimeFormatConstraint(DateConstraint.DateTimeFormatConstraint.hh_mm)
                        .setFromDateTime(scheduleObject.getFromTime())
                        .setToDateTime(scheduleObject.getToTime())
                        .setCurrentDay(false)
                        .buildDateTime();

                if (result.equalsIgnoreCase(DateConstraint.DateTimeResult.MATCH)) {
                    Utility.Logger(TAG, "Open");
                    txtStatus.setText(Utility.getStringFromRes(getActivity(), R.string.open));
                } else {
                    Utility.Logger(TAG, "Closed");
                    txtStatus.setText(Utility.getStringFromRes(getActivity(), R.string.closed));
                }

                break;

            } else {
                txtStatus.setText(Utility.getStringFromRes(getActivity(), R.string.closed));
            }

        }*/


        //reviewList.addAll(dataObject.getReviewerList());
        reviewSize = reviewList.size() < 5 ? reviewList.size() :
                reviewList.size() - (reviewList.size() - 5);

        scrollerComment = v.findViewById(R.id.scroller_comment);
        txtReviewTagline = v.findViewById(R.id.txt_review_tagline);
        txtReviewTagline.setText(reviewSize + " " + Utility.getStringFromRes(getActivity(), R.string.review_tagline));

        for (int i = 0; i < reviewSize; i++) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.reviewer_item_layout, null, false);

            ImageView imageUser = view.findViewById(R.id.image_reviewer);
            TextView txtCount = view.findViewById(R.id.txt_count);

            GlideApp.with(getActivity())
                    .load(Constant.ServerInformation.PICTURE_URL + reviewList.get(i))
                    .centerCrop().into(imageUser);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageUser.getLayoutParams();

            if (i > 0)
                params.setMarginStart(Utility.dpToPx(20 * i)); //substitute parameters for left, top, right, bottom

            imageUser.setLayoutParams(params);
            if (reviewSize > 4 && i == reviewSize - 1) {
                txtCount.setText(reviewList.size() + "+");
                txtCount.setLayoutParams(params);
                txtCount.setVisibility(View.VISIBLE);
            }

            scrollerComment.addView(view);
        }

        viewPagerCategories = (ViewPager) v.findViewById(R.id.view_pager_categories);
        categoriesPager = new CategoriesTabPager(getChildFragmentManager(), fragmentArrayList);
        viewPagerCategories.setAdapter(categoriesPager);

        layoutTab = v.findViewById(R.id.layout_tab);
        layoutTab.setupWithViewPager(viewPagerCategories);

        //txtName.setText(dataObject.getObject_name());
        //txtTags.setText(dataObject.getObject_tags());
        //txtDeliveryTime.setText(dataObject.getObject_min_delivery_time());
        //txtMinimumCharges.setText(dataObject.getObject_currency_symbol() + " " + dataObject.getObject_min_order_price());
        //txtExpense.setText(Utility.getBudgetType(getActivity(), dataObject.getObject_currency_symbol(), dataObject.getObject_expense()));
        //txtRating.setText(dataObject.getObject_rating());


        GlideApp.with(getActivity()).load(Constant.ServerInformation.PICTURE_URL + dataObject.getObject_picture())
                .centerCrop().into(imageCover);

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson(dataObject.getObject_id()))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RESTAURANT_DETAIL)
                .setConnectionCallback(this));

        layoutViewCart.setOnClickListener(this);
        imageShare.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        layoutComment.setOnClickListener(this);
        imageDetail.setOnClickListener(this);

    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String restaurant_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "specific_restaurant_menu");
            jsonObject.accumulate("restaurant_id", restaurant_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getReportJson(String userId, String postId, String comment) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "add_report");
            jsonObject.accumulate("user_id", userId);
            jsonObject.accumulate("post_id", postId);
            jsonObject.accumulate("comment", comment);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    @Override
    public void onResume() {
        super.onResume();


        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserId(true).setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        cartList.clear();
        cartList.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setTypeOperation(Constant.TYPE.CART)
                .setDbOperation(Constant.DB.SPECIFIC_TYPE)
                .setDataObject(new DataObject()
                        .setUser_id("0"))));

        totalBudgetOfCart = 0;
        totalNoOfItem = 0;

        if (cartList.size() > 0) {

            for (int i = 0; i < cartList.size(); i++) {
                DataObject dataObject = (DataObject) cartList.get(i);
                totalBudgetOfCart += Integer.parseInt(dataObject.getPost_price());
                totalNoOfItem += Integer.parseInt(dataObject.getPost_quantity());
            }

            //txtTotalBudget.setText(dataObject.getObject_currency_symbol() + " " + String.valueOf(totalBudgetOfCart));
            txtCount.setText(String.valueOf(totalNoOfItem));
            layoutViewCart.setVisibility(View.VISIBLE);

        } else
            layoutViewCart.setVisibility(View.GONE);


        if (!prefObject.isLogin())
            return;

        //Check either getActivity() book is saved by userObject or not
        //It would send request to 'db' for checking it

        favouriteArraylist.clear();
        favouriteArraylist.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setTypeOperation(Constant.TYPE.FAVOURITES)
                .setDbOperation(Constant.DB.SPECIFIC_TYPE)
                .setDataObject(new DataObject()
                        .setUser_id(prefObject.getUserId())
                        .setObject_id(dataObject.getObject_id()))));

        favouriteMap.clear();

        for (int i = 0; i < favouriteArraylist.size(); i++) {
            DataObject favouriteObject = (DataObject) favouriteArraylist.get(i);
            favouriteMap.put(favouriteObject.getObject_id(), favouriteObject.getUser_id());
        }

        if (favouriteMap.containsKey(dataObject.getObject_id())) {
            imageFavourite.setColorFilter(Utility.getAttrColor(getActivity(), R.attr.colorSelectedFavouriteIcon));
        } else {
            imageFavourite.setColorFilter(Utility.getAttrColor(getActivity(), R.attr.colorDefaultFavouriteIcon));
        }


    }

    @Override
    public void onClick(View v) {

        if (v == imageFavourite) {

            if (!prefObject.isLogin()) {
                startActivity(new Intent(getActivity(), OnBoarding.class));
                return;
            }

            int tag = (int) imageFavourite.getTag();
            if (tag == 0) {

                imageFavourite.setImageResource(R.drawable.ic_btn_mark_favourite);
                imageFavourite.setTag(1);


            } else if (tag == 1) {

                imageFavourite.setImageResource(R.drawable.ic_btn_unmark_favourite);
                imageFavourite.setTag(0);

            }

        }

        if (v == imageShare) {

            int minVersion = BuildConfig.VERSION_CODE;
            Uri deepLinkUri = Uri.parse(String.format(Constant.ServerInformation.DESKTOP_NEW_LINKS
                    , dataObject.getObject_id()
                    , Constant.PostType.POST_TYPE
                    , dataObject.getObject_name()
                    , Utility.getAppPlaystoreUrl(getActivity())));

            buildDeepLink(deepLinkUri, minVersion);

        }

        if (v == imageReport) {

            if (!prefObject.isLogin()) {
                startActivity(new Intent(getActivity(), OnBoarding.class));
                return;
            }

            showReportSheet(getActivity());

        }

        if (v == layoutViewCart) {
            Intent intent = new Intent(getActivity(), ProductCart.class);
            intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
            startActivity(intent);
        }

        if (v == layoutComment) {

            Intent intent = new Intent(getActivity(), ListOfReviews.class);
            intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject.getObject_id());
            startActivity(intent);
        }

        if (v == imageDetail) {

            Intent intent = new Intent(getActivity(), RestaurantInformation.class);
            intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
            startActivity(intent);

        }


    }


    @Override
    public void onSuccess(final Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {
            DataObject dataObject = (DataObject) data;

            if (requestObject.getConnection() == Constant.CONNECTION.RESTAURANT_DETAIL) {

                for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {
                    DataObject dtObject = dataObject.getObjectArrayList().get(i);
                    fragmentArrayList.add(new PagerTabObject()
                            .setId(dtObject.getCategory_id())
                            .setTitle(dtObject.getCategory_name())
                            .setFragment(MenuFragment.getFragmentInstance(
                                    dtObject.setObject_currency_symbol(this.dataObject.getObject_currency_symbol())
                                            .setObject_delivery_charges(this.dataObject.getObject_delivery_charges())
                                            .setObject_min_delivery_time(this.dataObject.getObject_min_delivery_time())
                                            .setObject_min_order_price(this.dataObject.getObject_min_order_price())
                                            .setObject_id(this.dataObject.getObject_id())
                                            .setObject_latitude(this.dataObject.getObject_latitude())
                                            .setObject_longitude(this.dataObject.getObject_longitude())
                                            .setPaymentTypeList(this.dataObject.getPaymentTypeList())
                                            .setObject_status(txtStatus.getText().toString()))));
                }

                categoriesPager.notifyDataSetChanged();

                for (int i = 0; i < layoutTab.getTabCount(); i++) {

                    TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_item_layout, null);
                    tv.setTextColor(Utility.getColourFromRes(getActivity(), R.color.colorPrimaryDark));
                    tv.setText(Utility.capitalize(fragmentArrayList.get(i).getTitle()));
                    layoutTab.getTabAt(i).setCustomView(tv);

                }

            }


        }

    }


    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Error = " + data);

        //It would trigger when error generate
        //in result of any server request


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    /**
     * <p>It is used to retrieve Progress Dialog object</p>
     *
     * @param context
     * @param progress
     * @return
     */

    private ACProgressFlower getACProgressFlower(Context context, String progress) {

        ACProgressFlower dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(progress)
                .textTypeface(Font.ubuntu_medium_font(context))
                .fadeColor(Color.DKGRAY).build();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;


    }


    /**
     * <p>Used to show bottom sheet dialog for Report Book</p>
     */
    private void showReportSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.add_report_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();


        final EditText txtReview = (EditText) view.findViewById(R.id.txt_review);
        TextView txtSubmit = (TextView) view.findViewById(R.id.txt_submit);

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utility.isEmptyString(txtReview.getText().toString()))
                    return;


                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();


                /*management.sendRequestToServer(new RequestObject()
                        .setJson(getReportJson(prefObject.getUserId(), postDetail.getPostId(), txtReview.getText().toString()))
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnection(Constant.CONNECTION.REPORT)
                        .setConnectionCallback(RestaurantDetail.this));*/

            }
        });

    }


    /**
     * <p>It is used to build Deeplink for Direct Sharing</p>
     *
     * @param deepLink   the deep link your app will open. This link must be a valid URL and use the
     *                   HTTP or HTTPS scheme.
     * @param minVersion the {@code versionCode} of the minimum version of your app that can open
     *                   the deep link. If the installed app is an older version, the userObject is taken
     *                   to the Play store to upgrade the app. Pass 0 if you do not
     *                   require a minimum version.
     * @return a {@link Uri} representing a properly formed deep link.
     */
    public void buildDeepLink(@NonNull Uri deepLink, int minVersion) {
        String uriPrefix = Constant.ServerInformation.DEFFERED_DEEP_LINK_URL;

        final Uri[] uri = {null};

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setDomainUriPrefix(uriPrefix)
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder()
                        .setMinimumVersion(minVersion)
                        .build())
                .setLink(deepLink)
                .buildShortDynamicLink()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {

                        if (task.isSuccessful()) {
                            // Short link created
                            uri[0] = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Utility.Logger(TAG, "ShortLink = " + uri[0].toString() + " flowChartLink = " + flowchartLink.toString());
                            Utility.shareApp(getActivity(), uri[0].toString());

                        } else {
                            Utility.Logger(TAG, "Error = " + task.getException().getMessage());
                        }
                    }
                });

        // [END build_dynamic_link]


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_dashboard, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view); //Initialize UI
    }


}
