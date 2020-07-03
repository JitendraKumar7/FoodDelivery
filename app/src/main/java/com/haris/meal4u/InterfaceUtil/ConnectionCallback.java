package com.haris.meal4u.InterfaceUtil;

import com.haris.meal4u.ObjectUtil.RequestObject;

public interface ConnectionCallback {

    void onSuccess(Object data, RequestObject requestObject);

    void onError(String data, RequestObject requestObject);


}
