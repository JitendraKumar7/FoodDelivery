package com.haris.meal4u.FragmentUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haris.meal4u.ActivityUtil.CheckoutActivity;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.ObjectUtil.BillingObject;
import com.haris.meal4u.ObjectUtil.DateTimeObject;
import com.haris.meal4u.ObjectUtil.ScheduleObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;

public class BillingFragment extends Fragment implements View.OnClickListener {

    private TextView txtConfirmCheckout;
    private ImageView imageMethodCod;
    private ImageView imageMethodLive;
    private CardView cardMethodCod;
    private CardView cardMethodLive;
    private ArrayList<String> acceptedPaymentType = new ArrayList<>();
    private HashMap<String, String> acceptedPaymentTypeHash = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_billing, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtConfirmCheckout = view.findViewById(R.id.txt_confirm_checkout);
        imageMethodCod = view.findViewById(R.id.image_method_cod);
        imageMethodLive = view.findViewById(R.id.image_method_live);
        cardMethodCod = view.findViewById(R.id.card_method_cod);
        cardMethodLive = view.findViewById(R.id.card_method_live);

        acceptedPaymentType.clear();
        acceptedPaymentType.addAll(((CheckoutActivity) getActivity()).restaurantDetail.getPaymentTypeList());

        for (int i = 0; i < acceptedPaymentType.size(); i++) {
            acceptedPaymentTypeHash.put(acceptedPaymentType.get(i), "0");
        }

        txtConfirmCheckout.setOnClickListener(this);
        cardMethodCod.setOnClickListener(this);
        cardMethodLive.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == txtConfirmCheckout) {

            if (imageMethodCod.getVisibility() == View.VISIBLE) {

                ((CheckoutActivity) getActivity()).onStepClick(2);
                ((CheckoutActivity) getActivity()).onBillingChangeListener(new BillingObject()
                        .setPaymentMethod(Utility.getStringFromRes(getActivity(), R.string.cod)));

                ScheduleObject scheduleObject = new ScheduleObject();
                scheduleObject.setNow(true);
                DateTimeObject dateTimeObject = Utility.parseTimeDate(new DateTimeObject()
                        .setCurrentDate(true)
                        .setDatetimeType(Constant.DATETIME.DATE_DD_MM_YYYY_hh_mm_ss));
                scheduleObject.setSchedule(dateTimeObject.getDatetime());

                ((CheckoutActivity) getActivity()).onDeliveryChangeListener(scheduleObject);

                return;

            } else if (imageMethodLive.getVisibility() == View.VISIBLE) {

                ((CheckoutActivity) getActivity()).onStepClick(3);
                ((CheckoutActivity) getActivity()).onBillingChangeListener(new BillingObject()
                        .setPaymentMethod(Utility.getStringFromRes(getActivity(), R.string.credit_debit_card)));

                return;
            }

            Utility.Toaster(getContext(), Utility.getStringFromRes(getContext(), R.string.select_payment_method), Toast.LENGTH_SHORT);

        }
        if (v == cardMethodCod) {
            imageMethodLive.setVisibility(View.GONE);
            imageMethodCod.setVisibility(View.VISIBLE);
        }
        if (v == cardMethodLive) {
            imageMethodCod.setVisibility(View.GONE);
            imageMethodLive.setVisibility(View.VISIBLE);
        }

    }

}

