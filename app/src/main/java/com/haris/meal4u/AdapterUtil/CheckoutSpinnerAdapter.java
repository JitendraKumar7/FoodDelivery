package com.haris.meal4u.AdapterUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haris.meal4u.ObjectUtil.ViewTypeObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import java.util.ArrayList;

public class CheckoutSpinnerAdapter extends BaseAdapter {

    private ArrayList<ViewTypeObject> objects = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private boolean isLightBackground;
    private int layout;

    public CheckoutSpinnerAdapter(Context context, ArrayList<ViewTypeObject> objects) {
        this.context = context;
        this.objects = objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public CheckoutSpinnerAdapter(Context context, ArrayList<ViewTypeObject> objects, boolean isLightBackground) {
        this.context = context;
        this.objects = objects;
        this.isLightBackground=isLightBackground;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
        ViewHolder viewHolder = null;

        if (convertView == null) {
            if (isLightBackground){
                layout=R.layout.light_spinner_item_layout;
            }else {
                layout=R.layout.checkout_spinner_item_layout;
            }
            convertView = layoutInflater.inflate(layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /*if (objects.get(position).getPicture() != 0)
            viewHolder.imgObjective.setImageResource(objects.get(position).getPicture());*/

        viewHolder.txtObjective.setText(objects.get(position).getTitle());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);

        TextView title = (TextView) view.findViewById(R.id.txt_objective);
        ImageView img = (ImageView) view.findViewById(R.id.img_objective);

        if (position == 0) {

            title.setTextColor(Utility.getAttrColor(context, R.attr.colorDefaultNavigationIcon));
            //img.setColorFilter(Color.GRAY);

            //img.setVisibility(View.GONE);
        } else {
            //img.setVisibility(View.VISIBLE);
            //img.setColorFilter(null);
            title.setTextColor(Utility.getAttrColor(context, R.attr.colorHeading));
        }


        return view;
    }

    protected class ViewHolder {
        private ImageView imgObjective;
        private TextView txtObjective;

        public ViewHolder(View view) {
            imgObjective = (ImageView) view.findViewById(R.id.img_objective);
            txtObjective = (TextView) view.findViewById(R.id.txt_objective);
        }
    }
}
