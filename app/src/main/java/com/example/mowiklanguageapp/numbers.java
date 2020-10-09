package com.example.mowiklanguageapp;

public class numbers {
    private String movikLanguage;
    private String englishLanguage;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int imageResource = NO_IMAGE_PROVIDED;
    private int media;

    public numbers(String movikLanguage, String englishLanguage, int imageResource, int media) {
        this.movikLanguage = movikLanguage;
        this.englishLanguage = englishLanguage;
        this.imageResource = imageResource;
        this.media = media;


    }

    public numbers(String movikLanguage, String englishLanguage, int media) {
        this.movikLanguage = movikLanguage;
        this.englishLanguage = englishLanguage;
        this.media = media;
    }

    public String getMovikLanguage() {
        return movikLanguage;
    }

    public String getEnglishLanguage() {
        return englishLanguage;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean hasImage() {
        if (imageResource == -1)
            return false;
        else
            return true;
    }

    public int getMedia() {
        return media;
    }

    @Override
    public String toString() {
        return "numbers{" +
                "movikLanguage='" + movikLanguage + '\'' +
                ", englishLanguage='" + englishLanguage + '\'' +
                ", imageResource=" + imageResource +
                ", media=" + media +
                '}';
    }
}

