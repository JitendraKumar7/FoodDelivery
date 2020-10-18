package com.rch.etawah.ActivityUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rch.etawah.AdapterUtil.ReviewAdapter;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.InterfaceUtil.ConnectionCallback;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.BarObject;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.EmptyObject;
import com.rch.etawah.ObjectUtil.InternetObject;
import com.rch.etawah.ObjectUtil.PrefObject;
import com.rch.etawah.ObjectUtil.ProgressObject;
import com.rch.etawah.ObjectUtil.RequestObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfReviews extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private String TAG = ListOfReviews.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewReview;
    private ReviewAdapter reviewAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private String restaurantId;
    private Constant.CONNECTION connection;
    private PrefObject prefObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getIntentData(); //Retrieve Intent Data
        initUI(); //Initialize UI


    }

    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {

        restaurantId = getIntent().getStringExtra(Constant.IntentKey.RESTAURANT_DETAIL);


    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        Utility.Logger(TAG, "Setting = Restaurant Id " + restaurantId);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.restaurant_review));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveParallaxMode(true));

        objectArrayList.add(new ProgressObject());

        //Initialize & Setup Layout Manager with Recycler View

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (objectArrayList.get(position) instanceof EmptyObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof InternetObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof ProgressObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof BarObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof DataObject) {

                    return 1;
                } else {
                    return 1;
                }
            }
        });

        recyclerViewReview = (RecyclerView) findViewById(R.id.recycler_view_review);
        recyclerViewReview.setLayoutManager(gridLayoutManager);

        /*int spanCount = 1; // 3 columns
        int spacing = 15; // 50px
        boolean includeEdge = true;
        recyclerViewReview.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));*/

        //Initialize & Setup Adapter with Recycler View

        reviewAdapter = new ReviewAdapter(this, objectArrayList);
        recyclerViewReview.setAdapter(reviewAdapter);


        //Send request to Server for retrieving TrendingPhotos Wallpapers

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson(restaurantId))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.ALL_COMMENT)
                .setConnectionCallback(this));


        imageBack.setOnClickListener(this);

    }




    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String restaurant_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.accumulate("functionality", "all_reviews");
            jsonObject.accumulate("restaurant_id", restaurant_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }

    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (requestObject.getConnection() == Constant.CONNECTION.ALL_COMMENT) {

            objectArrayList.clear();
            DataObject dataObject = (DataObject) data;
            objectArrayList.addAll(((DataObject) data).getObjectArrayList());
            reviewAdapter.notifyDataSetChanged();


        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {


        Utility.Logger(TAG, "Error = " + data);
        objectArrayList.clear();
        objectArrayList.add(new EmptyObject()
                .setTitle(Utility.getStringFromRes(this, R.string.no_book))
                .setDescription(Utility.getStringFromRes(this, R.string.no_book_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_book));
        reviewAdapter.notifyDataSetChanged();


    }


}