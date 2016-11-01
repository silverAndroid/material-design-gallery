package com.github.silverAndroid.gallery.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.silverAndroid.gallery.schematic.GalleryDB;
import com.github.silverAndroid.gallery.schematic.PhotoColumns;

/**
 * Created by silver_android on 26/10/16.
 */

public class Photo {

    private final int albumId;
    private final int id;
    private final String title;
    private final String url;
    private final String thumbnailUrl;

    public Photo(int albumId, int id, String title, String url, String thumbnailUrl) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static class Alias {
        public static final String id = "photo_id";
        public static final String title = "photo_title";
    }

    public Photo(Cursor cursor) {
        albumId = cursor.getInt(cursor.getColumnIndexOrThrow(PhotoColumns.albumID));
        id = cursor.getInt(cursor.getColumnIndexOrThrow(Alias.id));
        title = cursor.getString(cursor.getColumnIndexOrThrow(Alias.title));
        url = cursor.getString(cursor.getColumnIndexOrThrow(PhotoColumns.url));
        thumbnailUrl = cursor.getString(cursor.getColumnIndexOrThrow(PhotoColumns.thumbnailURL));
    }

    public int getAlbumId() {
        return albumId;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public ContentValues convert() {
        ContentValues values = new ContentValues(5);
        values.put(PhotoColumns.albumID, albumId);
        values.put(PhotoColumns.id, id);
        values.put(PhotoColumns.title, title);
        values.put(PhotoColumns.url, url);
        values.put(PhotoColumns.thumbnailURL, thumbnailUrl);
        return values;
    }
}
