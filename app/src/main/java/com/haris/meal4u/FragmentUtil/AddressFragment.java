package com.haris.meal4u.FragmentUtil;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haris.meal4u.ActivityUtil.CheckoutActivity;
import com.haris.meal4u.ActivityUtil.Login;
import com.haris.meal4u.EditTextUtil.NormalEditText;
import com.haris.meal4u.ObjectUtil.AddressObject;
import com.haris.meal4u.R;
import com.haris.meal4u.TextviewUtil.NormalTextview;
import com.haris.meal4u.TextviewUtil.UbuntuMediumTextview;
import com.haris.meal4u.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.haris.meal4u.ConstantUtil.Constant.ServerInformation.REST_API_URL;

public class AddressFragment extends Fragment implements View.OnClickListener {
    private NormalTextview add_address_tv;
    private RecyclerView address_rv;
    private ProgressBar progress_bar;
    private LinearLayout linearLayout;

    @Override
    public void onClick(View v) {
        if (v == add_address_tv) {
            dialogAddAddress();
//            if (Utility.isEmptyString(editBuilding.getText().toString())) {
//                Utility.Toaster(getActivity(), Utility.getStringFromRes(getActivity(), R.string.required_building), Toast.LENGTH_SHORT);
//                return;
//            }
//
//            if (Utility.isEmptyString(editStreetAddress.getText().toString())) {
//                Utility.Toaster(getActivity(), Utility.getStringFromRes(getActivity(), R.string.required_street_address), Toast.LENGTH_SHORT);
//                return;
//            }
//
//            if (Utility.isEmptyString(editArea.getText().toString())) {
//                Utility.Toaster(getActivity(), Utility.getStringFromRes(getActivity(), R.string.required_area_name), Toast.LENGTH_SHORT);
//                return;
//            }
//
//            String address = editBuilding.getText().toString() + " " + editStreetAddress.getText().toString()
//                    + " " + editArea.getText().toString() + " " + editUnit.getText().toString();
//            try {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("address", address);
//                jsonObject.accumulate("functionality", "user_add_address");
//                jsonObject.accumulate("user_id", ((CheckoutActivity) getActivity()).getUserId());
//                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//
//                                Utility.Logger("TAG", response.toString());
//
//
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                });
//
//                Volley.newRequestQueue(getActivity()).add(jsonRequest);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            ((CheckoutActivity) getActivity()).onStepClick(1);
//            ((CheckoutActivity) getActivity()).onAddressChangeListener(new AddressObject()
//                    .setBuildingName(editBuilding.getText().toString())
//                    .setStreetName(editStreetAddress.getText().toString())
//                    .setAreaName(editArea.getText().toString())
//                    .setFloorName(editUnit.getText().toString())
//                    .setNoteRider(editComment.getText().toString())
//                    .setLatitude(String.valueOf(0.00))
//                    .setLongitude(String.valueOf(0.00)));

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
//        address_rv = view.findViewById(R.id.address_rv);
        progress_bar = view.findViewById(R.id.progress_bar);
        linearLayout = view.findViewById(R.id.linearLayout);
        add_address_tv = view.findViewById(R.id.add_address_tv);
        add_address_tv.setOnClickListener(this);

