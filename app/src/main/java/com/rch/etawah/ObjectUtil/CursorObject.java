package com.rch.etawah.ObjectUtil;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CursorObject {
    private Cursor cursor;
    private int insertionId;
    private SQLiteDatabase database;


    public int getInsertionId() {
        return insertionId;
    }

    public CursorObject setInsertionId(int insertionId) {
        this.insertionId = insertionId;
        return this;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public CursorObject setCursor(Cursor cursor) {
        this.cursor = cursor;
        return this;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public CursorObject setDatabase(SQLiteDatabase database) {
        this.database = database;
        return this;
    }



}
