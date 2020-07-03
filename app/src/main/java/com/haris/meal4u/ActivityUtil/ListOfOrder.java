package com.haris.meal4u.ActivityUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.haris.meal4u.AdapterUtil.OrderAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.InterfaceUtil.OrderCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.InternetObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfOrder extends AppCompatActivity implements View.OnClickListener, ConnectionCallback, OrderCallback {
    private String TAG = ListOfOrder.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewOrder;
    private OrderAdapter orderAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private PrefObject prefObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initUI(); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */


    private void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.list_of_order));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        objectArrayList.add(new ProgressObject());
        management = new Management(this);

        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));


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
                } else {
                    return 1;
                }
            }
        });

        recyclerViewOrder = (RecyclerView) findViewById(R.id.recycler_view_categories);
        recyclerViewOrder.setLayoutManager(gridLayoutManager);

        //Initialize & Setup Adapter with Recycler View

        orderAdapter = new OrderAdapter(this, objectArrayList, this);
        recyclerViewOrder.setAdapter(orderAdapter);


        //Send request to Server for retrieving TrendingPhotos Wallpapers

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.ORDER_HISTORY)
                .setConnectionCallback(this));


        imageBack.setOnClickListener(this);

    }



    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */


    private String getJson() {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.accumulate("functionality", "order_history");
            jsonObject.accumulate("user_id", prefObject.getUserId());


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
        if (requestObject.getConnection() == Constant.CONNECTION.ORDER_HISTORY) {

            objectArrayList.clear();
            DataObject dataObject = (DataObject) data;

            for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {
                objectArrayList.add(dataObject.getObjectArrayList().get(i));
            }

            orderAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Setting = " + data);
        objectArrayList.clear();
        objectArrayList.add(new EmptyObject()
                .setTitle(Utility.getStringFromRes(this, R.string.no_order))
                .setDescription(Utility.getStringFromRes(this, R.string.no_order_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_order));
        orderAdapter.notifyDataSetChanged();
    }


    @Override
    public void onOrderClickListener(int position) {
        DataObject dataObject = (DataObject) objectArrayList.get(position);

        if (dataObject.getOrder_status().equalsIgnoreCase(Utility.getStringFromRes(this,R.string.project_status_delivered))){
            return;
        }

        Intent intent = new Intent(this, TrackOrder.class);
        intent.putExtra(Constant.IntentKey.ORDER_DETAIL, dataObject);
        startActivity(intent);
    }
}
