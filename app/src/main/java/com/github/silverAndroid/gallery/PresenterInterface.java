package com.github.silverAndroid.gallery;

import com.github.silverAndroid.gallery.models.Album;
import com.github.silverAndroid.gallery.models.Photo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

/**
 * Created by silver_android on 26/10/16.
 */

public interface PresenterInterface {

    void loadAlbumsFromNetwork(Callback<List<Album>> callback);

    void loadPhotosFromNetwork(Callback<List<Photo>> callback);
}
