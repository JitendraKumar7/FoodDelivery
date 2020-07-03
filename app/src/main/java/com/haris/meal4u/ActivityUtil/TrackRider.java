package com.haris.meal4u.ActivityUtil;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.CustomUtil.GlideApp;
import com.haris.meal4u.ManagementUtil.Management;
import com.haris.meal4u.ObjectUtil.ChattingObject;
import com.haris.meal4u.ObjectUtil.DataObject;
import com.haris.meal4u.ObjectUtil.RiderObject;
import com.haris.meal4u.ObjectUtil.TrackingObject;
import com.haris.meal4u.ObjectUtil.TypingObject;
import com.haris.meal4u.ObjectUtil.UserObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.MapUtils;
import com.haris.meal4u.Utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.LineString;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.maps.model.JointType.ROUND;


public class TrackRider extends AppCompatActivity {
    private String TAG = TrackRider.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_rider);



    }




}



