package com.rch.etawah.InterfaceUtil;

import android.net.Uri;

import com.rch.etawah.ObjectUtil.RequestObject;

public interface DatabaseCallback {

    void onSuccess(Uri data, RequestObject requestObject);

    void onError(String data, RequestObject requestObject);

}
