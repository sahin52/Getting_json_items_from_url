package com.sahins.itemsproject;

import android.graphics.Bitmap;

public class item {
    private String name;
    private Bitmap photo;

    public item(String name,Bitmap photo) {
        this.name = name;
        this.photo = photo;

    }

    public Bitmap getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

}
