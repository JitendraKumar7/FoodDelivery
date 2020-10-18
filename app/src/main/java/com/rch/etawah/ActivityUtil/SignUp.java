package com.rch.etawah.ActivityUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.InterfaceUtil.ConnectionCallback;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.PrefObject;
import com.rch.etawah.ObjectUtil.RequestObject;
import com.rch.etawah.R;
import com.rch.etawah.TextviewUtil.UbuntuMediumTextview;
import com.rch.etawah.Utility.Utility;

import net.bohush.geometricprogressview.GeometricProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_SHORT;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private TextView txtMenu;
    private ImageView imageBack;
    private EditText editFirstName;
    private EditText editLastName;
    private ImageView imageProfile;
    private EditText editEmail;
    private EditText editPhone;
    private EditText editPassword;
    private TextView txtSignUp;
    private TextView txtLogin;
    private EditText editConfirmPassword;
    private Management management;

    int otp = 0;
    String phoneNo;

    public final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return !matcher.find();
    }

    @Override
    public void onClick(View v) {
        if (v == txtSignUp) {
            if (Utility.isEmptyString(editFirstName.getText().toString())) {
                showError("Please enter First Name", editFirstName);
//                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.empty_first_name), LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editPhone.getText().toString()) || editPhone.getText().toString().length() != 10) {
                showError("Please enter your valid Mobile Number", editPhone);
//                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_phone), LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editEmail.getText().toString()) || validate(editEmail.getText().toString())) {
                showError("Please enter your valid Email Id", editEmail);
//                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_empty), LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editPassword.getText().toString())) {
                showError("Please enter Password", editPassword);
//                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_empty), LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editConfirmPassword.getText().toString())) {
                showError("Please enter Confirm Password", editConfirmPassword);
//                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_empty), LENGTH_SHORT);
                return;
            }
            if (!(editPassword.getText().toString().equals(editConfirmPassword.getText().toString()))) {
                showError("Password and Confirm Password must be same", editConfirmPassword);
//                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_mis_match), LENGTH_SHORT);
                return;
            }

            showPhoneAuthenticationSheet(this, editPhone.getText().toString());

        }
        if (v == txtLogin) {
            startActivity(new Intent(this, Login.class));
            finish();
        }
        if (v == imageBack) {
            startActivity(new Intent(this, Login.class));
            finish();
        }

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.create_account));

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);

        editFirstName = findViewById(R.id.edit_firstName);
        editLastName = findViewById(R.id.edit_lastName);
        imageProfile = findViewById(R.id.image_profile);
        editEmail = findViewById(R.id.edit_email);
        editPhone = findViewById(R.id.edit_phone);
        editPassword = findViewById(R.id.edit_password);
        editConfirmPassword = findViewById(R.id.edit_confirm_password);
        txtSignUp = findViewById(R.id.txt_sign_up);
        txtLogin = findViewById(R.id.txt_login);

        txtSignUp.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        imageProfile.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    public String generateRandomNumber(String phoneNumber) {
        int range = 9;  // to generate a single number with this range, by default its 0..9
        int length = 4; // by default length is 4

        if (otp == 0 || !phoneNo.equalsIgnoreCase(phoneNumber)) {
            SecureRandom secureRandom = new SecureRandom();
            String s = "";
            for (int i = 0; i < length; i++) {
                int number = secureRandom.nextInt(range);
                if (number == 0 && i == 0) { // to prevent the Zero to be the first number as then it will reduce the length of generated pin to three or even more if the second or third number came as zeros
                    i = -1;
                    continue;
                }
                s = s + number;
            }

            otp = Integer.parseInt(s);
            phoneNo = phoneNumber;
        }

        return String.format("http://2factor.in/API/V1/f58a6770-ad34-11ea-9fa5-0200cd936042/SMS/%s/%s/RCHJKS", phoneNumber, otp);
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

    private void showPhoneAuthenticationSheet(final Context context, String phoneNo) {
        final View view = getLayoutInflater().inflate(R.layout.phone_authencitation_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        final GeometricProgressView progress_bar = view.findViewById(R.id.progress_bar);
        final EditText editPhoneNo = view.findViewById(R.id.edit_phone_no);
        LinearLayout layoutDone = view.findViewById(R.id.layout_done);
        ImageView btnClose = view.findViewById(R.id.btnClose);

        final String url = generateRandomNumber(phoneNo);
        Volley.newRequestQueue(this).add(new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("2factor", url);
                        Utility.Toaster(SignUp.this, "OTP Sent Successfully", LENGTH_SHORT);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        );

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();
            }
        });
        layoutDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String verify = editPhoneNo.getText().toString();
                if (verify.equalsIgnoreCase(String.valueOf(otp))) {
                    progress_bar.setVisibility(View.VISIBLE);
                    try {

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.accumulate("functionality", "register");
                        jsonObject.accumulate("userType", Constant.LoginType.NATIVE_LOGIN);
                        jsonObject.accumulate("first_name", editFirstName.getText().toString());
                        jsonObject.accumulate("last_name", "");
                        jsonObject.accumulate("phone", editPhone.getText().toString());
                        jsonObject.accumulate("email", editEmail.getText().toString());
                        jsonObject.accumulate("password", editPassword.getText().toString());
                        jsonObject.accumulate("device_id", Constant.Credentials.DEVICE_TOKEN);
                        jsonObject.accumulate("picture", "user.jpg");

                        management.sendRequestToServer(new RequestObject()
                                .setJson(jsonObject.toString())
                                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                .setConnection(Constant.CONNECTION.SIGN_UP)
                                .setConnectionCallback(new ConnectionCallback() {
                                    @Override
                                    public void onSuccess(Object data, RequestObject requestObject) {
                                        if (data != null && requestObject != null) {

                                            DataObject dataObject = (DataObject) data;

                                            management.savePreferences(new PrefObject()
                                                    .setSaveLogin(true)
                                                    .setLogin(true));

                                            management.savePreferences(new PrefObject()
                                                    .setSaveUserCredential(true)
                                                    .setLoginType(dataObject.getLogin_type())
                                                    .setUserId(dataObject.getUser_id())
                                                    .setFirstName(dataObject.getUser_fName())
                                                    .setLastName(dataObject.getUser_lName())
                                                    .setUserPhone(dataObject.getPhone())
                                                    .setUserPassword(dataObject.getUser_password())
                                                    .setUserEmail(dataObject.getUser_email())
                                                    .setPictureUrl(dataObject.getUser_picture()));


                                            startActivity(new Intent(getApplicationContext(), Base.class));
                                            bottomSheetDialog.dismiss();
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onError(String data, RequestObject requestObject) {
                                        Utility.Toaster(SignUp.this, data, LENGTH_SHORT);
                                        progress_bar.setVisibility(View.GONE);
                                        bottomSheetDialog.dismiss();
                                    }
                                }));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //
                else {
                    editPhoneNo.setError("Error");
                }

            }
        });


    }

}