package com.haris.meal4u.ActivityUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.DatabaseUtil.DatabaseObject;
import com.haris.meal4u.FragmentUtil.AddressFragment;
import com.haris.meal4u.FragmentUtil.BillingFragment;
import com.haris.meal4u.FragmentUtil.ScheduleFragment;
import com.haris.meal4u.InterfaceUtil.CheckoutCallback;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.AddressObject;
import com.haris.meal4u.ObjectUtil.BillingObject;
import com.haris.meal4u.ObjectUtil.ChattingObject;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.ObjectUtil.RiderObject;
import com.haris.meal4u.ObjectUtil.RiderTrackingObject;
import com.haris.meal4u.ObjectUtil.ScheduleObject;
import com.haris.meal4u.ObjectUtil.TrackingObject;
import com.haris.meal4u.ObjectUtil.TypingObject;
import com.haris.meal4u.ObjectUtil.UserChattingObject;
import com.haris.meal4u.ObjectUtil.UserObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.CartObjectModal;
import com.haris.meal4u.Utility.Utility;
import com.shuhart.stepview.StepView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Checkout extends AppCompatActivity implements View.OnClickListener, StepView.OnStepClickListener, ConnectionCallback, CheckoutCallback {
    private String TAG = Checkout.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private PrefObject prefObject;
    public DataObject restaurantDetail;
    private StepView stepView;
    private ArrayList<String> stepList = new ArrayList<>();
    private List<CartObjectModal> objectArrayList = new ArrayList<>();
    private AddressObject addressObject;
    private BillingObject billingObject;
    private ScheduleObject scheduleObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        getIntentData(); //Retrieve Intent Data
        initUI(); //Initialize UI


    }

    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {
        restaurantDetail = getIntent().getParcelableExtra(Constant.IntentKey.RESTAURANT_DETAIL);
    }


    /**
     * <p>It is used to initialize UI</p>
     */
    private void initUI() {

        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true)
                .setRetrieveLogin(true));

        objectArrayList = CartObjectModal.getList();

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.checkout));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        stepList.add(Utility.getStringFromRes(this, R.string.address));
        stepList.add(Utility.getStringFromRes(this, R.string.billing));
        stepList.add(Utility.getStringFromRes(this, R.string.delivery));

        stepView = findViewById(R.id.step_view);
        stepView.setSteps(stepList);

        openFragment(new AddressFragment());


        imageBack.setOnClickListener(this);
        stepView.setOnStepClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();

        }

    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String user_id, String restaurant_id, String price, String couponId, String delivery_time, String payment_type) {
        String json = null;

        // 1. build jsonObject
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
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    /**
     * <p>It is used to convert Cart Product into Json Array</p>
     *
     * @return
     */
    private JSONArray convertProductIntoArray() {

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < objectArrayList.size(); i++) {

            CartObjectModal dataObject = objectArrayList.get(i);
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.accumulate("product_id", dataObject.getPostId());
                jsonObject.accumulate("quantity", dataObject.getPostQuantity());
                jsonObject.accumulate("price", dataObject.getPostQtyPrice());
                jsonObject.accumulate("attribute_id", "");
                jsonObject.accumulate("special_instruction", "No Instruction");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(jsonObject);

        }

        return jsonArray;
    }


    /**
     * <p>It is used to open Fragment</p>
     *
     * @param fragment
     */
    public void openFragment(Fragment fragment) {

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_container, fragment);
            fragmentTransaction.commit();

        }
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {

            DataObject dataObject = (DataObject) data;
            Utility.Toaster(this, dataObject.getMessage(), Toast.LENGTH_SHORT);

            Intent intent = new Intent();
            intent.putExtra(Constant.IntentKey.COUPON_DETAIL, dataObject);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        if (!Utility.isEmptyString(data)) {

            Utility.Toaster(this, data, Toast.LENGTH_SHORT);


        }
    }

    @Override
    public void onStepClick(int step) {
        if (step == 0) {

            stepView.go(0, true);
            stepView.done(false);

            openFragment(new AddressFragment());

        } else if (step == 1) {
            stepView.go(1, true);
            stepView.done(false);

            openFragment(new BillingFragment());

        } else {
            stepView.go(2, true);
            stepView.done(true);
            openFragment(new ScheduleFragment());
        }
    }

    @Override
    public void onAddressChangeListener(AddressObject addressObject) {
        Utility.Logger(TAG, addressObject.toString());
        this.addressObject = addressObject;
    }

    @Override
    public void onBillingChangeListener(BillingObject billingObject) {
        Utility.Logger(TAG, billingObject.toString());
        this.billingObject = billingObject;
    }

    @Override
    public void onDeliveryChangeListener(ScheduleObject scheduleObject) {
        Utility.Logger(TAG, scheduleObject.toString());
        this.scheduleObject = scheduleObject;

        if (addressObject != null && billingObject != null && scheduleObject != null) {

            Utility.Logger(TAG, scheduleObject.toString());
            restaurantDetail.setAddressObject(addressObject)
                    .setBillingObject(billingObject)
                    .setScheduleObject(scheduleObject);

            showCheckoutBottomSheet(this);

        }

    }


    /**
     * <p>It is used to show Language Selector</p>
     *
     * @param context
     */
    private void showCheckoutBottomSheet(final Context context) {
        final View view = getLayoutInflater().inflate(R.layout.process_order_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson(prefObject.getUserId(), restaurantDetail.getObject_id(), restaurantDetail.getPost_price(), restaurantDetail.getCoupon_id(), restaurantDetail.getObject_min_delivery_time(), billingObject.getPaymentMethod()))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.CHECK_OUT)
                .setConnectionCallback(new ConnectionCallback() {
                    @Override
                    public void onSuccess(Object data, RequestObject requestObject) {
                        bottomSheetDialog.dismiss();

                        if (requestObject.getConnection() == Constant.CONNECTION.CHECK_OUT) {

                            DataObject dtObject = (DataObject) data;
                            DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference usersRef = rootReference.child(dtObject.getOrder_id());

                            UserObject userObject = new UserObject(Integer.parseInt(prefObject.getUserId())
                                    , prefObject.getFirstName() + " " + prefObject.getLastName()
                                    , prefObject.getPictureUrl()
                                    , Double.parseDouble(restaurantDetail.getAddressObject().getLatitude())
                                    , Double.parseDouble(restaurantDetail.getAddressObject().getLongitude()));

                            RiderObject riderObject = new RiderObject();
                            riderObject.setRider_latitude(Double.parseDouble(restaurantDetail.getObject_latitude()));
                            riderObject.setRider_longitude(Double.parseDouble(restaurantDetail.getObject_longitude()));

                            TrackingObject trackingObject = new TrackingObject(0.0, 0.0, "0 min", "0 km");
                            TypingObject typingObject = new TypingObject();
                            UserChattingObject userChattingObject = new UserChattingObject();

                            usersRef.setValue(new RiderTrackingObject(userObject, riderObject, trackingObject, userChattingObject, typingObject));

                        }

                        management.getDataFromDatabase(new DatabaseObject()
                                .setTypeOperation(Constant.TYPE.CART)
                                .setDbOperation(Constant.DB.DELETE_SPECIFIC_TYPE)
                                .setDataObject(new DataObject().setUser_id("0")));

                        CartObjectModal.setList(new ArrayList<CartObjectModal>());
                        startActivity(new Intent(getApplicationContext(), Base.class));
                        finish();
                    }

                    @Override
                    public void onError(String data, RequestObject requestObject) {
                        bottomSheetDialog.dismiss();
                        Utility.Toaster(context, data, Toast.LENGTH_SHORT);
                    }
                }));


    }

}
