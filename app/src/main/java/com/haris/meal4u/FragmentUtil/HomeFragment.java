package com.haris.meal4u.FragmentUtil;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.BottomSheetDialog;
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

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.haris.meal4u.ActivityUtil.OnBoarding;
import com.haris.meal4u.ActivityUtil.RestaurantDetail;
import com.haris.meal4u.AdapterUtil.HomeAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.DatabaseUtil.DatabaseObject;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.InterfaceUtil.HomeCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.GeocodeObject;
import com.haris.meal4u.ObjectUtil.HomeObject;
import com.haris.meal4u.ObjectUtil.InternetObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.ObjectUtil.SpaceObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment;
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker;
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;


public class HomeFragment extends Fragment implements View.OnClickListener, ConnectionCallback, HomeCallback, OnLocationUpdatedListener {
    private TextView editSearch;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewHome;
    private HomeAdapter homeAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<Object> favouriteArraylist = new ArrayList<>();
    private HashMap<String, String> favouriteMap = new HashMap<>();
    private String TAG = HomeFragment.class.getName();
    private PrefObject prefObject;
    private ImageView imageMic;
    private SmartLocation smartLocation;
    private LatLng locationObject;
    private int PLACE_SELECTION_REQUEST_CODE = 12;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */

