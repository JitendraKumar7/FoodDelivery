package com.rch.etawah.TextviewUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.rch.etawah.FontUtil.Font;

public class NeuroTextView extends AppCompatTextView {
    public NeuroTextView(Context context) {
        super(context);
        setFont(context);
    }

    public NeuroTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public NeuroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.neuro_political_font(context));
    }
}


