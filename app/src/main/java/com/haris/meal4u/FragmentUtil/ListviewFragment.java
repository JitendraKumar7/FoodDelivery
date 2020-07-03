package com.haris.meal4u.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.haris.meal4u.ActivityUtil.OnBoarding;
import com.haris.meal4u.ActivityUtil.RestaurantDetail;
import com.haris.meal4u.AdapterUtil.CategorySpinnerAdapter;
import com.haris.meal4u.AdapterUtil.NearbyListAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.DatabaseUtil.DatabaseObject;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.InterfaceUtil.NearbyCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.FilterObject;
import com.haris.meal4u.ObjectUtil.InternetObject;
import com.haris.meal4u.ObjectUtil.NativeAdObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.ObjectUtil.SpaceObject;
import com.haris.meal4u.ObjectUtil.ViewTypeObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListviewFragment extends Fragment implements ConnectionCallback,
        NearbyCallback, View.OnClickListener, AdapterView.OnItemSelectedListener,
        TextView.OnEditorActionListener, TextWatcher {

    private String TAG = ListviewFragment.class.getName();
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewNearby;
    private NearbyListAdapter nearbyListAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<Object> cloneList = new ArrayList<>();
    private int visibleItemCount, totalItemCount,pastVisiblesItems;
    private boolean loading = true;
    private ArrayList<ViewTypeObject> viewTypeArrayList = new ArrayList<>();
    private ArrayList<Object> favouriteArraylist = new ArrayList<>();
    private HashMap<String, String> favouriteMap = new HashMap<>();
    private StringBuilder stringBuilder = new StringBuilder();
    private CategorySpinnerAdapter spinnerViewTypeAdapter;
    private AppCompatSpinner spinnerViewType;
    private EditText editSearch;
    private ImageView imageClose;
    private FilterObject filterObject;
    private String radius;
    private String rating;
    private String expense;
    private String category;
    private PrefObject prefObject;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list_view, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getIntentData(); //Retrieve Intent Data
        initUI(view); //Initialize UI


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

    private void initUI(View view) {

        management = new Management(getActivity());

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

        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        editSearch=view.findViewById(R.id.edit_search);
        imageClose = view.findViewById(R.id.img_close);

        viewTypeArrayList.add(new ViewTypeObject().setTitle(Utility.getStringFromRes(getActivity(), R.string.select_view_type)));
        viewTypeArrayList.add(new ViewTypeObject().setTitle(Utility.getStringFromRes(getActivity(), R.string.list_view)));
        viewTypeArrayList.add(new ViewTypeObject().setTitle(Utility.getStringFromRes(getActivity(), R.string.card_view)));

        spinnerViewType = (AppCompatSpinner) view.findViewById(R.id.spinner_view_type);
        spinnerViewTypeAdapter = new CategorySpinnerAdapter(getActivity(), viewTypeArrayList);
        spinnerViewType.setAdapter(spinnerViewTypeAdapter);
        spinnerViewType.setSelection(1);

        management = new Management(getActivity());
        objectArrayList.add(new ProgressObject());

        //Initialize & Setup Layout Manager with Recycler View

        gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (objectArrayList.get(position) instanceof EmptyObject) {
                    return 1;
                }
                else if (objectArrayList.get(position) instanceof InternetObject) {
                    return 1;
                }
                else if (objectArrayList.get(position) instanceof ProgressObject) {
                    return 1;
                }
                else {
                    return 1;
                }
            }
        });

        recyclerViewNearby = (RecyclerView) view.findViewById(R.id.recycler_view_places);
        recyclerViewNearby.setLayoutManager(gridLayoutManager);


        //Initialize & Setup Adapter with Recycler View

        nearbyListAdapter = new NearbyListAdapter(getActivity(), objectArrayList, this) {
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
                                    .setJson(getJson("31.459660", "73.123389",
                                            radius,stringBuilder.toString(),category, rating, expense))
                                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                    .setConnection(Constant.CONNECTION.NEAREST)
                                    .setConnectionCallback(ListviewFragment.this));
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });


        //Send request to Server for retrieving Nearest Data

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson(String.valueOf(Constant.getBaseLatLng().latitude), String.valueOf(Constant.getBaseLatLng().longitude),
                        radius,null,category, rating, expense))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.NEAREST)
                .setConnectionCallback(this));


        spinnerViewType.setOnItemSelectedListener(this);
        editSearch.addTextChangedListener(this);
        editSearch.setOnEditorActionListener(this);
        imageClose.setOnClickListener(this);


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
            }
            else {
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
                    Utility.Logger(TAG,"Data Object = Working");
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
        if (v==imageClose){
            imageClose.setVisibility(View.GONE);
            editSearch.setText(null);
            objectArrayList.clear();
            objectArrayList.addAll(cloneList);
            nearbyListAdapter.notifyDataSetChanged();
        }
    }


    /**
     * <p>It is used to switch List Style</p>
     *
     * @param switchChange
     */
    private void changeListStyle(boolean switchChange) {
        for (int i = 0; i < objectArrayList.size(); i++) {
            if (objectArrayList.get(i) instanceof DataObject)
                ((DataObject) objectArrayList.get(i)).setIs_layout_01(switchChange);
        }
        nearbyListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (position == 0)
            return;


        if (viewTypeArrayList.get(position).getTitle()
                .equalsIgnoreCase(Utility.getStringFromRes(getActivity(), R.string.list_view))) {
            changeListStyle(true);
        }
        else if (viewTypeArrayList.get(position).getTitle()
                .equalsIgnoreCase(Utility.getStringFromRes(getActivity(), R.string.card_view))) {
            changeListStyle(false);

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            performSearch(v.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!Utility.isEmptyString(s.toString())) {
            imageClose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * <p>It trigger by Search & find required search terms</p>
     */
    private void performSearch(String searchText) {

        Constant.CONNECTION connection = null;
        Utility.Logger(TAG, "Search : " + searchText);

        if (!Utility.isEmptyString(editSearch.getText().toString())) {

            //For showing Progressing Animation

            Utility.hideKeyboard(getActivity(), editSearch);
            objectArrayList.clear();
            for (int i = 0; i < cloneList.size(); i++) {
                if (cloneList.get(i) instanceof DataObject) {
                    DataObject dtObject = (DataObject) cloneList.get(i);
                    Utility.Logger(TAG,"Name = "+dtObject.getObject_name()+" Searched = "+searchText);
                    if (dtObject.getObject_name().toLowerCase().contains(searchText.toLowerCase())) {
                        objectArrayList.add(dtObject);
                    }
                }
            }
            nearbyListAdapter.notifyDataSetChanged();


        }

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

