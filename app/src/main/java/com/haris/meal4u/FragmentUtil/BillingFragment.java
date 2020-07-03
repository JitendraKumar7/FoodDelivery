package com.haris.meal4u.FragmentUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.haris.meal4u.ActivityUtil.Checkout;
import com.haris.meal4u.AdapterUtil.CardAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.InterfaceUtil.CardCallback;
import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.MyApplication;
import com.haris.meal4u.ObjectUtil.BillingObject;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.ixidev.gdpr.GDPRChecker;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.SourceParams;
import com.stripe.android.model.Token;

import net.bohush.geometricprogressview.GeometricProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class BillingFragment extends Fragment implements View.OnClickListener, CardCallback, ConnectionCallback {
    private String TAG = BillingFragment.class.getName();
    private ImageView imageFilter;
    private Management management;
    private TextView txtConfirmCheckout;
    private EditText editBuilding;
    private EditText editStreetAddress;
    private EditText editArea;
    private EditText editUnit;
    private EditText editComment;
    private ImageView imageMethodCod;
    private LinearLayout layoutAddCard;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private RecyclerView recyclerViewCard;
    private GridLayoutManager gridLayoutManager;
    private CardAdapter cardAdapter;
    private CardView cardMethodCod;
    private ArrayList<String> acceptedPaymentType = new ArrayList<>();
    private HashMap<String, String> acceptedPaymentTypeHash = new HashMap<>();
    private boolean isCardSelected = false;
    private PrefObject prefObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_billing, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initUI(view); //Initialize UI


    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        management = new Management(getActivity());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveLogin(true)
                .setRetrieveUserCredential(true));

        editBuilding = (EditText) view.findViewById(R.id.edit_building);
        editStreetAddress = (EditText) view.findViewById(R.id.edit_street_address);
        editArea = (EditText) view.findViewById(R.id.edit_area);
        editUnit = (EditText) view.findViewById(R.id.edit_unit);
        editComment = (EditText) view.findViewById(R.id.edit_comment);
        txtConfirmCheckout = view.findViewById(R.id.txt_confirm_checkout);
        layoutAddCard = view.findViewById(R.id.layout_add_card);
        imageMethodCod = view.findViewById(R.id.image_method_cod);
        cardMethodCod = view.findViewById(R.id.card_method_cod);

        acceptedPaymentType.clear();
        acceptedPaymentType.addAll(((Checkout) getActivity()).restaurantDetail.getPaymentTypeList());

        for (int i = 0; i < acceptedPaymentType.size(); i++) {
            acceptedPaymentTypeHash.put(acceptedPaymentType.get(i), "0");
        }


        objectArrayList.add(new ProgressObject());

        gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        recyclerViewCard = view.findViewById(R.id.recycler_view_card);
        recyclerViewCard.setLayoutManager(gridLayoutManager);

        cardAdapter = new CardAdapter(getActivity(), objectArrayList, this) {
            @Override
            public void deleteCard(int position) {

            }
        };
        recyclerViewCard.setAdapter(cardAdapter);

        if (acceptedPaymentTypeHash.containsKey(Utility.getStringFromRes(getContext(), R.string.credit_debit_card))) {

            management.sendRequestToServer(new RequestObject()
                    .setJson(getJson(prefObject.getUserId()))
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.PAYMENT_CARDS)
                    .setConnectionCallback(this));

        } else {

            objectArrayList.clear();
            objectArrayList.add(new EmptyObject()
                    .setTitle(Utility.getStringFromRes(getContext(), R.string.cards_not_supported)));

        }


        txtConfirmCheckout.setOnClickListener(this);
        layoutAddCard.setOnClickListener(this);
        cardMethodCod.setOnClickListener(this);


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


    @Override
    public void onClick(View v) {
        if (v == txtConfirmCheckout) {


            if (imageMethodCod.getVisibility() == View.VISIBLE) {

                ((Checkout) getActivity()).onStepClick(2);
                ((Checkout) getActivity()).onBillingChangeListener(new BillingObject()
                        .setPaymentMethod(Utility.getStringFromRes(getActivity(), R.string.cod)));

                return;

            } else {
                for (int i = 0; i < objectArrayList.size(); i++) {
                    DataObject dataObject = (DataObject) objectArrayList.get(i);
                    if (dataObject.isPaymentCardSelected()) {

                        ((Checkout) getActivity()).onStepClick(2);
                        ((Checkout) getActivity()).onBillingChangeListener(new BillingObject()
                                .setPaymentMethod(Utility.getStringFromRes(getActivity(), R.string.credit_debit_card))
                                .setStripeCustomerToken(dataObject.getStripe_customer_no()));

                        return;

                    }
                }
            }

            Utility.Toaster(getContext(), Utility.getStringFromRes(getContext(), R.string.select_payment_method), Toast.LENGTH_SHORT);

        }
        if (v == cardMethodCod) {

            if (!acceptedPaymentTypeHash.containsKey(Utility.getStringFromRes(getContext(), R.string.cod))) {
                Utility.Toaster(getContext(), Utility.getStringFromRes(getContext(), R.string.cod_not_supported), Toast.LENGTH_SHORT);
                return;
            }

            if (imageMethodCod.getVisibility() != View.VISIBLE)
                imageMethodCod.setVisibility(View.VISIBLE);

            for (int i = 0; i < objectArrayList.size(); i++) {
                if (objectArrayList.get(i) instanceof DataObject)
                    ((DataObject) objectArrayList.get(i)).setPaymentCardSelected(false);
            }
            cardAdapter.notifyDataSetChanged();

        }
        if (v == layoutAddCard) {
            showCardAddingBottomSheet(getActivity());
        }
    }


    @Override
    public void onCardSelectionListener(int position, boolean isSelected) {

        for (int i = 0; i < objectArrayList.size(); i++) {
            if (i != position)
                ((DataObject) objectArrayList.get(i)).setPaymentCardSelected(false);
            else
                ((DataObject) objectArrayList.get(i)).setPaymentCardSelected(true);
        }
        imageMethodCod.setVisibility(View.GONE);
        cardAdapter.notifyDataSetChanged();
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
                        .setTitle(Utility.getStringFromRes(getActivity(), R.string.no_card_avaialble)));
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

                Stripe stripe = MyApplication.getStripe();
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
                stripe.createToken(card, new TokenCallback() {
                    @Override
                    public void onError(@NonNull Exception error) {
                        txtDone.setText(Utility.getStringFromRes(context, R.string.try_again));
                        Utility.Toaster(context, error.getMessage(), Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onSuccess(@NonNull final Token token) {
                        Utility.Logger(TAG, "Token = " + token.getId());

                        management.sendRequestToServer(new RequestObject()
                                .setJson(getAddingCardJson(prefObject.getUserId(), editCardNumber.getText().toString().trim(), card.getBrand(), token.getId()))
                                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                .setConnection(Constant.CONNECTION.ADD_CARD)
                                .setConnectionCallback(new ConnectionCallback() {
                                    @Override
                                    public void onSuccess(Object data, RequestObject requestObject) {
                                        if (data != null && requestObject != null) {

                                            objectArrayList.clear();
                                            objectArrayList.add(new DataObject()
                                                    .setStripe_customer_no(token.getId())
                                                    .setPayment_card_company(card.getBrand())
                                                    .setPayment_card_no(Utility.maskSomeCharacter(editCardNumber.getText().toString())));

                                            cardAdapter.notifyDataSetChanged();
                                            txtDone.setVisibility(View.VISIBLE);
                                            txtDone.setText(Utility.getStringFromRes(context, R.string.successfully_added));
                                            progressBar.setVisibility(View.GONE);
                                            bottomSheetDialog.dismiss();

                                        }
                                    }

                                    @Override
                                    public void onError(String data, RequestObject requestObject) {

                                        txtDone.setVisibility(View.VISIBLE);
                                        txtDone.setText(Utility.getStringFromRes(context, R.string.try_again));
                                        progressBar.setVisibility(View.GONE);
                                        bottomSheetDialog.dismiss();

                                    }
                                }));


                    }
                });

                SourceParams.createSourceFromTokenParams("");


            }
        });


    }


}

