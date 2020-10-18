package com.rch.etawah.TextviewUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.rch.etawah.FontUtil.Font;

public class PrimeLightTextview extends AppCompatTextView {
    public PrimeLightTextview(Context context) {
        super(context);
        setFont(context);
    }

    public PrimeLightTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public PrimeLightTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.prime_light_font(context));
    }
}

