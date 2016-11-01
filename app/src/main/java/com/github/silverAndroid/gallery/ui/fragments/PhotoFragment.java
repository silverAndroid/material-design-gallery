package com.github.silverAndroid.gallery.ui.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.github.silverAndroid.gallery.R;
import com.github.silverAndroid.gallery.Util;
import com.github.silverAndroid.gallery.lib.facebook.zoomable.ZoomableDraweeView;
import com.github.silverAndroid.gallery.models.Photo;
import com.github.silverAndroid.gallery.schematic.GalleryDB;
import com.github.silverAndroid.gallery.schematic.GalleryDBProvider;
import com.github.silverAndroid.gallery.schematic.PhotoColumns;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static int photoID;
    private ZoomableDraweeView image;

    public PhotoFragment() {
        // Required empty public constructor
    }

    public static PhotoFragment newInstance(int photoID) {
        PhotoFragment.photoID = photoID;
        return new PhotoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image = (ZoomableDraweeView) view.findViewById(R.id.image);
        getLoaderManager().initLoader(photoID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                GalleryDBProvider.Photo.withId(id),
                new String[]{
                        PhotoColumns.albumID,
                        Util.getColumn(GalleryDB.Tables.PHOTO, PhotoColumns.id, Photo.Alias.id),
                        Util.getColumn(GalleryDB.Tables.PHOTO, PhotoColumns.title, Photo.Alias.title),
                        PhotoColumns.url,
                        PhotoColumns.thumbnailURL
                },
                null, null, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            Photo photo = new Photo(data);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setLowResImageRequest(ImageRequest.fromUri(photo.getThumbnailUrl()))
                    .setImageRequest(ImageRequest.fromUri(photo.getUrl()))
                    .setOldController(image.getController())
                    .build();
            image.setController(controller);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        image.setController(null);
    }
}
