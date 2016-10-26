package com.github.silverAndroid.jsonplaceholder.models;

/**
 * Created by silver_android on 26/10/16.
 */

public class Photo {

    private final int albumID;
    private final int id;
    private final String title;
    private final String url;
    private final String thumbnailURL;

    public Photo(int albumID, int id, String title, String url, String thumbnailURL) {
        this.albumID = albumID;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailURL = thumbnailURL;
    }

    public int getAlbumID() {
        return albumID;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
