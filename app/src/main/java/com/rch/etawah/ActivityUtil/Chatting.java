package com.rch.etawah.ActivityUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;

public class Chatting extends AppCompatActivity {
    private String TAG = Chatting.class.getName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);



    }








}
