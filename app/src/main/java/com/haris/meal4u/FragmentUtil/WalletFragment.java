package com.haris.meal4u.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.haris.meal4u.ActivityUtil.AddMoneyActivity;
import com.haris.meal4u.ActivityUtil.CheckoutActivity;
import com.haris.meal4u.AdapterUtil.OrderAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.AddressObject;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.InternetObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.TextviewUtil.NormalTextview;
import com.haris.meal4u.Utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;
import static com.haris.meal4u.ConstantUtil.Constant.ServerInformation.REST_API_URL;

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
                                    walletAmount.setText("Rs " + address.getString("amount"));

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
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progress_bar.setVisibility(View.GONE);
                            Utility.Logger("TAG", response.toString());
                            try {
                                if (response.getString("message").equalsIgnoreCase("Sucess")) {
                                    JSONArray address = response.getJSONArray("result");
                                    for (int i = 0; i <= address.length(); i++) {
                                        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_wallet_trans, null);
                                        final TextView amount = view1.findViewById(R.id.txt_charges);
                                        final TextView transaction_id = view1.findViewById(R.id.txt_tran_id);
                                        final TextView trans_date = view1.findViewById(R.id.txt_date);
                                        final TextView type = view1.findViewById(R.id.type);


                                        amount.setText("Rs " + address.getJSONObject(i).getString("amount"));
                                        transaction_id.setText(address.getJSONObject(i).getString("id"));
                                        trans_date.setText(address.getJSONObject(i).getString("created_at"));
                                        if (address.getJSONObject(i).getString("type").equalsIgnoreCase("dr")) {
                                            type.setTextColor(Utility.getColourFromRes(getActivity(), R.color.colorSelectedFavouriteDay));
                                        } else {
                                            type.setTextColor(Utility.getColourFromRes(getActivity(), R.color.order_on_the_way));
                                        }
                                        type.setText(address.getJSONObject(i).getString("type"));
                                        linearLayout.addView(view1);


                                    }
                                } else {
                                    Toast.makeText(getActivity(), "No data found.", Toast.LENGTH_SHORT).show();
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
