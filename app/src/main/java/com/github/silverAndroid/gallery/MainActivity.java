package com.github.silverAndroid.gallery;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.silverAndroid.gallery.models.Album;
import com.github.silverAndroid.gallery.models.Photo;
import com.github.silverAndroid.gallery.schematic.GalleryDBProvider;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AlbumsFragment.AlbumSelectedListener, AlbumFragment.PhotoSelectedListener,
        FragmentManager.OnBackStackChangedListener {

    private static final String TAG = "MainActivity";
    private PresenterLayer presenterLayer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkService networkService = ((MyApplication) getApplication()).getNetworkService();
        presenterLayer = new PresenterLayer(networkService);

        Hawk.init(getBaseContext()).build();
        boolean setupCompleted = Hawk.get("setupCompleted", false);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        if (setupCompleted) {
            loadFragment(false, false);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            loadFromNetwork();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    private void loadFragment(boolean failedAlbums, boolean failedPhotos) {
        Log.i(TAG, "loadFragment: failedAlbums = " + failedAlbums + ", failedPhotos = " + failedPhotos);
        if (failedAlbums || failedPhotos) {
            Toast.makeText(getBaseContext(), "Unable to retrieve images. Try again later.", Toast.LENGTH_LONG).show();
        } else {
            Hawk.put("setupCompleted", true);
            showAlbumsFragment();
        }
        progressBar.setVisibility(View.GONE);
    }

    private void showAlbumsFragment() {
        showFragment(AlbumsFragment.newInstance(this), false);
    }

    private void showAlbumFragment(int albumID) {
        showFragment(AlbumFragment.newInstance(albumID, this), true);
    }

    private void showPhotoFragment(int photoID) {
        showFragment(PhotoFragment.newInstance(photoID), true);
    }

    private void showFragment(Fragment fragment, boolean addToStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.container, fragment);
        if (addToStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void albumSelected(int albumID) {
        showAlbumFragment(albumID);
    }

    @Override
    public void photoSelected(int photoID) {
        showPhotoFragment(photoID);
    }

    @Override
    public void onBackStackChanged() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        getSupportActionBar().setDisplayHomeAsUpEnabled(backStackCount != 0);
    }

    private void loadFromNetwork() {

        final boolean[] loadedAlbums = {false}, loadedPhotos = {false}, failedAlbums = {false}, failedPhotos = {false};
        presenterLayer.loadAlbums(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                List<Album> body = response.body();
                int bodySize = body.size();
                ContentValues[] values = new ContentValues[bodySize];
                for (int i = 0; i < bodySize; i++) {
                    Album album = body.get(i);
                    values[i] = album.convert();
                }
                getContentResolver().bulkInsert(GalleryDBProvider.Album.CONTENT_URI, values);

                loadedAlbums[0] = true;
                if (loadedPhotos[0]) {
                    loadFragment(failedAlbums[0], failedPhotos[0]);
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                failedAlbums[0] = !call.isCanceled();
                loadedAlbums[0] = true;
                Log.e(TAG, "onFailure: Failed to load albums", t);

                if (loadedPhotos[0]) {
                    loadFragment(failedAlbums[0], failedPhotos[0]);
                }
            }
        });

        presenterLayer.loadPhotos(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                List<Photo> body = response.body();
                int bodySize = body.size();
                ContentValues[] values = new ContentValues[bodySize];
                for (int i = 0; i < bodySize; i++) {
                    Photo photo = body.get(i);
                    values[i] = photo.convert();
                }
                getContentResolver().bulkInsert(GalleryDBProvider.Photo.CONTENT_URI, values);

                loadedPhotos[0] = true;
                if (loadedAlbums[0]) {
                    loadFragment(failedAlbums[0], failedPhotos[0]);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                failedPhotos[0] = !call.isCanceled();
                loadedPhotos[0] = true;
                Log.e(TAG, "onFailure: Failed to load albums", t);

                if (loadedAlbums[0]) {
                    loadFragment(failedAlbums[0], failedPhotos[0]);
                }
            }
        });
    }
}
