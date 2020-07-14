package com.example.Module;

public class UploadImage {
    private String NameFile;
    private String ImageUrl;

    public String getNameFile() {
        return NameFile;
    }

    public void setNameFile(String nameFile) {
        NameFile = nameFile;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public UploadImage() {
    }

    public UploadImage(String nameFile, String imageUrl) {
        NameFile = nameFile;
        ImageUrl = imageUrl;
    }
}
