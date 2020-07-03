package com.haris.meal4u.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class FilterAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int PROGRESS_VIEW = 2;
    private int DATA_VIEW = 3;
    private Context context;
    private ArrayList<Object> wallpaperArray = new ArrayList<>();


    public FilterAdapter(Context context, ArrayList<Object> wallpaperArray) {
        this.context = context;
        this.wallpaperArray = wallpaperArray;
    }

    @Override
    public int getItemViewType(int position) {


        if (wallpaperArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (wallpaperArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        } else if (wallpaperArray.get(position) instanceof DataObject) {
            return DATA_VIEW;
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

        } else if (viewType == DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_cuisine_item_layout, parent, false);
            viewHolder = new CuisineHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProgressHolder) {

            ProgressHolder lookUpHolder = (ProgressHolder) holder;


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyState = (EmptyObject) wallpaperArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyState.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyState.getTitle());
            emptyHolder.txtDescription.setText(emptyState.getDescription());

        } else if (holder instanceof CuisineHolder) {

            final DataObject dataObject = (DataObject) wallpaperArray.get(position);
            final CuisineHolder categoryHolder = (CuisineHolder) holder;

            if (dataObject.isLongTap()) {
                categoryHolder.txtCuisine.setTextColor(Utility.getColourFromRes(context, R.color.white));
                categoryHolder.cardCuisine.setCardBackgroundColor(Utility.getAttrColor(context, R.attr.colorButton));
            } else {
                categoryHolder.txtCuisine.setTextColor(Utility.getAttrColor(context, R.attr.colorHeading));
                categoryHolder.cardCuisine.setCardBackgroundColor(Utility.getAttrColor(context, R.attr.colorBackground));
            }

            categoryHolder.txtCuisine.setText(dataObject.getCategory_name());
            categoryHolder.cardCuisine.setTag(position);
            categoryHolder.cardCuisine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) categoryHolder.cardCuisine.getTag();

                    if (dataObject.isLongTap()) {
                        dataObject.setLongTap(false);
                    } else {
                        dataObject.setLongTap(true);
                    }

                    notifyItemChanged(pos);
                    //selectItem(pos);
                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return wallpaperArray.size();

    }

    public abstract void selectItem(int position);

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

    protected class CuisineHolder extends RecyclerView.ViewHolder {
        private CardView cardCuisine;
        private TextView txtCuisine;

        public CuisineHolder(View view) {
            super(view);

            cardCuisine = (CardView) view.findViewById(R.id.card_cuisine);
            txtCuisine = (TextView) view.findViewById(R.id.txt_cuisine);

        }

    }


}
