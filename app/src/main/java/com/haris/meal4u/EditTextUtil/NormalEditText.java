package com.haris.meal4u.EditTextUtil;

import android.content.Context;
import android.util.AttributeSet;

import com.haris.meal4u.FontUtil.Font;

public class NormalEditText extends android.support.v7.widget.AppCompatEditText {
    public NormalEditText(Context context) {
        super(context);
        setFont(context);
    }

    public NormalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public NormalEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.ubuntu_regular_font(context));
    }
}

