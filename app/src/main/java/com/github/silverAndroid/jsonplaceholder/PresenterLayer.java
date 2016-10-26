package com.github.silverAndroid.jsonplaceholder;

import com.github.silverAndroid.jsonplaceholder.models.Album;
import com.github.silverAndroid.jsonplaceholder.models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by silver_android on 26/10/16.
 */

public class PresenterLayer implements PresenterInterface {

    private final NetworkService networkService;

    public PresenterLayer(NetworkService service) {
        networkService = service;
    }

    @Override
    public void loadAlbums(Callback<List<Album>> callback) {
        Call<List<Album>> call = networkService.getAPI().getAlbums();
        call.enqueue(callback);
    }

    @Override
    public void loadPictures(Callback<List<Photo>> callback) {
        Call<List<Photo>> call = networkService.getAPI().getPhotos();
        call.enqueue(callback);
    }
}
