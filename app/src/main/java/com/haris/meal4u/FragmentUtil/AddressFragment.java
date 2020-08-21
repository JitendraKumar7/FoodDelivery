package com.haris.meal4u.FragmentUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haris.meal4u.ActivityUtil.CheckoutActivity;
import com.haris.meal4u.ObjectUtil.AddressObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.haris.meal4u.ConstantUtil.Constant.ServerInformation.REST_API_URL;

public class AddressFragment extends Fragment implements View.OnClickListener {
    private TextView txtConfirmCheckout;
    private EditText editBuilding;
    private EditText editStreetAddress;
    private EditText editArea;
    private EditText editUnit;
    private EditText editComment;

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

            String address = editBuilding.getText().toString() + " " + editStreetAddress.getText().toString()
                    + " " + editArea.getText().toString() + " " + editUnit.getText().toString();
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("address", address);
                jsonObject.accumulate("functionality", "user_add_address");
                jsonObject.accumulate("user_id", ((CheckoutActivity) getActivity()).getUserId());
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Utility.Logger("TAG", response.toString());


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                Volley.newRequestQueue(getActivity()).add(jsonRequest);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ((CheckoutActivity) getActivity()).onStepClick(1);
            ((CheckoutActivity) getActivity()).onAddressChangeListener(new AddressObject()
                    .setBuildingName(editBuilding.getText().toString())
                    .setStreetName(editStreetAddress.getText().toString())
                    .setAreaName(editArea.getText().toString())
                    .setFloorName(editUnit.getText().toString())
                    .setNoteRider(editComment.getText().toString())
                    .setLatitude(String.valueOf(0.00))
                    .setLongitude(String.valueOf(0.00)));

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_address, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editBuilding = view.findViewById(R.id.edit_building);
        editStreetAddress = view.findViewById(R.id.edit_street_address);
        editArea = view.findViewById(R.id.edit_area);
        editUnit = view.findViewById(R.id.edit_unit);
        editComment = view.findViewById(R.id.edit_comment);
        txtConfirmCheckout = view.findViewById(R.id.txt_confirm_checkout);

        txtConfirmCheckout.setOnClickListener(this);


        final LinearLayout linearLayout = view.findViewById(R.id.linearLayout);

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "user_get_address");
            jsonObject.accumulate("user_id", ((CheckoutActivity) getActivity()).getUserId());
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Utility.Logger("TAG", response.toString());
                            try {
                                JSONArray address = new JSONArray(response.toString());
                                for (int i = 0; i < address.length(); i++) {

                                    View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.order_placed_item_layout, null);
                                    final TextView txtPostTitle = view1.findViewById(R.id.txt_post_title);
                                    TextView txtPrice = view1.findViewById(R.id.txt_price);
                                    TextView txtCount = view1.findViewById(R.id.txt_count);
                                    txtPrice.setVisibility(View.GONE);
                                    txtCount.setVisibility(View.GONE);

                                    txtPostTitle.setText(address.getJSONObject(i).getString("address"));

                                    linearLayout.addView(view1);

                                    txtPostTitle.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ((CheckoutActivity) getActivity()).onStepClick(1);
                                            ((CheckoutActivity) getActivity()).onAddressChangeListener(new AddressObject()
                                                    .setBuildingName(editBuilding.getText().toString())
                                                    .setStreetName(txtPostTitle.getText().toString())
                                                    .setAreaName(editArea.getText().toString())
                                                    .setFloorName(editUnit.getText().toString())
                                                    .setNoteRider(editComment.getText().toString())
                                                    .setLatitude(String.valueOf(0.00))
                                                    .setLongitude(String.valueOf(0.00)));
                                        }
                                    });

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            Volley.newRequestQueue(getActivity()).add(jsonRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}

