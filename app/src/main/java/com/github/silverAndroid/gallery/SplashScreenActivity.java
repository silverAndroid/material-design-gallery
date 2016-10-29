package com.github.silverAndroid.gallery;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.silverAndroid.gallery.models.Album;
import com.github.silverAndroid.gallery.models.Photo;
import com.github.silverAndroid.gallery.schematic.GalleryDBProvider;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void goToNextActivity(boolean albumsLoadingFail, boolean photosLoadingFail) {
        Hawk.put("setupCompleted", true);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("albumsFailed", albumsLoadingFail);
        intent.putExtra("photosFailed", photosLoadingFail);
        startActivity(intent);
    }
}
