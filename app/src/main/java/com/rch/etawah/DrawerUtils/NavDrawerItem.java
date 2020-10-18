package com.rch.etawah.DrawerUtils;

/**
 * Created by Haris on 2/4/2016.
 */
public class NavDrawerItem {

    private String title;
    private int icon;
    private String count = "0";
    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;

    public NavDrawerItem(){}

    public NavDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count){
        this.title = title;
        this.icon = icon;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public NavDrawerItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getIcon() {
        return icon;
    }

    public NavDrawerItem setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public String getCount() {
        return count;
    }

    public NavDrawerItem setCount(String count) {
        this.count = count;
        return this;
    }

    public boolean isCounterVisible() {
        return isCounterVisible;
    }

    public NavDrawerItem setCounterVisible(boolean counterVisible) {
        isCounterVisible = counterVisible;
        return this;
    }
}