        getAddressList();
    }

    public void getAddressList() {
        try {
            progress_bar.setVisibility(View.VISIBLE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "user_get_address");
            jsonObject.accumulate("user_id", ((CheckoutActivity) getActivity()).getUserId());
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progress_bar.setVisibility(View.GONE);
                            Utility.Logger("TAG", response.toString());
                            try {
                                if (response.getString("message").equalsIgnoreCase("Sucess")) {
                                    JSONArray address = response.getJSONArray("result");
                                    for (int i = address.length()-1; i >= 0 ; i--) {
                                        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.address_adapter, null);
                                        ConstraintLayout address_lo = view1.findViewById(R.id.address_lo);
                                        final NormalTextview tv_house_no = view1.findViewById(R.id.tv_house_no);
                                        final NormalTextview tv_address = view1.findViewById(R.id.tv_address);
                                        final NormalTextview tv_landmark = view1.findViewById(R.id.tv_landmark);
                                        final RadioButton radioButton = view1.findViewById(R.id.radioButton);

                                        tv_house_no.setText(address.getJSONObject(i).getString("house_no"));
                                        tv_address.setText(address.getJSONObject(i).getString("address"));
                                        tv_landmark.setText(address.getJSONObject(i).getString("landmark"));

                                        linearLayout.addView(view1);

                                        address_lo.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                radioButton.setChecked(true);
                                                ((CheckoutActivity) getActivity()).onStepClick(1);
                                                ((CheckoutActivity) getActivity()).onAddressChangeListener(new AddressObject()
                                                        .setBuildingName(tv_house_no.getText().toString())
                                                        .setStreetName(tv_address.getText().toString())
                                                        .setAreaName(tv_landmark.getText().toString())
                                                        .setFloorName("")
                                                        .setNoteRider("")
                                                        .setLatitude(String.valueOf(0.00))
                                                        .setLongitude(String.valueOf(0.00)));
                                            }
                                        });
                                        radioButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ((CheckoutActivity) getActivity()).onStepClick(1);
                                                ((CheckoutActivity) getActivity()).onAddressChangeListener(new AddressObject()
                                                        .setBuildingName(tv_house_no.getText().toString())
                                                        .setStreetName(tv_address.getText().toString())
                                                        .setAreaName(tv_landmark.getText().toString())
                                                        .setFloorName("")
                                                        .setNoteRider("")
                                                        .setLatitude(String.valueOf(0.00))
                                                        .setLongitude(String.valueOf(0.00)));
                                            }
                                        });

                                    }
                                } else {
                                    Toast.makeText(getActivity(), "No data found.\nPlease add new address", Toast.LENGTH_SHORT).show();
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

    private void dialogAddAddress() {
        try {
            final Dialog add_address_dialog = new Dialog(getActivity());
            add_address_dialog.setCanceledOnTouchOutside(false);
            add_address_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            add_address_dialog.setContentView(R.layout.add_address_dialog);
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
            add_address_dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            add_address_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            final NormalEditText edit_building = add_address_dialog.findViewById(R.id.edit_building);
            final NormalEditText edit_street_address = add_address_dialog.findViewById(R.id.edit_street_address);
            final NormalEditText edit_area = add_address_dialog.findViewById(R.id.edit_area);
            final ImageView close_iv = add_address_dialog.findViewById(R.id.close_iv);
            NormalTextview txt_submit = add_address_dialog.findViewById(R.id.txt_submit);

            close_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    add_address_dialog.dismiss();
                }
            });

            txt_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utility.isEmptyString(edit_building.getText().toString())) {
                        Toast.makeText(getActivity(), "Please enter House No / Building", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (Utility.isEmptyString(edit_street_address.getText().toString())) {
                        Toast.makeText(getActivity(), "Please enter Address", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (Utility.isEmptyString(edit_area.getText().toString())) {
                        Toast.makeText(getActivity(), "Please enter Landmark", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        add_address_dialog.dismiss();
                        progress_bar.setVisibility(View.VISIBLE);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.accumulate("house_no", edit_building.getText().toString());
                        jsonObject.accumulate("address", edit_street_address.getText().toString());
                        jsonObject.accumulate("landmark", edit_area.getText().toString());
                        jsonObject.accumulate("functionality", "user_add_address");
                        jsonObject.accumulate("user_id", ((CheckoutActivity) getActivity()).getUserId());
                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            progress_bar.setVisibility(View.GONE);
                                            Utility.Logger("TAG", response.toString());
                                            if (response.getString("message").equalsIgnoreCase("Data add Successfully")) {
                                                Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
                                                ((CheckoutActivity) getActivity()).onStepClick(1);
                                                ((CheckoutActivity) getActivity()).onAddressChangeListener(new AddressObject()
                                                        .setBuildingName(edit_building.getText().toString())
                                                        .setStreetName(edit_street_address.getText().toString())
                                                        .setAreaName(edit_area.getText().toString())
                                                        .setFloorName("")
                                                        .setNoteRider("")
                                                        .setLatitude(String.valueOf(0.00))
                                                        .setLongitude(String.valueOf(0.00)));
                                            } else {
                                                Toast.makeText(getActivity(), "Something went wrong\nPlease try again later.", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                progress_bar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Something went wrong\nPlease try again later.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        Volley.newRequestQueue(getActivity()).add(jsonRequest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Something went wrong\nPlease try again later.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            add_address_dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

