package com.haris.meal4u.ActivityUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class RedeemCoupon extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private String TAG = RedeemCoupon.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private EditText editCouponCode;
    private LinearLayout layoutRedeem;
    private PrefObject prefObject;
    private DataObject dataObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_coupon);

        getIntentData(); // Retrieve Intent Data
        initUI(); //Initialize UI


    }


    /**
     * <p>It is used to get Intent Data</p>
     */
    private void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.RESTAURANT_DETAIL);
    }


    /**
     * <p>It is used to initialize UI</p>
     */
    private void initUI() {

        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true)
                .setRetrieveLogin(true));

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.my_playlist));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        layoutRedeem = findViewById(R.id.layout_redeem);
        editCouponCode = (EditText) findViewById(R.id.edit_coupon_code);

        imageBack.setOnClickListener(this);
        layoutRedeem.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            setResult(RESULT_CANCELED);
            finish();
        }
        if (v == layoutRedeem) {

            if (Utility.isEmptyString(editCouponCode.getText().toString()))
                return;

            management.sendRequestToServer(new RequestObject()
                    .setJson(getJson(editCouponCode.getText().toString()))
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.REDEEM_COUPON)
                    .setConnectionCallback(this));

        }

    }


    /**
     * <p>It is used to build Deeplink for Direct Sharing</p>
     *
     * @param deepLink   the deep link your app will open. This link must be a valid URL and use the
     *                   HTTP or HTTPS scheme.
     * @param minVersion the {@code versionCode} of the minimum version of your app that can open
     *                   the deep link. If the installed app is an older version, the userObject is taken
     *                   to the Play store to upgrade the app. Pass 0 if you do not
     *                   require a minimum version.
     * @return a {@link Uri} representing a properly formed deep link.
     */
    public void buildDeepLink(@NonNull Uri deepLink, int minVersion) {
        String uriPrefix = Constant.ServerInformation.DEFFERED_DEEP_LINK_URL;

        final Uri[] uri = {null};

        Utility.Logger(TAG, "Link = " + deepLink.getPath());

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setDomainUriPrefix(uriPrefix)
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder()
                        .setMinimumVersion(minVersion)
                        .build())
                .setLink(deepLink)
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {

                        if (task.isSuccessful()) {
                            // Short link created
                            uri[0] = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Utility.Logger(TAG, "ShortLink = " + uri[0].toString() + " flowChartLink = " + flowchartLink.toString());
                            Utility.shareApp(getApplicationContext(), uri[0].toString());

                        } else {
                            Utility.Logger(TAG, "Error = " + task.getException().getMessage());
                        }
                    }
                });

        // [END build_dynamic_link]


    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String couponCode) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "verify_coupon");
            jsonObject.accumulate("coupon_code", couponCode);
            jsonObject.accumulate("restaurant_id", dataObject.getObject_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);

        return json;
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
