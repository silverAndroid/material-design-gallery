package com.github.silverAndroid.gallery;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.github.silverAndroid.gallery.models.Album;
import com.github.silverAndroid.gallery.models.Photo;
import com.github.silverAndroid.gallery.schematic.GalleryDBProvider;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private PresenterLayer presenterLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkService networkService = ((MyApplication) getApplication()).getNetworkService();
        presenterLayer = new PresenterLayer(networkService);

        Hawk.init(getBaseContext()).build();
        boolean setupCompleted = Hawk.get("setupCompleted", false);
        final boolean[] loadedAlbums = {false}, loadedPhotos = {false}, failedAlbums = {false}, failedPhotos = {false};
        if (setupCompleted) {

        } else {
            presenterLayer.loadAlbumsFromNetwork(new Callback<List<Album>>() {
                @Override
                public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                    ContentValues[] values = new ContentValues[response.body().size()];
                    List<Album> body = response.body();
                    for (int i = 0, bodySize = body.size(); i < bodySize; i++) {
                        Album album = body.get(i);
                        values[i] = album.convert();
                    }
                    getContentResolver().bulkInsert(GalleryDBProvider.Album.CONTENT_URI, values);

                    loadedAlbums[0] = true;

                    if (loadedPhotos[0]) {
                        goToNextActivity(failedAlbums[0], failedPhotos[0]);
                    }
                }

                @Override
                public void onFailure(Call<List<Album>> call, Throwable t) {
                    failedAlbums[0] = true;

                    if (loadedPhotos[0]) {
                        goToNextActivity(failedAlbums[0], failedPhotos[0]);
                    }
                }
            });

            presenterLayer.loadPhotosFromNetwork(new Callback<List<Photo>>() {
                @Override
                public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                    ContentValues[] values = new ContentValues[response.body().size()];
                    List<Photo> body = response.body();
                    for (int i = 0, bodySize = body.size(); i < bodySize; i++) {
                        Photo photo = body.get(i);
                        values[i] = photo.convert();
                    }
                    getContentResolver().bulkInsert(GalleryDBProvider.Photo.CONTENT_URI, values);

                    loadedPhotos[0] = true;

                    if (loadedAlbums[0]) {
                        goToNextActivity(failedAlbums[0], failedPhotos[0]);
                    }
                }

                @Override
                public void onFailure(Call<List<Photo>> call, Throwable t) {
                    failedPhotos[0] = true;

                    if (loadedAlbums[0]) {
                        goToNextActivity(failedAlbums[0], failedPhotos[0]);
                    }
                }
            });
        }

        Intent intent = getIntent();
        boolean failedAlbums = intent.getBooleanExtra("albumsFailed", false);
        boolean failedPhotos = intent.getBooleanExtra("photosFailed", false);

        if (failedAlbums || failedPhotos) {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Unable to retrieve images. Try again later.")
                    .show();
        } else {
            showAlbumFragment();
        }
    }

    private void showAlbumFragment() {
        showFragment(AlbumFragment.newInstance(presenterLayer), false);
    }

    private void showFragment(Fragment fragment, boolean addToStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.container, fragment);
        if (addToStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}
