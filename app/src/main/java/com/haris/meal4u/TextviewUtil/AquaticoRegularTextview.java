package com.haris.meal4u.TextviewUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.haris.meal4u.FontUtil.Font;

public class AquaticoRegularTextview extends AppCompatTextView {
    public AquaticoRegularTextview(Context context) {
        super(context);
        setFont(context);
    }

    public AquaticoRegularTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public AquaticoRegularTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.aquatico_regular_font(context));
    }
}

