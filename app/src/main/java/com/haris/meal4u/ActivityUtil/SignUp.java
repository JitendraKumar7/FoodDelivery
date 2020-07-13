package com.haris.meal4u.ActivityUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import net.bohush.geometricprogressview.GeometricProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
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
    private boolean isLoginRequired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getIntentData();  //Retrieve Intent Data
        initUI(); //Initialize UI

    }

    /**
     * <p>It is used to get Intent Data</p>
     */
    private void getIntentData() {
        isLoginRequired = getIntent().getBooleanExtra(Constant.IntentKey.LOGIN_REQUIRED, false);
    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {


        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.create_account));

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);

        editFirstName = (EditText) findViewById(R.id.edit_firstName);
        editLastName = (EditText) findViewById(R.id.edit_lastName);
        imageProfile = (ImageView) findViewById(R.id.image_profile);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editPhone = findViewById(R.id.edit_phone);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
        txtSignUp = (TextView) findViewById(R.id.txt_sign_up);
        txtLogin = (TextView) findViewById(R.id.txt_login);

        txtSignUp.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        imageProfile.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }


    /**
     * <p>It is used to send request to server for userObject registration</p>
     */
    private void sendServerRequest() {

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.SIGN_UP)
                .setConnectionCallback(this));

    }

    public String getJson() {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "register");
            jsonObject.accumulate("userType", Constant.LoginType.NATIVE_LOGIN);
            jsonObject.accumulate("first_name", editFirstName.getText().toString());
            jsonObject.accumulate("last_name", editLastName.getText().toString());
            jsonObject.accumulate("phone", editPhone.getText().toString());
            jsonObject.accumulate("email", editEmail.getText().toString());
            jsonObject.accumulate("password", editPassword.getText().toString());
            jsonObject.accumulate("device_id", Constant.Credentials.DEVICE_TOKEN);
            jsonObject.accumulate("picture", "user.jpg");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.extraData("JSON", json);
        return json;

    }

    @Override
    public void onClick(View v) {
        if (v == txtSignUp) {

            if (Utility.isEmptyString(editFirstName.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.empty_first_name), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editPhone.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_phone), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editEmail.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.email_empty), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editPassword.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_empty), Toast.LENGTH_SHORT);
                return;
            }
            if (Utility.isEmptyString(editConfirmPassword.getText().toString())) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_empty), Toast.LENGTH_SHORT);
                return;
            }

            if (!(editPassword.getText().toString().equals(editConfirmPassword.getText().toString()))) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.password_mis_match), Toast.LENGTH_SHORT);
                return;
            }

            showPhoneAuthenticationSheet(this, editPhone.getText().toString());

        }
        if (v == txtLogin) {

            if (isLoginRequired)
                startActivity(new Intent(this, Login.class));

            finish();
        }
        if (v == imageBack) {
            startActivity(new Intent(this, Login.class));
            finish();
        }

    }


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


           /*

            management.getDataFromDatabase(new DatabaseObject()
                    .setDbOperation(Constant.DB.INSERT)
                    .setTypeOperation(Constant.TYPE.REWARD)
                    .setDataObject(new DataObject()
                            .setCoin(dataObject.getCoin())
                            .setUserId(dataObject.getUserId())));*/


            finish();

        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Toaster(this, data, Toast.LENGTH_SHORT);
    }

    int otp = 0;

    public String generateRandomNumber(String phoneNumber) {
        int range = 9;  // to generate a single number with this range, by default its 0..9
        int length = 4; // by default length is 4

        if (otp == 0) {
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
        }
        return String.format("http://2factor.in/API/V1/f58a6770-ad34-11ea-9fa5-0200cd936042/SMS/%s/%s/RCHJKS", phoneNumber, otp);
    }

    private void showPhoneAuthenticationSheet(final Context context, String phoneNo) {
        final View view = getLayoutInflater().inflate(R.layout.phone_authencitation_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        final EditText editPhoneNo = view.findViewById(R.id.edit_phone_no);
        LinearLayout layoutDone = view.findViewById(R.id.layout_done);
        final TextView txtDone = view.findViewById(R.id.txt_done);
        final GeometricProgressView progressBar = view.findViewById(R.id.progress_bar);

        editPhoneNo.setText(phoneNo);

        String url = generateRandomNumber(phoneNo);


        layoutDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editPhoneNo.getText().toString().equals(otp)) {
                    sendServerRequest();
                }

                //
                else {
                    editPhoneNo.setError("Error");
                }

            }
        });


    }

}