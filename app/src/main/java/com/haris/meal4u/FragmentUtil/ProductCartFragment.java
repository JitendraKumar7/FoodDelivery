package com.haris.meal4u.FragmentUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.google.gson.Gson;
import com.haris.meal4u.ActivityUtil.CheckoutActivity;
import com.haris.meal4u.ActivityUtil.OnBoarding;
import com.haris.meal4u.ActivityUtil.RedeemCoupon;
import com.haris.meal4u.AdapterUtil.CartAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.CartCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.CartObjectModal;
import com.haris.meal4u.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class ProductCartFragment extends Fragment implements View.OnClickListener, CartCallback {
    private ImageView imageBack;
    private CartAdapter cartAdapter;
    private List<CartObjectModal> objectArrayList = new ArrayList<>();
    private DataObject dataObject;
    private LinearLayout layoutCoupon;
    private TextView txtAddToCart;
    private TextView txtDeliveryCharge;
    private TextView txtGrandTotal;
    private TextView txtTotal;
    private ImageView imageDone;
    private ImageView imageMore;
    private TextView txtApplyCoupon;
    private TextView txtCouponTagline;
    private boolean isCouponRedeem = false;
    private String discountOffer;
    private String couponId;
    private PrefObject prefObject;
    private LinearLayout lbl_discount;
    private TextView txt_discount;


    int totalItemCharges = 0;

    private void updatePrice() {

        int totalCharges = 0;

        for (int i = 0; i < objectArrayList.size(); i++) {
            CartObjectModal cartObject = objectArrayList.get(i);
            totalCharges += Integer.parseInt(cartObject.getPostQtyPrice());
        }

        totalItemCharges = totalCharges + getDeliveryCharges(totalCharges);

        txtGrandTotal.setText(String.format("%s %s.00", dataObject.getObject_currency_symbol(), totalCharges));
        txtTotal.setText(String.format("%s %s.00", dataObject.getObject_currency_symbol(), totalItemCharges));


        if (isCouponRedeem) {

            double discount = Double.parseDouble(discountOffer) / 100.0;
            int discountBill = (int) (totalItemCharges * discount);
            totalItemCharges = totalItemCharges - discountBill;
            lbl_discount.setVisibility(View.VISIBLE);

            txtTotal.setText(String.format("%s %s.00", dataObject.getObject_currency_symbol(), totalItemCharges));
            txt_discount.setText(String.format("- %s %s.00", dataObject.getObject_currency_symbol(), discountBill));

        }

    }

    @Override
    public void onClick(View v) {
        if (v == layoutCoupon) {

            if (objectArrayList.size() <= 0)
                return;

            Intent intent = new Intent(getActivity(), RedeemCoupon.class);
            intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
            startActivityForResult(intent, Constant.RequestCode.COUPON_CODE);
        }
        if (v == txtAddToCart) {

            if (objectArrayList.size() <= 0)
                return;


            Intent intent = new Intent(getActivity(), CheckoutActivity.class);
            intent.putExtra(Constant.IntentKey.RESTAURANT_DETAIL, dataObject
                    .setPost_price(String.valueOf(totalItemCharges))
                    .setCoupon_code(isCouponRedeem ? couponId : "0")
            );

            startActivity(intent);
        }
    }

    @Override
    public void onItemDeleteListener(int position) {

        objectArrayList.remove(position);
        cartAdapter.notifyDataSetChanged();
        CartObjectModal.setList(objectArrayList);
        updatePrice();

    }

    public int getDeliveryCharges(int totalCharges) {

        txtDeliveryCharge.setText(String.format("%s %s.00", dataObject.getObject_currency_symbol(), totalCharges > 200 ? 0 : 50));
        return totalCharges > 200 ? 0 : 50;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_address, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        dataObject = CartObjectModal.getSingleRestaurant(getActivity());


        Management management = new Management(getActivity());

        objectArrayList = CartObjectModal.getList();

        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserCredential(true)
                .setRetrieveLogin(true));

        lbl_discount = view.findViewById(R.id.lbl_discount);
        txt_discount = view.findViewById(R.id.txt_discount);
        imageBack = view.findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageDone = view.findViewById(R.id.image_done);
        imageMore = view.findViewById(R.id.image_more);

        TextView txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(getActivity(), R.string.cart));

        layoutCoupon = view.findViewById(R.id.layout_coupon);
        txtGrandTotal = view.findViewById(R.id.txt_grand_total);
        txtDeliveryCharge = view.findViewById(R.id.txt_delivery_charge);
        txtTotal = view.findViewById(R.id.txt_total);
        txtAddToCart = view.findViewById(R.id.txt_add_to_cart);

        txtApplyCoupon = view.findViewById(R.id.txt_apply_coupon);
        txtCouponTagline = view.findViewById(R.id.txt_coupon_tagline);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewCart = view.findViewById(R.id.recycler_view_cart);
        recyclerViewCart.setLayoutManager(gridLayoutManager);

        cartAdapter = new CartAdapter(getActivity(), objectArrayList, this) {
            @Override
            public void select(int childPosition, boolean isSelected) {

            }
        };
        recyclerViewCart.setAdapter(cartAdapter);

        if (objectArrayList.size() > 0) {
            updatePrice();
        } else {
            txtGrandTotal.setText("0");
            txtTotal.setText("0");
        }


        /*management.sendRequestToServer(new RequestObject()
                .setJson(getJson())
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.DELIVERY_CHARGES)
                .setConnectionCallback(this));*/

        imageBack.setVisibility(View.GONE);
        layoutCoupon.setOnClickListener(this);
        txtAddToCart.setOnClickListener(this);

    }

    @Override
    public void onItemQuantityListener(int position, boolean isIncrease) {
        int updatedPrice;
        int oldPrice;
        int quantity = Integer.parseInt(objectArrayList.get(position).getPostQuantity());

        if (isIncrease) {
            quantity++;
        } else {
            if (quantity > 1) {
                quantity--;
            }
        }
        oldPrice = Integer.parseInt(objectArrayList.get(position).getPostPrice());
        updatedPrice = oldPrice * quantity;
        objectArrayList.get(position).setPostQtyPrice(String.valueOf(updatedPrice));
        objectArrayList.get(position).setPostQuantity(String.valueOf(quantity));
        cartAdapter.notifyItemChanged(position);

        CartObjectModal.setList(objectArrayList);
        updatePrice();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.COUPON_CODE
                && resultCode == Activity.RESULT_OK) {

            imageMore.setVisibility(View.GONE);
            imageDone.setVisibility(View.VISIBLE);
            layoutCoupon.setEnabled(false);

            DataObject couponDetail = data.getParcelableExtra(Constant.IntentKey.COUPON_DETAIL);
            couponId = couponDetail.getCoupon_id();
            discountOffer = couponDetail.getCoupon_reward();

            txtApplyCoupon.setText(Utility.getStringFromRes(getActivity(), R.string.redeem_success));
            txtCouponTagline.setText(String.format(Utility.getStringFromRes(getActivity(), R.string.success_coupon_tagline), couponDetail.getCoupon_reward() + "%"));
            isCouponRedeem = true;

            updatePrice();


            Utility.Logger("TAG", "COUPON DETAIL " + new Gson().toJson(couponDetail));

        }

    }

}

