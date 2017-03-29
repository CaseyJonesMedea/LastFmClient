package by.viachaslau.kukhto.lastfmclient.UI.ArtistActivity.ArtistActivityFragments;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.LibraryFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.Album.AlbumActivity;
import by.viachaslau.kukhto.lastfmclient.UI.ArtistActivity.ArtistActivity;
import by.viachaslau.kukhto.lastfmclient.UI.ListActivity.ListActivity;
import by.viachaslau.kukhto.lastfmclient.UI.TrackActivity.TrackActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by kuhto on 03.03.2017.
 */

public class LibraryFragmentArtist extends Fragment {

    private static final String LIBRARY_FRAGMENT_INFORMATION = "libraryFragmentInformation";
    private static final String LIBRARY_FRAGMENT_NAME_ARTIST = "artist";

    public static final String TAG = LibraryFragmentArtist.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    SectionedRecyclerViewAdapter sectionAdapter;


    private String artist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AppLog.log(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public static LibraryFragmentArtist newInstance(LibraryFragmentInformation libraryFragmentInformation, String artist) {
        AppLog.log(TAG, "newInstance");
        LibraryFragmentArtist fragmentArtist = new LibraryFragmentArtist();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIBRARY_FRAGMENT_INFORMATION, libraryFragmentInformation);
        bundle.putString(LIBRARY_FRAGMENT_NAME_ARTIST, artist);
        fragmentArtist.setArguments(bundle);
        return fragmentArtist;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.log(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_artist_library, container, false);
        ButterKnife.bind(this, view);
        LibraryFragmentInformation libraryFragmentInformation = (LibraryFragmentInformation) getArguments().getSerializable(LIBRARY_FRAGMENT_INFORMATION);
        artist = getArguments().getString(LIBRARY_FRAGMENT_NAME_ARTIST);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        SimilarArtistsSection similarArtistsSection = new SimilarArtistsSection(R.layout.section_header, R.layout.section_footer, R.layout.section_artists, R.layout.section_load_empty, R.layout.section_fail_empty);
        similarArtistsSection.setArtist(artist);
        similarArtistsSection.setSimilarArtists(libraryFragmentInformation.getSimilarArtists());
        ArtistAlbumsSection artistAlbumsSection = new ArtistAlbumsSection(R.layout.section_header, R.layout.section_footer, R.layout.section_albums, R.layout.section_load_empty, R.layout.section_fail_empty);
        artistAlbumsSection.setArtist(artist);
        artistAlbumsSection.setArtistAlbums(libraryFragmentInformation.getAlbumsArtist());
        ArtistTracksSection artistTracksSection = new ArtistTracksSection(R.layout.section_header, R.layout.section_footer, R.layout.section_tracks, R.layout.section_load_empty, R.layout.section_fail_empty);
        artistTracksSection.setArtist(artist);
        artistTracksSection.setArtistTracks(libraryFragmentInformation.getTracksArtist());
        sectionAdapter.addSection(similarArtistsSection);
        sectionAdapter.addSection(artistAlbumsSection);
        sectionAdapter.addSection(artistTracksSection);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        return view;
    }


    class ArtistTracksSection extends Section {

        public final String TAG = ArtistTracksSection.class.getSimpleName();

        private String artist;

        List<Track> artistTracks;
        private ImageLoader imageLoader;
        private final static String TITLE = Data.ARTIST_TOP_TRACKS;

        public void setArtistTracks(List<Track> artistTracks) {
            AppLog.log(TAG, "setArtistTracks");
            this.artistTracks = artistTracks;
        }

        public void setArtist(String artist) {
            AppLog.log(TAG, "setArtist");
            this.artist = artist;
        }


        public ArtistTracksSection(int headerResourceId, int footerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, footerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            AppLog.log(TAG, "createArtistTracksSection");
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getContentItemsTotal() {
            if (artistTracks.size() < 5) {
                return artistTracks.size();
            }
            return 5;
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            imageLoader.displayImage(artistTracks.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgAlbums);
            itemHolder.artistName.setText(artistTracks.get(position).getArtist());
            itemHolder.songTitle.setText(artistTracks.get(position).getName());
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppLog.log(TAG, "onItemClick");
                    Intent intent = new Intent(getContext(), TrackActivity.class);
                    intent.putExtra(TrackActivity.TRACK, artistTracks.get(position));
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

        @Override
        public RecyclerView.ViewHolder getFooterViewHolder(View view) {
            return new FooterViewHolder(view);
        }

        @Override
        public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            if (artistTracks.size() < 5) {
                footerHolder.rootView.setVisibility(View.INVISIBLE);
            } else {
                footerHolder.rootView.setVisibility(View.VISIBLE);
                footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppLog.log(TAG, "onFooterClick");
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra(ListActivity.NAME_USER, artist);
                        intent.putExtra(ListActivity.TITLE, TITLE);
                        getActivity().startActivity(intent);
                    }
                });
            }
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

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        View rootView;

