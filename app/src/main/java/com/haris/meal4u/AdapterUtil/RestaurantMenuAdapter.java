package com.haris.meal4u.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.ProductCallback;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */


public abstract class RestaurantMenuAdapter extends RecyclerView.Adapter {
    private Context context;
    private int NO_DATA_VIEW = 1;
    private int PROGRESS_VIEW = 2;
    private int SMALL_PROGRESS_VIEW = 3;
    private int PRODUCT_MENU_VIEW = 4;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private String TAG = RestaurantMenuAdapter.class.getName();
    private ProductCallback productCallback;


    public RestaurantMenuAdapter(Context context, ArrayList<Object> dataArray, ProductCallback productCallback) {
        this.context = context;
        this.dataArray = dataArray;
        this.productCallback = productCallback;

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
            return PRODUCT_MENU_VIEW;
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

            int height = parent.getMeasuredHeight() / 2;
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_progress_item_layout, parent, false);
            RelativeLayout layoutContainer = view.findViewById(R.id.layout_container);
            layoutContainer.setMinimumHeight(height);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == PRODUCT_MENU_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_menu_item_layout, parent, false);
            viewHolder = new MenuProductHolder(view);

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

        } else if (holder instanceof MenuProductHolder) {

            final DataObject dataObject = (DataObject) dataArray.get(position);
            final MenuProductHolder menuProductHolder = (MenuProductHolder) holder;


            menuProductHolder.layoutProduct.setTag(position);
            menuProductHolder.layoutProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) menuProductHolder.layoutProduct.getTag();
                    if (productCallback != null)
                        productCallback.onSelect(pos);

                }
            });

            menuProductHolder.txtProduct.setText(dataObject.getPost_name());
            menuProductHolder.txtDescription.setText(dataObject.getPost_description());
            menuProductHolder.txtPrice.setText(dataObject.getObject_currency_symbol() + " " + dataObject.getPost_price());

            if (!Utility.isEmptyString(dataObject.getPost_image())) {
                Glide.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getPost_image())
                        .into(menuProductHolder.imageProduct);
                menuProductHolder.layoutImage.setVisibility(View.VISIBLE);
            } else {
                menuProductHolder.layoutImage.setVisibility(View.GONE);
            }

            menuProductHolder.txtCount.setText(dataObject.getNoOfItemInCart());

        }


    }

    @Override
    public int getItemCount() {
        return dataArray.size();

    }

    public abstract void selectProduct(int position);

    public abstract void addToCartProduct(View v, DataObject dataObject);

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

    protected class MenuProductHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutProduct;
        private LinearLayout layoutImage;
        private RoundedImageView imageProduct;
        private ImageView btn_decrease;
        private ImageView btn_increase;
        private TextView txtProduct;
        private TextView txtDescription;
        private TextView txtPrice;
        private TextView txtCount;

        public MenuProductHolder(View view) {
            super(view);

            layoutProduct = view.findViewById(R.id.layout_product);
            layoutImage = view.findViewById(R.id.layout_image);
            imageProduct = view.findViewById(R.id.image_product);
            txtProduct = view.findViewById(R.id.txt_product);
            txtDescription = view.findViewById(R.id.txt_description);
            txtPrice = view.findViewById(R.id.txt_price);
            txtCount = view.findViewById(R.id.txt_count);
            btn_decrease = view.findViewById(R.id.btn_decrease);
            btn_increase = view.findViewById(R.id.btn_increase);

            btn_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DataObject dataObject = (DataObject) dataArray.get(getAdapterPosition());
                    addToCartProduct(v, dataObject);
                }
            });

            btn_increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DataObject dataObject = (DataObject) dataArray.get(getAdapterPosition());
                    addToCartProduct(v, dataObject);

                }
            });
        }
    }

}

