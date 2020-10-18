package com.rch.etawah.ObjectUtil;

import com.rch.etawah.ConstantUtil.Constant;

import java.util.ArrayList;

public class HomeObject {
    private String title;
    private String label;
    private Constant.DATA_TYPE data_type;
    private boolean isLayout01=true;
    private ArrayList<DataObject> dataObjectArrayList = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public HomeObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public HomeObject setLabel(String label) {
        this.label = label;
        return this;
    }

    public Constant.DATA_TYPE getData_type() {
        return data_type;
    }

    public HomeObject setData_type(Constant.DATA_TYPE data_type) {
        this.data_type = data_type;
        return this;
    }

    public boolean isLayout01() {
        return isLayout01;
    }

    public HomeObject setLayout01(boolean layout01) {
        isLayout01 = layout01;
        return this;
    }

    public ArrayList<DataObject> getDataObjectArrayList() {
        return dataObjectArrayList;
    }

    public HomeObject setDataObjectArrayList(ArrayList<DataObject> dataObjectArrayList) {
        this.dataObjectArrayList = dataObjectArrayList;
        return this;
    }
}
