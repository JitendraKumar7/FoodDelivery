package com.rch.etawah.CustomUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.R;

public class PictureSelector {

    private Activity activity;
    private boolean isPicture;
    private boolean isGallery;


    public PictureSelector(Activity activity) {
        this.activity = activity;
    }

    /**
     * <p>It trigger dialog for picture selection </p>
     *
     * @param context
     */
    public void onPictureSelector(Context context) {
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
    public void onSelectCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(takePicture, Constant.RequestCode.REQUEST_CODE_CAMERA);//zero can be replaced with any action code
    }


    /**
     * <p>It is used to open Gallery for picture selection</p>
     */
    public void onSelectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        activity.startActivityForResult(intent, Constant.RequestCode.REQUEST_CODE_GALLERY);//one can be replaced with any action code
    }


}
