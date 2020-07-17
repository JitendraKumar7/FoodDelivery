package com.haris.meal4u.FragmentUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.haris.meal4u.ActivityUtil.ProductCart;
import com.haris.meal4u.ActivityUtil.RestaurantDetail;
import com.haris.meal4u.AdapterUtil.CategoriesTabPager;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.JsonUtil.HomeUtil.HomeJson;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.PagerTabObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.CartObjectModal;
import com.haris.meal4u.Utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.event.OnSlideClickListener;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

import static com.haris.meal4u.ConstantUtil.Constant.ServerInformation.PICTURE_URL;
import static com.haris.meal4u.ConstantUtil.Constant.ServerInformation.REST_API_URL;

public class DashboardFragment extends Fragment implements View.OnClickListener, ConnectionCallback {

    private ArrayList<PagerTabObject> fragmentArrayList = new ArrayList<>();
    private String TAG = RestaurantDetail.class.getName();
    private CategoriesTabPager categoriesPager;
    private ImageView btnShoppingCart;
    private DataObject dataObject;
    private TabLayout layoutTab;

    public static class PicassoImageLoadingService implements ImageLoadingService {
        public Context context;

        public PicassoImageLoadingService(Context context) {

            this.context = context;
        }

        @Override
        public void loadImage(String url, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(context).load(url).into(imageView);
        }

        @Override
        public void loadImage(int resource, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(context).load(resource).into(imageView);
        }

        @Override
        public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(context).load(url).placeholder(placeHolder).error(errorDrawable).into(imageView);
        }
    }

    public class MainSliderAdapter extends SliderAdapter {
        JSONArray images;

        public MainSliderAdapter(JSONArray images) {

            this.images = images;
        }


        @Override
        public int getItemCount() {

            return images.length();
        }

        @Override
        public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

            try {
                Log.e(TAG, "get_product_special_cover " + images.get(position));
                viewHolder.bindImageSlide(PICTURE_URL + images.get(position));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void initUI(final View v) {

        Management management = new Management(getActivity());

        btnShoppingCart = v.findViewById(R.id.btnShoppingCart);

        ViewPager viewPagerCategories = v.findViewById(R.id.view_pager_categories);
        categoriesPager = new CategoriesTabPager(getChildFragmentManager(), fragmentArrayList);
        viewPagerCategories.setAdapter(categoriesPager);

        layoutTab = v.findViewById(R.id.layout_tab);
        layoutTab.setupWithViewPager(viewPagerCategories);

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "specific_restaurant_menu");
            jsonObject.accumulate("restaurant_id", dataObject.getObject_id());

            management.sendRequestToServer(new RequestObject()
                    .setJson(jsonObject.toString())
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.RESTAURANT_DETAIL)
                    .setConnectionCallback(this));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnShoppingCart.setOnClickListener(this);


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "get_product_special_cover");


            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //["ring-12.jpg","ring-11.jpg"]


                            JSONArray images = null;
                            try {
                                Log.e(TAG, "get_product_special_cover " + response.toString());
                                images = response.getJSONArray("Slider");
                                Slider.init(new PicassoImageLoadingService(getActivity()));
                                final Slider slider = v.findViewById(R.id.banner_slider1);


                                slider.setOnSlideClickListener(new OnSlideClickListener() {
                                    @Override
                                    public void onSlideClick(int position) {
                                        //Do what you want
                                    }
                                });
                                slider.setAdapter(new MainSliderAdapter(images));
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

            Volley.newRequestQueue(getActivity()).add(jsonRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        if (v == btnShoppingCart) {
            if (CartObjectModal.getList().size() > 0) {
                Intent intent = new Intent(getActivity(), ProductCart.class);
                startActivity(intent);
            } else {
                Utility.Toaster(getActivity(), "Cart is empty", Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public void onSuccess(final Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {
            DataObject dataObject = (DataObject) data;

            if (requestObject.getConnection() == Constant.CONNECTION.RESTAURANT_DETAIL) {

                for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {
                    DataObject dtObject = dataObject.getObjectArrayList().get(i);
                    fragmentArrayList.add(new PagerTabObject()
                            .setId(dtObject.getCategory_id())
                            .setTitle(dtObject.getCategory_name())
                            .setFragment(MenuFragment.getFragmentInstance(
                                    dtObject.setObject_currency_symbol(this.dataObject.getObject_currency_symbol())
                                            .setObject_delivery_charges(this.dataObject.getObject_delivery_charges())
                                            .setObject_min_delivery_time(this.dataObject.getObject_min_delivery_time())
                                            .setObject_min_order_price(this.dataObject.getObject_min_order_price())
                                            .setObject_id(this.dataObject.getObject_id())
                                            .setObject_latitude(this.dataObject.getObject_latitude())
                                            .setObject_longitude(this.dataObject.getObject_longitude())
                                            .setPaymentTypeList(this.dataObject.getPaymentTypeList())
                                            .setObject_status("Open"))));
                }

                categoriesPager.notifyDataSetChanged();

                for (int i = 0; i < layoutTab.getTabCount(); i++) {

                    TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_item_layout, null);
                    tv.setTextColor(Utility.getColourFromRes(getActivity(), R.color.colorPrimaryDark));
                    tv.setText(Utility.capitalize(fragmentArrayList.get(i).getTitle()));
                    layoutTab.getTabAt(i).setCustomView(tv);

                }

            }


        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Error = " + data);

        //It would trigger when error generate
        //in result of any server request


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dashboard, null);

        dataObject = CartObjectModal.getSingleRestaurant(getActivity());
        initUI(view); //Initialize UI
        return view;
    }

}
