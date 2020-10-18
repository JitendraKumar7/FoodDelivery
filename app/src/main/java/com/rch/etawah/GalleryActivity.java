package com.rch.etawah;

import android.graphics.Color;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class GalleryActivity extends AppCompatActivity {

    private List<GalleryImage> mDataList;

    /*@Override
    public void onCreateView(LayoutInflater inflater) {

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        Basic basic = (Basic) getArguments().getSerializable(SHARE_CARD);

        JsonObject params = new JsonObject();
        params.addProperty("konnect_id", basic.getCardId());

        RetrofitClient.getInstance(getActivity(), object -> {
            try {
                List<GalleryImage> list = ParseHelper.getGallery(object);
                mDataList = new ArrayList<>();

                for (GalleryImage image : list) {
                    mDataList.add(new GalleryImage(image));
                    for (GalleryImage.GalleryImage1 image1 : image.getGalleryImage()) {
                        mDataList.add(new GalleryImage(image1));
                    }
                }

                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(new GalleryIvAdapter(manager));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).getGallery(params);

    }


    public class GalleryIvAdapter extends RecyclerView.Adapter {

        int ITEM_VIEW_TYPE_ITEM = 1;
        int ITEM_VIEW_TYPE_HEADER = 0;

        GalleryIvAdapter(GridLayoutManager manager) {
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isHeader(position) ? manager.getSpanCount() : 1;
                }
            });

        }

        @Override
        public int getItemCount() {

            return mDataList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return isHeader(position) ?
                    ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof CustomViewHolder) {
                CustomViewHolder cvh = (CustomViewHolder) holder;
                cvh.onBindView(mDataList.get(position));
            }
            //
            else if (holder instanceof HeaderViewHolder) {
                HeaderViewHolder cvh = (HeaderViewHolder) holder;
                cvh.setText(mDataList.get(position));
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            if (viewType == ITEM_VIEW_TYPE_ITEM) {
                View view = inflater.inflate(R.layout.ui_row_testimonial, parent, Boolean.FALSE);
                return new CustomViewHolder(view);
            } else {

                TextView textView = new TextView(parent.getContext());
                textView.setPadding(12, 12, 12, 12);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setTextColor(Color.BLACK);

                return new HeaderViewHolder(textView);
            }

        }

    }

    public final class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView itemView;

        void setText(GalleryImage bean) {

            itemView.setText(bean.getTitle());
        }

        HeaderViewHolder(TextView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    public final class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.ivProduct)
        ImageView ivProduct;

        CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void onBindView(GalleryImage bean) {

            Picasso.get().load(bean.getTitle())
                    .placeholder(R.drawable.placeholder).into(ivProduct);

            ivProduct.setOnClickListener(v -> {
                // View Full Screen
                new FullScreenPopup(getActivity(), bean.getTitle());
            });

        }
    }*/

}

/*https://meo.co.in/meoApiPro/konnectBusiness_v4/index.php/addNewCard

{"user_id":"881","name":"Jitendra7","email":"jitendra7.meo@gmail.com","designation":"Android Developer","company_name":"","mobile_number":"9654431845","category_of_business":"","nature_of_business":"","cardType":"ecommerce","konnect_type":"ecommerce","referralCode":"R85JX0","other_description":""}

 {"status":"201","message":"userover","result":"userover"}


contact
gallery

share offer


proforma invoice edit
party master phone or whtasapp
payment auto save -->


bhartiyarishte-com.preview-domain.com

https://webmail1.hostinger.in/
info@bhartiyarishte.com
K1AXHb[d



u776573652_db
u776573652_me

K*Tw$A^4




*/