package by.viachaslau.kukhto.lastfmclient.View.ListActivity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.View.ListActivity.Adapters.FullListAlbumSection;
import by.viachaslau.kukhto.lastfmclient.View.ListActivity.Adapters.FullListArtistSection;
import by.viachaslau.kukhto.lastfmclient.View.ListActivity.Adapters.FullListTrackSection;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by VKukh on 06.03.2017.
 */

public class ListFragmentFull extends Fragment {

    public static final String ARTIST_TYPE = "artistType";
    public static final String ALBUM_TYPE = "albumType";
    public static final String TRACK_TYPE = "trackType";


    public static final String TAG = ListFragmentFull.class.getSimpleName();

    private static final String TITLE = "title";
    private static final String LIST = "list";
    private static final String LIST_TYPE = "listType";

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private SectionedRecyclerViewAdapter adapter;

    private View view;


    public static ListFragmentFull newTrackInstance(ArrayList<Track> list, String title, String listType) {
        ListFragmentFull listFragmentFull = new ListFragmentFull();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST, list);
        bundle.putString(TITLE, title);
        bundle.putString(LIST_TYPE, listType);
        listFragmentFull.setArguments(bundle);
        return listFragmentFull;
    }

    public static ListFragmentFull newArtistInstance(ArrayList<Artist> list, String title, String listType) {
        ListFragmentFull listFragmentFull = new ListFragmentFull();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST, list);
        bundle.putString(TITLE, title);
        bundle.putString(LIST_TYPE, listType);
        listFragmentFull.setArguments(bundle);
        return listFragmentFull;
    }

    public static ListFragmentFull newAlbumInstance(ArrayList<Album> list, String title, String listType) {
        ListFragmentFull listFragmentFull = new ListFragmentFull();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST, list);
        bundle.putString(TITLE, title);
        bundle.putString(LIST_TYPE, listType);
        listFragmentFull.setArguments(bundle);
        return listFragmentFull;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_list_full, container, false);
            initViews(view);
            adapter = new SectionedRecyclerViewAdapter();
            String listType = getArguments().getString(LIST_TYPE);
            if (listType.equals(ARTIST_TYPE)) {
                FullListArtistSection section = new FullListArtistSection(getContext(), R.layout.section_header, R.layout.section_footer, R.layout.section_artists, R.layout.section_load_empty, R.layout.section_fail_empty);
                String title = getArguments().getString(TITLE);
                List<Artist> list = (List<Artist>) getArguments().getSerializable(LIST);
                section.setArtistList(list);
                section.setTitle(title);
                adapter.addSection(section);
            } else if (listType.equals(ALBUM_TYPE)) {
                FullListAlbumSection section = new FullListAlbumSection(getContext(), R.layout.section_header, R.layout.section_footer, R.layout.section_albums, R.layout.section_load_empty, R.layout.section_fail_empty);
                String title = getArguments().getString(TITLE);
                List<Album> list = (List<Album>) getArguments().getSerializable(LIST);
                section.setAlbumList(list);
                section.setTitle(title);
                adapter.addSection(section);
            } else if (listType.equals(TRACK_TYPE)) {
                FullListTrackSection section = new FullListTrackSection(getContext(), R.layout.section_header, R.layout.section_footer, R.layout.section_tracks, R.layout.section_load_empty, R.layout.section_fail_empty);
                String title = getArguments().getString(TITLE);
                List<Track> list = (List<Track>) getArguments().getSerializable(LIST);
                section.setTrackList(list);
                section.setTitle(title);
                adapter.addSection(section);
            }
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }
}
