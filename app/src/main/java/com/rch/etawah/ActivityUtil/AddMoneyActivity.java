package com.rch.etawah.ActivityUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.PrefObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

import static com.rch.etawah.ConstantUtil.Constant.ServerInformation.REST_API_URL;

public class AddMoneyActivity extends AppCompatActivity implements PaymentResultListener {

    private String TAG = AddMoneyActivity.class.getName();
    private PrefObject prefObject;

    public static class RandomString {

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
        try {
            String money = etAmount.getText().toString();
            int amount = Integer.parseInt(money);

            progress_bar.setVisibility(View.VISIBLE);
            txtMessage.setVisibility(View.VISIBLE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "add_user_wallet_amount");
            jsonObject.accumulate("user_id", prefObject.getUserId());
            jsonObject.accumulate("amount", String.valueOf(amount));
            jsonObject.accumulate("type", "cr");

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progress_bar.setVisibility(View.GONE);
                            Utility.Logger("TAG", response.toString());
                            try {
                                if (response.getString("code").equalsIgnoreCase("200")) {
                                    JSONObject result = response.getJSONObject("result");
                                    txtMessage.setText(String.format("Rs %s Added Sucess", result.getString("amount")));
                                    Toast.makeText(AddMoneyActivity.this, "Rs " + result.getString("amount"), Toast.LENGTH_SHORT).show();
                                    AddMoneyActivity.this.finish();
                                }
                                //
                                else {
                                    Toast.makeText(AddMoneyActivity.this, "Error Contact Admin", Toast.LENGTH_SHORT).show();
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

            Volley.newRequestQueue(AddMoneyActivity.this).add(jsonRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ProgressBar progress_bar;
    TextView txtMessage;
    EditText etAmount;

    @Override
    public void onPaymentError(int code, String response) {
        /*
         * Add your logic here for a failed payment response
         */
        Utility.Logger(TAG, "Payment Failed " + response);
        Utility.Toaster(this, "Payment Failed " + response, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_money);

        TextView txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText("Add Money");

        ImageView imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        TextView addMoneyBtn = findViewById(R.id.addmoney_btn);
        progress_bar = findViewById(R.id.progress_bar);
        txtMessage = findViewById(R.id.txtMessage);
        etAmount = findViewById(R.id.et_amount);

        addMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = etAmount.getText().toString();
                if (money.length() > 0) {

                    etAmount.setFocusable(false);
                    int amount = Integer.parseInt(money);

                    String easy = CheckoutActivity.RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
                    RandomString tickets = new RandomString(23, new SecureRandom(), easy);
                    startPayment(tickets.nextString(), String.valueOf(amount * 100));
                    //onPaymentSuccess(easy);
                }
            }
        });

        Management management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true)
                .setRetrieveLogin(true));

    }

}

