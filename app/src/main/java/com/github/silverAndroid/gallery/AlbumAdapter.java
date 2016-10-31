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
    private ArrayList<ArrayList<Photo>> photos;
    private PipelineDraweeControllerBuilder controllerBuilder;
    private ItemClickListener<Integer> itemClickListener;

    public AlbumAdapter(Cursor cursor) {
        changeCursor(cursor);
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

        Photo photo = photos.get(position).get(0);
        controllerBuilder = controllerBuilder
                .setLowResImageRequest(ImageRequest.fromUri(photo.getThumbnailUrl()))
                .setImageRequest(ImageRequest.fromUri(photo.getUrl()))
                .setOldController(holder.previewImage.getController());
        holder.previewImage.setController(controllerBuilder.build());

        int numPhotos = photos.get(position).size();
        String numPhotosText = numPhotos + (numPhotos == 1 ? " photo" : " photos");
        holder.numPhotos.setText(numPhotosText);
    }

    public void addCursor(Cursor cursor) {

    }

    public void changeCursor(Cursor cursor) {
        albums = new ArrayList<>();
        photos = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            Album previousAlbum = null;
            ArrayList<Photo> albumPhotos = new ArrayList<>();
            do {
                Album album = new Album(cursor);
                Photo photo = new Photo(cursor);
                albumPhotos.add(photo);
                if (previousAlbum != null && !album.equals(previousAlbum)) {
                    albums.add(album);
                    photos.add(albumPhotos);
                    albumPhotos = new ArrayList<>();
                }
                previousAlbum = album;
            } while (cursor.moveToNext());
        }
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
