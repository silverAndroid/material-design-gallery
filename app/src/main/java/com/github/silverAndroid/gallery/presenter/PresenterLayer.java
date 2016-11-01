package com.github.silverAndroid.gallery.presenter;

import com.github.silverAndroid.gallery.NetworkService;
import com.github.silverAndroid.gallery.models.Album;
import com.github.silverAndroid.gallery.models.Photo;

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
    public Call loadAlbums(Callback<List<Album>> callback) {
        Call<List<Album>> call = networkService.getAPI().getAlbums();
        call.enqueue(callback);
        return call;
    }

    @Override
    public Call loadPhotos(Callback<List<Photo>> callback) {
        Call<List<Photo>> call = networkService.getAPI().getPhotos();
        call.enqueue(callback);
        return call;
    }
}
