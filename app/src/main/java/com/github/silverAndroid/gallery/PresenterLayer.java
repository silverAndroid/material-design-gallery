package com.github.silverAndroid.gallery;

import android.content.Context;

import com.github.silverAndroid.gallery.models.Album;
import com.github.silverAndroid.gallery.models.Photo;
import com.github.silverAndroid.gallery.schematic.AlbumColumns;
import com.github.silverAndroid.gallery.schematic.GalleryDBProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by silver_android on 26/10/16.
 */

public class PresenterLayer implements PresenterInterface {

    private final NetworkService networkService;

    public PresenterLayer(NetworkService service) {
        networkService = service;
    }

    @Override
    public void loadAlbumsFromNetwork(Callback<List<Album>> callback) {
        Call<List<Album>> call = networkService.getAPI().getAlbums();
        call.enqueue(callback);
    }

    @Override
    public void loadPhotosFromNetwork(Callback<List<Photo>> callback) {
        Call<List<Photo>> call = networkService.getAPI().getPhotos();
        call.enqueue(callback);
    }
}
