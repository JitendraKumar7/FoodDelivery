package com.rch.etawah.InterfaceUtil;

import com.rch.etawah.ObjectUtil.RequestObject;

public interface ConnectionCallback {

    void onSuccess(Object data, RequestObject requestObject);

    void onError(String data, RequestObject requestObject);


}
