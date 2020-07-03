package com.haris.meal4u.TextviewUtil;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.haris.meal4u.FontUtil.Font;

/**
 * Created by hp on 5/20/2018.
 */

public class RalewaySemBoldTextview extends AppCompatTextView {
    public RalewaySemBoldTextview(Context context) {
        super(context);
        setFont(context);
    }

    public RalewaySemBoldTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public RalewaySemBoldTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.raleway_semi_bold_font(context));
    }
}

