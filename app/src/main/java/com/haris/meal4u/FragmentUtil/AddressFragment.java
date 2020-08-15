package com.haris.meal4u.FragmentUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haris.meal4u.ActivityUtil.CheckoutActivity;
import com.haris.meal4u.ObjectUtil.AddressObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

public class AddressFragment extends Fragment implements View.OnClickListener {
    private TextView txtConfirmCheckout;
    private EditText editBuilding;
    private EditText editStreetAddress;
    private EditText editArea;
    private EditText editUnit;
    private EditText editComment;

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

}

