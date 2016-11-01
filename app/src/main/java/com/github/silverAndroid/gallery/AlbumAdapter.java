package com.github.silverAndroid.gallery;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.github.silverAndroid.gallery.models.Album;
import com.github.silverAndroid.gallery.models.Photo;

import java.util.ArrayList;

/**
 * Created by silver_android on 26/10/16.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private ArrayList<Album> albums;
    private ArrayList<Photo> photos;
    private ArrayList<Integer> numPhotosList;
    private PipelineDraweeControllerBuilder controllerBuilder;
    private ItemClickListener<Integer> itemClickListener;

    public AlbumAdapter(Cursor cursor) {
        changeCursor(cursor, true);
        controllerBuilder = Fresco.newDraweeControllerBuilder();
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
        holder.albumName.setSelected(true);

        Photo photo = photos.get(position);
        controllerBuilder = controllerBuilder
                .setLowResImageRequest(ImageRequest.fromUri(photo.getThumbnailUrl()))
                .setImageRequest(ImageRequest.fromUri(photo.getUrl()))
                .setOldController(holder.previewImage.getController());
        holder.previewImage.setController(controllerBuilder.build());

        int numPhotos = numPhotosList.get(position);
        String numPhotosText = numPhotos + (numPhotos == 1 ? " photo" : " photos");
        holder.numPhotos.setText(numPhotosText);
    }

    public void changeCursor(Cursor cursor, boolean refresh) {
        if (refresh) {
            albums = new ArrayList<>();
            photos = new ArrayList<>();
            numPhotosList = new ArrayList<>();
        }
        int albumsAdded = 0;
        int previousSize = albums.size();
        if (cursor != null && cursor.moveToFirst()) {
            Album previousAlbum = null;
            Photo photo = null;
            int numPhotos = 0;
            do {
                Album album = new Album(cursor);
                if (photo == null)
                    photo = new Photo(cursor);
                // Have done this cause of weird bug where first album gets 51 photos and last album gets 49 albums
                if (previousAlbum != null && !album.equals(previousAlbum)) {
                    albums.add(previousAlbum);
                    albumsAdded++;
                    photos.add(photo);
                    photo = null;
                    numPhotosList.add(numPhotos);
                    numPhotos = 0;
                } else if (cursor.isLast()) {
                    albums.add(album);
                    albumsAdded++;
                    photos.add(photo);
                    photo = null;
                    numPhotosList.add(numPhotos);
                    numPhotos = 0;
                }
                previousAlbum = album;
                numPhotos++;
            } while (cursor.moveToNext());
        }

        notifyItemRangeInserted(previousSize, albumsAdded);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public void setOnItemClickListener(ItemClickListener<Integer> clickListener) {
        itemClickListener = clickListener;
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SimpleDraweeView previewImage;
        TextView albumName;
        TextView numPhotos;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            albumName = (TextView) itemView.findViewById(R.id.album_name);
            numPhotos = (TextView) itemView.findViewById(R.id.num_photos);
            previewImage = (SimpleDraweeView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (itemClickListener != null) {
                itemClickListener.onItemClick(albums.get(position).getId());
            }
        }
    }
}
