package com.rch.etawah.ActivityUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rch.etawah.AdapterUtil.ScheduleAdapter;
import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.CustomUtil.MyGlideApp;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.R;
import com.rch.etawah.Utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class RestaurantInformation extends AppCompatActivity implements View.OnClickListener {
    private RoundedImageView imageCover;
    private ImageView imageBack;
    private TextView txtMenu;
    private TextView txtName;
    private TextView txtAddress;
    private RecyclerView recyclerViewSchedule;
    private GridLayoutManager gridLayoutManager;
    private ScheduleAdapter scheduleAdapter;
    private DataObject dataObject;
    private ArrayList<Object> objectArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_information);

        getIntentData();  //Retrieve Intent Data
        initUI(); //Initialize UI

    }


    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.RESTAURANT_DETAIL);
    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        objectArrayList.addAll(dataObject.getSchedule());

        imageBack = findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.VISIBLE);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this,R.string.detail));

        imageCover = (RoundedImageView) findViewById(R.id.image_cover);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtAddress = (TextView) findViewById(R.id.txt_address);

        txtName.setText(dataObject.getObject_name());
        txtAddress.setText(dataObject.getObject_address());
        //MyGlideApp.with(this).load(Constant.ServerInformation.PICTURE_URL + dataObject.getObject_picture()).into(imageCover);

        gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        recyclerViewSchedule = (RecyclerView) findViewById(R.id.recycler_view_schedule);
        recyclerViewSchedule.setLayoutManager(gridLayoutManager);

        scheduleAdapter = new ScheduleAdapter(this,objectArrayList);
        recyclerViewSchedule.setAdapter(scheduleAdapter);


        imageBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==imageBack){
            finish();
        }
    }


}
