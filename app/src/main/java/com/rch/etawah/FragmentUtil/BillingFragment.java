package com.rch.etawah.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rch.etawah.ActivityUtil.AddMoneyActivity;
import com.rch.etawah.ActivityUtil.CheckoutActivity;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.ObjectUtil.BillingObject;
import com.rch.etawah.ObjectUtil.DateTimeObject;
import com.rch.etawah.ObjectUtil.ScheduleObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import static com.rch.etawah.ConstantUtil.Constant.ServerInformation.REST_API_URL;

public class BillingFragment extends Fragment implements View.OnClickListener {

    private TextView txtConfirmCheckout;
    private ImageView imageMethodCod;
    private ImageView imageMethodLive;
    private ImageView imageMethodWallet;
    private CardView cardMethodCod;
    private CardView cardMethodLive;
    private CardView cardMethodWallet;


    private String mAmount = "0";

    private TextView addToWallet;
    private TextView walletAmount;
    private ProgressBar progress_bar;

    @Override
    public void onStart() {
        super.onStart();

        try {
            progress_bar.setVisibility(View.VISIBLE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "get_user_wallet_balance");
            //jsonObject.accumulate("user_id", "1");
            jsonObject.accumulate("user_id", ((CheckoutActivity) getActivity()).getUserId());
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progress_bar.setVisibility(View.GONE);
                            Utility.Logger("TAG", response.toString());
                            try {
                                if (response.getString("code").equalsIgnoreCase("200")) {
                                    JSONObject wallet = response.getJSONObject("result");
                                    walletAmount.setText(String.format("₹ %s", wallet.getString("amount")));
                                    mAmount = wallet.getString("amount");
                                }
                                //
                                //else {
                                //Toast.makeText(getActivity(), "No data found.\nPlease add balance", Toast.LENGTH_SHORT).show();
                                //}
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

    @Override
    public void onClick(View v) {
        if (v == addToWallet) {
            Intent intent = new Intent(getActivity(), AddMoneyActivity.class);
            startActivity(intent);
        }
        if (v == txtConfirmCheckout) {

            if (imageMethodWallet.getVisibility() == View.VISIBLE) {
                try {
                    int billPrice = ((CheckoutActivity) getActivity()).getAmount();
                    int walletAmount = Integer.parseInt(mAmount);
                    if (walletAmount >= billPrice) {

                        progress_bar.setVisibility(View.VISIBLE);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.accumulate("functionality", "add_user_wallet_amount");
                        jsonObject.accumulate("user_id", ((CheckoutActivity) getActivity()).getUserId());
                        jsonObject.accumulate("amount", String.valueOf(billPrice));
                        jsonObject.accumulate("type", "dr");

                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        progress_bar.setVisibility(View.GONE);
                                        Utility.Logger("TAG", response.toString());
                                        try {
                                            if (response.getString("code").equalsIgnoreCase("200")) {
                                                ((CheckoutActivity) getActivity()).onBillingChangeListener(new BillingObject()
                                                        .setPaymentMethod(Utility.getStringFromRes(getActivity(), R.string.wallet)));

                                                ScheduleObject scheduleObject = new ScheduleObject();
                                                scheduleObject.setNow(true);
                                                DateTimeObject dateTimeObject = Utility.parseTimeDate(new DateTimeObject()
                                                        .setCurrentDate(true)
                                                        .setDatetimeType(Constant.DATETIME.DATE_DD_MM_YYYY_hh_mm_ss));
                                                scheduleObject.setSchedule(dateTimeObject.getDatetime());

                                                ((CheckoutActivity) getActivity()).onDeliveryChangeListener(scheduleObject);
                                            }
                                            //
                                            else {
                                                Toast.makeText(getActivity(), "Error Contact Admin", Toast.LENGTH_SHORT).show();
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

                    }
                    //
                    else {
                        Toast.makeText(getActivity(), "Please add balance", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;

            }

            if (imageMethodCod.getVisibility() == View.VISIBLE) {

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

            }
            //
            else if (imageMethodLive.getVisibility() == View.VISIBLE) {

                ((CheckoutActivity) getActivity()).onStepClick(2);
                ((CheckoutActivity) getActivity()).onBillingChangeListener(new BillingObject()
                        .setPaymentMethod(Utility.getStringFromRes(getActivity(), R.string.credit_debit_card)));

                return;
            }

            Utility.Toaster(getContext(), Utility.getStringFromRes(getContext(), R.string.select_payment_method), Toast.LENGTH_SHORT);

        }
        if (v == cardMethodCod) {
            imageMethodLive.setVisibility(View.GONE);
            imageMethodWallet.setVisibility(View.GONE);
            imageMethodCod.setVisibility(View.VISIBLE);
        }
        if (v == cardMethodLive) {
            imageMethodCod.setVisibility(View.GONE);
            imageMethodWallet.setVisibility(View.GONE);
            imageMethodLive.setVisibility(View.VISIBLE);
        }
        if (v == cardMethodWallet) {
            imageMethodCod.setVisibility(View.GONE);
            imageMethodLive.setVisibility(View.GONE);
            imageMethodWallet.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_billing, null);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtConfirmCheckout = view.findViewById(R.id.txt_confirm_checkout);
        imageMethodCod = view.findViewById(R.id.image_method_cod);
        imageMethodLive = view.findViewById(R.id.image_method_live);
        imageMethodWallet = view.findViewById(R.id.image_method_wallet);
        cardMethodCod = view.findViewById(R.id.card_method_cod);
        cardMethodLive = view.findViewById(R.id.card_method_live);
        cardMethodWallet = view.findViewById(R.id.card_method_wallet);


        txtConfirmCheckout.setOnClickListener(this);
        cardMethodCod.setOnClickListener(this);
        cardMethodLive.setOnClickListener(this);
        cardMethodWallet.setOnClickListener(this);


        progress_bar = view.findViewById(R.id.progress_bar);
        walletAmount = view.findViewById(R.id.wallet_amount);
        addToWallet = view.findViewById(R.id.add_to_bag);
        addToWallet.setOnClickListener(this);

    }

}

