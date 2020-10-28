package com.rch.etawah.ActivityUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rch.etawah.AdapterUtil.MainStepperAdapter;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.GlobalDataObject;
import com.rch.etawah.ObjectUtil.PictureObject;
import com.rch.etawah.ObjectUtil.PrefObject;
import com.rch.etawah.ObjectUtil.RequestObject;
import com.rch.etawah.ObjectUtil.StepperObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;
import com.hsalf.smilerating.SmileRating;
import com.liefery.android.vertical_stepper_view.VerticalStepperView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrackOrder extends AppCompatActivity implements View.OnClickListener {
    private VerticalStepperView listStepper;
    private MainStepperAdapter mainStepperAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private TextView txtEta;
    private TextView txtAmount;
    private TextView txtOrderId;
    private TextView txtDate;
    private TextView txtDelivery;
    private Management management;
    private CardView cardRating;
    private CardView cardRiderRating;
    private DataObject dataObject;
    private ImageView imagePaymentType;
    private TextView txtPaymentType;
    private TextView txtMenu;
    private ImageView imageBack;
    private LinearLayout layoutTrack;
    private LinearLayout listViewOrderDetail;
    private PrefObject prefObject;
    private AppCompatRatingBar riderRating;
    private TextView txtDeliveryCharges;


    public View getView(Object objects) {

        View view = LayoutInflater.from(this).inflate(R.layout.order_detail_item_layout, null);

        LinearLayout layoutProduct = view.findViewById(R.id.layout_product);
        TextView txtProduct = view.findViewById(R.id.txt_product);
        TextView txtQuantity = view.findViewById(R.id.txt_quantity);
        TextView txtPrice = view.findViewById(R.id.txt_price);

        try {
            txtProduct.setText(((DataObject) objects).getOrder_product_name());
            txtQuantity.setText("(x" + ((DataObject) objects).getOrder_product_quantity() + ")");
            txtPrice.setText(((DataObject) objects).getOrder_product_price().replace("INR", "₹"));
        } catch (Exception ignore) {

        }

        layoutProduct.setPadding(0, 12, 0, 12);

        return view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);


        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.ORDER_DETAIL);


        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.order_status));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true));

        txtDate = (TextView) findViewById(R.id.txt_date);
        txtOrderId = (TextView) findViewById(R.id.txt_order_id);
        txtAmount = (TextView) findViewById(R.id.txt_amount);
        txtEta = (TextView) findViewById(R.id.txt_eta);
        txtPaymentType = findViewById(R.id.txt_payment_type);
        imagePaymentType = findViewById(R.id.image_payment_type);
        txtDeliveryCharges = findViewById(R.id.txt_delivery_charge);

        layoutTrack = findViewById(R.id.layout_track);
        layoutTrack.setVisibility(View.GONE);

        cardRating = findViewById(R.id.card_review);

        cardRiderRating = findViewById(R.id.card_rider_rating);
        cardRiderRating.setVisibility(View.GONE);

        txtDate.setText(String.format("%s , %s", dataObject.getOrder_delivery_date(), dataObject.getOrder_delivery_time()));
        txtAmount.setText(String.format("%s : %s", Utility.getStringFromRes(this, R.string.total), dataObject.getOrder_price().replace("INR", "₹")));
        txtEta.setText(String.format("%s : %s", Utility.getStringFromRes(this, R.string.eta), dataObject.getDelivery_time()));
        txtOrderId.setText(String.format("%s # %s", Utility.getStringFromRes(this, R.string.order_id), dataObject.getOrder_id()));
        txtPaymentType.setText(dataObject.getPaymentType());

        ///View listHeaderView = getLayoutInflater().inflate(R.layout.product_, null, false);

        listViewOrderDetail = findViewById(R.id.list_view_order_detail);

        ArrayList<Object> arrayList = new ArrayList<Object>(dataObject.getProduct_order_detail_list());
        for (Object object : arrayList) {

            listViewOrderDetail.addView(getView(object));
        }

        txtDeliveryCharges.setText(dataObject.getDelivery_fee());

        if (dataObject.getPaymentType().equalsIgnoreCase(Utility.getStringFromRes(this, R.string.cod))) {
            imagePaymentType.setImageResource(R.drawable.ic_cod);
        } else {
            imagePaymentType.setImageResource(R.drawable.ic_credit_card);
        }

        objectArrayList.add(new StepperObject()
                .setTitle(Utility.getStringFromRes(this, R.string.order_placed))
                .setDescription(Utility.getStringFromRes(this, R.string.order_place_tagline))
                .setIcon(R.drawable.ic_order));

        objectArrayList.add(new StepperObject()
                .setTitle(Utility.getStringFromRes(this, R.string.process_order))
                .setDescription(Utility.getStringFromRes(this, R.string.process_order_tagline))
                .setIcon(R.drawable.ic_order_process));

        objectArrayList.add(new StepperObject()
                .setTitle(Utility.getStringFromRes(this, R.string.on_the_way))
                .setDescription(Utility.getStringFromRes(this, R.string.on_the_Way_tagline))
                .setIcon(R.drawable.ic_bike));

        objectArrayList.add(new StepperObject()
                .setTitle(Utility.getStringFromRes(this, R.string.received_successfully))
                .setDescription(Utility.getStringFromRes(this, R.string.success_tagline))
                .setIcon(R.drawable.ic_success));

        listStepper = (VerticalStepperView) findViewById(R.id.list_stepper);
        mainStepperAdapter = new MainStepperAdapter(this, objectArrayList);
        listStepper.setStepperAdapter(mainStepperAdapter);

        if (dataObject.getOrder_status().equalsIgnoreCase(Utility.getStringFromRes(this, R.string.project_status_ready))) {

            if (mainStepperAdapter.hasNext())
                mainStepperAdapter.next();

        } else if (dataObject.getOrder_status().equalsIgnoreCase(Utility.getStringFromRes(this, R.string.project_status_completed))) {

            if (mainStepperAdapter.hasNext()) {
                mainStepperAdapter.next();
                mainStepperAdapter.next();
            }

        }

        cardRating.setOnClickListener(this);
        cardRiderRating.setOnClickListener(this);

        imageBack.setOnClickListener(this);
        layoutTrack.setOnClickListener(this);

    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String userId, String restaurantId, String comment, String rating) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "add_reviews_into_restaurants");
            jsonObject.accumulate("user_id", userId);
            jsonObject.accumulate("restaurant_id", restaurantId);
            jsonObject.accumulate("review", comment);
            jsonObject.accumulate("rating", rating);
            jsonObject.accumulate("review_pictures", convertPicturesIntoJsonArray());

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
    private String getOrderRatingJson(String order_id, boolean isOrderRating) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (isOrderRating)
                jsonObject.accumulate("functionality", "order_rating_status");
            else
                jsonObject.accumulate("functionality", "rider_rating_status");

            jsonObject.accumulate("order_id", order_id);

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
    private String getRiderRatingJson(String order_id, String rating) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "add_rider_rating");
            jsonObject.accumulate("order_id", order_id);
            jsonObject.accumulate("rating", rating);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
    }


    /**
     * <p>Used to show bottom sheet dialog for Adding Review to Book</p>
     */

    private void showReviewSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.add_review_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();


        final SmileRating smileRating = (SmileRating) view.findViewById(R.id.smile_rating);
        final EditText txtReview = (EditText) view.findViewById(R.id.txt_review);
        final RelativeLayout layoutReview = view.findViewById(R.id.layout_review);
        final ImageView imageAttach = view.findViewById(R.id.image_attach);
        TextView txtSubmit = (TextView) view.findViewById(R.id.txt_submit);

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                // level is from 1 to 5 (0 when none selected)
                // reselected is false when userObject selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.

                layoutReview.setVisibility(View.VISIBLE);

            }
        });

        imageAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, PictureUploader.class), Constant.RequestCode.REQUEST_CODE_PICTURE);
            }
        });

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utility.isEmptyString(txtReview.getText().toString()))
                    return;


                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();


                management.sendRequestToServer(new RequestObject()
                        .setJson(getJson(prefObject.getUserId(), dataObject.getObject_id(), txtReview.getText().toString(), String.valueOf(smileRating.getRating())))
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnection(Constant.CONNECTION.ADD_COMMENT)
                        .setConnectionCallback(null));

                management.sendRequestToServer(new RequestObject()
                        .setJson(getOrderRatingJson(dataObject.getOrder_id(), true))
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnection(Constant.CONNECTION.ORDER_RIDER_STATUS)
                        .setConnectionCallback(null));

                Constant.globalDataObject = null;

            }
        });

    }


    /**
     * <p>Used to show bottom sheet dialog for Adding Review to Book</p>
     */

    private void showRiderReviewSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.add_review_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();


        final SmileRating smileRating = (SmileRating) view.findViewById(R.id.smile_rating);
        final EditText txtReview = (EditText) view.findViewById(R.id.txt_review);
        final RelativeLayout layoutReview = view.findViewById(R.id.layout_review);
        final ImageView imageAttach = view.findViewById(R.id.image_attach);
        TextView txtSubmit = (TextView) view.findViewById(R.id.txt_submit);

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                // level is from 1 to 5 (0 when none selected)
                // reselected is false when userObject selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.

                ///layoutReview.setVisibility(View.VISIBLE);

            }
        });

        imageAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, PictureUploader.class), Constant.RequestCode.REQUEST_CODE_PICTURE);
            }
        });

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();


                management.sendRequestToServer(new RequestObject()
                        .setJson(getRiderRatingJson(dataObject.getOrder_id(), String.valueOf(smileRating.getRating())))
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnection(Constant.CONNECTION.ADD_RIDER_RATING)
                        .setConnectionCallback(null));

                management.sendRequestToServer(new RequestObject()
                        .setJson(getOrderRatingJson(dataObject.getOrder_id(), false))
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnection(Constant.CONNECTION.ORDER_RIDER_STATUS)
                        .setConnectionCallback(null));

                Constant.globalDataObject = null;

            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v == cardRating) {

            if (dataObject.getReview_status().equalsIgnoreCase("0")) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.you_already_rate_order), Toast.LENGTH_SHORT);
                return;
            }

            showReviewSheet(this);
        }
        if (v == cardRiderRating) {

            if (!(dataObject.getOrder_status()
                    .equalsIgnoreCase(Utility.getStringFromRes(getApplicationContext(), R.string.project_status_completed)))) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.currently_in_preparationg), Toast.LENGTH_SHORT);
                return;
            }

            if (dataObject.getRider_review_status().equalsIgnoreCase("0")) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.you_already_rate_order), Toast.LENGTH_SHORT);
                return;
            }

            showRiderReviewSheet(this);

        }
        if (v == imageBack) {
            finish();
        }
        if (v == layoutTrack) {

            if (!(dataObject.getOrder_status()
                    .equalsIgnoreCase(Utility.getStringFromRes(getApplicationContext(), R.string.project_status_completed)))) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.currently_in_preparationg), Toast.LENGTH_SHORT);
                return;
            }

            Intent intent = new Intent(this, TrackRider.class);
            intent.putExtra(Constant.IntentKey.ORDER_DETAIL, dataObject);
            startActivity(intent);
        }
    }


    private JSONArray convertPicturesIntoJsonArray() {

        JSONArray jsonArray = new JSONArray();
        GlobalDataObject globalDataObject = Constant.globalDataObject;

        if (globalDataObject != null) {
            for (Object pictures : globalDataObject.getObjectArrayList()) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("picture", ((PictureObject) pictures).getPicture());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
            }
        }

        return jsonArray;
    }

}



