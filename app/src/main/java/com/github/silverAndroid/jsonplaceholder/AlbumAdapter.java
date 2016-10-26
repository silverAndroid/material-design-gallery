package com.github.silverAndroid.jsonplaceholder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.github.silverAndroid.jsonplaceholder.models.Album;

import java.util.ArrayList;

/**
 * Created by silver_android on 26/10/16.
 */

public class AlbumAdapter extends RecyclerView.Adapter {

    private ArrayList<Album> albums;

    public AlbumAdapter(ArrayList<Album> albums) {
        this.albums = albums;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
