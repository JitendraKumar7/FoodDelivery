package com.haris.meal4u.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.haris.meal4u.ActivityUtil.AboutUs;
import com.haris.meal4u.ActivityUtil.ListOfOrder;
import com.haris.meal4u.ActivityUtil.ListOfPaymentMethod;
import com.haris.meal4u.ActivityUtil.OnBoarding;
import com.haris.meal4u.ActivityUtil.ProductCart;
import com.haris.meal4u.ActivityUtil.UserProfile;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.DatabaseUtil.DatabaseObject;

import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private String TAG = SettingFragment.class.getName();
    private TextView txtMenu;
    private RelativeLayout layoutDownload;
    private RelativeLayout layoutRate;
    private RelativeLayout layoutShare;
    private RelativeLayout layoutPrivacy;
    private RelativeLayout layoutPlaylist;
    private RelativeLayout layoutProfile;
    private RelativeLayout layoutLogout;
    private RelativeLayout layoutFeed;
    private CardView cardProfile;
    private Management management;
    private PrefObject prefObject;
    private LinearLayout layoutEdit;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtDividerLogout;
    private ImageView imageUser;
    private String pictureUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_setting, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view); //Initialize UI

    }

    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        management = new Management(getActivity());

        txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(getActivity(), R.string.setting));

        layoutProfile = view.findViewById(R.id.layout_profile);
        layoutLogout = view.findViewById(R.id.layout_logout);
        layoutDownload = view.findViewById(R.id.layout_download);
        layoutPlaylist = view.findViewById(R.id.layout_playlist);
        layoutRate = view.findViewById(R.id.layout_rate);
        layoutFeed = view.findViewById(R.id.layout_feed);
        layoutShare = view.findViewById(R.id.layout_share);
        layoutPrivacy = view.findViewById(R.id.layout_privacy);
        layoutEdit = view.findViewById(R.id.layout_edit);
        cardProfile = view.findViewById(R.id.card_profile);

        imageUser = view.findViewById(R.id.image_user);
        txtName = view.findViewById(R.id.txt_name);
        txtEmail = view.findViewById(R.id.txt_email);
        txtDividerLogout = view.findViewById(R.id.txt_divider_logout);


        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveNightMode(true)
                .setRetrieveParallaxMode(true)
                .setRetrievePowerSavingMode(true)
                .setRetrieveScrollingMode(true)
                .setRetrieveRetrieveMotion(true)
                .setRetrieveDelay(true)
                .setRetrievePush(true)
                .setRetrieveUserCredential(true));


        layoutLogout.setOnClickListener(this);
        layoutProfile.setOnClickListener(this);
        layoutDownload.setOnClickListener(this);
        layoutPlaylist.setOnClickListener(this);
        layoutRate.setOnClickListener(this);
        layoutShare.setOnClickListener(this);
        layoutPrivacy.setOnClickListener(this);
        layoutFeed.setOnClickListener(this);
        layoutEdit.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v == layoutProfile) {

            if (prefObject == null)
                return;

            if (prefObject.isLogin()) {

                Intent intent = new Intent(getActivity(), UserProfile.class);
                startActivity(intent);

            } else {

                startActivity(new Intent(getActivity(), OnBoarding.class));

            }

        }
        if (v == layoutShare) {
            Utility.shareApp(getActivity());
        }
        if (v == layoutRate) {
            Utility.rateApp(getActivity());
        }
        if (v == layoutPrivacy) {
            startActivity(new Intent(getActivity(), AboutUs.class));
        }
        if (v == layoutPlaylist) {

            if (prefObject.isLogin()) {

                startActivity(new Intent(getActivity(), ListOfOrder.class));

            } else {

                startActivity(new Intent(getActivity(), OnBoarding.class));

            }

        }
        if (v == layoutLogout) {

            management.savePreferences(new PrefObject()
                    .setSaveLogin(true)
                    .setLogin(false));

            layoutLogout.setVisibility(View.GONE);
            cardProfile.setVisibility(View.GONE);
            txtDividerLogout.setVisibility(View.GONE);

            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FAVOURITES)
                    .setDbOperation(Constant.DB.DELETE_SPECIFIC_TYPE)
                    .setDataObject(new DataObject()
                            .setUser_id(prefObject.getUserId())));


            if (prefObject.getLoginType().equalsIgnoreCase(Constant.LoginType.FACEBOOK_LOGIN)
                    || prefObject.getLoginType().equalsIgnoreCase(Constant.LoginType.GOOGLE_LOGIN))
                FirebaseAuth.getInstance().signOut();

            onResume();

        }
        if (v == layoutEdit) {

            if (prefObject == null)
                return;

            if (prefObject.isLogin()) {

                Intent intent = new Intent(getActivity(), UserProfile.class);
                startActivity(intent);

            } else {

                startActivity(new Intent(getActivity(), OnBoarding.class));

            }

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onResume() {
        super.onResume();

        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        if (prefObject.isLogin()) {

            layoutLogout.setVisibility(View.GONE);
            cardProfile.setVisibility(View.VISIBLE);
            txtDividerLogout.setVisibility(View.VISIBLE);

            txtName.setText(prefObject.getFirstName());
            txtEmail.setText(prefObject.getUserEmail());

            if (prefObject.getLoginType().equals(Constant.LoginType.NATIVE_LOGIN)) {
                pictureUrl = Constant.ServerInformation.PICTURE_URL + prefObject.getPictureUrl();
            }

            Utility.Logger(TAG, "Picture = " + pictureUrl);

            try {

                Glide.with(this).load(pictureUrl).apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(R.drawable.app_icon)
                        .error(R.drawable.app_icon))
                        .into(imageUser);

            } catch (IllegalArgumentException ex) {
                Log.e("Glide-tag", String.valueOf(imageUser.getTag()));
            }

        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
