package com.haris.meal4u.AdapterUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.CustomUtil.GlideApp;
import com.haris.meal4u.CustomUtil.GridSpacingItemDecoration;
import com.haris.meal4u.FontUtil.Font;
import com.haris.meal4u.FragmentUtil.FeaturedRestaurant;
import com.haris.meal4u.InterfaceUtil.HomeCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.AdObject;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.HomeObject;
import com.haris.meal4u.ObjectUtil.NativeAdObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.ObjectUtil.SpaceObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;
import com.makeramen.roundedimageview.RoundedImageView;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

/**
 * Created by hp on 5/5/2018.
 */


public abstract class HomeAdapter extends RecyclerView.Adapter {
    private RecyclerView.RecycledViewPool viewPool;
    private Management management;
    private Gson gson;
    private int NO_DATA_VIEW = 1;
    private int HOME_VIEW = 2;
    private int PROGRESS_VIEW = 3;
    private int NATIVE_VIEW = 4;
    private int POPULAR_VIEW = 5;
    private int FEATURED_VIEW = 6;
    private int ADMOB_VIEW = 7;
    private int RESTAURANTS_VIEW = 8;
    private int HEADING_VIEW = 9;
    private int DETAIL_RESTAURANT_VIEW = 10;
    private int SPACE_VIEW=11;
    private Context context;
    private HomeCallback homeCallback;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private ArrayList<Fragment> featureArrayList = new ArrayList<>();
    private String TAG = HomeAdapter.class.getName();
    private FragmentManager fragmentManager;
    ArrayList<Object> list = new ArrayList<>();

    public HomeAdapter(Context context, ArrayList<Object> dataArray) {
        this.context = context;
        this.dataArray = dataArray;
        gson = new Gson();
        management = new Management(context);

    }

    public HomeAdapter(Context context, ArrayList<Object> dataArray, HomeCallback homeCallback) {
        this.context = context;
        this.dataArray = dataArray;
        this.homeCallback = homeCallback;
        gson = new Gson();
        management = new Management(context);

    }

    public HomeAdapter(Context context, ArrayList<Object> dataArray, FragmentManager fragmentManager, HomeCallback homeCallback) {
        this.context = context;
        this.dataArray = dataArray;
        this.homeCallback = homeCallback;
        gson = new Gson();
        management = new Management(context);
        this.fragmentManager = fragmentManager;
        viewPool = new RecyclerView.RecycledViewPool();

    }

