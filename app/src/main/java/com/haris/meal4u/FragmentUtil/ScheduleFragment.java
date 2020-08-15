package com.haris.meal4u.FragmentUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haris.meal4u.ActivityUtil.Base;
import com.haris.meal4u.AdapterUtil.CartAdapter;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.CartObjectModal;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_schedule, null);
    }

    public int getDeliveryCharges(int totalCharges) {

        return totalCharges > 200 ? 0 : 50;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        String user_name = bundle.getString("user_name");
        String user_phone = bundle.getString("user_phone");

        String order_id = bundle.getString("order_id");
        String order_price = bundle.getString("order_price");
        String payment_type = bundle.getString("payment_type");
        String delivery_date = bundle.getString("delivery_date");
        String billing_address = bundle.getString("billing_address");

        TextView txt_order_id = view.findViewById(R.id.txt_order_id);
        TextView txt_order_name = view.findViewById(R.id.txt_order_name);
        TextView txt_order_date = view.findViewById(R.id.txt_order_date);
        TextView txt_order_address = view.findViewById(R.id.txt_order_address);
        TextView txt_order_payment = view.findViewById(R.id.txt_order_payment);


        txt_order_id.setText("#" + order_id);
        txt_order_name.setText(user_name);
        txt_order_date.setText(delivery_date);
        txt_order_payment.setText(payment_type);
        txt_order_address.setText(billing_address);

        TextView txtDone = view.findViewById(R.id.txt_done);
        txtDone.setOnClickListener(this);

        LinearLayout linearLayout = view.findViewById(R.id.linearLayout);


        View order_placed1 = LayoutInflater.from(getActivity()).inflate(R.layout.order_placed_item_layout, null);
        linearLayout.addView(order_placed1);

        int totalCharges = 0;
        for (CartObjectModal modal : CartObjectModal.getList()) {

            View order_placed2 = LayoutInflater.from(getActivity()).inflate(R.layout.order_placed_item_layout, null);
            new ProductDetailHolder(order_placed2, modal);
            linearLayout.addView(order_placed2);

            totalCharges += Integer.parseInt(modal.getPostQtyPrice());
        }

        totalCharges = totalCharges + getDeliveryCharges(totalCharges);


        int totalOrderPrice = Integer.parseInt(order_price);

        if (totalCharges == totalOrderPrice) {
            View order_placed3 = LayoutInflater.from(getActivity()).inflate(R.layout.order_placed_item_layout, null);
            new ProductDetailHolder(order_placed3, "Total Price", order_price);
            linearLayout.addView(order_placed3);
        }
        //
        else {


            View order_placed4 = LayoutInflater.from(getActivity()).inflate(R.layout.order_placed_item_layout, null);
            new ProductDetailHolder(order_placed4, "Discount", String.valueOf(totalCharges - totalOrderPrice));
            linearLayout.addView(order_placed4);

            View order_placed5 = LayoutInflater.from(getActivity()).inflate(R.layout.order_placed_item_layout, null);
            new ProductDetailHolder(order_placed5, "Total Price", order_price);
            linearLayout.addView(order_placed5);
        }

    }

    protected class ProductDetailHolder {

        public ProductDetailHolder(View view, CartObjectModal modal) {

            TextView txtPostTitle = view.findViewById(R.id.txt_post_title);
            TextView txtPrice = view.findViewById(R.id.txt_price);
            TextView txtCount = view.findViewById(R.id.txt_count);

            txtPostTitle.setText(modal.getPostName());
            txtPrice.setText(String.format("₹%s", modal.getPostQtyPrice()));
            txtCount.setText(modal.getPostQuantity());
        }

        public ProductDetailHolder(View view, String label, String order_price) {
            TextView txtPostTitle = view.findViewById(R.id.txt_post_title);
            TextView txtPrice = view.findViewById(R.id.txt_price);
            TextView txtCount = view.findViewById(R.id.txt_count);

            txtPostTitle.setText(label);
            txtPostTitle.setGravity(Gravity.RIGHT);
            txtPrice.setText(String.format("₹%s", order_price));
            txtCount.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        CartObjectModal.setList(new ArrayList<CartObjectModal>());
        Intent intent = new Intent(getActivity(), Base.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();

    }

}
