package com.rch.etawah.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rch.etawah.ObjectUtil.EmptyObject;
import com.rch.etawah.ObjectUtil.PictureObject;
import com.rch.etawah.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * Created by hp on 6/13/2018.
 */

public abstract class PictureUploaderAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> homeArrays = new ArrayList<>();
    private ArrayList<Object> object = new ArrayList<>();
    private int PICTURE_VIEW = 0, EMPTY_VIEW = 1;
    private ArrayList<Object> nearbyObject = new ArrayList<>();
    private String photoRef;


    public PictureUploaderAdapter(Context context, ArrayList<Object> homeArrays) {
        this.context = context;
        this.homeArrays = homeArrays;
    }

    @Override
    public int getItemViewType(int position) {

        if (homeArrays.get(position) instanceof PictureObject)
            return PICTURE_VIEW;
        else if (homeArrays.get(position) instanceof EmptyObject)
            return EMPTY_VIEW;
        else
            return PICTURE_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == PICTURE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item_layout, parent, false);
            viewHolder = new PictureHolder(view);

        } else if (viewType == EMPTY_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof PictureHolder) {


            final PictureHolder placeHolder = (PictureHolder) holder;
            PictureObject pictureObject = (PictureObject) homeArrays.get(position);

            Glide.with(context).load(Base64.decode(pictureObject.getPicture(), Base64.DEFAULT)).into(placeHolder.imagePicture);
            //placeHolder.imagePicture.setImageBitmap(pictureObject.getBitmap());

            if (pictureObject.isLongTap()) {

                placeHolder.layoutOptions.setVisibility(View.VISIBLE);

            } else {

                placeHolder.layoutOptions.setVisibility(View.GONE);

            }

            placeHolder.layoutPicture.setTag(position);
            placeHolder.layoutPicture.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = (int) placeHolder.layoutPicture.getTag();
                    ((PictureObject) homeArrays.get(pos)).setLongTap(true);
                    notifyItemChanged(pos);
                    return true;
                }
            });
            placeHolder.layoutDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) placeHolder.layoutPicture.getTag();
                    onDelete(pos);


                }
            });
            placeHolder.layoutClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) placeHolder.layoutPicture.getTag();
                    ((PictureObject) homeArrays.get(pos)).setLongTap(false);
                    notifyItemChanged(pos);


                }
            });


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyState = (EmptyObject) homeArrays.get(position);

            emptyHolder.imageIcon.setImageResource(emptyState.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyState.getTitle());
            emptyHolder.txtDescription.setText(emptyState.getDescription());

        }


    }

    @Override
    public int getItemCount() {
        return homeArrays.size();
    }

    public abstract void onDelete(int position);


    protected class PictureHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imagePicture;
        private RelativeLayout layoutPicture;
        private LinearLayout layoutDelete;
        private LinearLayout layoutClose;
        private RelativeLayout layoutOptions;

        public PictureHolder(View view) {
            super(view);

            imagePicture = (RoundedImageView) view.findViewById(R.id.image_picture);
            layoutPicture = (RelativeLayout) view.findViewById(R.id.layout_picture);
            layoutDelete = (LinearLayout) view.findViewById(R.id.layout_delete);
            layoutClose = (LinearLayout) view.findViewById(R.id.layout_close);
            layoutOptions = view.findViewById(R.id.layout_options);

        }


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

}
