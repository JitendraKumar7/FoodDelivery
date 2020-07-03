package com.haris.meal4u.ObjectUtil;

import android.support.v4.app.Fragment;

public class PagerObject {
    private String title;
    private Fragment fragment;


    public String getTitle() {
        return title;
    }

    public PagerObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public PagerObject setFragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }
}
