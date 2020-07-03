package com.haris.meal4u.CustomUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

public class TriangleShapeView extends View {
    private Context context;

    public TriangleShapeView(Context context) {
        super(context);
        this.context=context;
    }

    public TriangleShapeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
    }

    public TriangleShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth() / 2;

        Path path = new Path();
        path.moveTo( w, 0);
        path.lineTo( 2 * w , 0);
        path.lineTo( 2 * w , w);
        path.lineTo( w , 0);
        path.close();

        Paint p = new Paint();
        p.setColor(Utility.getAttrColor(context, R.attr.colorBottomNavigation));

        canvas.drawPath(path, p);
    }
}