package com.haris.meal4u.ObjectUtil;

public class BubbleObject {
    private String title;
    private String id;
    private int startColor;
    private int endColor;
    private int drawable;


    public String getId() {
        return id;
    }

    public BubbleObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BubbleObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getStartColor() {
        return startColor;
    }

    public BubbleObject setStartColor(int startColor) {
        this.startColor = startColor;
        return this;
    }

    public int getEndColor() {
        return endColor;
    }

    public BubbleObject setEndColor(int endColor) {
        this.endColor = endColor;
        return this;
    }

    public int getDrawable() {
        return drawable;
    }

    public BubbleObject setDrawable(int drawable) {
        this.drawable = drawable;
        return this;
    }
}
