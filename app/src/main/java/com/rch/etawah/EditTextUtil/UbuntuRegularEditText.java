package com.rch.etawah.EditTextUtil;

import android.content.Context;
import android.util.AttributeSet;

import com.rch.etawah.FontUtil.Font;

public class UbuntuRegularEditText extends android.support.v7.widget.AppCompatEditText {
    public UbuntuRegularEditText(Context context) {
        super(context);
        setFont(context);
    }

    public UbuntuRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public UbuntuRegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.ubuntu_regular_font(context));
    }
}

