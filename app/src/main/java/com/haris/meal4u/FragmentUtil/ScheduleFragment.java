package com.haris.meal4u.FragmentUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.haris.meal4u.ActivityUtil.CheckoutActivity;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.ObjectUtil.DateTimeObject;
import com.haris.meal4u.ObjectUtil.ScheduleObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import java.util.Date;


public class ScheduleFragment extends Fragment implements View.OnClickListener {
    private String TAG = ScheduleFragment.class.getName();
    private CardView cardSchedule;
    private CardView cardNow;
    private TextView txtDone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_schedule, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardSchedule = view.findViewById(R.id.card_schedule);
        cardNow = view.findViewById(R.id.card_now);

        txtDone = view.findViewById(R.id.txt_done);

        txtDone.setOnClickListener(this);
        cardSchedule.setOnClickListener(this);
        cardNow.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {


    }

}

