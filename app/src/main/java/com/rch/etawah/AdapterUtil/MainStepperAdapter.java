package com.rch.etawah.AdapterUtil;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.rch.etawah.ObjectUtil.StepperObject;
import com.liefery.android.vertical_stepper_view.VerticalStepperAdapter;

import java.util.ArrayList;

public class MainStepperAdapter extends VerticalStepperAdapter {
    private ArrayList<Object> objectArrayList;

    public MainStepperAdapter( Context context,ArrayList objectArrayList ) {
        super( context );
        this.objectArrayList=objectArrayList;
    }

    @NonNull
    @Override
    public CharSequence getTitle( int position ) {
        return ((StepperObject)objectArrayList.get(position)).getTitle();
    }

    @Nullable
    @Override
    public CharSequence getSummary( int position ) {
        return ((StepperObject)objectArrayList.get(position)).getDescription();
    }

    @Nullable
    @Override
    public int getImage(int position) {
        return ((StepperObject)objectArrayList.get(position)).getIcon();
    }

    @Override
    public boolean isEditable( int position ) {
        return position == 1;
    }

    @NonNull
    @Override
    public View onCreateContentView(Context context, int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Void getItem( int position ) {
        return null;
    }

}
