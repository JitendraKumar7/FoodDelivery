package com.haris.meal4u.TextviewUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.haris.meal4u.FontUtil.Font;

/**
 * Created by hp on 5/20/2018.
 */

public class LabelTextview extends AppCompatTextView {
    public LabelTextview(Context context) {
        super(context);
        setFont(context);
    }

    public LabelTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public LabelTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.ubuntu_medium_font(context));
    }
}

