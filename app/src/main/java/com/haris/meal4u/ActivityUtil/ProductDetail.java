package com.haris.meal4u.ActivityUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haris.meal4u.AdapterUtil.ProductDetailAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.DatabaseUtil.DatabaseObject;
import com.haris.meal4u.InterfaceUtil.ProductDetailCallback;
import com.haris.meal4u.JsonUtil.ProductUtil.Attribute;
import com.haris.meal4u.JsonUtil.ProductUtil.ProductAttribute;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.SpaceObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.helpers.Util;

import java.util.ArrayList;

public class ProductDetail extends AppCompatActivity implements View.OnClickListener, ProductDetailCallback {
    private String TAG = ProductCart.class.getName();
    private ImageView imageBack;
    private TextView txtMenu;
    private Management management;
    private DataObject dataObject;
    private RecyclerView recyclerViewDetail;
    private ProductDetailAdapter productDetailAdapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ImageView btnDecrease;
    private TextView txtCount;
    private ImageView btnIncrease;
    private TextView txtAddToCart;
    private int quantity = 0;
    private int basePrice = 0;
    private String baseUnitPrice;
    private StringBuilder attributeID = new StringBuilder();
    private String instruction;
    private String alertMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        getIntentData(); //Retrieve Intent Data
        initUI(); //Initialize UI


    }


    private void getIntentData() {

        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.POST_DETAIL);

    }

    /**
     * <p>It is used to initialize UI</p>
     */
    private void initUI() {

        management = new Management(this);

        objectArrayList.add(dataObject);
        for (int i = 0; i < dataObject.getProductAttribute().size(); i++) {
            ProductAttribute productAttribute = dataObject.getProductAttribute().get(i);
            objectArrayList.add(productAttribute);

            boolean isSingle = false;

            if (productAttribute.getAttributeSelector().equalsIgnoreCase("0"))
                isSingle = true;
            else if (productAttribute.getAttributeSelector().equalsIgnoreCase("1"))
                isSingle = false;

            for (int j = 0; j < productAttribute.getAttribute().size(); j++) {
                Attribute attribute = productAttribute.getAttribute().get(j);
                attribute.setRadio(isSingle);
                attribute.setIdentificationNo(i);
                attribute.setCurrencySymbol(dataObject.getObject_currency_symbol());
                objectArrayList.add(attribute);
            }

        }

        objectArrayList.add(new SpaceObject());
        baseUnitPrice = dataObject.getPost_price();

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.detail));

        btnDecrease = (ImageView) findViewById(R.id.btn_decrease);
        txtCount = (TextView) findViewById(R.id.txt_count);
        btnIncrease = (ImageView) findViewById(R.id.btn_increase);
        txtAddToCart = (TextView) findViewById(R.id.txt_add_to_cart);

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        recyclerViewDetail = findViewById(R.id.recycler_view_detail);
        recyclerViewDetail.setLayoutManager(gridLayoutManager);

        productDetailAdapter = new ProductDetailAdapter(this, objectArrayList, this) {
            @Override
            public void select(int childPosition, boolean isSelected) {

            }
        };
        recyclerViewDetail.setAdapter(productDetailAdapter);

        imageBack.setOnClickListener(this);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        txtAddToCart.setOnClickListener(this);

    }


    /**
     * <p>It is used to convert Object into Json</p>
     *
     * @param
     * @return
     */
    private String getJson(String user_id, String restaurant_id, String product_id, String quantity, String price, String attribute, String orderType, String couponId) {
        String json = null;

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "manage_order");
            jsonObject.accumulate("user_id", user_id);
            jsonObject.accumulate("restaurant_id", restaurant_id);
            jsonObject.accumulate("product_id", product_id);
            jsonObject.accumulate("quantity", quantity);
            jsonObject.accumulate("price", price);
            jsonObject.accumulate("attribute_id", attribute);
            jsonObject.accumulate("order_type", orderType);
            jsonObject.accumulate("coupon_id", couponId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(TAG, "JSON " + json);

        return json;
    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            setResult(RESULT_CANCELED);
            finish();
        }
        if (v == btnDecrease) {

            /* For decreasing the quantity of product
             * and set it to 'Counter' */

            quantity = Integer.parseInt(txtCount.getText().toString());
            if (quantity > 1) {
                quantity--;
            }

            txtCount.setText(String.valueOf(quantity));
            if (basePrice == 0)
                basePrice = Integer.parseInt(((DataObject) objectArrayList.get(0)).getPost_price());

            ((DataObject) objectArrayList.get(0)).setPost_price(String.valueOf(basePrice * quantity));
            productDetailAdapter.notifyItemChanged(0);

        }
        if (v == btnIncrease) {

            /* For Increase the quantity of product
             * and set it to 'Counter' */

            quantity = Integer.parseInt(txtCount.getText().toString());
            quantity++;
            txtCount.setText(String.valueOf(quantity));

            if (basePrice == 0)
                basePrice = Integer.parseInt(((DataObject) objectArrayList.get(0)).getPost_price());

            ((DataObject) objectArrayList.get(0)).setPost_price(String.valueOf(basePrice * quantity));
            productDetailAdapter.notifyItemChanged(0);

        }
        if (v == txtAddToCart) {

            if (dataObject.getObject_status().equalsIgnoreCase(Utility.getStringFromRes(this, R.string.closed))){
                Utility.Toaster(getApplicationContext(),"Restaurant is closed right now",Toast.LENGTH_SHORT);
                return;
            }

            /* Add the 'Product' into Cart table by getting
             * all of the required data of product */

            for (int i = 0; i < dataObject.getProductAttribute().size(); i++) {

                ArrayList<String> selectedAttribute = new ArrayList<>();
                ProductAttribute productAttribute = dataObject.getProductAttribute().get(i);

                if (productAttribute.getAttributeSelector().equalsIgnoreCase("0")) {

                    for (int j = 0; j < objectArrayList.size(); j++) {
                        if (objectArrayList.get(j) instanceof Attribute) {

                            Attribute attribute = (Attribute) objectArrayList.get(j);
                            if (attribute.getIdentificationNo() == i) {

                                //For checking radio
                                if (attribute.isSelected())
                                    selectedAttribute.add(attribute.getId());

                            }
                        }
                    }

                    if (selectedAttribute.size() <= 0 &&
                            productAttribute.getAttributeType().equalsIgnoreCase("1")) {

                        alertMessage = productAttribute.getAttributeTagline();
                        Utility.Toaster(this, alertMessage, Toast.LENGTH_SHORT);
                        return;
                    }

                } else if (productAttribute.getAttributeSelector().equalsIgnoreCase("1")) {

                    for (int j = 0; j < objectArrayList.size(); j++) {
                        if (objectArrayList.get(j) instanceof Attribute) {
                            Attribute attribute = (Attribute) objectArrayList.get(j);
                            if (attribute.getIdentificationNo() == i) {

                                //For checking radio
                                if (attribute.isSelected())
                                    selectedAttribute.add(attribute.getId());

                            }
                        }
                    }

                    int minNoOfSelection = 0;
                    if (productAttribute.getAttributeDescription().matches(".*\\d.*"))
                      minNoOfSelection = Integer.parseInt(Utility.extractNumericDataFromString(productAttribute.getAttributeDescription()));

                    if (selectedAttribute.size() < minNoOfSelection &&
                            productAttribute.getAttributeType().equalsIgnoreCase("1")) {

                        alertMessage = productAttribute.getAttributeDescription();
                        Utility.Toaster(this, alertMessage, Toast.LENGTH_SHORT);
                        return;

                    }

                }

            }

            Utility.Logger(TAG, "Attribute ID = " + attributeID.toString());

            if (Integer.parseInt(txtCount.getText().toString()) <= 0) {
                Utility.Toaster(this, Utility.getStringFromRes(this, R.string.select_quantity), Toast.LENGTH_SHORT);
                return;
            }


            /* Gather 'Attribute IDs' into StringBuilder so that
             * we would send it to Server for further process  */

            for (int i = 0; i < objectArrayList.size(); i++) {
                if (objectArrayList.get(i) instanceof Attribute) {
                    Attribute attribute = (Attribute) objectArrayList.get(i);
                    if (attribute.isSelected()) {
                        attributeID.append(attribute.getId());
                        if (i != (objectArrayList.size() - 1))
                            attributeID.append(",");
                    }
                }
            }

            StringBuilder paymentBuilder = new StringBuilder();
            for (int i = 0; i < dataObject.getPaymentTypeList().size(); i++) {
                paymentBuilder.append(dataObject.getPaymentTypeList().get(i));
                if (i != (dataObject.getPaymentTypeList().size() - 1))
                    paymentBuilder.append(",");
            }


            /* Check either Carty is empty or already filled
             * with the item of another Restaurant */

            ArrayList<Object> cartList = new ArrayList<>();
            cartList.addAll(management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.CART)
                    .setDbOperation(Constant.DB.SPECIFIC_ID)
                    .setDataObject(dataObject)));

            /* If cart is not empty then it would show
             * Alert Bottom Sheet Dialog  */

            if (cartList.size() > 0) {
                showAlertSheet(this);
                return;
            }


            /* Gather data & save it in local database 'Cart'
             * it save user_id , total quantity , price , attribute_id
             * along with special instructions */

            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(Constant.TYPE.CART)
                    .setDbOperation(Constant.DB.INSERT)
                    .setDataObject(dataObject
                            .setUser_id("0")
                            .setPost_quantity(txtCount.getText().toString())
                            .setBasePrice(String.valueOf(basePrice))
                            .setPost_price(
                                    Utility.extractNumericDataFromString(
                                            ((DataObject) objectArrayList.get(0)).getPost_price()
                                    )
                            )
                            .setPaymentType(paymentBuilder.toString())
                            .setPost_attribute_id(attributeID.toString())
                            .setSpecial_instructions(instruction)));


            /*management.sendRequestToServer(new RequestObject()
                    .setContext(this)
                    .setJson(getJson("31.459660", "73.123389", Constant.AppConfiguration.DEFAULT_RADIUS))
                    .setConnection(Constant.CONNECTION.PRODUCT_ORDER)
                    .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                    .setConnectionCallback(null));*/
            Intent intent = new Intent();
            intent.putExtra(Constant.IntentKey.POST_ID, dataObject.getPost_id());
            setResult(RESULT_OK, intent);
            finish();

        }
    }


    @Override
    public void onSelect(int parentPosition, boolean isSelected) {

        if (objectArrayList.get(parentPosition) instanceof Attribute) {

            Attribute attribute = (Attribute) objectArrayList.get(parentPosition);
            Utility.Logger(TAG, "Selected = " + isSelected);
            Utility.Logger(TAG, "Attribute Detail = " + attribute.toString());

            int basePrice;
            int attributePrice;
            int updatedPrice;

            int attributeConfigDetail = attribute.getIdentificationNo();

            if (attribute.isRadio()) {

                for (int i = 0; i < objectArrayList.size(); i++) {
                    if (objectArrayList.get(i) instanceof Attribute) {

                        if (i == parentPosition) {
                            ((Attribute) objectArrayList.get(parentPosition)).setSelected(isSelected);

                        } else if (((Attribute) objectArrayList.get(i)).getIdentificationNo() ==
                                attribute.getIdentificationNo()) {

                            if (dataObject.getProductAttribute().get(attributeConfigDetail).getAttributeNature()
                                    .equalsIgnoreCase("0")) {

                                ((Attribute) objectArrayList.get(i)).setSelected(false);
                                this.basePrice = 0;

                                Utility.Logger(TAG, "Selection 01");


                            } else {

                                if (((Attribute) objectArrayList.get(i)).isSelected()
                                        && ((Attribute) objectArrayList.get(i)).isRadio()) {

                                    ((Attribute) objectArrayList.get(i)).setSelected(false);

                                    Utility.Logger(TAG, "Radio Selection Beginning " + i);

                                    basePrice = Integer.parseInt(Utility.extractNumericDataFromString(((DataObject) objectArrayList.get(0)).getPost_price()));
                                    attributePrice = Integer.parseInt(attribute.getPrice());
                                    updatedPrice = basePrice - attributePrice;

                                    ((DataObject) objectArrayList.get(0))
                                            .setPost_price(String.valueOf(updatedPrice));

                                }

                            }


                        }
                    }
                }

            } else {
                ((Attribute) objectArrayList.get(parentPosition)).setSelected(isSelected);
            }


            if (dataObject.getProductAttribute().get(attributeConfigDetail).getAttributeNature()
                    .equalsIgnoreCase("0")) {

                ((DataObject) objectArrayList.get(0)).setPost_price(attribute.getPrice());
                txtCount.setText("0");

                for (int i = 0; i < objectArrayList.size(); i++) {
                    if (objectArrayList.get(i) instanceof Attribute) {

                        Utility.Logger(TAG, "Parent = " + parentPosition + " I = " + i);

                        if (i != parentPosition && ((Attribute) objectArrayList.get(i)).isSelected()) {
                            Utility.Logger(TAG, "Matched Radio Found");
                            ((Attribute) objectArrayList.get(i)).setSelected(false);
                        }

                    }
                }

            } else if (dataObject.getProductAttribute().get(attributeConfigDetail).getAttributeNature()
                    .equalsIgnoreCase("1")) {

                basePrice = Integer.parseInt(Utility.extractNumericDataFromString(((DataObject) objectArrayList.get(0)).getPost_price()));
                attributePrice = Integer.parseInt(attribute.getPrice());


                if (isSelected)
                    updatedPrice = basePrice + attributePrice;
                else
                    updatedPrice = basePrice - attributePrice;

                ((DataObject) objectArrayList.get(0))
                        .setPost_price(String.valueOf(updatedPrice));

            }

            productDetailAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public void specialInstructionsListener(String instructions) {
        this.instruction = instructions;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * <p>Used to show bottom sheet dialog for Cart Alert</p>
     */
    private void showAlertSheet(final Context context) {

        View view = getLayoutInflater().inflate(R.layout.cart_alert_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();


        final TextView txtDone = (TextView) view.findViewById(R.id.txt_done);
        TextView txtCancel = (TextView) view.findViewById(R.id.txt_cancel);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                management.getDataFromDatabase(new DatabaseObject()
                        .setTypeOperation(Constant.TYPE.CART)
                        .setDbOperation(Constant.DB.DELETE_SPECIFIC_TYPE)
                        .setDataObject(new DataObject().setUser_id("0")));

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

                txtAddToCart.callOnClick();


            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

            }
        });

    }


}
