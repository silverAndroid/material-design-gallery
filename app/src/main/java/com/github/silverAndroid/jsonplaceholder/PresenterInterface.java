package com.github.silverAndroid.jsonplaceholder;

import com.github.silverAndroid.jsonplaceholder.models.Album;
import com.github.silverAndroid.jsonplaceholder.models.Photo;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by silver_android on 26/10/16.
 */

public interface PresenterInterface {

    void loadAlbums(Callback<List<Album>> callback);

    void loadPictures(Callback<List<Photo>> callback);
}