        public FooterViewHolder(View view) {
            super(view);
            rootView = view;
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


    class SimilarArtistsSection extends Section {

        public final String TAG = SimilarArtistsSection.class.getSimpleName();

        private String artist;

        private ImageLoader imageLoader;
        private List<Artist> similarArtists;
        private final static String TITLE = Data.SIMILAR_ARTISTS;


        public void setSimilarArtists(List<Artist> similarArtists) {
            AppLog.log(TAG, "setSimilarArtists");
            this.similarArtists = similarArtists;
        }

        public void setArtist(String artist) {
            AppLog.log(TAG, "setArtist");
            this.artist = artist;
        }

        public SimilarArtistsSection(int headerResourceId, int footerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, footerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            AppLog.log(TAG, "createSimilarArtistsSection");
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getContentItemsTotal() {
            if (similarArtists.size() < 5) {
                return similarArtists.size();
            }
            return 5;
        }

        @Override
        public RecyclerView.ViewHolder getFooterViewHolder(View view) {
            return new FooterViewHolder(view);
        }

        @Override
        public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            if (similarArtists.size() < 5) {
                footerHolder.rootView.setVisibility(View.INVISIBLE);
            } else {
                footerHolder.rootView.setVisibility(View.VISIBLE);
                footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppLog.log(TAG, "onFooterClick");
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra(ListActivity.NAME_USER, artist);
                        intent.putExtra(ListActivity.TITLE, TITLE);
                        getActivity().startActivity(intent);
                    }
                });
            }
        }


        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            imageLoader.displayImage(similarArtists.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgArtist);
            itemHolder.artistName.setText(similarArtists.get(position).getName());
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppLog.log(TAG, "onItemClick");
                    Intent intent = new Intent(getContext(), ArtistActivity.class);
                    intent.putExtra(ArtistActivity.ARTIST, similarArtists.get(position).getName());
                    getActivity().startActivity(intent);
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

        class FooterViewHolder extends RecyclerView.ViewHolder {
            View rootView;

            public FooterViewHolder(View view) {
                super(view);
                rootView = view;
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
            @BindView(R.id.section_artist)
            LinearLayout cell;
            @BindView(R.id.artist_photo)
            ImageView imgArtist;
            @BindView(R.id.artist_band)
            TextView artistName;

            public ItemViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    class ArtistAlbumsSection extends Section {

        public final String TAG = ArtistTracksSection.class.getSimpleName();

        private String artist;

        private List<Album> artistAlbums;
        private ImageLoader imageLoader;
        private final static String TITLE = Data.ARTIST_TOP_ALBUMS;

        public void setArtistAlbums(List<Album> artistAlbums) {
            AppLog.log(TAG, "setArtistAlbums");
            this.artistAlbums = artistAlbums;
        }

        public void setArtist(String artist) {
            AppLog.log(TAG, "setArtist");
            this.artist = artist;
        }

        public ArtistAlbumsSection(int headerResourceId, int footerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, footerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getContentItemsTotal() {
            if (artistAlbums.size() < 5) {
                return artistAlbums.size();
            }
            return 5;
        }

        @Override
        public RecyclerView.ViewHolder getFooterViewHolder(View view) {
            return new FooterViewHolder(view);
        }

        @Override
        public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            if (artistAlbums.size() < 5) {
                footerHolder.rootView.setVisibility(View.INVISIBLE);
            } else {
                footerHolder.rootView.setVisibility(View.VISIBLE);
                footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppLog.log(TAG, "onFooterClick");
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra(ListActivity.NAME_USER, artist);
                        intent.putExtra(ListActivity.TITLE, TITLE);
                        getActivity().startActivity(intent);
                    }
                });
            }
        }


        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            imageLoader.displayImage(artistAlbums.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgAlbum);
            itemHolder.albumsName.setText(artistAlbums.get(position).getName());
            itemHolder.artistName.setText(artistAlbums.get(position).getArtist());
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppLog.log(TAG, "onItemClick");
                    Intent intent = new Intent(getContext(), AlbumActivity.class);
                    intent.putExtra(AlbumActivity.ARTIST_NAME, artistAlbums.get(position).getArtist());
                    intent.putExtra(AlbumActivity.ALBUM_NAME, artistAlbums.get(position).getName());
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

        class FooterViewHolder extends RecyclerView.ViewHolder {
            View rootView;

            public FooterViewHolder(View view) {
                super(view);
                rootView = view;
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
            @BindView(R.id.albums_photo_main)
            ImageView imgAlbum;
            @BindView(R.id.album_name)
            TextView albumsName;
            @BindView(R.id.artist_band)
            TextView artistName;
            @BindView(R.id.section_album)
            LinearLayout cell;

            public ItemViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

}

