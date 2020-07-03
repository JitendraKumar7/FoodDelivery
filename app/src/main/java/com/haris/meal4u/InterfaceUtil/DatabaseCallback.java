package com.haris.meal4u.InterfaceUtil;

import android.net.Uri;

import com.haris.meal4u.ObjectUtil.RequestObject;

public interface DatabaseCallback {

    void onSuccess(Uri data, RequestObject requestObject);

    void onError(String data, RequestObject requestObject);

}
