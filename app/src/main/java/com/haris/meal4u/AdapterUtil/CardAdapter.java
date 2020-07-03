package com.haris.meal4u.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haris.meal4u.InterfaceUtil.CardCallback;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.ProgressObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class CardAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int PROGRESS_VIEW = 2;
    private int CARD_VIEW = 3;
    private int CARD_02_VIEW = 4;
    private int SMALL_PROGRESS_VIEW = 5;
    private int EMPTY_VIEW = 6;
    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private CardCallback cardCallback;
    private boolean isPaymentMethodScreen;


    public CardAdapter(Context context, ArrayList<Object> dataArray) {
        this.context = context;
        this.dataArray = dataArray;
        isPaymentMethodScreen = true;
    }

    public CardAdapter(Context context, ArrayList<Object> dataArray, CardCallback cardCallback) {
        this.context = context;
        this.dataArray = dataArray;
        this.cardCallback = cardCallback;
    }


    @Override
    public int getItemViewType(int position) {

        if (dataArray.get(position) instanceof EmptyObject) {
            if (((EmptyObject) dataArray.get(position)).isRequiredCompletePlaceHolder()) {
                return EMPTY_VIEW;
            } else
                return NO_DATA_VIEW;
        } else if (dataArray.get(position) instanceof ProgressObject) {

            if (isPaymentMethodScreen) {
                return SMALL_PROGRESS_VIEW;
            } else
                return PROGRESS_VIEW;

        } else if (dataArray.get(position) instanceof DataObject) {

            if (isPaymentMethodScreen) {
                return CARD_02_VIEW;
            } else
                return CARD_VIEW;
        }


        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view,false);

        } else if (viewType == EMPTY_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view,true);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == SMALL_PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == CARD_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout, parent, false);
            viewHolder = new CardHolder(view, false);

        } else if (viewType == CARD_02_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_02_layout, parent, false);
            viewHolder = new CardHolder(view, true);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProgressHolder) {

            //LookUpObject lookUpObject = (LookUpObject) dataArray.get(position);
            ProgressHolder lookUpHolder = (ProgressHolder) holder;


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyObject = (EmptyObject) dataArray.get(position);

            if (emptyObject.isRequiredCompletePlaceHolder()) {

                emptyHolder.imageIcon.setImageResource(emptyObject.getPlaceHolderIcon());
                emptyHolder.txtTitle.setText(emptyObject.getTitle());
                emptyHolder.txtDescription.setText(emptyObject.getDescription());

            } else
                emptyHolder.txtTitle.setText(emptyObject.getTitle());


        } else if (holder instanceof CardHolder) {

            final CardHolder cardHolder = (CardHolder) holder;
            DataObject dataObject = (DataObject) dataArray.get(position);

            int cardCompanyLogo = 0;

            if (dataObject.getPayment_card_company().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.visa))) {
                cardCompanyLogo = R.drawable.ic_visa;
            } else if (dataObject.getPayment_card_company().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.master_card))) {
                cardCompanyLogo = R.drawable.ic_mastercard;
            } else if (dataObject.getPayment_card_company().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.discovery_network))) {
                cardCompanyLogo = R.drawable.ic_discover_network;
            } else if (dataObject.getPayment_card_company().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.american_express))) {
                cardCompanyLogo = R.drawable.ic_american_express;
            } else if (dataObject.getPayment_card_company().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.jcb))) {
                cardCompanyLogo = R.drawable.ic_jcb;
            } else if (dataObject.getPayment_card_company().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.union_pay))) {
                cardCompanyLogo = R.drawable.ic_unionpay;
            }

            if (((DataObject) dataArray.get(position)).isPaymentCardSelected()) {
                cardHolder.imageSelection.setVisibility(View.VISIBLE);
            } else {
                cardHolder.imageSelection.setVisibility(View.GONE);
            }

            cardHolder.imageLogo.setImageResource(cardCompanyLogo);
            cardHolder.txtCardType.setText(dataObject.getPayment_card_company());
            cardHolder.txtCardNo.setText(dataObject.getPayment_card_no());
            cardHolder.layoutCard.setTag(position);
            cardHolder.layoutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) cardHolder.layoutCard.getTag();
                    if (cardCallback != null) {
                        cardCallback.onCardSelectionListener(pos, cardHolder.imageSelection.getVisibility() == View.VISIBLE ? true : false);
                    }
                }
            });

            if (cardHolder.layoutDelete != null) {
                cardHolder.layoutDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) cardHolder.layoutCard.getTag();
                        deleteCard(pos);
                    }
                });
            }


        }

    }

    @Override
    public int getItemCount() {
        return dataArray.size();

    }


    public abstract void deleteCard(int position);

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private ImageView imageIcon;
        private TextView txtDescription;

        public EmptyHolder(View view,boolean isFullEmpty) {
            super(view);

            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            if (isFullEmpty){

                imageIcon = (ImageView) view.findViewById(R.id.image_icon);
                txtDescription = (TextView) view.findViewById(R.id.txt_description);

            }
        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

    }

    protected class CardHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutCard;
        private ImageView imageLogo;
        private TextView txtCardType;
        private TextView txtCardNo;
        private ImageView imageSelection;
        private LinearLayout layoutDelete;

        public CardHolder(View view, boolean isCardScreen) {
            super(view);

            layoutCard = (LinearLayout) view.findViewById(R.id.layout_card);
            imageLogo = (ImageView) view.findViewById(R.id.image_logo);
            txtCardType = (TextView) view.findViewById(R.id.txt_card_type);
            txtCardNo = (TextView) view.findViewById(R.id.txt_card_no);
            imageSelection = (ImageView) view.findViewById(R.id.image_selection);

            if (isCardScreen) {
                layoutDelete = view.findViewById(R.id.layout_delete);
            }

        }
    }

}
