package com.haris.meal4u.ObjectUtil;

public class EmptyObject {
    private String title;
    private String description;
    private int placeHolderIcon = 0;
    private boolean isResizeable=false;
    private boolean isRequiredCompletePlaceHolder=false;


    public boolean isRequiredCompletePlaceHolder() {
        return isRequiredCompletePlaceHolder;
    }

    public EmptyObject setRequiredCompletePlaceHolder(boolean requiredCompletePlaceHolder) {
        isRequiredCompletePlaceHolder = requiredCompletePlaceHolder;
        return this;
    }

    public boolean isResizeable() {
        return isResizeable;
    }

    public EmptyObject setResizeable(boolean resizeable) {
        isResizeable = resizeable;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public EmptyObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EmptyObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getPlaceHolderIcon() {
        return placeHolderIcon;
    }

    public EmptyObject setPlaceHolderIcon(int placeHolderIcon) {
        this.placeHolderIcon = placeHolderIcon;
        return this;
    }
}
