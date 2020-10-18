package com.rch.etawah.AdapterUtil;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.rch.etawah.InterfaceUtil.ProductDetailCallback;
import com.rch.etawah.JsonUtil.ProductUtil.Attribute;
import com.rch.etawah.JsonUtil.ProductUtil.ProductAttribute;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.EmptyObject;
import com.rch.etawah.ObjectUtil.ProgressObject;
import com.rch.etawah.ObjectUtil.SpaceObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class ProductDetailAdapter extends RecyclerView.Adapter {
    private String TAG = ProductDetailAdapter.class.getSimpleName();
    private int NO_DATA_VIEW = 1;
    private int PROGRESS_VIEW = 2;
    private int PRODUCT_DETAIL_VIEW = 3;
    private int PRODUCT_ATTRIBUTE_RADIO_VIEW = 4;
    private int PRODUCT_ATTRIBUTE_CHECKBOX_VIEW = 5;
    private int RADIO_ATTRIBUTE_VIEW = 6;
    private int CHECKBOX_ATTRIBUTE_VIEW = 7;
    private int SPECIAL_INSTRUCTION_VIEW = 8;
    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private ArrayList<Object> attributeArray = new ArrayList<>();
    private ProductDetailCallback productDetailCallback;
    private boolean isRadio;
    private String currencySymbol;

    public ProductDetailAdapter(Context context, ArrayList<Object> dataArray, boolean isRadio) {
        this.context = context;
        this.dataArray = dataArray;
        this.isRadio = isRadio;
    }

    public ProductDetailAdapter(Context context, ArrayList<Object> dataArray, ProductDetailCallback productDetailCallback) {
        this.context = context;
        this.dataArray = dataArray;
        this.productDetailCallback = productDetailCallback;
    }


    @Override
    public int getItemViewType(int position) {

        if (dataArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (dataArray.get(position) instanceof DataObject) {
            return PRODUCT_DETAIL_VIEW;
        } else if (dataArray.get(position) instanceof ProductAttribute) {
            ProductAttribute productAttribute = (ProductAttribute) dataArray.get(position);

            if (productAttribute.getAttributeSelector().equalsIgnoreCase("0")) {
                return PRODUCT_ATTRIBUTE_RADIO_VIEW;
            }
            else if (productAttribute.getAttributeSelector().equalsIgnoreCase("1")) {
                return PRODUCT_ATTRIBUTE_CHECKBOX_VIEW;
            }

        } else if (dataArray.get(position) instanceof Attribute) {
            Attribute attribute = (Attribute) dataArray.get(position);

            if (attribute.isRadio()) {
              return RADIO_ATTRIBUTE_VIEW;
            }
            else {
                return CHECKBOX_ATTRIBUTE_VIEW;
            }

        } else if (dataArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        }else if (dataArray.get(position) instanceof SpaceObject) {
            return SPECIAL_INSTRUCTION_VIEW;
        }


        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == PRODUCT_DETAIL_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_detail_item_layout, parent, false);
            viewHolder = new ProductDetailHolder(view);

        } else if (viewType == PRODUCT_ATTRIBUTE_RADIO_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_attribute_radio_layout, parent, false);
            viewHolder = new RadioAttributeHolder(view);

        } else if (viewType == PRODUCT_ATTRIBUTE_CHECKBOX_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_attribute_checkbox_layout, parent, false);
            viewHolder = new RadioAttributeHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == RADIO_ATTRIBUTE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_item_layout, parent, false);
            viewHolder = new RadioHolder(view);

        } else if (viewType == CHECKBOX_ATTRIBUTE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_item_layout, parent, false);
            viewHolder = new CheckboxHolder(view);

        }else if (viewType == SPECIAL_INSTRUCTION_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.special_instruction_item_layout, parent, false);
            viewHolder = new SpecialInstructionHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProgressHolder) {

            ProgressHolder lookUpHolder = (ProgressHolder) holder;


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyObject = (EmptyObject) dataArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyObject.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyObject.getTitle());
            emptyHolder.txtDescription.setText(emptyObject.getDescription());


        } else if (holder instanceof ProductDetailHolder) {

            ProductDetailHolder productDetailHolder = (ProductDetailHolder) holder;
            DataObject dataObject = (DataObject) dataArray.get(position);

            productDetailHolder.txtPostTitle.setText(dataObject.getPost_name());
            productDetailHolder.txtPostTagline.setText(dataObject.getPost_description());
            productDetailHolder.txtPrice.setText(dataObject.getObject_currency_symbol()+" "+dataObject.getPost_price());


        } else if (holder instanceof RadioAttributeHolder) {

            RadioAttributeHolder radioAttributeHolder = (RadioAttributeHolder) holder;
            ProductAttribute productAttribute = (ProductAttribute) dataArray.get(position);

            radioAttributeHolder.txtType.setTag(position);
            radioAttributeHolder.txtAttributeName.setText(productAttribute.getAttributeTagline());
            radioAttributeHolder.txtAttributeTagline.setText(productAttribute.getAttributeDescription());

            if (productAttribute.getAttributeType().equalsIgnoreCase("0"))
                radioAttributeHolder.txtType.setText(Utility.getStringFromRes(context, R.string.optional));
            else if (productAttribute.getAttributeType().equalsIgnoreCase("1"))
                radioAttributeHolder.txtType.setText(Utility.getStringFromRes(context, R.string.required));


        }else if (holder instanceof RadioHolder) {

            final RadioHolder radioHolder = (RadioHolder) holder;
            final Attribute attribute = (Attribute) dataArray.get(position);

            radioHolder.radioAttr.setText(attribute.getName());
            radioHolder.txtAttrPrice.setText("+"+attribute.getCurrencySymbol()+". "+attribute.getPrice());

            if (attribute.isSelected()){
                radioHolder.radioAttr.setChecked(true);
            }else {
                radioHolder.radioAttr.setChecked(false);
            }

            radioHolder.txtAttrPrice.setTag(position);
            radioHolder.layoutRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) radioHolder.txtAttrPrice.getTag();
                    if (attribute.isSelected()){
                        if (productDetailCallback!=null)
                            productDetailCallback.onSelect(pos,false);
                    }
                    else {
                        if (productDetailCallback!=null)
                            productDetailCallback.onSelect(pos,true);
                    }
                }
            });


        }else if (holder instanceof CheckboxHolder) {

            final CheckboxHolder checkboxHolder = (CheckboxHolder) holder;
            final Attribute attribute = (Attribute) dataArray.get(position);

            checkboxHolder.checkBoxAttr.setText(attribute.getName());
            checkboxHolder.txtAttrPrice.setText("+"+attribute.getCurrencySymbol()+". "+attribute.getPrice());

            if (attribute.isSelected()){
                checkboxHolder.checkBoxAttr.setChecked(true);
            }else {
                checkboxHolder.checkBoxAttr.setChecked(false);
            }

            checkboxHolder.txtAttrPrice.setTag(position);
            checkboxHolder.layoutCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) checkboxHolder.txtAttrPrice.getTag();
                    if (attribute.isSelected()){
                        if (productDetailCallback!=null)
                            productDetailCallback.onSelect(pos,false);
                    }
                    else {
                        if (productDetailCallback!=null)
                            productDetailCallback.onSelect(pos,true);
                    }
                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return dataArray.size();

    }

    public abstract void select(int childPosition,boolean isSelected);

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private ImageView imageIcon;
        private TextView txtTitle;
        private TextView txtDescription;
        private LinearLayout layoutContainer;

        public EmptyHolder(View view) {
            super(view);

            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
            layoutContainer = view.findViewById(R.id.layout_container);
        }
    }

    protected class ProductDetailHolder extends RecyclerView.ViewHolder {
        private TextView txtPostTitle;
        private TextView txtPostTagline;
        private TextView txtPrice;

        public ProductDetailHolder(View view) {
            super(view);

            txtPostTitle = (TextView) view.findViewById(R.id.txt_post_title);
            txtPostTagline = (TextView) view.findViewById(R.id.txt_post_tagline);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);

        }
    }

    protected class RadioAttributeHolder extends RecyclerView.ViewHolder {
        private TextView txtAttributeName;
        private TextView txtAttributeTagline;
        private TextView txtType;

        public RadioAttributeHolder(View view) {
            super(view);

            txtAttributeName = (TextView) view.findViewById(R.id.txt_attribute_name);
            txtAttributeTagline = (TextView) view.findViewById(R.id.txt_attribute_tagline);
            txtType = (TextView) view.findViewById(R.id.txt_type);

        }
    }

    protected class RadioHolder extends RecyclerView.ViewHolder {
        private RadioButton radioAttr;
        private TextView txtAttrPrice;
        private ConstraintLayout layoutRadio;

        public RadioHolder(View view) {
            super(view);
            radioAttr = (RadioButton) view.findViewById(R.id.radio_attr);
            txtAttrPrice = (TextView) view.findViewById(R.id.txt_attr_price);
            layoutRadio=view.findViewById(R.id.layout_radio);
        }
    }

    protected class CheckboxHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBoxAttr;
        private TextView txtAttrPrice;
        private ConstraintLayout layoutCheckbox;

        public CheckboxHolder(View view) {
            super(view);
            checkBoxAttr = (CheckBox) view.findViewById(R.id.checkbox_attr);
            txtAttrPrice = (TextView) view.findViewById(R.id.txt_attr_price);
            layoutCheckbox = view.findViewById(R.id.layout_checkbox);
        }
    }

    protected class SpecialInstructionHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBoxAttr;
        private TextView txtAttrPrice;
        private ConstraintLayout layoutCheckbox;
        private EditText editInstructions;

        public SpecialInstructionHolder(View view) {
            super(view);
            checkBoxAttr = (CheckBox) view.findViewById(R.id.checkbox_attr);
            txtAttrPrice = (TextView) view.findViewById(R.id.txt_attr_price);
            layoutCheckbox = view.findViewById(R.id.layout_checkbox);
            editInstructions = view.findViewById(R.id.edit_instruction);
            editInstructions.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (productDetailCallback!=null)
                        productDetailCallback.specialInstructionsListener(editInstructions.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });

        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

    }



}
