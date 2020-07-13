package com.haris.meal4u.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.haris.meal4u.ActivityUtil.RestaurantDetail;
import com.haris.meal4u.AdapterUtil.NearbyListAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.CustomUtil.GridSpacingItemDecoration;
import com.haris.meal4u.DatabaseUtil.DatabaseObject;
import com.haris.meal4u.FragmentUtil.ListviewFragment;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.InterfaceUtil.NearbyCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.InternetObject;
import com.haris.meal4u.ObjectUtil.NativeAdObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.ObjectUtil.SpaceObject;
import com.haris.meal4u.ObjectUtil.WallpaperHeaderObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Favourites extends Fragment implements View.OnClickListener, ConnectionCallback, NearbyCallback {
    private String TAG = Favourites.class.getName();
    private ImageView imageBack;
    private TextView txtMenu;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewNearby;
    private NearbyListAdapter nearbyListAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<Object> cloneList = new ArrayList<>();
    private ArrayList<Object> favouriteArraylist = new ArrayList<>();
    private PrefObject prefObject;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;
    private boolean loading = true;
    private StringBuilder stringBuilder = new StringBuilder();
    private HashMap<String, String> favouriteMap = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_favourites, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Utility.changeAppTheme(getActivity());
        super.onViewCreated(view, savedInstanceState);

        ///getIntentData(); //Receive Intent Data
        initUI(view); //Initialize UI

    }

    /**
     * <p> It initialize
     * the UI</p>
     */
    private void initUI(View view) {


        txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(getActivity(), R.string.my_favourites));

        imageBack = view.findViewById(R.id.image_back);
        imageBack.setVisibility(View.GONE);
        imageBack.setImageResource(R.drawable.ic_back);


        management = new Management(getActivity());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        objectArrayList.clear();
        objectArrayList.add(new ProgressObject());

        //Initialize & Setup Layout Manager with Recycler View

        gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
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

        recyclerViewNearby = (RecyclerView) view.findViewById(R.id.recycler_view_favourites);
        recyclerViewNearby.setLayoutManager(gridLayoutManager);


        //Initialize & Setup Adapter with Recycler View

        nearbyListAdapter = new NearbyListAdapter(getActivity(), objectArrayList, this) {
            @Override
            public void selectPlace(int position) {

            }
        };
        recyclerViewNearby.setAdapter(nearbyListAdapter);


        //Send request to Server for retrieving TrendingPhotos Wallpapers

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.ALL_FAVOURITES)
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

            jsonObject.accumulate("functionality", "all_favourites");
            jsonObject.accumulate("user_id", prefObject.getUserId());
            jsonObject.accumulate("latitude", String.valueOf(Constant.getBaseLatLng().latitude));
            jsonObject.accumulate("longitude", String.valueOf(Constant.getBaseLatLng().longitude));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getFavouriteJson(String functionality, String user_id, String restaurant_id) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", functionality);
            jsonObject.accumulate("restaurant_id", restaurant_id);
            jsonObject.accumulate("user_id", user_id);

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


    }


    @Override
    public void onResume() {
        super.onResume();


        favouriteArraylist.clear();
        favouriteArraylist.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setTypeOperation(Constant.TYPE.FAVOURITES)
                .setDbOperation(Constant.DB.SPECIFIC_ID)
                .setDataObject(new DataObject()
                        .setUser_id(prefObject.getUserId()))));

        favouriteMap.clear();

        for (int i = 0; i < favouriteArraylist.size(); i++) {
            DataObject favouriteObject = (DataObject) favouriteArraylist.get(i);
            favouriteMap.put(favouriteObject.getObject_id(), favouriteObject.getUser_id());
        }

    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {


            if (requestObject.isFirstRequest()) {
                objectArrayList.clear();
            } else {
                stringBuilder.append(",");
                int listLastItem = objectArrayList.size() - 1;
                objectArrayList.remove(listLastItem);

                listLastItem = objectArrayList.size() - 1;
                objectArrayList.remove(listLastItem);
            }

            loading = true;
            DataObject dataObject = (DataObject) data;

            for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {

                if (i != 0 && 0 == i % Constant.Credentials.nativeAdInterval && Constant.Credentials.isFbNativeAds)
                    objectArrayList.add(new NativeAdObject());

                DataObject dtObject = dataObject.getObjectArrayList().get(i);
                if (favouriteMap.containsKey(dtObject.getObject_id())) {
                    dtObject.setFavourite(true);
                    Utility.Logger(TAG, "Data Object = Working");
                }

                objectArrayList.add(dtObject);

                stringBuilder.append("'");
                stringBuilder.append(dataObject.getObjectArrayList().get(i).getObject_id());
                stringBuilder.append("'");

                if (i < (dataObject.getObjectArrayList().size() - 1))
                    stringBuilder.append(",");

            }

            objectArrayList.add(new SpaceObject());
            cloneList.clear();
            cloneList.addAll(objectArrayList);

            nearbyListAdapter.notifyDataSetChanged();

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (objectArrayList.size() > 0) {

                        Bundle bundle = new Bundle();
                        DataObject dataObject = (DataObject) objectArrayList.get(0);
                        bundle.putParcelable(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
                    }
                }
            });

        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Error = " + data);
        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
            objectArrayList.add(new EmptyObject()
                    .setTitle(Utility.getStringFromRes(getActivity(), R.string.no_favourite_found))
                    .setDescription(Utility.getStringFromRes(getActivity(), R.string.no_favourite_tagline))
                    .setPlaceHolderIcon(R.drawable.em_no_favourite));

        } else {
            int listLastItem = objectArrayList.size() - 1;
            objectArrayList.remove(listLastItem);
        }

        loading = false;
        nearbyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSelect(int position) {
        DataObject dataObject = (DataObject) objectArrayList.get(position);
        Intent intent = new Intent(getActivity(), RestaurantDetail.class);
        intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
        startActivity(intent);
    }

    @Override
    public void onFavourite(int position, boolean isFavourite) {
        DataObject dtObject = (DataObject) objectArrayList.get(position);
        //((DataObject) objectArrayList.get(position)).setFavourite(isFavourite);


        //TODO Replace UserObject ID

        if (isFavourite) {

            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FAVOURITES)
                    .setDbOperation(Constant.DB.INSERT)
                    .setDataObject(new DataObject()
                            .setUser_id(prefObject.getUserId())
                            .setObject_id(dtObject.getObject_id())));


        } else {

            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FAVOURITES)
                    .setDbOperation(Constant.DB.DELETE_SPECIFIC_TYPE)
                    .setDataObject(new DataObject()
                            .setUser_id(prefObject.getUserId())
                            .setObject_id(dtObject.getObject_id())));

            management.sendRequestToServer(new RequestObject()
                    .setContext(getActivity())
                    .setJson(getFavouriteJson("delete_favourites", prefObject.getUserId(), dtObject.getObject_id()))
                    .setConnection(Constant.CONNECTION.DELETE_FAVOURITES)
                    .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND));

        }

        objectArrayList.remove(position);
        if (objectArrayList.size() <= 1) {

            objectArrayList.clear();
            objectArrayList.add(new EmptyObject()
                    .setTitle(Utility.getStringFromRes(getActivity(), R.string.no_favourite_found))
                    .setDescription(Utility.getStringFromRes(getActivity(), R.string.no_favourite_tagline))
                    .setPlaceHolderIcon(R.drawable.em_no_favourite));

        }

        nearbyListAdapter.notifyDataSetChanged();


    }

}