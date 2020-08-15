package com.haris.meal4u.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haris.meal4u.ActivityUtil.ListOfOrder;
import com.haris.meal4u.ActivityUtil.TrackOrder;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfOrderFragment extends Fragment implements ConnectionCallback, OrderCallback {
    private String TAG = ListOfOrder.class.getName();
    private OrderAdapter orderAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (getActivity() != null && isAdded()) {
            if (requestObject.getConnection() == Constant.CONNECTION.ORDER_HISTORY) {

                objectArrayList.clear();
                DataObject dataObject = (DataObject) data;

                for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {
                    objectArrayList.add(dataObject.getObjectArrayList().get(i));
                }

                orderAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Setting = " + data);
        objectArrayList.clear();
        objectArrayList.add(new EmptyObject()
                .setTitle(Utility.getStringFromRes(getActivity(), R.string.no_order))
                .setDescription(Utility.getStringFromRes(getActivity(), R.string.no_order_tagline))
                .setPlaceHolderIcon(R.drawable.em_no_order));
        orderAdapter.notifyDataSetChanged();
    }


    @Override
    public void onOrderClickListener(int position) {
        DataObject dataObject = (DataObject) objectArrayList.get(position);

        if (dataObject.getOrder_status().equalsIgnoreCase(Utility.getStringFromRes(getActivity(), R.string.project_status_delivered))) {
            return;
        }

        Intent intent = new Intent(getActivity(), TrackOrder.class);
        intent.putExtra(Constant.IntentKey.ORDER_DETAIL, dataObject);
        startActivity(intent);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_order, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(getActivity(), R.string.list_of_order));

        ImageView imageBack = view.findViewById(R.id.image_back);
        LinearLayout orderLayoutSpace = view.findViewById(R.id.orderLayoutSpace);
        orderLayoutSpace.setVisibility(View.VISIBLE);
        imageBack.setVisibility(View.GONE);
        imageBack.setImageResource(R.drawable.ic_back);

        objectArrayList.add(new ProgressObject());
        Management management = new Management(getActivity());

        PrefObject prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));


        //Initialize & Setup Layout Manager with Recycler View

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
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

        RecyclerView recyclerViewOrder = (RecyclerView) view.findViewById(R.id.recycler_view_categories);
        recyclerViewOrder.setLayoutManager(gridLayoutManager);

        //Initialize & Setup Adapter with Recycler View

        orderAdapter = new OrderAdapter(getActivity(), objectArrayList, this);
        recyclerViewOrder.setAdapter(orderAdapter);


        //Send request to Server for retrieving TrendingPhotos Wallpapers


        try {


            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "order_history");
            jsonObject.accumulate("user_id", prefObject.getUserId());

            management.sendRequestToServer(new RequestObject()
                    .setJson(jsonObject.toString())
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.ORDER_HISTORY)
                    .setConnectionCallback(this));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}

