package com.haris.meal4u.ActivityUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haris.meal4u.AdapterUtil.PictureUploaderAdapter;
import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.ObjectUtil.Base64Object;
import com.haris.meal4u.ObjectUtil.EmptyObject;
import com.haris.meal4u.ObjectUtil.GlobalDataObject;
import com.haris.meal4u.ObjectUtil.PictureObject;
import com.haris.meal4u.R;
import com.haris.meal4u.Utility.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class PictureUploader extends AppCompatActivity implements View.OnClickListener {
    private TextView txtMenu;
    private ImageView imageAdd;
    private ImageView imageBack;
    private ImageView imageDone;
    private RecyclerView recyclerViewPicture;
    private PictureUploaderAdapter pictureUploaderAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private Bitmap selectedBitmap;
    private GridLayoutManager gridLayoutManager;
    private boolean isFirstTime = true;
    private String base64;
    private GlobalDataObject globalDataObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_uploader);

        getIntentData(); //Retrieve Intent Data
        initUI(); //Initialize UI

    }


    /**
     * <p>It is used to retrieve Intent Data</p>
     */
    private void getIntentData() {

        globalDataObject = Constant.globalDataObject;

        if (globalDataObject != null) {
            objectArrayList.addAll(globalDataObject.getObjectArrayList());
            isFirstTime = false;
        }

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.picture_uploader));

        imageBack = findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.VISIBLE);

        imageDone = findViewById(R.id.image_favourite);
        imageDone.setImageResource(R.drawable.ic_tick);
        imageDone.setVisibility(View.VISIBLE);

        imageAdd = findViewById(R.id.image_filter);
        imageAdd.setVisibility(View.VISIBLE);
        imageAdd.setImageResource(R.drawable.ic_add);

        if (objectArrayList.size() <= 0)
            objectArrayList.add(new EmptyObject()
                    .setTitle(Utility.getStringFromRes(this, R.string.upload_picture))
                    .setDescription(Utility.getStringFromRes(this, R.string.upload_picture_tagline))
                    .setPlaceHolderIcon(R.drawable.em_no_picture));

        gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (objectArrayList.get(position) instanceof EmptyObject) {
                    return 2;
                } else if (objectArrayList.get(position) instanceof PictureObject) {
                    return 1;
                } else
                    return 1;
            }
        });

        recyclerViewPicture = (RecyclerView) findViewById(R.id.recycler_view_picture);
        recyclerViewPicture.setLayoutManager(gridLayoutManager);

        pictureUploaderAdapter = new PictureUploaderAdapter(this, objectArrayList) {
            @Override
            public void onDelete(int position) {
                objectArrayList.remove(position);

                if (objectArrayList.size() <= 0) {
                    objectArrayList.add(new EmptyObject()
                            .setTitle(Utility.getStringFromRes(getApplicationContext(), R.string.upload_picture))
                            .setDescription(Utility.getStringFromRes(getApplicationContext(), R.string.upload_picture_tagline))
                            .setPlaceHolderIcon(R.drawable.em_no_picture));
                    isFirstTime = true;
                }

                pictureUploaderAdapter.notifyDataSetChanged();
            }
        };
        recyclerViewPicture.setAdapter(pictureUploaderAdapter);

        imageAdd.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        imageDone.setOnClickListener(this);
    }


    /**
     * <p>It trigger dialog for picture selection </p>
     *
     * @param context
     */
    private void onPictureSelector(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.custom_dialog_layout);

        LinearLayout layout_camera = (LinearLayout) dialog.findViewById(R.id.layout_camera);
        layout_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectCamera();
                dialog.dismiss();
            }
        });

        LinearLayout layout_gallery = (LinearLayout) dialog.findViewById(R.id.layout_gallery);
        layout_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSelectGallery();
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    /**
     * <P>It is used to initialize Camera for picture capture</P>
     */
    private void onSelectCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, Constant.RequestCode.REQUEST_CODE_CAMERA);//zero can be replaced with any action code
    }


    /**
     * <p>It is used to open Gallery for picture selection</p>
     */
    private void onSelectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, Constant.RequestCode.REQUEST_CODE_GALLERY);//one can be replaced with any action code
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //selectedBitmap = getResizeBitmap(photo);
            base64 = Utility.base64Converter(new Base64Object(true, false, photo));


            if (isFirstTime) {
                objectArrayList.clear();
                isFirstTime = false;
            }

            objectArrayList.add(new PictureObject().setPicture(base64));
            pictureUploaderAdapter.notifyDataSetChanged();
        }

        if (requestCode == Constant.RequestCode.REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();

            if (isFirstTime) {
                objectArrayList.clear();
                isFirstTime = false;
            }
            try {

                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                base64 = Utility.base64Converter(new Base64Object(true, false, selectedBitmap));
                objectArrayList.add(new PictureObject().setPicture(base64));

            } catch (IOException e) {
                e.printStackTrace();
            }

            pictureUploaderAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public void onClick(View v) {
        if (v == imageAdd) {
            onPictureSelector(this);
        }
        if (v == imageBack) {
            setResult(RESULT_CANCELED);
            finish();
        }
        if (v == imageDone) {

            if (objectArrayList.size() == 1) {

                if (objectArrayList.get(0) instanceof EmptyObject) {

                    Constant.globalDataObject = null;
                    setResult(RESULT_OK);
                    finish();
                    return;
                }

            }

            Constant.globalDataObject = new GlobalDataObject()
                    .setObjectArrayList(objectArrayList);

            setResult(RESULT_OK);
            finish();
        }
    }

}
