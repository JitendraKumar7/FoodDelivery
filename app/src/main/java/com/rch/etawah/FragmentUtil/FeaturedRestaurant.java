package com.rch.etawah.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rch.etawah.ActivityUtil.RestaurantDetail;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.CustomUtil.GlideApp;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.R;


public class FeaturedRestaurant extends Fragment implements View.OnClickListener {
    private LinearLayout layoutFeatured;
    private ImageView imageFeatured;
    private Management management;
    private DataObject feature;


    public static Fragment getFeaturedRestaurant(DataObject feature) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.IntentKey.FEATURED_PLACE, (Parcelable) feature);
        Fragment fragment = new FeaturedRestaurant();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_featured_restaurant, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getIntentData(); //Receive Intent Data
        initUI(view); //Initialize UI

    }


    /**
     * <p>It is used to receive Intent data</p>
     */
    private void getIntentData() {

        feature = getArguments().getParcelable(Constant.IntentKey.FEATURED_PLACE);

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        management = new Management(getActivity());

        layoutFeatured = (LinearLayout) view.findViewById(R.id.layout_featured);
        imageFeatured = (ImageView) view.findViewById(R.id.image_featured);

        GlideApp.with(getActivity()).load(Constant.ServerInformation.PICTURE_URL + feature.getOffer_image()).into(imageFeatured);

        layoutFeatured.setOnClickListener(this);

    }


    /**
     * <p>It is used to start new activity &
     * also send data to respective activity</p>
     *
     * @param object
     * @param data
     */
    private void startNewActivity(Class object, String intentKey, Parcelable data) {
        Intent intent = new Intent(getActivity(), object);
        intent.putExtra(intentKey, data);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if (v == layoutFeatured) {

            Intent intent = new Intent(getActivity(), RestaurantDetail.class);
            intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL,feature);
            startActivity(intent);

        }
    }


}
