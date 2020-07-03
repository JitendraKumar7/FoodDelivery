package com.haris.meal4u.ActivityUtil;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haris.meal4u.AdapterUtil.ChattingAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.ChattingObject;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.DateTimeObject;
import com.haris.meal4u.ObjectUtil.PrefObject;
import com.haris.meal4u.ObjectUtil.RiderObject;
import com.haris.meal4u.ObjectUtil.TypingObject;
import com.haris.meal4u.ObjectUtil.UserObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;

public class Chatting extends AppCompatActivity {
    private String TAG = Chatting.class.getName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);



    }








}
