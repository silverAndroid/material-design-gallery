package com.github.silverAndroid.gallery;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.silverAndroid.gallery.models.Album;
import com.github.silverAndroid.gallery.models.Photo;

import java.util.ArrayList;

/**
 * Created by silver_android on 26/10/16.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private ArrayList<Album> albums;
    private ArrayList<ArrayList<Photo>> photos;

    public AlbumAdapter(Cursor cursor) {
        changeCursor(cursor);
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Album album = albums.get(position);

        holder.albumName.setText(album.getTitle());
        holder.numPhotos.setText(photos.get(position).size());
    }

    public void changeCursor(Cursor cursor) {
        albums = new ArrayList<>();
        photos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            Album previousAlbum = null;
            ArrayList<Photo> albumPhotos = new ArrayList<>();
            while (cursor.moveToNext()) {
                Album album = new Album(cursor);
                Photo photo = new Photo(cursor);
                albumPhotos.add(photo);
                if (previousAlbum != null && !album.equals(previousAlbum)) {
                    albums.add(album);
                    photos.add(albumPhotos);
                    albumPhotos = new ArrayList<>();
                }
                previousAlbum = album;
            }
        }
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {

        TextView albumName;
        TextView numPhotos;
        RecyclerView photos;

        public AlbumViewHolder(View itemView) {
            super(itemView);
        }
    }
}
