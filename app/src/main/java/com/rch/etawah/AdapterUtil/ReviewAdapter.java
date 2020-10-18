package com.rch.etawah.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.CustomUtil.GlideApp;
import com.rch.etawah.InterfaceUtil.NearbyCallback;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.EmptyObject;
import com.rch.etawah.ObjectUtil.ProgressObject;
import com.rch.etawah.R;
import com.makeramen.roundedimageview.RoundedImageView;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter {
    private Context context;
    private int NO_DATA_VIEW = 1;
    private int PROGRESS_VIEW = 2;
    private int REVIEW_VIEW = 3;
    private int REVIEW_PICTURE_VIEW = 4;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private String TAG = ReviewAdapter.class.getName();
    private NearbyCallback nearbyCallback;

    public ReviewAdapter(Context context, ArrayList<Object> dataArray) {
        this.context = context;
        this.dataArray = dataArray;

    }


    @Override
    public int getItemViewType(int position) {


        if (dataArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (dataArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        } else if (dataArray.get(position) instanceof DataObject) {
            return REVIEW_VIEW;
        } else if (dataArray.get(position) instanceof String) {
            return REVIEW_PICTURE_VIEW;
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

        } else if (viewType == REVIEW_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_layout, parent, false);
            viewHolder = new ReviewHolder(view);

        } else if (viewType == REVIEW_PICTURE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_picture_item_layout, parent, false);
            viewHolder = new ReviewPictureHolder(view);

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

        } else if (holder instanceof ReviewHolder) {

            DataObject dataObject = (DataObject) dataArray.get(position);
            ReviewHolder reviewHolder = (ReviewHolder) holder;

            reviewHolder.txtName.setText(dataObject.getReview_title());
            reviewHolder.ratingUser.setRating(Float.parseFloat(dataObject.getRating()));
            reviewHolder.txtReview.setText(dataObject.getReviews());
            reviewHolder.txtDate.setText(dataObject.getReview_date());

            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + dataObject.getReviewer_picture())
                    .into(reviewHolder.imageUser);

            reviewHolder.pictureArray.clear();
            reviewHolder.pictureArray.addAll(dataObject.getReviewPictureList());
            reviewHolder.reviewAdapter.notifyDataSetChanged();

        } else if (holder instanceof ReviewPictureHolder) {

            String reviewPicture = (String) dataArray.get(position);
            ReviewPictureHolder reviewPictureHolder = (ReviewPictureHolder) holder;

            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + reviewPicture)
                    .into(reviewPictureHolder.imagePicture);

        }


    }

    @Override
    public int getItemCount() {
        return dataArray.size();

    }

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

    protected class ReviewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageUser;
        private TextView txtName;
        private TextView txtDate;
        private ImageView imageHistory;
        private AppCompatRatingBar ratingUser;
        private TextView txtReview;
        private LinearLayoutManager linearLayoutManager;
        private RecyclerView recyclerViewPicture;
        private ReviewAdapter reviewAdapter;
        public ArrayList<Object> pictureArray = new ArrayList<>();

        public ReviewHolder(View view) {
            super(view);

            imageUser = (RoundedImageView) view.findViewById(R.id.image_user);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtDate = (TextView) view.findViewById(R.id.txt_date);
            imageHistory = (ImageView) view.findViewById(R.id.image_history);
            ratingUser = (AppCompatRatingBar) view.findViewById(R.id.rating_user);
            txtReview = (TextView) view.findViewById(R.id.txt_review);

            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewPicture = (RecyclerView) view.findViewById(R.id.recycler_view_picture);
            recyclerViewPicture.setLayoutManager(linearLayoutManager);

            reviewAdapter = new ReviewAdapter(context, pictureArray);
            recyclerViewPicture.setAdapter(reviewAdapter);

        }
    }

    protected class ReviewPictureHolder extends RecyclerView.ViewHolder {
        private ImageView imagePicture;

        public ReviewPictureHolder(View view) {
            super(view);
            imagePicture = (ImageView) view.findViewById(R.id.image_picture);
        }
    }

}

