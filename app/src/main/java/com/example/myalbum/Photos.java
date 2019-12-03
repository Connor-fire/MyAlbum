package com.example.myalbum;

public class Photos {
    private String name;
    private int imageId;
    public Photos(String name,int imageId){
        this.name=name;
        this.imageId=imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
