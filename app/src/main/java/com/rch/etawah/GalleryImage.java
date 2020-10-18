package com.rch.etawah;

import java.io.Serializable;

public class GalleryImage implements Serializable {

    private String gallery;

    private String[] photos;


    public String getGallery() {

        return gallery;
    }

    public String[] getPhotos() {

        return photos;
    }

}
