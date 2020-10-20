package com.rch.etawah;

import java.io.Serializable;

public class GalleryImage implements Serializable {

    private String gallery;

    private String[] photos;

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public String getGallery() {

        return gallery;
    }

    public String[] getPhotos() {

        return photos;
    }

}
