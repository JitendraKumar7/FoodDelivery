package com.rch.etawah.ObjectUtil;

import java.util.ArrayList;

public class GlobalObject {
    private int position;
    private String playlistId;
    private String dataType = "null";
    private ArrayList<Object> objectArrayList = new ArrayList<>();


    public ArrayList<Object> getObjectArrayList() {
        return objectArrayList;
    }

    public GlobalObject setObjectArrayList(ArrayList<Object> objectArrayList) {
        this.objectArrayList.addAll(objectArrayList);
        return this;
    }

    public int getPosition() {
        return position;
    }

    public GlobalObject setPosition(int position) {
        this.position = position;
        return this;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public GlobalObject setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public GlobalObject setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }
}
