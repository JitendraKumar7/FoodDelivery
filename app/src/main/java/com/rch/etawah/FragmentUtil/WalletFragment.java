package com.rch.etawah.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rch.etawah.ActivityUtil.AddMoneyActivity;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.PrefObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.rch.etawah.ConstantUtil.Constant.ServerInformation.REST_API_URL;

public class WalletFragment extends Fragment implements View.OnClickListener {

    private TextView txtMenu;
    private TextView addToWallet;
    private TextView walletAmount;
    private Management management;
    private PrefObject prefObject;
    private ProgressBar progress_bar;
    private LinearLayout linearLayout;

    @Override
    public void onStart() {
        super.onStart();

        try {
            progress_bar.setVisibility(View.VISIBLE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "get_user_wallet_balance");
            //jsonObject.accumulate("user_id", "1");
            jsonObject.accumulate("user_id", prefObject.getUserId());
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progress_bar.setVisibility(View.GONE);
                            Utility.Logger("TAG", response.toString());
                            try {
                                if (response.getString("message").equalsIgnoreCase("Sucess")) {
                                    JSONObject address = response.getJSONObject("result");
                                    walletAmount.setText(String.format("₹%s", address.getString("amount")));

                                } else {
                                    Toast.makeText(getActivity(), "No data found.\nPlease add balance", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        if (v == addToWallet) {
            Intent intent = new Intent(getActivity(), AddMoneyActivity.class);
            startActivity(intent);
        }

    }

    public void getWalletTransaction() {
        try {
            progress_bar.setVisibility(View.VISIBLE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("functionality", "get_user_wallet_history");
            //jsonObject.accumulate("user_id", "1");
            jsonObject.accumulate("user_id", prefObject.getUserId());
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, REST_API_URL, jsonObject,
                    response -> {
                        progress_bar.setVisibility(View.GONE);
                        Utility.Logger("TAG", response.toString());
                        try {
                            if (response.getString("message").equalsIgnoreCase("Sucess")) {
                                JSONArray address = response.getJSONArray("result");
                                for (int i = address.length(); i > -1; i--) {
                                    try {

                                        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_wallet_trans, null);
                                        final TextView amount = view1.findViewById(R.id.txt_charges);
                                        final TextView transaction_id = view1.findViewById(R.id.txt_name);
                                        final TextView trans_date = view1.findViewById(R.id.txt_date);
                                        final TextView type = view1.findViewById(R.id.type);


                                        amount.setText(String.format("₹ %s", address.getJSONObject(i).getString("amount")));
                                        String id = address.getJSONObject(i).getString("id");
                                        transaction_id.setText(String.format("Transaction Id - #%s", id));
                                        trans_date.setText(address.getJSONObject(i).getString("created_at"));
                                        if (address.getJSONObject(i).getString("type").equalsIgnoreCase("dr")) {
                                            type.setTextColor(Utility.getColourFromRes(getActivity(), R.color.colorSelectedFavouriteDay));
                                            type.setText("  Dr.");
                                        } else {
                                            type.setTextColor(Utility.getColourFromRes(getActivity(), R.color.order_on_the_way));
                                            type.setText("  Cr.");
                                        }

                                        linearLayout.addView(view1);
                                    } catch (NullPointerException ignore) {

                                    }

                                }
                            } else {
                                Toast.makeText(getActivity(), "No data found.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wallet_transaction, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress_bar = view.findViewById(R.id.progress_bar);
        txtMenu = view.findViewById(R.id.txt_menu);
        ImageView imageBack = view.findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        txtMenu.setText("Wallet Transaction History");
        linearLayout = view.findViewById(R.id.linearLayout);
        walletAmount = view.findViewById(R.id.wallet_amount);
        addToWallet = view.findViewById(R.id.add_to_bag);
        addToWallet.setOnClickListener(this);
        management = new Management(getActivity());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true)
                .setRetrieveLogin(true));
        getWalletTransaction();

    }
}
