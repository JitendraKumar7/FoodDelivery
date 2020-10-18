package com.rch.etawah.ActivityUtil;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.InterfaceUtil.ConnectionCallback;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.RequestObject;
import com.rch.etawah.R;
import com.rch.etawah.TextviewUtil.UbuntuMediumTextview;
import com.rch.etawah.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;

import static android.widget.Toast.LENGTH_SHORT;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {

    private TextView txtForgot;
    private ImageView imageBack;
    private Management management;
    private LinearLayout layoutOtp, layoutPassword;
    private EditText editEmail, editOtp, editPassword;

    int otp = 0;
    String phoneNo;

    @Override
    public void onClick(View v) {
        if (v == txtForgot) {

            String phone = editEmail.getText().toString().trim();
            if (Utility.isEmptyString(phone)) {
                showError("Please enter your Mobile Number", editEmail);
//                Utility.Toaster(this, "Phone is empty", Toast.LENGTH_LONG);
                return;
            }

            if (phone.length() != 10) {
                showError("Please enter your valid Mobile Number", editEmail);
//                Utility.Toaster(this, "Phone not valid", Toast.LENGTH_LONG);
                return;
            }

            if (layoutOtp.getVisibility() == View.GONE ||
                    layoutPassword.getVisibility() == View.GONE) {
                final String url = generateRandomNumber(phone);
                Volley.newRequestQueue(this).add(new StringRequest(
                                url, response -> {
                            Utility.Toaster(ForgotPassword.this, "OTP Sent Successfully", LENGTH_SHORT);
                            layoutPassword.setVisibility(View.VISIBLE);
                            layoutOtp.setVisibility(View.VISIBLE);
                            txtForgot.setText("Change");
                        },
                        error -> {
                        })
                );
            }

            //
            if (layoutOtp.getVisibility() == View.VISIBLE ||
                    layoutPassword.getVisibility() == View.VISIBLE) {

                if (Utility.isEmptyString(editOtp.getText().toString())) {
                    Utility.Toaster(this, "Otp is empty", Toast.LENGTH_LONG);
                    return;
                }
                if (Utility.isEmptyString(editPassword.getText().toString())) {
                    Utility.Toaster(this, "Password is empty", Toast.LENGTH_LONG);
                    return;
                }

                if (editOtp.getText().toString().equalsIgnoreCase(String.valueOf(otp))) {

                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.accumulate("functionality", "forgot_password");
                        jsonObject.accumulate("password", editPassword.getText().toString());
                        jsonObject.accumulate("phone", editEmail.getText().toString());

                        management.sendRequestToServer(new RequestObject()
                                .setJson(jsonObject.toString())
                                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                .setConnection(Constant.CONNECTION.FORGOT)
                                .setConnectionCallback(this));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
        if (v == imageBack) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        TextView txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.forgot_password));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);

        editEmail = findViewById(R.id.edit_email);
        editOtp = findViewById(R.id.edit_otp);
        editPassword = findViewById(R.id.edit_password);
        txtForgot = findViewById(R.id.txt_forgot);

        layoutOtp = findViewById(R.id.layoutOtp);
        layoutPassword = findViewById(R.id.layoutPassword);

        txtForgot.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    private String generateRandomNumber(String phoneNumber) {
        int range = 9;  // to generate a single number with this range, by default its 0..9
        int length = 4; // by default length is 4

        if (otp == 0 || !phoneNo.equalsIgnoreCase(phoneNumber)) {
            SecureRandom secureRandom = new SecureRandom();
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int number = secureRandom.nextInt(range);
                if (number == 0 && i == 0) { // to prevent the Zero to be the first number as then it will reduce the length of generated pin to three or even more if the second or third number came as zeros
                    i = -1;
                    continue;
                }
                s.append(number);
            }

            otp = Integer.parseInt(s.toString());
            phoneNo = phoneNumber;
        }

        return String.format("http://2factor.in/API/V1/f58a6770-ad34-11ea-9fa5-0200cd936042/SMS/%s/%s/RCHJKS", phoneNumber, otp);
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (requestObject != null) {
            Utility.Toaster(this, "Password Update Successfully", Toast.LENGTH_SHORT);
            finish();
        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Toaster(this, data, Toast.LENGTH_SHORT);
    }

    private void showError(String error_st, final EditText editText) {
        final Dialog error_dialog = new Dialog(this);
        error_dialog.setCanceledOnTouchOutside(false);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setContentView(R.layout.error_dialog);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        error_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        UbuntuMediumTextview error_text = error_dialog.findViewById(R.id.error_text);
        UbuntuMediumTextview ok_btn = error_dialog.findViewById(R.id.ok_btn);
        error_text.setText(error_st);
        error_dialog.show();
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error_dialog.dismiss();
                requestFocus(editText);
            }
        });
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
