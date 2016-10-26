package com.github.silverAndroid.jsonplaceholder.models;

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

    public int getUserID() {
        return userID;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
