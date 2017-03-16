package by.viachaslau.kukhto.lastfmclient.View.AlbumActivity.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.View.AlbumActivity.AlbumActivity;
import by.viachaslau.kukhto.lastfmclient.View.ArtistActivity.ArtistActivity;
import by.viachaslau.kukhto.lastfmclient.View.TrackActivity.TrackActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by VKukh on 04.03.2017.
 */

public class FullFragmentAlbum extends Fragment {

    private static final String FULL_ALBUM_FRAGMENT_INFORMATION = "fullalbumFragmentInformation";

    public static final String TAG = FullFragmentAlbum.class.getSimpleName();

    private RecyclerView recyclerView;
    SectionedRecyclerViewAdapter sectionAdapter;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public static FullFragmentAlbum newInstance(Album album) {
        FullFragmentAlbum fragmentArtist = new FullFragmentAlbum();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FULL_ALBUM_FRAGMENT_INFORMATION, album);
        fragmentArtist.setArguments(bundle);
        return fragmentArtist;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_album_full, container, false);
            initViews(view);
            Album album = (Album) getArguments().getSerializable(FULL_ALBUM_FRAGMENT_INFORMATION);
            sectionAdapter = new SectionedRecyclerViewAdapter();
            AlbumsTracksSection albumsTracksSection = new AlbumsTracksSection(R.layout.section_header, R.layout.section_tracks, R.layout.section_load_empty, R.layout.section_fail_empty);
            List<Track> tracks = new ArrayList<>(album.getTracks());
            albumsTracksSection.setAlbumsTracks(tracks, album.getImageURL(ImageSize.EXTRALARGE));
            sectionAdapter.addSection(albumsTracksSection);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(sectionAdapter);
        }
        return view;
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }

    class AlbumsTracksSection extends Section {

        List<Track> albumsTracks;
        private ImageLoader imageLoader;
        private final static String TITLE = "Albums Tracks";
        private String imgUrl;

        public void setAlbumsTracks(List<Track> albumsTracks, String imgUrl) {
            this.albumsTracks = albumsTracks;
            this.imgUrl = imgUrl;
        }


        public AlbumsTracksSection(int headerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getContentItemsTotal() {
            return albumsTracks.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            imageLoader.displayImage(imgUrl, itemHolder.imgAlbums);
            itemHolder.artistName.setText(albumsTracks.get(position).getArtist());
            itemHolder.songTitle.setText(albumsTracks.get(position).getName());
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), TrackActivity.class);
                    intent.putExtra(TrackActivity.TRACK_URL, albumsTracks.get(position).getUrl());
                    startActivity(intent);
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.tvTitle.setText(TITLE);
        }

    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgAlbums;
        private final TextView artistName;
        private final TextView songTitle;
        private final LinearLayout cell;

        public ItemViewHolder(View view) {
            super(view);
            cell = (LinearLayout) view.findViewById(R.id.section_track);
            imgAlbums = (ImageView) view.findViewById(R.id.albums_photo);
            artistName = (TextView) view.findViewById(R.id.artist_name);
            songTitle = (TextView) view.findViewById(R.id.song_title);
        }
    }


}
