package com.haris.meal4u.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Space;
import android.widget.TextView;

import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.CustomUtil.GlideApp;
import com.haris.meal4u.InterfaceUtil.NearbyCallback;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.ObjectUtil.SpaceObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */


public abstract class SearchAdapter extends RecyclerView.Adapter {
    private Context context;
    private int NO_DATA_VIEW = 1;
    private int PROGRESS_VIEW = 2;
    private int RESTAURANT_VIEW_01 = 3;
    private int RESTAURANT_VIEW_02 = 4;
    private int SMALL_PROGRESS_VIEW = 5;
    private int RESTAURANT_VIEW_03 = 6;
    private int SPACE_VIEW=7;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private String TAG = SearchAdapter.class.getName();
    private NearbyCallback nearbyCallback;
    private boolean isMapView = false;

    public SearchAdapter(Context context, ArrayList<Object> dataArray) {
        this.context = context;
        this.dataArray = dataArray;

    }


    public SearchAdapter(Context context, ArrayList<Object> dataArray, NearbyCallback nearbyCallback) {
        this.context = context;
        this.dataArray = dataArray;
        this.nearbyCallback = nearbyCallback;

    }

    public SearchAdapter(Context context, ArrayList<Object> dataArray, NearbyCallback nearbyCallback, boolean isMapView) {
        this.context = context;
        this.dataArray = dataArray;
        this.nearbyCallback = nearbyCallback;
        this.isMapView = isMapView;

    }


    @Override
    public int getItemViewType(int position) {


        if (dataArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (dataArray.get(position) instanceof ProgressObject) {

            ProgressObject progressObject = (ProgressObject) dataArray.get(position);
            if (progressObject.isScrollLoading()) {
                return SMALL_PROGRESS_VIEW;
            } else {
                return PROGRESS_VIEW;
            }

        } else if (dataArray.get(position) instanceof DataObject) {
            DataObject dataObject = (DataObject) dataArray.get(position);

            if (dataObject.isIs_layout_01())
                return RESTAURANT_VIEW_01;
            else if (!isMapView)
                return RESTAURANT_VIEW_02;
            else
                return RESTAURANT_VIEW_03;
        }else if (dataArray.get(position) instanceof SpaceObject){
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

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == SMALL_PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == RESTAURANT_VIEW_01) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item_layout, parent, false);
            viewHolder = new NearbyHolder(view, false);

        } else if (viewType == RESTAURANT_VIEW_02) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_02_item_layout, parent, false);
            viewHolder = new NearbyHolder(view, true);

        } else if (viewType == RESTAURANT_VIEW_03) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_03_item_layout, parent, false);
            viewHolder = new MapHolder(view);

        }else if (viewType == SPACE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.space_view_item_layout, parent, false);
            viewHolder = new SpaceHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProgressHolder) {

            ProgressHolder lookUpHolder = (ProgressHolder) holder;

        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyState = (EmptyObject) dataArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyState.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyState.getTitle());
            emptyHolder.txtDescription.setText(emptyState.getDescription());

        } else if (holder instanceof NearbyHolder) {

            final DataObject dataObject = (DataObject) dataArray.get(position);
            final NearbyHolder nearbyHolder = (NearbyHolder) holder;


            nearbyHolder.layoutRestaurant.setTag(position);
            nearbyHolder.layoutRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) nearbyHolder.layoutRestaurant.getTag();
                    if (nearbyCallback != null)
                        nearbyCallback.onSelect(pos);

                }
            });

            nearbyHolder.txtName.setText(dataObject.getObject_name());
            String tags;

            if (dataObject.isIs_layout_01()) {
                tags = dataObject.getObject_tags();
                nearbyHolder.txtRating.setText(dataObject.getObject_rating());
            } else {

                tags = dataObject.getObject_category_name();
                nearbyHolder.txtDistance.setText(dataObject.getObject_distance() + " " +
                        Utility.getStringFromRes(context, R.string.km));

                nearbyHolder.ratingBar.setRating(Float.parseFloat(dataObject.getObject_rating()));
                nearbyHolder.txtRating.setText("(" + dataObject.getReviewerList().size() + ")");

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


            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getObject_logo())
                    .into(nearbyHolder.imageLogo);

            nearbyHolder.imageFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) nearbyHolder.layoutRestaurant.getTag();
                    if (dataObject.isFavourite()){

                        if (nearbyCallback!=null)
                            nearbyCallback.onFavourite(pos,false);

                    }
                    else {

                        if (nearbyCallback!=null)
                            nearbyCallback.onFavourite(pos,true);

                    }

                }
            });


        } else if (holder instanceof MapHolder) {

            DataObject dataObject = (DataObject) dataArray.get(position);
            final MapHolder nearbyHolder = (MapHolder) holder;


            nearbyHolder.layoutRestaurant.setTag(position);
            nearbyHolder.layoutRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) nearbyHolder.layoutRestaurant.getTag();
                    if (nearbyCallback != null)
                        nearbyCallback.onSelect(pos);

                }
            });

            nearbyHolder.txtName.setText(dataObject.getObject_name());
            /*String tags;

            if (dataObject.isIs_layout_01()) {
                tags = dataObject.getObject_tags();
            } else {
                tags = dataObject.getObject_category_name();
                GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getObject_picture())
                        .into(nearbyHolder.imageCover);
            }*/

            nearbyHolder.txtDistance.setText(dataObject.getObject_distance() + " " +
                    Utility.getStringFromRes(context, R.string.km));
            nearbyHolder.txtRating.setText(dataObject.getObject_rating());

            nearbyHolder.ratingBar.setRating(Float.parseFloat(dataObject.getObject_rating()));
            nearbyHolder.txtRating.setText("(" + dataObject.getObject_no_of_rating() + ")");
            nearbyHolder.txtDeliveryTime.setText(dataObject.getObject_min_delivery_time());
            //nearbyHolder.txtMinimumCharges.setText(dataObject.getObject_currency_symbol() + " " + dataObject.getObject_min_order_price());

            //nearbyHolder.txtTags.setText(tags);
            //nearbyHolder.txtExpense.setText(Utility.getBudgetType(context, dataObject.getObject_currency_symbol(), dataObject.getObject_expense()));


            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getObject_logo())
                    .into(nearbyHolder.imageLogo);


        }


    }

    @Override
    public int getItemCount() {
        return dataArray.size();

    }

    public abstract void selectPlace(int position);

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

    protected class MapHolder extends RecyclerView.ViewHolder {
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

        public MapHolder(View view) {
            super(view);

            layoutRestaurant = (LinearLayout) view.findViewById(R.id.layout_restaurant);
            imageLogo = (ImageView) view.findViewById(R.id.image_logo);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            //txtTags = (TextView) view.findViewById(R.id.txt_tags);
            txtRating = (TextView) view.findViewById(R.id.txt_rating);
            txtDeliveryTime = (TextView) view.findViewById(R.id.txt_delivery_time);
            txtMinimumCharges = (TextView) view.findViewById(R.id.txt_minimum_charges);
            //txtExpense = (TextView) view.findViewById(R.id.txt_expense);
            imageFavourite = (ImageView) view.findViewById(R.id.image_favourite);

            txtDistance = (TextView) view.findViewById(R.id.txt_distance);
            ratingBar = view.findViewById(R.id.rating_bar);
            //imageCover = (ImageView) view.findViewById(R.id.image_cover);

        }
    }

    protected class SpaceHolder extends RecyclerView.ViewHolder {


        public SpaceHolder(View view) {
            super(view);



        }
    }

}

