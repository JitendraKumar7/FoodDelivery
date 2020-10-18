package com.rch.etawah.TextviewUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.rch.etawah.FontUtil.Font;

/**
 * Created by hp on 5/20/2018.
 */

public class RalewayRegularTextview extends AppCompatTextView {
    public RalewayRegularTextview(Context context) {
        super(context);
        setFont(context);
    }

    public RalewayRegularTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public RalewayRegularTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.raleway_regular_font(context));
    }
}

