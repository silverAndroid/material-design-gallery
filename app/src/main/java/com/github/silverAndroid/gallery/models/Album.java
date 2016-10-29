package com.github.silverAndroid.gallery.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.silverAndroid.gallery.schematic.AlbumColumns;

/**
 * Created by silver_android on 26/10/16.
 */

public class Album {

    private final int userID;
    private final int id;
    private final String title;

    public Album(int userID, int id, String title) {
        this.userID = userID;
        this.id = id;
        this.title = title;
    }

    public Album(Cursor cursor) {
        userID = cursor.getInt(cursor.getColumnIndexOrThrow(AlbumColumns.userID));
        id = cursor.getInt(cursor.getColumnIndexOrThrow(AlbumColumns.id));
        title = cursor.getString(cursor.getColumnIndexOrThrow(AlbumColumns.title));
    }

    public int getUserID() {
        return userID;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ContentValues convert() {
        ContentValues values = new ContentValues(3);
        values.put(AlbumColumns.id, id);
        values.put(AlbumColumns.userID, userID);
        values.put(AlbumColumns.title, title);
        return values;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof Album) {
            Album album = (Album) obj;
            return userID == album.userID && id == album.id && title.equals(album.title);
        }
        return false;
    }
}
