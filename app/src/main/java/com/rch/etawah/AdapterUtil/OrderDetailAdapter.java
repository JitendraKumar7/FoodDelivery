package com.rch.etawah.AdapterUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.R;

import java.util.ArrayList;

public class OrderDetailAdapter extends BaseAdapter {

    private ArrayList<Object> objects = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public OrderDetailAdapter(Context context,ArrayList<Object> objects) {
        this.context = context;
        this.objects=objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.order_detail_item_layout, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }


        viewHolder.txtProduct.setText( ((DataObject)objects.get(position)).getOrder_product_name() );
        viewHolder.txtQuantity.setText( "(x"+((DataObject)objects.get(position)).getOrder_product_quantity()+")" );
        viewHolder.txtPrice.setText( ((DataObject)objects.get(position)).getOrder_product_price() );

        return convertView;
    }


    protected class ViewHolder {
        private LinearLayout layoutProduct;
        private TextView txtProduct;
        private TextView txtQuantity;
        private TextView txtPrice;

        public ViewHolder(View view) {
            layoutProduct = (LinearLayout) view.findViewById(R.id.layout_product);
            txtProduct = (TextView) view.findViewById(R.id.txt_product);
            txtQuantity = (TextView) view.findViewById(R.id.txt_quantity);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
        }
    }
}
