package com.github.silverAndroid.jsonplaceholder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.silverAndroid.jsonplaceholder.models.Album;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {

    private static PresenterLayer presenterLayer;

    public AlbumFragment() {
        // Required empty public constructor
    }

    public static AlbumFragment newInstance(PresenterLayer presenterLayer) {
        AlbumFragment.presenterLayer = presenterLayer;
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        presenterLayer.loadAlbums(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albums = new ArrayList<>(response.body());
                recyclerView.setAdapter(new AlbumAdapter(albums));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.e("AlbumFragment", "onFailure: Failed to load albums", t);
            }
        });
        return view;
    }

}