    @Override
    public int getItemViewType(int position) {

        if (dataArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        }
        else if (dataArray.get(position) instanceof HomeObject) {

            HomeObject homeObject = (HomeObject) dataArray.get(position);

            if (homeObject.getData_type() == Constant.DATA_TYPE.FEATURED)
                return FEATURED_VIEW;
            else if (homeObject.getData_type() == Constant.DATA_TYPE.TOP_BRANDS)
                return HOME_VIEW;
            else if (homeObject.getData_type() == Constant.DATA_TYPE.HEADING_BAR)
                return HEADING_VIEW;

        }
        else if (dataArray.get(position) instanceof DataObject) {

            DataObject dataObject = (DataObject) dataArray.get(position);

            if (dataObject.getDataType() == Constant.DATA_TYPE.TOP_BRANDS) {
                return POPULAR_VIEW;
            } else if (dataObject.getDataType() == Constant.DATA_TYPE.NEAREST) {
                if (dataObject.isIs_layout_01())
                    return RESTAURANTS_VIEW;
                else {
                    return DETAIL_RESTAURANT_VIEW;
                }
            }

        }
        else if (dataArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        }
        else if (dataArray.get(position) instanceof NativeAdObject) {
            return NATIVE_VIEW;
        }
        else if (dataArray.get(position) instanceof AdObject) {
            return ADMOB_VIEW;
        }
        else if (dataArray.get(position) instanceof SpaceObject) {
            return SPACE_VIEW;
        }


        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        }
        else if (viewType == HOME_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item_layout, parent, false);
            viewHolder = new HomeHolder(view);

        }
        else if (viewType == POPULAR_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item_layout, parent, false);
            viewHolder = new PopularHolder(view);

        }
        else if (viewType == FEATURED_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_item_layout, parent, false);
            viewHolder = new FeaturedHolder(view);

        }
        else if (viewType == RESTAURANTS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item_layout, parent, false);
            viewHolder = new NearbyHolder(view, false);

        }
        else if (viewType == DETAIL_RESTAURANT_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_02_item_layout, parent, false);
            viewHolder = new NearbyHolder(view, true);

        }
        else if (viewType == HEADING_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.for_you_item_layout, parent, false);
            viewHolder = new HeaderHolder(view);

        }
        else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        }
        else if (viewType == SPACE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.space_view_item_layout, parent, false);
            viewHolder = new SpaceHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProgressHolder) {

            ProgressHolder lookUpHolder = (ProgressHolder) holder;


        }
        else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyObject = (EmptyObject) dataArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyObject.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyObject.getTitle());
            emptyHolder.txtDescription.setText(emptyObject.getDescription());


        }
        else if (holder instanceof HomeHolder) {

            HomeObject dataObject = (HomeObject) dataArray.get(position);
            final HomeHolder homeHolder = (HomeHolder) holder;

            if (!Utility.isEmptyString(dataObject.getLabel()) ||
                    !Utility.isEmptyString(dataObject.getTitle())
            ) {

                homeHolder.txtLabel.setText(dataObject.getLabel());
                homeHolder.txtTitle.setText(dataObject.getTitle());

            } else {
                homeHolder.layoutLabel.setVisibility(View.GONE);
            }

            list.clear();
            list.addAll(dataObject.getDataObjectArrayList());

            Utility.Logger(TAG, "List Size = " + list.size() + " Data Size = " + dataObject.getDataObjectArrayList().size());

            homeHolder.txtLabel.setTag(position);

            if (((HomeObject) dataArray.get(position)).getData_type() == Constant.DATA_TYPE.FEATURED ||
                    ((HomeObject) dataArray.get(position)).getData_type() == Constant.DATA_TYPE.ARTIST)

                homeHolder.txtMore.setVisibility(View.GONE);

            homeHolder.txtMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) homeHolder.txtLabel.getTag();
                    HomeObject homeObject = (HomeObject) dataArray.get(pos);
                    if (homeCallback != null) {
                        homeCallback.onMore(homeObject.getData_type());
                    }

                }
            });

            homeHolder.homeAdapter.notifyDataSetChanged();




        }
        else if (holder instanceof PopularHolder) {

            DataObject dataObject = (DataObject) dataArray.get(position);
            final PopularHolder popularHolder = (PopularHolder) holder;


            popularHolder.cardTopBrands.setTag(position);
            popularHolder.cardTopBrands.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) popularHolder.cardTopBrands.getTag();
                    select(false, pos);

                }
            });

            popularHolder.txtName.setText(dataObject.getObject_name());
            popularHolder.txtDistance.setText(dataObject.getObject_distance() + " Km");
            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getObject_logo()).into(popularHolder.imageLogo);


        }
        else if (holder instanceof FeaturedHolder) {

            HomeObject dataObject = (HomeObject) dataArray.get(position);
            final FeaturedHolder featuredHolder = (FeaturedHolder) holder;

            //<editor-fold desc="Initializing Feature Videos View Pager">

            featureArrayList.clear();

            for (int i = 0; i < dataObject.getDataObjectArrayList().size(); i++) {
                featureArrayList.add(FeaturedRestaurant.getFeaturedRestaurant(dataObject.getDataObjectArrayList().get(i)));
            }

            featuredHolder.featurePager.notifyDataSetChanged();

            //</editor-fold>


        }
        else if (holder instanceof NearbyHolder) {

            final DataObject dataObject = (DataObject) dataArray.get(position);
            final NearbyHolder nearbyHolder = (NearbyHolder) holder;


            nearbyHolder.layoutRestaurant.setTag(position);
            nearbyHolder.layoutRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) nearbyHolder.layoutRestaurant.getTag();
                    if (homeCallback!=null)
                        homeCallback.onSelect(-1,pos);


                }
            });

            nearbyHolder.txtName.setText(dataObject.getObject_name());
            String tags;

            if (dataObject.isIs_layout_01()) {
                tags = dataObject.getObject_tags();
                nearbyHolder.txtRating.setText(dataObject.getObject_rating());
            }
            else {

                tags = dataObject.getObject_category_name();
                nearbyHolder.txtDistance.setText(dataObject.getObject_distance() + " "+
                        Utility.getStringFromRes(context,R.string.km));

                nearbyHolder.ratingBar.setRating(Float.parseFloat(dataObject.getObject_rating()));
                nearbyHolder.txtRating.setText("("+dataObject.getReviewerList().size()+")");

                GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getObject_picture())
                        .into(nearbyHolder.imageCover);
            }

            if (dataObject.isFavourite()){
                nearbyHolder.imageFavourite.setColorFilter(Utility.getAttrColor(context, R.attr.colorSelectedFavouriteIcon));
            }else {
                nearbyHolder.imageFavourite.setColorFilter(Utility.getAttrColor(context, R.attr.colorDefaultFavouriteIcon));
            }

            nearbyHolder.txtTags.setText(tags);
            nearbyHolder.txtDeliveryTime.setText(dataObject.getObject_min_delivery_time());
            nearbyHolder.txtMinimumCharges.setText(dataObject.getObject_currency_symbol() + " " + dataObject.getObject_min_order_price());
            nearbyHolder.txtExpense.setText(Utility.getBudgetType(context, dataObject.getObject_currency_symbol(), dataObject.getObject_expense()));

            nearbyHolder.imageFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) nearbyHolder.layoutRestaurant.getTag();
                    if (dataObject.isFavourite()){

                        if (homeCallback!=null)
                            homeCallback.onFavourite(pos,false);

                    }
                    else {

                        if (homeCallback!=null)
                            homeCallback.onFavourite(pos,true);

                    }

                }
            });

            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getObject_logo())
                    .into(nearbyHolder.imageLogo);


        }
        else if (holder instanceof HeaderHolder) {

            HomeObject dataObject = (HomeObject) dataArray.get(position);
            final HeaderHolder homeHolder = (HeaderHolder) holder;
            final boolean[] isSwitchLayout = new boolean[1];

            if (!Utility.isEmptyString(dataObject.getLabel()) ||
                    !Utility.isEmptyString(dataObject.getTitle())
            ) {

                homeHolder.txtLabel.setText(dataObject.getLabel());
                homeHolder.txtTitle.setText(dataObject.getTitle());

            }
            else {
                homeHolder.layoutLabel.setVisibility(View.GONE);
            }

            homeHolder.txtLabel.setTag(position);
            if (((HomeObject) dataArray.get(position)).getData_type() == Constant.DATA_TYPE.FEATURED ||
                    ((HomeObject) dataArray.get(position)).getData_type() == Constant.DATA_TYPE.ARTIST)

                homeHolder.txtMore.setVisibility(View.GONE);

            if (dataObject.isLayout01()) {
                homeHolder.imageLayout.setImageResource(R.drawable.ic_layout_01);
            }
            else {
                homeHolder.imageLayout.setImageResource(R.drawable.ic_layout_02);
            }


            homeHolder.imageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) homeHolder.txtLabel.getTag();
                    HomeObject homeObject = (HomeObject) dataArray.get(pos);

                    if (homeObject.isLayout01()) {
                        homeObject.setLayout01(false);
                        isSwitchLayout[0] = false;
                    }
                    else {
                        homeObject.setLayout01(true);
                        isSwitchLayout[0] = true;
                    }

                    if (homeCallback != null) {
                        homeCallback.switchLayout(pos, isSwitchLayout[0]);
                    }

                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return dataArray.size();

    }

    public abstract void select(boolean isLocked, int position);

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private ImageView imageIcon;
        private TextView txtTitle;
        private TextView txtDescription;

        public EmptyHolder(View view) {
            super(view);

            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

    }

    protected class HomeHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtLabel;
        private TextView txtMore;
        private GridLayoutManager gridLayoutManager;
        private RecyclerView recyclerViewRadio;
        private HomeAdapter homeAdapter;
        private RelativeLayout layoutLabel;

        public HomeHolder(View view) {
            super(view);

            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtLabel = (TextView) view.findViewById(R.id.txt_label);
            txtMore = view.findViewById(R.id.txt_more);

            layoutLabel = view.findViewById(R.id.layout_label);

            gridLayoutManager = new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRadio = (RecyclerView) view.findViewById(R.id.recycler_view_radio);
            recyclerViewRadio.setLayoutManager(gridLayoutManager);

            homeAdapter = new HomeAdapter(context, list) {
                @Override
                public void select(boolean isLocked, int position) {
                    int pos = (int) txtLabel.getTag();
                    if (homeCallback!=null)
                    homeCallback.onSelect(pos, position);
                }
            };
            recyclerViewRadio.setAdapter(homeAdapter);

            recyclerViewRadio.setRecycledViewPool(viewPool);

            int spanCount = 5; // 3 columns
            int spacing = 15; // 50px
            boolean includeEdge = true;
            recyclerViewRadio.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));


        }
    }

    protected class PopularHolder extends RecyclerView.ViewHolder {
        private CardView cardTopBrands;
        private RoundedImageView imageLogo;
        private TextView txtName;
        private TextView txtDistance;

        public PopularHolder(View view) {
            super(view);

            cardTopBrands = (CardView) view.findViewById(R.id.card_top_brands);
            imageLogo = (RoundedImageView) view.findViewById(R.id.image_logo);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtDistance = (TextView) view.findViewById(R.id.txt_distance);

        }
    }

    protected class FeaturedHolder extends RecyclerView.ViewHolder {
        private ViewPager viewPagerFeatured;
        private FeaturePager featurePager;
        private ScrollingPagerIndicator indicator;

        public FeaturedHolder(View view) {
            super(view);

            viewPagerFeatured = (ViewPager) view.findViewById(R.id.view_pager_featured);
            indicator = (ScrollingPagerIndicator) view.findViewById(R.id.indicator);

            featurePager = new FeaturePager(fragmentManager, featureArrayList);
            viewPagerFeatured.setAdapter(featurePager);

            indicator.attachToPager(viewPagerFeatured);


        }
    }

    protected class NearbyHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutRestaurant;
        private ImageView imageLogo;
        private ImageView imageCover;
        private TextView txtName;
        private TextView txtTags;
        private TextView txtDistance;
        private TextView txtRating;
        private TextView txtDeliveryTime;
        private TextView txtMinimumCharges;
        private TextView txtExpense;
        private ImageView imageFavourite;
        private RatingBar ratingBar;

        public NearbyHolder(View view, boolean secondLayout) {
            super(view);

            layoutRestaurant = (LinearLayout) view.findViewById(R.id.layout_restaurant);
            imageLogo = (ImageView) view.findViewById(R.id.image_logo);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtTags = (TextView) view.findViewById(R.id.txt_tags);
            txtRating = (TextView) view.findViewById(R.id.txt_rating);
            txtDeliveryTime = (TextView) view.findViewById(R.id.txt_delivery_time);
            txtMinimumCharges = (TextView) view.findViewById(R.id.txt_minimum_charges);
            txtExpense = (TextView) view.findViewById(R.id.txt_expense);
            imageFavourite = (ImageView) view.findViewById(R.id.image_favourite);

            if (secondLayout) {
                txtDistance = (TextView) view.findViewById(R.id.txt_distance);
                ratingBar = view.findViewById(R.id.rating_bar);
                imageCover = (ImageView) view.findViewById(R.id.image_cover);
            }
        }
    }

    protected class HeaderHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtLabel;
        private TextView txtMore;
        private ImageView imageLayout;
        private RelativeLayout layoutLabel;

        public HeaderHolder(View view) {
            super(view);

            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtLabel = (TextView) view.findViewById(R.id.txt_label);
            txtMore = view.findViewById(R.id.txt_more);
            imageLayout = view.findViewById(R.id.image_layout);
            layoutLabel = view.findViewById(R.id.layout_label);


        }
    }

    protected class SpaceHolder extends RecyclerView.ViewHolder {


        public SpaceHolder(View view) {
            super(view);



        }
    }

}

