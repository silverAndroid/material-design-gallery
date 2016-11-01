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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.silverAndroid.gallery.lib.recyclerview.EndlessRecyclerViewScrollListener;
import com.github.silverAndroid.gallery.models.Album;
import com.github.silverAndroid.gallery.models.Photo;
import com.github.silverAndroid.gallery.schematic.AlbumColumns;
import com.github.silverAndroid.gallery.schematic.GalleryDB;
import com.github.silverAndroid.gallery.schematic.GalleryDBProvider;
import com.github.silverAndroid.gallery.schematic.PhotoColumns;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, ItemClickListener<Integer> {

    private static final String TAG = "AlbumsFragment";
    private static AlbumSelectedListener albumSelectedListener;
    private RecyclerView recyclerView;
    private AlbumAdapter adapter;
    private boolean refresh;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    public static AlbumsFragment newInstance(AlbumSelectedListener selectedListener) {
        AlbumsFragment.albumSelectedListener = selectedListener;
        return new AlbumsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
//        GridLayoutManager layoutManager;
        recyclerView.setLayoutManager(/*layoutManager = */new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }

        /*EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                refresh = false;
                Log.i(TAG, "onLoadMore: Page: " + page);

                Bundle bundle = new Bundle();
                bundle.putInt("page", page);
                getLoaderManager().restartLoader(10, bundle, AlbumsFragment.this);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        refresh = true;*/
        getLoaderManager().initLoader(10, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /*final int limit = 500;
        String limitOffset = String.format("LIMIT %d OFFSET %d", limit, !refresh ? (limit * args.getInt("page")) : 0);
        Log.i(TAG, "onCreateLoader: " + limitOffset);*/
        Log.d(TAG, "onCreateLoader: New CursorLoader");
        refresh = false;
        return new CursorLoader(
                getContext(),
                GalleryDBProvider.Album.CONTENT_URI,
                new String[]{
                        Util.getColumn(GalleryDB.Tables.ALBUM, AlbumColumns.id, Album.Alias.id),
                        AlbumColumns.userID,
                        Util.getColumn(GalleryDB.Tables.ALBUM, AlbumColumns.title, Album.Alias.title),
                        PhotoColumns.albumID,
                        Util.getColumn(GalleryDB.Tables.PHOTO, PhotoColumns.id, Photo.Alias.id),
                        Util.getColumn(GalleryDB.Tables.PHOTO, PhotoColumns.title, Photo.Alias.title),
                        PhotoColumns.url,
                        PhotoColumns.thumbnailURL
                }, null, null, /*PhotoColumns.albumID + " " + limitOffset*/ null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        Log.i(TAG, "onLoadFinished: refresh = " + String.valueOf(refresh));
        Log.d(TAG, "onLoadFinished: New data");
        if (adapter == null) {
            recyclerView.setAdapter(adapter = new AlbumAdapter(data));
            adapter.setOnItemClickListener(this);
        } else {
            adapter.changeCursor(data, refresh);
            refresh = true;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: Reset data");
        if (adapter != null) {
            adapter.changeCursor(null, true);
        }
    }

    @Override
    public void onItemClick(Integer albumID) {
        albumSelectedListener.albumSelected(albumID);
    }

    interface AlbumSelectedListener {
        void albumSelected(int albumID);
    }
}
