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
import com.haris.meal4u.FragmentUtil.AddressFragment;
import com.haris.meal4u.FragmentUtil.BillingFragment;
import com.haris.meal4u.FragmentUtil.ScheduleFragment;
import com.haris.meal4u.InterfaceUtil.CheckoutCallback;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.AddressObject;
import com.haris.meal4u.ObjectUtil.BillingObject;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.DateTimeObject;
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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.shuhart.stepview.StepView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener,
        StepView.OnStepClickListener, CheckoutCallback, PaymentResultListener {


    public class RandomString {

        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }

        public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String lower = "abcdefghijklmnopqrstuvwxyz";
        public static final String digits = "0123456789";

        public static final String alphanum = upper + lower + digits;

        private final Random random;

        private final char[] symbols;

        private final char[] buf;

        public RandomString(int length, Random random, String symbols) {
            if (length < 1) throw new IllegalArgumentException();
            if (symbols.length() < 2) throw new IllegalArgumentException();
            this.random = Objects.requireNonNull(random);
            this.symbols = symbols.toCharArray();
            this.buf = new char[length];
        }

        /**
         * Create an alphanumeric string generator.
         */
        public RandomString(int length, Random random) {

            this(length, random, alphanum);
        }

        /**
         * Create an alphanumeric strings from a secure generator.
         */
        public RandomString(int length) {

            this(length, new SecureRandom());
        }

        /**
         * Create session identifiers.
         */
        public RandomString() {

            this(21);
        }

    }

    public void startPayment(String order_id, String amount) {

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Ratlam Cafe House");
            options.put("description", "Reference No. #" + order_id + " Amount : " + amount);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://cdn.razorpay.com/logos/FFRuHuUlyOjFVw_medium.png");
            options.put("currency", "INR");
            options.put("amount", amount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", prefObject.getUserEmail());
            preFill.put("contact", prefObject.getUserPhone());

            options.put("prefill", preFill);

            final Checkout co = new Checkout();
            co.open(this, options);
        } catch (Exception e) {
            Utility.Logger(TAG, "Error in starting Razorpay Checkout");
            Utility.Toaster(this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {

        ScheduleObject scheduleObject = new ScheduleObject();
        scheduleObject.setNow(true);
        DateTimeObject dateTimeObject = Utility.parseTimeDate(new DateTimeObject()
                .setCurrentDate(true)
                .setDatetimeType(Constant.DATETIME.DATE_DD_MM_YYYY_hh_mm_ss));
        scheduleObject.setSchedule(dateTimeObject.getDatetime());

        onDeliveryChangeListener(scheduleObject);

        Utility.Toaster(this, "Payment Success, Scheduling your order", Toast.LENGTH_SHORT);
    }

    @Override
    public void onPaymentError(int code, String response) {
        /*
         * Add your logic here for a failed payment response
         */
        Utility.Logger(TAG, "Payment Failed " + response);
        Utility.Toaster(this, "Payment Failed " + response, Toast.LENGTH_SHORT);
    }

    private String TAG = CheckoutActivity.class.getName();
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
    public void onBackPressed() {
        CartObjectModal.setList(new ArrayList<CartObjectModal>());
        Intent intent = new Intent(this, Base.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public String getUserId() {

        return prefObject.getUserId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);

        Checkout.preload(getApplicationContext());
        setContentView(R.layout.activity_checkout);

        restaurantDetail = getIntent().getParcelableExtra(Constant.IntentKey.RESTAURANT_DETAIL);

        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true)
                .setRetrieveLogin(true));

        objectArrayList = CartObjectModal.getList();

        TextView txtMenu = findViewById(R.id.txt_menu);
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


    public void openFragment(Fragment fragment) {

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_container, fragment);
            fragmentTransaction.commit();

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

            if (restaurantDetail.getPost_price() != null) {

                int amount = Integer.parseInt(restaurantDetail.getPost_price());

                String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
                RandomString tickets = new RandomString(23, new SecureRandom(), easy);

                startPayment(tickets.nextString(), String.valueOf(amount * 100));


            }
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

    private void showCheckoutBottomSheet(final Context context) {
        final View view = getLayoutInflater().inflate(R.layout.process_order_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();


        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "order_product");
            jsonObject.accumulate("user_id", prefObject.getUserId());
            jsonObject.accumulate("restaurant_id", restaurantDetail.getObject_id());
            jsonObject.accumulate("order_price", restaurantDetail.getPost_price());
            jsonObject.accumulate("discount_id", restaurantDetail.getCoupon_id());
            jsonObject.accumulate("delivery_time", restaurantDetail.getObject_min_delivery_time());
            jsonObject.accumulate("payment_type", billingObject.getPaymentMethod());
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

            final Bundle bundle = new Bundle();
            bundle.putString("user_name", prefObject.getFirstName());
            bundle.putString("user_phone", prefObject.getUserPhone());
            bundle.putString("order_price", restaurantDetail.getPost_price());
            bundle.putString("payment_type", billingObject.getPaymentMethod());
            bundle.putString("billing_address", addressObject.getBuildingName() + " " + addressObject.getFloorName() + " " + addressObject.getStreetName() + " " + addressObject.getAreaName());
            bundle.putString("delivery_date", scheduleObject.getSchedule() + " " + restaurantDetail.getObject_min_delivery_time());

            management.sendRequestToServer(new RequestObject()
                    .setJson(jsonObject.toString())
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

                                stepView.go(2, true);
                                stepView.done(true);

                                bundle.putString("order_id", dtObject.getOrder_id());
                                Fragment fragment = new ScheduleFragment();
                                fragment.setArguments(bundle);
                                openFragment(fragment);

                            }
                        }

                        @Override
                        public void onError(String data, RequestObject requestObject) {
                            bottomSheetDialog.dismiss();
                            Utility.Toaster(context, data, Toast.LENGTH_SHORT);
                        }
                    }));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
