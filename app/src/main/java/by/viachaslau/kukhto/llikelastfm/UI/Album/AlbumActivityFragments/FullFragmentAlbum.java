package by.viachaslau.kukhto.llikelastfm.UI.Album.AlbumActivityFragments;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.llikelastfm.Others.Model.AppLog;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.llikelastfm.R;
import by.viachaslau.kukhto.llikelastfm.UI.Track.TrackActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by VKukh on 04.03.2017.
 */

public class FullFragmentAlbum extends Fragment {

    private static final String FULL_ALBUM_FRAGMENT_INFORMATION = "fullAlbumFragmentInformation";

    public static final String TAG = FullFragmentAlbum.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    SectionedRecyclerViewAdapter sectionAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setRetainInstance(true);
    }


    public static FullFragmentAlbum newInstance(Album album) {
        AppLog.log(TAG, "newInstance");
        FullFragmentAlbum fragmentArtist = new FullFragmentAlbum();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FULL_ALBUM_FRAGMENT_INFORMATION, album);
        fragmentArtist.setArguments(bundle);
        return fragmentArtist;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_full, container, false);
        ButterKnife.bind(this, view);
        Album album = (Album) getArguments().getSerializable(FULL_ALBUM_FRAGMENT_INFORMATION);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        AlbumsTracksSection albumsTracksSection = new AlbumsTracksSection(R.layout.section_header, R.layout.section_tracks, R.layout.section_load_empty, R.layout.section_fail_empty);
        List<Track> tracks = new ArrayList<>(album.getTracks());
        albumsTracksSection.setAlbumsTracks(tracks, album.getImageURL(ImageSize.EXTRALARGE));
        sectionAdapter.addSection(albumsTracksSection);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        return view;
    }


    class AlbumsTracksSection extends Section {

        private List<Track> albumsTracks;
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
                    AppLog.log(TAG, "onClick");
                    Intent intent = new Intent(getContext(), TrackActivity.class);
                    intent.putExtra(TrackActivity.TRACK, albumsTracks.get(position));
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
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.albums_photo)
        ImageView imgAlbums;
        @BindView(R.id.artist_name)
        TextView artistName;
        @BindView(R.id.song_title)
        TextView songTitle;
        @BindView(R.id.section_track)
        LinearLayout cell;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
