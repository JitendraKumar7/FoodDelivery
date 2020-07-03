package com.haris.meal4u.ObjectUtil;

public class ProgressObject {
    private boolean isScrollLoading=false;

    public boolean isScrollLoading() {
        return isScrollLoading;
    }

    public ProgressObject setScrollLoading(boolean scrollLoading) {
        isScrollLoading = scrollLoading;
        return this;
    }
}
