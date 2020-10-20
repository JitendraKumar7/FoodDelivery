package com.rch.etawah;

import android.graphics.Color;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rch.etawah.ActivityUtil.SignUp;
import com.rch.etawah.Utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static android.widget.Toast.LENGTH_SHORT;

public class GalleryActivity extends AppCompatActivity {

    private List<GalleryImage> mDataList;

    public class GalleryIvAdapter extends RecyclerView.Adapter<CustomViewHolder> {

        @Override
        public int getItemCount() {

            return mDataList.size();
        }



        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

                //View view = inflater.inflate(R.layout.ui_row_testimonial, parent, Boolean.FALSE);
                //return new CustomViewHolder(view);
            return null;

        }

    }

    public final class CustomViewHolder extends RecyclerView.ViewHolder {


        CustomViewHolder(View itemView) {
            super(itemView);
        }

        void onBindView(GalleryImage bean) {

           /* ivProduct.setOnClickListener(v -> {
                // View Full Screen
                new FullScreenPopup(getActivity(), bean.getTitle());
            });*/

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mDataList = new ArrayList<>();

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        String url = "http://gallery.rchetawah.com/apiGallery.php";
        Volley.newRequestQueue(GalleryActivity.this).add(new StringRequest(
                url, response -> {
            Gson gson = new Gson();
            Log.e("gallery", response);

            GalleryImage[] images = gson.fromJson(response, GalleryImage[].class);
            mDataList.addAll(Arrays.asList(images));


            LinearLayoutManager manager = new LinearLayoutManager(GalleryActivity.this);

            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            //mRecyclerView.setAdapter(new GalleryIvAdapter(manager));

        }, error -> {
            Log.e("gallery", Objects.requireNonNull(error.getMessage()));
            Log.e("gallery", Objects.requireNonNull(error.getMessage()));
        }
        ));
    }

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

    /*//http://gallery.rchetawah.com/apiGallery.php
//http://gallery.rchetawah.com/photos/{gallery}/{photo[]}

[{
	"gallery": "RCH Etawah",
	"photos": ["maggi puf.jpg", "rch_burger.jpeg", "rch_pizza.jpeg"]
}, {
	"gallery": "Random Photos",
	"photos": ["Bugatti_Molsheim_PGOS_0735.0.jpg", "aaaaaaaaaaaaaaaaaa.jpg", "dfh.jpg", "mi-redmi-6-na-original-imaf8qtkgh6qhs3p.jpeg", "mi-redmi-note-5-pro-mzb6081in-mzb6089in-original-imaf52hhf3yy5bg8.jpeg", "mimix.jpg", "nokia.jpg"]
}, {
	"gallery": "Suits Collection",
	"photos": ["IMG_20160812_171122.jpg", "IMG_20160812_171138.jpg", "IMG_20160812_171153.jpg", "IMG_20160829_204027.jpg", "alephant.jpg", "bird flowers.jpg", "bird painting.jpg", "mahapurush.jpg", "photo frame.jpg", "tree painting.jpg"]
}]*/
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