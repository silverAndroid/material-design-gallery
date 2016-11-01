package com.github.silverAndroid.gallery.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.github.silverAndroid.gallery.ItemClickListener;
import com.github.silverAndroid.gallery.R;
import com.github.silverAndroid.gallery.models.Photo;

import java.util.ArrayList;

/**
 * Created by silver_android on 27/10/16.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private ArrayList<Photo> photos;
    private PipelineDraweeControllerBuilder controllerBuilder;
    private ItemClickListener<Integer> itemClickListener;

    public PhotoAdapter(Cursor cursor) {
        changeCursor(cursor);
        controllerBuilder = Fresco.newDraweeControllerBuilder();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        controllerBuilder = controllerBuilder
                .setLowResImageRequest(ImageRequest.fromUri(photo.getThumbnailUrl()))
                .setImageRequest(ImageRequest.fromUri(photo.getUrl()))
                .setOldController(holder.image.getController());
        holder.image.setController(controllerBuilder.build());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void changeCursor(Cursor cursor) {
        photos = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Photo photo = new Photo(cursor);
                photos.add(photo);
            } while (cursor.moveToNext());
        }
    }

    public void setOnItemClickListener(ItemClickListener<Integer> listener) {
        this.itemClickListener = listener;
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SimpleDraweeView image;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (itemClickListener != null) {
                itemClickListener.onItemClick(photos.get(position).getId());
            }
        }
    }
}
