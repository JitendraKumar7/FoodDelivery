package com.haris.meal4u.ActivityUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.DatabaseUtil.DatabaseObject;
import com.haris.meal4u.FontUtil.Font;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.JsonUtil.UserUtil.FavouriteList;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.ObjectUtil.RiderObject;
import com.haris.meal4u.ObjectUtil.RiderTrackingObject;
import com.haris.meal4u.ObjectUtil.TrackingObject;
import com.haris.meal4u.ObjectUtil.TypingObject;
import com.haris.meal4u.ObjectUtil.UserChattingObject;
import com.haris.meal4u.ObjectUtil.UserObject;
import com.haris.meal4u.R;
import com.haris.meal4u.TextviewUtil.UbuntuMediumTextview;
import com.haris.meal4u.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private TextView txtMenu;
    private ImageView imageBack;
    private EditText editEmail;
    private EditText editPassword;
    private AppCompatCheckBox checkboxRemember;
    private TextView txtForgot;
    private TextView txtLogin;
    private TextView txtSignUp;
    private Management management;
    private PrefObject userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI(); //Initialize UI

    }

    private void initUI() {

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.login));

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        editEmail = (EditText) findViewById(R.id.edit_email);
        editPassword = (EditText) findViewById(R.id.edit_password);

        checkboxRemember = (AppCompatCheckBox) findViewById(R.id.checkbox_remember);
        checkboxRemember.setTypeface(Font.ubuntu_regular_font(this));  //Setting Font

        txtForgot = (TextView) findViewById(R.id.txt_forgot);
        txtLogin = (TextView) findViewById(R.id.txt_login);
        txtSignUp = (TextView) findViewById(R.id.txt_sign_up);


        management = new Management(this);
        userData = management.getPreferences(new PrefObject()
                .setRetrieveUserRemember(true)
                .setRetrieveUserCredential(true));

        //Check either userObject remember Email or Password

        if (userData.isUserRemember()) {

            checkboxRemember.setChecked(userData.isUserRemember());
            editEmail.setText(userData.getUserEmail());
            editPassword.setText(userData.getUserPassword());

        } else
            checkboxRemember.setChecked(false);

        txtLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        txtForgot.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        checkboxRemember.setOnCheckedChangeListener(this);

    }

    public String getJson() {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "login");
            jsonObject.accumulate("userType", Constant.LoginType.NATIVE_LOGIN);
            jsonObject.accumulate("email", editEmail.getText().toString());
            jsonObject.accumulate("password", editPassword.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);
        return json;

    }

    @Override
    public void onClick(View v) {
        if (v == txtLogin) {
            if (Utility.isEmptyString(editEmail.getText().toString()) || editEmail.getText().toString().length() != 10) {
                showError("Please enter your valid Mobile Number", editEmail);
//                Utility.Toaster(this, "Please enter your valid Mobile Number", Toast.LENGTH_LONG);
                return;
            }
            if (Utility.isEmptyString(editPassword.getText().toString())) {
                showError("Please enter your password", editPassword);
//                Utility.Toaster(this, "Please enter your password", Toast.LENGTH_LONG);
                return;
            }

            showLoginBottomSheet(this);

        }
        if (v == txtSignUp) {
            startActivity(new Intent(this, SignUp.class));
            finish();
        }
        if (v == txtForgot) {
            startActivity(new Intent(this, ForgotPassword.class));
        }
        if (v == imageBack) {
            finish();
        }
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
                Login.this.requestFocus(editText);
            }
        });
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == checkboxRemember) {

        }
    }

    private void showLoginBottomSheet(final Context context) {
        final View view = getLayoutInflater().inflate(R.layout.process_order_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.LOGIN)
                .setConnectionCallback(new ConnectionCallback() {
                    @Override
                    public void onSuccess(Object data, RequestObject requestObject) {
                        bottomSheetDialog.dismiss();

                        if (data != null && requestObject != null) {

                            DataObject dataObject = (DataObject) data;

                            if (checkboxRemember.isChecked()) {

                                management.savePreferences(new PrefObject()
                                        .setSaveUserRemember(true)
                                        .setUserRemember(true));

                            }

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

                            if (dataObject.getUserFavourite().size() > 0) {
                                for (int i = 0; i < dataObject.getUserFavourite().size(); i++) {
                                    FavouriteList favouriteList = dataObject.getUserFavourite().get(i);
                                    management.getDataFromDatabase(new DatabaseObject()
                                            .setTypeOperation(Constant.TYPE.FAVOURITES)
                                            .setDbOperation(Constant.DB.INSERT)
                                            .setDataObject(new DataObject()
                                                    .setUser_id(favouriteList.getUserId())
                                                    .setObject_id(favouriteList.getRestaurantId())));
                                }

                            }

                            startActivity(new Intent(getApplicationContext(), Base.class));
                            finish();
                        }

                    }

                    @Override
                    public void onError(String data, RequestObject requestObject) {

                        bottomSheetDialog.dismiss();
                        Utility.Toaster(context, data, Toast.LENGTH_SHORT);

                    }
                }));


    }

}
