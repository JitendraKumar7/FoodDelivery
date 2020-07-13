package com.haris.meal4u.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haris.meal4u.InterfaceUtil.CartCallback;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.CartObjectModal;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class CartAdapter extends RecyclerView.Adapter {
    private String TAG = CartAdapter.class.getSimpleName();
    private int NO_DATA_VIEW = 1;
    private int PROGRESS_VIEW = 2;
    private int FIRST_PRODUCT_VIEW = 3;
    private int OTHER_PRODUCT_VIEW = 4;
    private Context context;
    private List<CartObjectModal> dataArray;
    private CartCallback cartCallback;

    public CartAdapter(Context context, List<CartObjectModal> dataArray, CartCallback cartCallback) {
        this.context = context;
        this.dataArray = dataArray;
        this.cartCallback = cartCallback;
    }


    @Override
    public int getItemViewType(int position) {

        return FIRST_PRODUCT_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == FIRST_PRODUCT_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_first_item_layout, parent, false);
            viewHolder = new ProductDetailHolder(view);

        } else if (viewType == OTHER_PRODUCT_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_detail_item_layout, parent, false);
            viewHolder = new ProductDetailHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProductDetailHolder) {

            final ProductDetailHolder productDetailHolder = (ProductDetailHolder) holder;
            final CartObjectModal dataObject = dataArray.get(position);

            productDetailHolder.txtPostTitle.setText(dataObject.getPostName());
            productDetailHolder.txtPrice.setText(String.format("%s %s", dataObject.getCurrencySymbol(), dataObject.getPostQtyPrice()));
            productDetailHolder.txtCount.setText(dataObject.getPostQuantity());
            productDetailHolder.txtPostTitle.setTag(position);


            productDetailHolder.btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) productDetailHolder.txtPostTitle.getTag();
                    if (cartCallback != null) {
                        cartCallback.onItemQuantityListener(pos, false);
                    }
                }
            });

            productDetailHolder.btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) productDetailHolder.txtPostTitle.getTag();
                    if (cartCallback != null) {
                        cartCallback.onItemQuantityListener(pos, true);
                    }

                }
            });

            productDetailHolder.layoutDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) productDetailHolder.txtPostTitle.getTag();
                    if (cartCallback != null) {
                        cartCallback.onItemDeleteListener(pos);
                    }
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return dataArray.size();

    }

    public abstract void select(int childPosition, boolean isSelected);

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private ImageView imageIcon;
        private TextView txtTitle;
        private TextView txtDescription;
        private LinearLayout layoutContainer;

        public EmptyHolder(View view) {
            super(view);

            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
            layoutContainer = view.findViewById(R.id.layout_container);
        }
    }

    protected class ProductDetailHolder extends RecyclerView.ViewHolder {
        private TextView txtPostTitle;
        private TextView txtPrice;
        private ImageView btnDecrease;
        private TextView txtCount;
        private ImageView btnIncrease;
        private LinearLayout layoutDelete;

        public ProductDetailHolder(View view) {
            super(view);

            txtPostTitle = (TextView) view.findViewById(R.id.txt_post_title);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            btnDecrease = (ImageView) view.findViewById(R.id.btn_decrease);
            txtCount = (TextView) view.findViewById(R.id.txt_count);
            btnIncrease = (ImageView) view.findViewById(R.id.btn_increase);
            layoutDelete = view.findViewById(R.id.layout_delete);

        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

    }


}
