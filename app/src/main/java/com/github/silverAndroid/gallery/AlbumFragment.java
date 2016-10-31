package com.github.silverAndroid.gallery;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.silverAndroid.gallery.models.Photo;
import com.github.silverAndroid.gallery.schematic.GalleryDB;
import com.github.silverAndroid.gallery.schematic.GalleryDBProvider;
import com.github.silverAndroid.gallery.schematic.PhotoColumns;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        ItemClickListener<Integer> {

    private static int albumID;
    private static PhotoSelectedListener photoSelectedListener;
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;

    public AlbumFragment() {
        // Required empty public constructor
    }

    public static AlbumFragment newInstance(int albumID, PhotoSelectedListener listener) {
        AlbumFragment.albumID = albumID;
        AlbumFragment.photoSelectedListener = listener;
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }

        getLoaderManager().initLoader(albumID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                GalleryDBProvider.Photo.withAlbumId(id),
                new String[]{
                        PhotoColumns.albumID,
                        Util.getColumn(GalleryDB.Tables.PHOTO, PhotoColumns.id, Photo.Alias.id),
                        PhotoColumns.thumbnailURL,
                        Util.getColumn(GalleryDB.Tables.PHOTO, PhotoColumns.title, Photo.Alias.title),
                        PhotoColumns.url
                },
                null, null, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (adapter == null) {
            recyclerView.setAdapter(adapter = new PhotoAdapter(data));
            adapter.setOnItemClickListener(this);
        } else {
            adapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (adapter != null) {
            adapter.changeCursor(null);
        }
    }

    @Override
    public void onItemClick(Integer photoID) {
        photoSelectedListener.photoSelected(photoID);
    }

    interface PhotoSelectedListener {
        void photoSelected(int photoID);
    }
}
