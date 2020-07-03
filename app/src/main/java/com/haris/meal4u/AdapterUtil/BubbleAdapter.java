package com.haris.meal4u.AdapterUtil;

import android.content.Context;

import com.haris.meal4u.FontUtil.Font;
import com.haris.meal4u.ObjectUtil.BubbleObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.BubbleGradient;
import com.igalata.bubblepicker.model.PickerItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

;

public class BubbleAdapter implements BubblePickerAdapter {
    private Context context;
    private ArrayList<BubbleObject> bubbleArraylist = new ArrayList<>();


    public BubbleAdapter(Context context, ArrayList<BubbleObject> bubbleArraylist) {
        this.context = context;
        this.bubbleArraylist = bubbleArraylist;
    }

    @Override
    public int getTotalCount() {
        return bubbleArraylist.size();
    }

    @NotNull
    @Override
    public PickerItem getItem(int position) {

        PickerItem item = new PickerItem();
        item.setTitle(bubbleArraylist.get(position).getTitle());
        item.setGradient(new BubbleGradient(bubbleArraylist.get(position).getStartColor(),
                bubbleArraylist.get(position).getEndColor(), BubbleGradient.VERTICAL));
        item.setTypeface(Font.raleway_regular_font(context));
        item.setTextColor(Utility.getColourFromRes(context, R.color.white));
        //item.setBackgroundImage(ContextCompat.getDrawable(context, bubbleArraylist.get(position).getDrawable()));
        //item.setSelected(true);
        //item.setIconOnTop(true);

        return item;
    }


}
