package com.rch.etawah.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.InterfaceUtil.ConnectionCallback;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.RequestObject;
import com.rch.etawah.R;
import com.rch.etawah.TextviewUtil.NormalTextview;
import com.rch.etawah.TextviewUtil.TaglineTextview;
import com.rch.etawah.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.rch.etawah.ConstantUtil.Constant.ServerInformation.REST_API_URL;

public class RedeemCoupon extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private EditText editCouponCode;
    private LinearLayout layoutCoupon;
    private NormalTextview layoutRedeem;
    private DataObject dataObject;

    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            setResult(RESULT_CANCELED);
            finish();
        }
        if (v == layoutRedeem) {

            if (Utility.isEmptyString(editCouponCode.getText().toString()))
                return;


            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("functionality", "verify_coupon");
                jsonObject.accumulate("coupon_code", editCouponCode.getText().toString());
                jsonObject.accumulate("restaurant_id", dataObject.getObject_id());

                management.sendRequestToServer(new RequestObject()
                        .setJson(jsonObject.toString())
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setConnection(Constant.CONNECTION.REDEEM_COUPON)
                        .setConnectionCallback(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_coupon);

        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.RESTAURANT_DETAIL);

        management = new Management(this);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.my_playlist));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        layoutCoupon = findViewById(R.id.layoutCoupon);
        layoutRedeem = findViewById(R.id.layout_redeem);
        editCouponCode = findViewById(R.id.edit_coupon_code);

        imageBack.setOnClickListener(this);
        layoutRedeem.setOnClickListener(this);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "get_all_coupon");


            //{"code":200,"message":"Sucess","resutl":[
            // {"id":"1",
            // "coupon_code":"RCHOPEN",
            // "coupon_reward":"99",
            // "coupon_to_date":"2021-07-16 00:00:00",
            // "coupon_from_date":"2020-07-16 00:00:00",
            // "coupon_date_created":"2020-07-16 05:13:42"}]}
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //["ring-12.jpg","ring-11.jpg"]

                            Utility.Logger("TAG", response.toString());
                            try {
                                JSONArray coupons = response.getJSONArray("result");
                                for (int i = 0; i < coupons.length(); i++) {
                                    final JSONObject object = coupons.getJSONObject(i);
                                    View view = LayoutInflater.from(RedeemCoupon.this).inflate(R.layout.item_redeem_coupon, null);

                                    final LinearLayout layoutProduct = view.findViewById(R.id.layout_product);
                                    final TextView txtProduct = view.findViewById(R.id.txt_product);
                                    final TextView txtPrice = view.findViewById(R.id.txt_price);
                                    final TaglineTextview apply_now_tv = view.findViewById(R.id.apply_now_tv);

                                    txtProduct.setText(object.getString("coupon_code"));
                                    txtPrice.setText(String.format("%s%% off", object.getString("coupon_reward")));

                                    apply_now_tv.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                          /*  management.sendRequestToServer(new RequestObject()
                                                    .setJson(getJson(editCouponCode.getText().toString()))
                                                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                                    .setConnection(Constant.CONNECTION.REDEEM_COUPON)
                                                    .setConnectionCallback(RedeemCoupon.this));*/
                                            CharSequence coupon = txtProduct.getText();
                                            Utility.Toaster(RedeemCoupon.this, "To apply '" + coupon + "' Click Redeem Now.", Toast.LENGTH_SHORT);
                                            editCouponCode.setText(coupon);
                                        }
                                    });

                                    layoutCoupon.addView(view);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            Volley.newRequestQueue(this).add(jsonRequest);

        } catch (JSONException e) {
            e.printStackTrace();
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

}
