package com.haris.meal4u.DatabaseUtil;

import com.haris.meal4u.ConstantUtil.Constant;
import com.haris.meal4u.ObjectUtil.DataObject;


public class DatabaseObject {
    private Constant.DB dbOperation;
    private Constant.TYPE typeOperation;
    private DataObject dataObject;

    public Constant.DB getDbOperation() {
        return dbOperation;
    }

    public DatabaseObject setDbOperation(Constant.DB dbOperation) {
        this.dbOperation = dbOperation;
        return this;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public DatabaseObject setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
        return this;
    }

    public Constant.TYPE getTypeOperation() {
        return typeOperation;
    }

    public DatabaseObject setTypeOperation(Constant.TYPE typeOperation) {
        this.typeOperation = typeOperation;
        return this;
    }

    @Override
    public String toString() {
        return "DatabaseObject{" +
                "dbOperation=" + dbOperation +
                ", typeOperation=" + typeOperation +
                ", appInfoObject=" + dataObject +
                '}';
    }
}
