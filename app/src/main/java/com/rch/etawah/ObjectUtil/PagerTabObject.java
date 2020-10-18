package com.rch.etawah.ObjectUtil;


import android.support.v4.app.Fragment;

public class PagerTabObject {
    private String id;
    private String title;
    private Fragment fragment;

    public String getId() {
        return id;
    }

    public PagerTabObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PagerTabObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public PagerTabObject setFragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }
}