    private void initUI(View view) {

        management = new Management(getActivity());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        Utility.Logger(TAG, prefObject.toString());

        startLocation();


        editSearch = view.findViewById(R.id.edit_search);
        imageMic = view.findViewById(R.id.image_mid);


        objectArrayList.add(new ProgressObject());
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
                } else if (objectArrayList.get(position) instanceof DataObject) {
                    return 1;
                } else if (objectArrayList.get(position) instanceof HomeObject) {
                    return 1;
                } else {
                    return 1;
                }
            }
        });

        recyclerViewHome = view.findViewById(R.id.recycler_view_home);
        recyclerViewHome.setLayoutManager(gridLayoutManager);

        homeAdapter = new HomeAdapter(getActivity(), objectArrayList, getFragmentManager(), this) {
            @Override
            public void select(boolean isLocked, int position) {


            }
        };
        recyclerViewHome.setAdapter(homeAdapter);


        imageMic.setOnClickListener(this);
        editSearch.setOnClickListener(this);

    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */

    private String getJson(String latitude, String longitude, String radius) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "home");
            jsonObject.accumulate("latitude", latitude);
            jsonObject.accumulate("longitude", longitude);
            jsonObject.accumulate("radius", radius);

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


    /**
     * <p>It is used to start location & get userObject current location</p>
     */
    private void startLocation() {

        smartLocation = new SmartLocation.Builder(getActivity()).logging(true).build();
        smartLocation.location().config(LocationParams.BEST_EFFORT).oneFix().start(this);

    }


    @Override
    public void onClick(View v) {
        if (v == imageMic) {
            askSpeechInput();
        }
        if (v == editSearch) {
            //searchSpecificPlace();
            showLocationSelectionBottomSheet(getActivity());
        }
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

        if (data != null && requestObject != null) {

            if (data instanceof DataObject) {

                objectArrayList.clear();
                DataObject dataObject = (DataObject) data;
                objectArrayList.addAll(dataObject.getHomeList());
                for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {

                    DataObject dtObject = dataObject.getObjectArrayList().get(i);

                    if (favouriteMap.containsKey(dtObject.getObject_id()))
                        dtObject.setFavourite(true);

                    objectArrayList.add(dtObject);
                }
                objectArrayList.add(new SpaceObject());

                homeAdapter.notifyDataSetChanged();


            }

        }


    }

    @Override
    public void onError(String data, RequestObject requestObject) {

        if (!Utility.isEmptyString(data) && requestObject != null) {

            Utility.Logger(TAG, "Error = " + data);
            objectArrayList.clear();
            homeAdapter.notifyDataSetChanged();
           /* if (favouriteArraylist.size() > 0)
                objectArrayList.add(new HomeObject()
                        .setData_type(Constant.DATA_TYPE.HISTORY)
                        .setTitle(Utility.getStringFromRes(getActivity(), R.string.continue_reading))
                        .setDataObjectArrayList(favouriteArraylist));*/
            //homeAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onSelect(int parentPosition, int childPosition) {

        HomeObject homeObject = null;
        DataObject dataObject = null;


        if (parentPosition >= 0) {

            homeObject = (HomeObject) objectArrayList.get(parentPosition);
            dataObject = homeObject.getDataObjectArrayList().get(childPosition);

            if (homeObject.getData_type() == Constant.DATA_TYPE.FEATURED) {

                Intent intent = new Intent(getActivity(), RestaurantDetail.class);
                intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
                startActivity(intent);

            } else if (homeObject.getData_type() == Constant.DATA_TYPE.TOP_BRANDS) {

                Intent intent = new Intent(getActivity(), RestaurantDetail.class);
                intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
                startActivity(intent);

            }

        } else {

            Utility.Logger(TAG, "Main Click Working");

            dataObject = (DataObject) objectArrayList.get(childPosition);
            Intent intent = new Intent(getActivity(), RestaurantDetail.class);
            intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
            startActivity(intent);

        }

        Utility.Logger(TAG, "Data = " + dataObject.toString());


    }

    @Override
    public void onMore(Constant.DATA_TYPE dataType) {

        if (dataType == Constant.DATA_TYPE.POPULAR) {


        }


    }

    @Override
    public void switchLayout(int position, boolean isSwitchLayout) {

        for (int i = (position + 1); i < objectArrayList.size(); i++) {
            if (objectArrayList.get(i) instanceof DataObject)
                ((DataObject) objectArrayList.get(i)).setIs_layout_01(isSwitchLayout);
        }

        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFavourite(int position, boolean isFavourite) {

        if (!prefObject.isLogin()) {
            startActivity(new Intent(getActivity(), OnBoarding.class));
            return;
        }

        DataObject dtObject = (DataObject) objectArrayList.get(position);
        ((DataObject) objectArrayList.get(position)).setFavourite(isFavourite);
        homeAdapter.notifyItemChanged(position);

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


    /**
     * <p>It is used to launch Speech Recognizer
     * to convert speech into text</p>
     */

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, Constant.RequestCode.REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }


    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_SELECTION_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {

            // Retrieve the information from the selected location's CarmenFeature

            CarmenFeature carmenFeature = PlacePicker.getPlace(data);
            Utility.Logger(TAG, "CarmenFeature = " + carmenFeature.toJson());
            GeocodeObject geocodeObject = Utility.getGeoCodeObject(getActivity(), carmenFeature.center().latitude()
                    , carmenFeature.center().longitude());
            editSearch.setText(geocodeObject.getAddress());

            Constant.setBaseLatLng(new com.google.android.gms.maps.model.LatLng(carmenFeature.center().latitude(), carmenFeature.center().longitude()));
            locationObject = new LatLng(carmenFeature.center().latitude(), carmenFeature.center().longitude());

            objectArrayList.clear();
            objectArrayList.add(new ProgressObject());
            homeAdapter.notifyDataSetChanged();

            //Send request to Server

            management.sendRequestToServer(new RequestObject()
                    .setContext(getActivity())
                    .setJson(getJson(String.valueOf(locationObject.getLatitude()), String.valueOf(locationObject.getLongitude()), Constant.AppConfiguration.DEFAULT_RADIUS))
                    .setConnection(Constant.CONNECTION.HOME)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnectionCallback(this));


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


    @Override
    public void onLocationUpdated(Location location) {
        if (location != null) {

            Utility.Logger(TAG, "Location = Latitude (" + location.getLatitude() + "," + location.getLongitude() + ")");

            GeocodeObject geoCodeObject = Utility.getGeoCodeObject(getActivity(), location.getLatitude(), location.getLongitude());
            editSearch.setText(geoCodeObject.getAddress());

            Constant.setBaseLatLng(new com.google.android.gms.maps.model.LatLng(location.getLatitude(), location.getLongitude()));
            locationObject = new LatLng(location.getLatitude(), location.getLongitude());

            //Send request to Server

            management.sendRequestToServer(new RequestObject()
                    .setContext(getActivity())
                    .setJson(getJson(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), Constant.AppConfiguration.DEFAULT_RADIUS))
                    .setConnection(Constant.CONNECTION.HOME)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnectionCallback(this));

        }
    }


    /**
     * <p>It is used to show Language Selector</p>
     *
     * @param context
     */
    private void showLocationSelectionBottomSheet(final Context context) {
        final View view = getLayoutInflater().inflate(R.layout.selection_location_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();

        LinearLayout layoutSelectLocation = (LinearLayout) view.findViewById(R.id.layout_select_location);
        LinearLayout layoutSearchLocation = (LinearLayout) view.findViewById(R.id.layout_search_location);

        layoutSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new PlacePicker.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken())
                        .placeOptions(
                                PlacePickerOptions.builder()
                                        .statingCameraPosition(
                                                new CameraPosition.Builder()
                                                        .target(locationObject)
                                                        .zoom(16)
                                                        .build())
                                        .build())
                        .build(getActivity());
                startActivityForResult(intent, PLACE_SELECTION_REQUEST_CODE);

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

            }
        });

        layoutSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken())
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Utility.getAttrColor(context, R.attr.colorBackground))
                                .limit(30)
                                /*.addInjectedFeature(home)
                                .addInjectedFeature(work)*/
                                .build(PlaceOptions.MODE_FULLSCREEN))
                        .build(getActivity());
                startActivityForResult(intent, PLACE_SELECTION_REQUEST_CODE);

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();


            }
        });


    }


}

