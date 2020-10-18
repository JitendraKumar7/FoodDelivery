package com.rch.etawah.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rch.etawah.ActivityUtil.OnBoarding;
import com.rch.etawah.ActivityUtil.RestaurantDetail;
import com.rch.etawah.AdapterUtil.NearbyListAdapter;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.DatabaseUtil.DatabaseObject;
import com.rch.etawah.InterfaceUtil.ConnectionCallback;
import com.rch.etawah.InterfaceUtil.NearbyCallback;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.EmptyObject;
import com.rch.etawah.ObjectUtil.FilterObject;
import com.rch.etawah.ObjectUtil.InternetObject;
import com.rch.etawah.ObjectUtil.NativeAdObject;
import com.rch.etawah.ObjectUtil.PrefObject;
import com.rch.etawah.ObjectUtil.ProgressObject;
import com.rch.etawah.ObjectUtil.RequestObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MapviewFragment extends Fragment implements ConnectionCallback, NearbyCallback, View.OnClickListener{
    private String TAG = MapviewFragment.class.getName();
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewNearby;
    private NearbyListAdapter nearbyListAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<Object> favouriteArraylist = new ArrayList<>();
    private HashMap<String, String> favouriteMap = new HashMap<>();
    private int visibleItemCount, totalItemCount;
    private int pastVisiblesItems;
    private boolean loading = true;
    private StringBuilder stringBuilder = new StringBuilder();
    private Double latitude, longitude;
    private FilterObject filterObject;
    private String radius;
    private String rating;
    private String expense;
    private String category;
    private PrefObject prefObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_map_view, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getIntentData(); //Retrieve Intent Data
        initUI(view, savedInstanceState); //Initialize UI


    }

    /**
     * <p>It is used to get Intent Data</p>
     */
    private void getIntentData(){
        if (getArguments()!=null)
            filterObject=getArguments().getParcelable(Constant.IntentKey.FILTER);
    }


    /**
     * <p>It initialize the UI</p>
     */

    private void initUI(View view, Bundle savedInstanceState) {

        management = new Management(getActivity());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        if (filterObject!=null){

            radius= Utility.isEmptyString(filterObject.getRadius()) ? Constant.AppConfiguration.DEFAULT_RADIUS :
                    filterObject.getRadius() ;

            rating=Utility.isEmptyString(filterObject.getRating()) ? null :
                    filterObject.getRating();

            expense=Utility.isEmptyString(filterObject.getExpense()) ? null :
                    filterObject.getExpense();

            category=Utility.isEmptyString(filterObject.getCusines()) ? null :
                    filterObject.getCusines();

        }else {

            radius=Constant.AppConfiguration.DEFAULT_RADIUS;
            rating=null;
            expense=null;
            category=null;

        }


        objectArrayList.add(new ProgressObject());


        //Initialize & Setup Layout Manager with Recycler View

        gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);
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

        recyclerViewNearby = (RecyclerView) view.findViewById(R.id.recycler_view_places);
        recyclerViewNearby.setLayoutManager(gridLayoutManager);


        //Initialize & Setup Adapter with Recycler View

        nearbyListAdapter = new NearbyListAdapter(getActivity(), objectArrayList, this, true) {
            @Override
            public void selectPlace(int position) {

            }
        };
        recyclerViewNearby.setAdapter(nearbyListAdapter);
        recyclerViewNearby.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = gridLayoutManager.getChildCount();
                    totalItemCount = gridLayoutManager.getItemCount();
                    pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Utility.Logger(TAG, "Last Item Wow !");
                            objectArrayList.add(new ProgressObject().setScrollLoading(true));
                            management.sendRequestToServer(new RequestObject()
                                    .setFirstRequest(false)
                                    .setJson(getJson("", "",
                                            radius,stringBuilder.toString(),category, rating, expense))
                                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                    .setConnection(Constant.CONNECTION.NEAREST)
                                    .setConnectionCallback(MapviewFragment.this));
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });


        //Send request to Server for retrieving Nearest Data

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson("", "",
                        Constant.AppConfiguration.DEFAULT_RADIUS,null,
                        category, rating, expense))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.NEAREST)
                .setConnectionCallback(this));
    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */

    private String getJson(String latitude, String longitude, String radius, String skipIds,String category, String rating, String expense) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "nearest_places");
            jsonObject.accumulate("latitude", latitude);
            jsonObject.accumulate("longitude", longitude);
            jsonObject.accumulate("radius", radius);

            if (!Utility.isEmptyString(category))
                jsonObject.accumulate("category", category);

            if (!Utility.isEmptyString(skipIds))
                jsonObject.accumulate("skip_ids", skipIds);

            if (!Utility.isEmptyString(rating))
                jsonObject.accumulate("rating", rating);

            if (!Utility.isEmptyString(expense))
                jsonObject.accumulate("expense", expense);

        }
        catch (JSONException e) {
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
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {

            if (requestObject.isFirstRequest()) {
                objectArrayList.clear();
            } else {
                stringBuilder.append(",");
                int listLastItem = objectArrayList.size() - 1;
                objectArrayList.remove(listLastItem);
            }

            loading = true;
            DataObject dataObject = (DataObject) data;

            for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {

                if (i != 0 && 0 == i % Constant.Credentials.nativeAdInterval && Constant.Credentials.isFbNativeAds)
                    objectArrayList.add(new NativeAdObject());

                if (favouriteMap.containsKey(dataObject.getObjectArrayList().get(i).getObject_id()))
                    dataObject.getObjectArrayList().get(i).setFavourite(true);

                objectArrayList.add(dataObject.getObjectArrayList().get(i).setIs_layout_01(false));


                stringBuilder.append("'");
                stringBuilder.append(dataObject.getObjectArrayList().get(i).getObject_id());
                stringBuilder.append("'");

                if (i < (dataObject.getObjectArrayList().size() - 1))
                    stringBuilder.append(",");

            }


            nearbyListAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onError(String data, RequestObject requestObject) {
        Utility.Logger(TAG, "Error = " + data);
        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
            objectArrayList.add(new EmptyObject()
                    .setTitle(Utility.getStringFromRes(getActivity(), R.string.no_book))
                    .setDescription(Utility.getStringFromRes(getActivity(), R.string.no_book_tagline))
                    .setPlaceHolderIcon(R.drawable.em_no_book));

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
        intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL,dataObject);
        startActivity(intent);
    }

    @Override
    public void onFavourite(int position, boolean isFavourite) {

        if (!prefObject.isLogin()) {
            startActivity(new Intent(getActivity(), OnBoarding.class));
            return;
        }



        DataObject dtObject = (DataObject) objectArrayList.get(position);
        ((DataObject) objectArrayList.get(position)).setFavourite(isFavourite);
        nearbyListAdapter.notifyItemChanged(position);

        if (isFavourite) {

            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.FAVOURITES)
                    .setDbOperation(Constant.DB.INSERT)
                    .setDataObject(new DataObject()
                            .setUser_id(prefObject.getUserId())
                            .setObject_id(dtObject.getObject_id())));

            management.sendRequestToServer(new RequestObject()
                    .setContext(getActivity())
                    .setJson(getFavouriteJson("add_favourites", prefObject.getUserId(), dtObject.getObject_id()))
                    .setConnection(Constant.CONNECTION.ADD_FAVOURITES)
                    .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                    .setConnectionCallback(this));


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
                    .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                    .setConnectionCallback(this));

        }
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * <p>It is used to switch List Style</p>
     *
     * @param switchChange
     */
    private void changeListStyle(boolean switchChange) {
        for (int i = 0; i < objectArrayList.size(); i++) {
            ((DataObject) objectArrayList.get(i)).setIs_layout_01(switchChange);
        }
        nearbyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!prefObject.isLogin())
            return;

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
}

