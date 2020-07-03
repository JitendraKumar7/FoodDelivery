package com.haris.meal4u.FragmentUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.haris.meal4u.ActivityUtil.Checkout;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.ObjectUtil.AddressObject;
import com.haris.meal4u.ObjectUtil.GeocodeObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker;
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions;

import java.util.Arrays;
import java.util.List;

public class AddressFragment extends Fragment implements View.OnClickListener {
    private String TAG = AddressFragment.class.getName();
    private int PLACE_SELECTION_REQUEST_CODE = 1;
    private ImageView imageLocationPointer;
    private TextView txtConfirmCheckout;
    private EditText editBuilding;
    private EditText editStreetAddress;
    private EditText editArea;
    private EditText editUnit;
    private EditText editComment;
    private GeocodeObject geoCodeObject;
    private LatLng locationObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_address, null);
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

        locationObject = new LatLng(Constant.getBaseLatLng().latitude,Constant.getBaseLatLng().longitude);
        geoCodeObject = Utility.getGeoCodeObject(getActivity(), Constant.getBaseLatLng().latitude, Constant.getBaseLatLng().longitude);

        editBuilding = (EditText) view.findViewById(R.id.edit_building);
        editStreetAddress = (EditText) view.findViewById(R.id.edit_street_address);
        editArea = (EditText) view.findViewById(R.id.edit_area);
        editUnit = (EditText) view.findViewById(R.id.edit_unit);
        editComment = (EditText) view.findViewById(R.id.edit_comment);
        txtConfirmCheckout = view.findViewById(R.id.txt_confirm_checkout);

        imageLocationPointer = view.findViewById(R.id.image_select_location);

        editStreetAddress.setText(geoCodeObject.getAddress());
        editArea.setText(geoCodeObject.getKnownName());

        txtConfirmCheckout.setOnClickListener(this);
        editStreetAddress.setOnClickListener(this);
        imageLocationPointer.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        if (v == txtConfirmCheckout) {

            if (Utility.isEmptyString(editBuilding.getText().toString())) {
                Utility.Toaster(getActivity(), Utility.getStringFromRes(getActivity(), R.string.required_building), Toast.LENGTH_SHORT);
                return;
            }

            if (Utility.isEmptyString(editStreetAddress.getText().toString())) {
                Utility.Toaster(getActivity(), Utility.getStringFromRes(getActivity(), R.string.required_street_address), Toast.LENGTH_SHORT);
                return;
            }

            if (Utility.isEmptyString(editArea.getText().toString())) {
                Utility.Toaster(getActivity(), Utility.getStringFromRes(getActivity(), R.string.required_area_name), Toast.LENGTH_SHORT);
                return;
            }

            ((Checkout) getActivity()).onStepClick(1);
            ((Checkout) getActivity()).onAddressChangeListener(new AddressObject()
                    .setBuildingName(editBuilding.getText().toString())
                    .setStreetName(editStreetAddress.getText().toString())
                    .setAreaName(editArea.getText().toString())
                    .setFloorName(editUnit.getText().toString())
                    .setNoteRider(editComment.getText().toString())
                    .setLatitude(String.valueOf(locationObject.getLatitude()))
                    .setLongitude(String.valueOf(locationObject.getLongitude())));
        }
        if (v == editStreetAddress) {
            searchSpecificPlace();
        }
        if (v == imageLocationPointer) {
            showLocationSelectionBottomSheet(getActivity());
        }
    }


    /**
     * <p>It is used to select select</p>
     */
    private void searchSpecificPlace() {

        // Initialize Places.
        Places.initialize(getActivity(), Constant.Credentials.GOOGLE_API_KEY);

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getActivity());

        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, placeFields)
                .build(getActivity());
        startActivityForResult(intent, Constant.RequestCode.SEARCH_SPECIFIC_PLACE);


    }


    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.SEARCH_SPECIFIC_PLACE
                && resultCode == getActivity().RESULT_OK) {

            Place place = Autocomplete.getPlaceFromIntent(data);
            editStreetAddress.setText(place.getAddress());
            GeocodeObject geocodeObject = Utility.getGeoCodeObject(getActivity(), place.getLatLng().latitude, place.getLatLng().longitude);
            editArea.setText(geocodeObject.getKnownName());

        }else  if (requestCode == PLACE_SELECTION_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {

            // Retrieve the information from the selected location's CarmenFeature

            CarmenFeature carmenFeature = PlacePicker.getPlace(data);
            Utility.Logger(TAG, "CarmenFeature = " + carmenFeature.toJson());
            GeocodeObject geocodeObject = Utility.getGeoCodeObject(getActivity(),carmenFeature.center().latitude()
                    ,carmenFeature.center().longitude());
            editStreetAddress.setText(geocodeObject.getAddress());
            editArea.setText(geocodeObject.getKnownName());

            locationObject = new LatLng(carmenFeature.center().latitude(), carmenFeature.center().longitude());




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

