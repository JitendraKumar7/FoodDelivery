package com.rch.etawah.ActivityUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rch.etawah.AdapterUtil.CardAdapter;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.InterfaceUtil.ConnectionCallback;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.AppController;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.EmptyObject;
import com.rch.etawah.ObjectUtil.PrefObject;
import com.rch.etawah.ObjectUtil.ProgressObject;
import com.rch.etawah.ObjectUtil.RequestObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import net.bohush.geometricprogressview.GeometricProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfPaymentMethod extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {
    private String TAG = ListOfPaymentMethod.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private ImageView imageAdd;
    private Management management;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerViewCard;
    private CardAdapter cardAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private PrefObject prefObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        initUI(); //Initialize UI


    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.list_of_credit_debit));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageAdd = findViewById(R.id.image_filter);
        imageAdd.setVisibility(View.VISIBLE);
        imageAdd.setImageResource(R.drawable.ic_add);

        objectArrayList.add(new ProgressObject());
        management = new Management(this);
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));


        //Initialize & Setup Layout Manager with Recycler View

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        recyclerViewCard = findViewById(R.id.recycler_view_card);
        recyclerViewCard.setLayoutManager(gridLayoutManager);

        cardAdapter = new CardAdapter(this, objectArrayList) {
            @Override
            public void deleteCard(int position) {

                management.sendRequestToServer(new RequestObject()
                        .setJson(getDeleteJson(((DataObject) objectArrayList.get(position)).getPayment_id()))
                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                        .setConnection(Constant.CONNECTION.DELETE_PAYMENT_CARD)
                        .setConnectionCallback(null));

                objectArrayList.remove(position);

                if (objectArrayList.size()<=0){
                    objectArrayList.add(new EmptyObject()
                            .setRequiredCompletePlaceHolder(true)
                            .setTitle(Utility.getStringFromRes(getApplicationContext(), R.string.no_debit_card))
                            .setDescription(Utility.getStringFromRes(getApplicationContext(), R.string.no_debit_tagline))
                            .setPlaceHolderIcon(R.drawable.em_no_card));
                }

                cardAdapter.notifyDataSetChanged();

            }
        };
        recyclerViewCard.setAdapter(cardAdapter);

        //Send request to Server for retrieving TrendingPhotos Wallpapers

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson(prefObject.getUserId()))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.PAYMENT_CARDS)
                .setConnectionCallback(this));


        imageBack.setOnClickListener(this);
        imageAdd.setOnClickListener(this);

    }




    /**
     * <p>It is used to convert data into json format for POST type Request</p>
     *
     * @return
     */
    public String getJson(String userId) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "retrieve_payment_cards");
            jsonObject.accumulate("user_id", userId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);
        return json;

    }

    /**
     * <p>It is used to convert data into json format for POST type Request</p>
     *
     * @return
     */
    public String getDeleteJson(String card_id) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "delete_card");
            jsonObject.accumulate("payment_card_id", card_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);
        return json;

    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }

        if (v == imageAdd) {
            showCardAddingBottomSheet(this);
        }

    }


    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        if (data != null && requestObject != null) {

            if (requestObject.getConnection() == Constant.CONNECTION.PAYMENT_CARDS) {

                DataObject dataObject = (DataObject) data;
                objectArrayList.clear();

                for (int i = 0; i < dataObject.getObjectArrayList().size(); i++) {

                    DataObject paymentObject = dataObject.getObjectArrayList().get(i);
                    String cardNo = paymentObject.getPayment_card_no();
                    objectArrayList.add(paymentObject
                            .setPayment_card_no(Utility.maskSomeCharacter(cardNo)));

                }

                cardAdapter.notifyDataSetChanged();

            }

        }
    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        if (requestObject != null) {

            if (requestObject.getConnection() == Constant.CONNECTION.PAYMENT_CARDS) {

                objectArrayList.clear();
                objectArrayList.add(new EmptyObject()
                        .setRequiredCompletePlaceHolder(true)
                        .setTitle(Utility.getStringFromRes(this, R.string.no_debit_card))
                        .setDescription(Utility.getStringFromRes(this, R.string.no_debit_tagline))
                        .setPlaceHolderIcon(R.drawable.em_no_card));
                cardAdapter.notifyDataSetChanged();

            }

        }
    }


    /**
     * <p>It is used to show Language Selector</p>
     *
     * @param context
     */
    private void showCardAddingBottomSheet(final Context context) {
        final View view = getLayoutInflater().inflate(R.layout.add_card_bottom_sheet, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();

        final TextView txtDone = view.findViewById(R.id.txt_done);
        final EditText editCardHolder = view.findViewById(R.id.edit_card_holder);
        final EditText editCardNumber = view.findViewById(R.id.edit_card_number);
        final EditText editExpiryMonth = view.findViewById(R.id.edit_expiry_month);
        final EditText editExpiryYear = view.findViewById(R.id.edit_expiry_year);
        final EditText editCVV = view.findViewById(R.id.edit_cvv);

        final GeometricProgressView progressBar = view.findViewById(R.id.progress_bar);
        LinearLayout layoutDone = view.findViewById(R.id.layout_done);

        layoutDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utility.isEmptyString(editCardHolder.getText().toString())) {
                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.fill_holder_name), Toast.LENGTH_SHORT);
                    return;
                }

                if (Utility.isEmptyString(editCardNumber.getText().toString())) {
                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.fill_card_no), Toast.LENGTH_SHORT);
                    return;
                }

                if (Utility.isEmptyString(editExpiryMonth.getText().toString())) {
                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.fill_expiry_month), Toast.LENGTH_SHORT);
                    return;
                }

                if (Utility.isEmptyString(editExpiryYear.getText().toString())) {
                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.fill_expiry_year), Toast.LENGTH_SHORT);
                    return;
                }

                if (Utility.isEmptyString(editCVV.getText().toString())) {
                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.fill_cvv), Toast.LENGTH_SHORT);
                    return;
                }

                Card.Builder builder = new Card.Builder(editCardNumber.getText().toString().trim()
                        , Integer.parseInt(editExpiryMonth.getText().toString())
                        , Integer.parseInt(editExpiryYear.getText().toString())
                        , editCVV.getText().toString());
                builder.name(editCardHolder.getText().toString());
                final Card card = builder.build();

                if (!card.validateCard()) {
                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.validate_card), Toast.LENGTH_SHORT);
                    return;
                }

                if (!card.validateCVC()) {
                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.validate_cvc), Toast.LENGTH_SHORT);
                    return;
                }

                if (!card.validateExpiryDate()) {
                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.validate_expiry_date), Toast.LENGTH_SHORT);
                    return;
                }

                if (!card.validateExpMonth()) {
                    Utility.Toaster(context, Utility.getStringFromRes(context, R.string.validate_expiry_month), Toast.LENGTH_SHORT);
                    return;
                }

                if (txtDone.getText().toString().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.done))) {

                    txtDone.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                }

                Utility.Logger(TAG, "Card Brand = " + card.getBrand());

            }
        });


    }


    /**
     * <p>It is used to convert data into json format for POST type Request</p>
     *
     * @return
     */
    public String getAddingCardJson(String userId, String card_no, String card_company, String stripe_customer_id) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", "add_payment_cards");
            jsonObject.accumulate("user_id", userId);
            jsonObject.accumulate("card_no", card_no);
            jsonObject.accumulate("card_company", card_company);
            jsonObject.accumulate("stripe_customer_id", stripe_customer_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger("JSON", json);
        return json;

    }


}
