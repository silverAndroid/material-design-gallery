package com.github.silverAndroid.gallery.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.silverAndroid.gallery.schematic.PhotoColumns;

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

    public Photo(Cursor cursor) {
        albumID = cursor.getInt(cursor.getColumnIndexOrThrow(PhotoColumns.albumID));
        id = cursor.getInt(cursor.getColumnIndexOrThrow(PhotoColumns.id));
        title = cursor.getString(cursor.getColumnIndexOrThrow(PhotoColumns.title));
        url = cursor.getString(cursor.getColumnIndexOrThrow(PhotoColumns.url));
        thumbnailURL = cursor.getString(cursor.getColumnIndexOrThrow(PhotoColumns.thumbnailURL));
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

    public ContentValues convert() {
        ContentValues values = new ContentValues(5);
        values.put(PhotoColumns.albumID, albumID);
        values.put(PhotoColumns.id, id);
        values.put(PhotoColumns.title, title);
        values.put(PhotoColumns.url, url);
        values.put(PhotoColumns.thumbnailURL, thumbnailURL);
        return values;
    }
}
