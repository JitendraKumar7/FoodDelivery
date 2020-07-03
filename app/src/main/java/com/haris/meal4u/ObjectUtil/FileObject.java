package com.haris.meal4u.ObjectUtil;

public class FileObject {
    private String fileUrl;
    private String fileName;


    public String getFileName() {
        return fileName;
    }

    public FileObject setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public FileObject setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }
}
