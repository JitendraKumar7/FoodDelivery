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
import com.haris.meal4u.ActivityUtil.Checkout;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.DateTimeObject;
import com.haris.meal4u.ObjectUtil.ScheduleObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import java.util.Date;


public class ScheduleFragment extends Fragment implements View.OnClickListener {
    private String TAG = ScheduleFragment.class.getName();
    private Management management;
    private CardView cardSchedule;
    private ImageView imageSchedule;
    private CardView cardNow;
    private ImageView imageNow;
    private TextView txtDone;
    private TextView txtDateTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_schedule, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initUI(view); //Initialize UI


    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI(View view) {

        management = new Management(getActivity());

        cardSchedule = (CardView) view.findViewById(R.id.card_schedule);
        cardNow = (CardView) view.findViewById(R.id.card_now);

        imageSchedule = (ImageView) view.findViewById(R.id.image_schedule);
        imageNow = (ImageView) view.findViewById(R.id.image_now);

        txtDone = (TextView) view.findViewById(R.id.txt_done);
        txtDateTime = view.findViewById(R.id.txt_date_time);

        txtDone.setOnClickListener(this);
        cardSchedule.setOnClickListener(this);
        cardNow.setOnClickListener(this);


    }


    /**
     * <p>It is used to show Schedule Bottom Sheet</p>
     *
     * @param context
     */
    private void showScheduleBottomSheet(Context context) {

        final String[] selectedDate = new String[1];
        final View view = getLayoutInflater().inflate(R.layout.schedule_order_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();

        final SingleDateAndTimePicker singleDateAndTimePicker = (SingleDateAndTimePicker) view.findViewById(R.id.time_date_selector);
        singleDateAndTimePicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String displayed, Date date) {
                Utility.Logger(TAG, "Date  = " + displayed);
                DateTimeObject dateTimeObject = Utility.parseTimeDate(new DateTimeObject()
                        .setDateObject(date)
                        .setCustomDateObject(true)
                        .setDatetimeType(Constant.DATETIME.DATE_DD_MM_YYYY_hh_mm_ss));

                selectedDate[0] = dateTimeObject.getDatetime();
            }
        });

        TextView txtDone = view.findViewById(R.id.txt_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDateTime.setText(selectedDate[0]);
                bottomSheetDialog.dismiss();
            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v == txtDone) {

            if (imageSchedule.getVisibility() == View.VISIBLE ||
                    imageNow.getVisibility() == View.VISIBLE) {

                ScheduleObject scheduleObject = new ScheduleObject();
                if (imageSchedule.getVisibility() == View.VISIBLE) {
                    scheduleObject.setNow(false);
                    scheduleObject.setSchedule(txtDateTime.getText().toString());
                }else {
                    scheduleObject.setNow(true);
                    DateTimeObject dateTimeObject = Utility.parseTimeDate(new DateTimeObject()
                            .setCurrentDate(true)
                            .setDatetimeType(Constant.DATETIME.DATE_DD_MM_YYYY_hh_mm_ss));
                    scheduleObject.setSchedule(dateTimeObject.getDatetime());

                }

                ((Checkout) getActivity()).onDeliveryChangeListener(scheduleObject);


            }

        }
        if (v == cardSchedule) {

            if (imageSchedule.getVisibility() != View.VISIBLE)
                imageSchedule.setVisibility(View.VISIBLE);

            imageNow.setVisibility(View.GONE);

            showScheduleBottomSheet(getActivity());

        }
        if (v == cardNow) {

            if (imageNow.getVisibility() != View.VISIBLE)
                imageNow.setVisibility(View.VISIBLE);

            imageSchedule.setVisibility(View.GONE);

        }

    }


}

