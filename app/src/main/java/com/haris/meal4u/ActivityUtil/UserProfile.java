package com.haris.meal4u.ActivityUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.CustomUtil.GlideApp;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.Base64Object;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserProfile extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private TextView txtMenu;
    private ImageView imageBack;
    private RoundedImageView imageProfile;
    private EditText editFName;
    private EditText editLName;
    private EditText editEmail;
    private EditText editPhone;
    private EditText editPassword;
    private TextView txtUpdate;
    private Management management;
    private PrefObject userData;
    private Bitmap selectedBitmap;
    private boolean isPictureSelected;
    private String userPicture = "null";
    private String pictureUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.profile));

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);

        imageProfile = findViewById(R.id.image_profile);
        editFName = findViewById(R.id.edit_fName);
        editLName = findViewById(R.id.edit_lName);
        editEmail = findViewById(R.id.edit_email);
        editPhone = findViewById(R.id.edit_phone);
        ///editBio = findViewById(R.id.edit_bio);
        editPassword = findViewById(R.id.edit_password);
        txtUpdate = findViewById(R.id.txt_update);

        userData = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true));

        editFName.setText(userData.getFirstName());
        editLName.setText(userData.getLastName());
        editEmail.setText(userData.getUserEmail());
        editPassword.setText(userData.getUserPassword());
        //editBio.setText(userData.getDescription());
        editPhone.setText(userData.getUserPhone());

        Utility.Logger("Picture Url", Constant.ServerInformation.PROFILE_URL + userData.getPictureUrl());
        if (userData.getLoginType().equalsIgnoreCase(Constant.LoginType.NATIVE_LOGIN)) {
            pictureUrl = Constant.ServerInformation.PROFILE_URL + userData.getPictureUrl();

        }


        GlideApp.with(this).load(pictureUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.profile_picture)
                        .error(R.drawable.profile_picture)
                        .signature(new ObjectKey(System.currentTimeMillis())))
                .into(imageProfile);

        editEmail.setEnabled(false);
        editPhone.setEnabled(false);

        txtUpdate.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        imageProfile.setOnClickListener(this);

    }


    /**
     * <p>It is used to send request to server for updating profile</p>
     */
    private void sendServerRequest() {

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.UPDATE)
                .setConnectionCallback(this));

    }


    /**
     * <p>It is used to convert data into json format for POST type Request</p>
     *
     * @return
     */
    public String getJson() {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "update_profile");
            jsonObject.accumulate("user_id", userData.getUserId());
            jsonObject.accumulate("first_name", editFName.getText().toString());
            jsonObject.accumulate("last_name", "");
            jsonObject.accumulate("email", editEmail.getText().toString());
            jsonObject.accumulate("password", editPassword.getText().toString());

            if (isPictureSelected)
                jsonObject.accumulate("picture", userPicture);


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
        if (v == imageBack) {
            finish();
        }
        if (v == txtUpdate) {
            sendServerRequest();
        }
        if (v == imageProfile) {
            onPictureSelector(this);
        }

    }


    /**
     * <p>It trigger dialog for picture selection </p>
     *
     * @param context
     */
    private void onPictureSelector(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.custom_dialog_layout);

        LinearLayout layout_camera = dialog.findViewById(R.id.layout_camera);
        layout_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectCamera();
                dialog.dismiss();
            }
        });

        LinearLayout layout_gallery = dialog.findViewById(R.id.layout_gallery);
        layout_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSelectGallery();
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    /**
     * <P>It is used to initialize Camera for picture capture</P>
     */
    private void onSelectCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, Constant.RequestCode.REQUEST_CODE_CAMERA);//zero can be replaced with any action code
    }


    /**
     * <p>It is used to open Gallery for picture selection</p>
     */
    private void onSelectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, Constant.RequestCode.REQUEST_CODE_GALLERY);//one can be replaced with any action code
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            selectedBitmap = photo;
            imageProfile.setImageBitmap(photo);
            isPictureSelected = true;

            userPicture = Utility.base64Converter(new Base64Object(true, false, selectedBitmap));

        }
        if (requestCode == Constant.RequestCode.REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            imageProfile.setImageURI(selectedImage);

            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            userPicture = Utility.base64Converter(new Base64Object(true, false, selectedBitmap));

            isPictureSelected = true;
        }


    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        management.savePreferences(new PrefObject()
                .setSaveUserCredential(true)
                .setUserId(userData.getUserId())
                .setLoginType(userData.getLoginType())
                .setFirstName(editFName.getText().toString())
                .setLastName(editLName.getText().toString())
                .setUserPhone(userData.getUserPhone())
                .setUserPassword(editPassword.getText().toString())
                .setUserEmail(userData.getUserEmail())
                .setPictureUrl("user_0" + userData.getUserId() + ".png"));

        pictureUrl = Constant.ServerInformation.PROFILE_URL + "user_0" + userData.getUserId() + ".png";

        GlideApp.with(this).load(pictureUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.profile_picture)
                        .error(R.drawable.profile_picture)
                        .signature(new ObjectKey(System.currentTimeMillis())))
                .into(imageProfile);

    }

    @Override
    public void onError(String data, RequestObject requestObject) {

    }

}
