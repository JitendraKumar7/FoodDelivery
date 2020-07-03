package com.haris.meal4u.ObjectUtil;

import java.util.ArrayList;

public class GlobalDataObject {

    public static RequestObject requestObject;
    private ArrayList<Object> objectArrayList = new ArrayList<>();

    public ArrayList<Object> getObjectArrayList() {
        return objectArrayList;
    }

    public GlobalDataObject setObjectArrayList(ArrayList<Object> objectArrayList) {
        this.objectArrayList = objectArrayList;
        return this;
    }

    public static RequestObject getRequestObject() {
        return requestObject;
    }

    public static void setRequestObject(RequestObject requestObject) {
        GlobalDataObject.requestObject = requestObject;
    }
}
