package com.haris.meal4u.ObjectUtil;

public class PdfContentObject {
    private String title;
    private String pageNo;

    public String getTitle() {
        return title;
    }

    public PdfContentObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPageNo() {
        return pageNo;
    }

    public PdfContentObject setPageNo(String pageNo) {
        this.pageNo = pageNo;
        return this;
    }
}
