package com.github.silverAndroid.jsonplaceholder;

import com.github.silverAndroid.jsonplaceholder.models.Album;
import com.github.silverAndroid.jsonplaceholder.models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by silver_android on 26/10/16.
 */

public class NetworkService {

    private static final String baseURL = "http://jsonplaceholder.typicode.com";
    private final NetworkAPI api;

    public NetworkService() {
        this(baseURL);
    }

    public NetworkService(String baseURL) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(NetworkAPI.class);
    }

    public NetworkAPI getAPI() {
        return api;
    }

    public interface NetworkAPI {
        @GET("/albums")
        Call<List<Album>> getAlbums();
        @GET("/photos")
        Call<List<Photo>> getPhotos();
    }
}
