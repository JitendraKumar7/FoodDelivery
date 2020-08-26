package com.haris.meal4u.ActivityUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haris.meal4u.InterfaceUtil.ConnectionCallback;
import com.haris.meal4u.ObjectUtil.RequestObject;
import com.haris.meal4u.R;

public class AddMoneyActivity extends AppCompatActivity implements View.OnClickListener, ConnectionCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_money);


        TextView txtMenu = (TextView) findViewById(R.id.txt_menu);
        txtMenu.setText("Add Money ");

        ImageView imageBack = (ImageView) findViewById(R.id.image_back);
//        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);
        EditText etAmount = findViewById(R.id.et_amount);
        TextView addmoneyBtn = findViewById(R.id.addmoney_btn);
//
        addmoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Go to Payment getway", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
//        if (v == addmoneyBtn) {
//            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();

//        }

    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {

    }

    @Override
    public void onError(String data, RequestObject requestObject) {

    }
}

