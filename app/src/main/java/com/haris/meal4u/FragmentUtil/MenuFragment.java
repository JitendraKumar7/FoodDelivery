package com.haris.meal4u.FragmentUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haris.meal4u.ActivityUtil.ProductDetail;
import com.haris.meal4u.AdapterUtil.RestaurantMenuAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.InterfaceUtil.ProductCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.CartObjectModal;
import com.haris.meal4u.Utility.SharedPreference;
import com.haris.meal4u.Utility.Utility;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuFragment extends Fragment implements ProductCallback, ConnectionCallback {
    private String TAG = MenuFragment.class.getSimpleName();
    private RestaurantMenuAdapter restaurantMenuAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ArrayList<Object> dataCloneList = new ArrayList<>();
    private DataObject dataObject;
    private String currencySymbol;
    private HashMap<String, String> isProductExist = new HashMap<>();
    private List<CartObjectModal> cartList = new ArrayList<>();
    private HashMap<String, String> productTrackerHash = new HashMap<>();


    public static Fragment getFragmentInstance(DataObject dataObject, int i) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.IntentKey.RESTAURANT_DETAIL, dataObject);
        args.putInt("categoryPosition", i);
        Fragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_menu_fragment, null);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        dataObject = getArguments().getParcelable(Constant.IntentKey.RESTAURANT_DETAIL);

        objectArrayList.clear();
        if (dataCloneList.size() <= 0)
            objectArrayList.add(new ProgressObject().setScrollLoading(true));

        Management management = new Management(getActivity());
        currencySymbol = dataObject.getObject_currency_symbol();

        Utility.Logger(TAG, "Data = " + dataObject.toString());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewMenu = view.findViewById(R.id.recycler_view_menu);
        recyclerViewMenu.setLayoutManager(gridLayoutManager);

        restaurantMenuAdapter = new RestaurantMenuAdapter(getActivity(), objectArrayList, this) {
            @Override
            public void selectProduct(int position) {

            }

            @Override
            public void addToCartProduct(View v, DataObject selectedDataObject) {
                int quantity = Integer.parseInt(selectedDataObject.getNoOfItemInCart());
                if (v.getId() == R.id.btn_decrease) {

                    if (quantity > 0) {
                        quantity -= 1;
                    } else {
                        return;
                    }

                }
                if (v.getId() == R.id.btn_increase) {

                    quantity += 1;

                }

                CartObjectModal.setList(new CartObjectModal(
                        selectedDataObject.getPost_id(),
                        selectedDataObject.getPost_name(),
                        selectedDataObject.getPost_price(), String.valueOf(quantity)));

                selectedDataObject.setNoOfItemInCart(String.valueOf(quantity));
                dataObject.setNoOfItemInCart(String.valueOf(quantity));
                notifyDataSetChanged();


                ((DashboardFragment) getParentFragment()).showShoppingCart();

            }
        };


        recyclerViewMenu.setAdapter(restaurantMenuAdapter);


        String id = dataObject.getCategory_id();
        menu_items = SharedPreference.getString("specific_restaurant_menu_items" + id);


        if (menu_items != null) {
            mDataObject = new Gson().fromJson(menu_items, listType);

            objectArrayList.clear();
            objectArrayList.addAll(mDataObject);
            restaurantMenuAdapter.notifyDataSetChanged();
        }

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("category_id", dataObject.getCategory_id());
            jsonObject.accumulate("functionality", "specific_restaurant_menu_items");


            management.sendRequestToServer(new RequestObject()
                    .setJson(jsonObject.toString())
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.PRODUCT_MENU)
                    .setConnectionCallback(MenuFragment.this));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSelect(int position) {

        DataObject dtObject = (DataObject) objectArrayList.get(position);
        dtObject.setObject_currency_symbol(dataObject.getObject_currency_symbol());
        dtObject.setObject_delivery_charges(dataObject.getObject_delivery_charges());
        dtObject.setObject_min_delivery_time(dataObject.getObject_min_delivery_time());
        dtObject.setObject_min_order_price(dataObject.getObject_min_order_price());
        dtObject.setObject_id(dataObject.getObject_id());
        dtObject.setObject_latitude(dataObject.getObject_latitude());
        dtObject.setObject_longitude(dataObject.getObject_longitude());
        dtObject.setPaymentTypeList(dataObject.getPaymentTypeList());
        dtObject.setObject_status(dataObject.getObject_status());

        Intent intent = new Intent(getActivity(), ProductDetail.class);
        intent.putExtra(Constant.IntentKey.POST_DETAIL, dtObject);
        //startActivityForResult(intent, Constant.RequestCode.PRODUCT_DETAIL_CODE);

    }

    String menu_items;
    List<DataObject> mDataObject = new ArrayList<>();

    Type listType = new TypeToken<List<DataObject>>() {
    }.getType();

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {

            DataObject dataObject = (DataObject) data;
            if (requestObject.getConnection() == Constant.CONNECTION.PRODUCT_MENU) {

                for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {
                    DataObject dtObject = dataObject.getObjectArrayList().get(i);
                    dtObject.setObject_currency_symbol(currencySymbol);

                    if (isProductExist.containsKey(dtObject.getPost_id())) {
                        dtObject.setAlreadyAddedIntoCart(true);
                        dtObject.setNoOfItemInCart(isProductExist.get(dtObject.getPost_id()));
                    }


                    dataCloneList.add(dtObject);
                    productTrackerHash.put(dtObject.getPost_id(), String.valueOf(i));

                    mDataObject.add(dtObject);
                }


                String id = dataObject.getCategory_id();
                String json = new Gson().toJson(mDataObject, listType);
                SharedPreference.putString("specific_restaurant_menu_items" + id, json);

                if (menu_items == null) {
                    objectArrayList.clear();
                    objectArrayList.addAll(mDataObject);
                    restaurantMenuAdapter.notifyDataSetChanged();
                }
            }
        }


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        if (requestObject != null) {
            objectArrayList.clear();
            restaurantMenuAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        cartList.clear();
        cartList = CartObjectModal.getList();

        //if (cartList.size() <= 0)
        isProductExist.clear();

        for (int i = 0; i < cartList.size(); i++) {
            CartObjectModal dataObject = cartList.get(i);
            isProductExist.put(dataObject.getPostId(), dataObject.getPostQuantity());
        }

        if (objectArrayList.size() > 0) {
            for (int i = 0; i < dataCloneList.size(); i++) {

                int index = Integer.parseInt(productTrackerHash.get(((DataObject) dataCloneList.get(i)).getPost_id()));
                if (isProductExist.containsKey(((DataObject) dataCloneList.get(i)).getPost_id())) {
                    ((DataObject) objectArrayList.get(index)).setNoOfItemInCart(isProductExist.get(((DataObject) dataCloneList.get(i)).getPost_id()));
                    ((DataObject) objectArrayList.get(index)).setAlreadyAddedIntoCart(true);
                    Utility.Logger(TAG, "Contain Key");
                } else {
                    ((DataObject) objectArrayList.get(index)).setAlreadyAddedIntoCart(false);
                    Utility.Logger(TAG, "Not Contain Key");
                }

            }

            restaurantMenuAdapter.notifyDataSetChanged();
        }

        ((DashboardFragment) getParentFragment()).showShoppingCart();

        int categoryPosition = getArguments().getInt("categoryPosition", 0);
        ((DashboardFragment) getParentFragment()).setListener(categoryPosition, new DashboardFragment.SearchListener() {
            @Override
            public void onTextChanged(final int i, final CharSequence s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Utility.Toaster(getActivity(), String.valueOf(s), Toast.LENGTH_SHORT);
                        final String value = String.valueOf(s).toLowerCase().trim();
                        List<DataObject> mDataObject1 = new ArrayList<>();
                        for (DataObject object : mDataObject) {

                            String postName = object.getPost_name().toLowerCase().trim();
                            Utility.Logger("CharSequence", postName + " " + value);
                            if (postName.contains(value)) {
                                mDataObject1.add(object);
                            }
                        }
                        objectArrayList.clear();
                        objectArrayList.addAll(mDataObject1);
                        restaurantMenuAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

}