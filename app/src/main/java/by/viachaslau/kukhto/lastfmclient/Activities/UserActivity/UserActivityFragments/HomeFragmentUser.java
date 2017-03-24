package by.viachaslau.kukhto.lastfmclient.Activities.UserActivity.UserActivityFragments;

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
import by.viachaslau.kukhto.lastfmclient.Others.App;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.HomeFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.AlbumActivity.AlbumActivity;
import by.viachaslau.kukhto.lastfmclient.Activities.ArtistActivity.ArtistActivity;
import by.viachaslau.kukhto.lastfmclient.Activities.ListActivity.ListActivity;
import by.viachaslau.kukhto.lastfmclient.Activities.TrackActivity.TrackActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by CaseyJones on 26.01.2017.
 */

public class HomeFragmentUser extends Fragment {

    private static final String HOME_FRAGMENT_INFORMATION = "homeFragmentInformation";
    private static final String HOME_FRAGMENT_NAME_USER = "user";

    public static final String TAG = HomeFragmentUser.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    SectionedRecyclerViewAdapter sectionAdapter;


    private String nameUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setRetainInstance(true);
    }


    public static HomeFragmentUser newInstance(HomeFragmentInformation homeFragmentInformation, String name) {
        AppLog.log(TAG, "newInstance");
        HomeFragmentUser fragmentUser = new HomeFragmentUser();
        Bundle bundle = new Bundle();
        bundle.putSerializable(HOME_FRAGMENT_INFORMATION, homeFragmentInformation);
        bundle.putString(HOME_FRAGMENT_NAME_USER, name);
        fragmentUser.setArguments(bundle);
        return fragmentUser;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.log(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        ButterKnife.bind(this, view);
        HomeFragmentInformation homeFragmentInformation = (HomeFragmentInformation) getArguments().getSerializable(HOME_FRAGMENT_INFORMATION);
        nameUser = getArguments().getString(HOME_FRAGMENT_NAME_USER);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        RecentTracksSection recentTracksSection = new RecentTracksSection(R.layout.section_header, R.layout.section_footer, R.layout.section_tracks, R.layout.section_load_empty, R.layout.section_fail_empty);
        recentTracksSection.setNameUser(nameUser);
        recentTracksSection.setResentTracks((ArrayList<Track>) homeFragmentInformation.getRecentTracks());
        TopArtistsSection topArtistsSection = new TopArtistsSection(R.layout.section_header, R.layout.section_footer, R.layout.section_artists, R.layout.section_load_empty, R.layout.section_fail_empty);
        topArtistsSection.setNameUser(nameUser);
        topArtistsSection.setTopArtists((ArrayList<Artist>) homeFragmentInformation.getTopArtists());
        TopAlbumsSection topAlbumsSection = new TopAlbumsSection(R.layout.section_header, R.layout.section_footer, R.layout.section_albums, R.layout.section_load_empty, R.layout.section_fail_empty);
        topAlbumsSection.setNameUser(nameUser);
        topAlbumsSection.setTopAlbums((ArrayList<Album>) homeFragmentInformation.getTopAlbums());
        TopTracksSection topTracksSection = new TopTracksSection(R.layout.section_header, R.layout.section_footer, R.layout.section_tracks, R.layout.section_load_empty, R.layout.section_fail_empty);
        topTracksSection.setNameUser(nameUser);
        topTracksSection.setTopTracks(homeFragmentInformation.getTopTracks());
        LovedTracksSection lovedTracksSection = new LovedTracksSection(R.layout.section_header, R.layout.section_footer, R.layout.section_tracks, R.layout.section_load_empty, R.layout.section_fail_empty);
        lovedTracksSection.setNameUser(nameUser);
        lovedTracksSection.setLovedTracks(homeFragmentInformation.getLovedTracks());
        sectionAdapter.addSection(recentTracksSection);
        sectionAdapter.addSection(topArtistsSection);
        sectionAdapter.addSection(topAlbumsSection);
        sectionAdapter.addSection(topTracksSection);
        sectionAdapter.addSection(lovedTracksSection);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        return view;
    }


    class RecentTracksSection extends Section {

        public final String TAG = RecentTracksSection.class.getSimpleName();

        private String nameUser;

        ArrayList<Track> resentTracks;
        private ImageLoader imageLoader;
        private final static String TITLE = Data.RECENT_TRACKS;

        public void setResentTracks(ArrayList<Track> resentTracks) {
            AppLog.log(TAG, "setResentTracks");
            this.resentTracks = resentTracks;
        }

        public void setNameUser(String nameUser) {
            AppLog.log(TAG, "setNameUser");
            this.nameUser = nameUser;
        }


        public RecentTracksSection(int headerResourceId, int footerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, footerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            AppLog.log(TAG, "createRecentTracksSection");
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getContentItemsTotal() {
            if (resentTracks.size() < 5) {
                return resentTracks.size();
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
            if (resentTracks.get(position).getImageURL(ImageSize.LARGE).equals("")) {
                itemHolder.imgAlbums.setImageResource(R.drawable.no_image);
            }else{
                imageLoader.displayImage(resentTracks.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgAlbums);
            }
            itemHolder.artistName.setText(resentTracks.get(position).getArtist());
            itemHolder.songTitle.setText(resentTracks.get(position).getName());
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppLog.log(TAG, "onItemClick");
                    Intent intent = new Intent(getContext(), TrackActivity.class);
                    intent.putExtra(TrackActivity.TRACK, resentTracks.get(position));
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
            if (resentTracks.size() < 5) {
                footerHolder.rootView.setVisibility(View.INVISIBLE);
            } else {
                footerHolder.rootView.setVisibility(View.VISIBLE);
                footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppLog.log(TAG, "onFooterClick");
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra(ListActivity.NAME_USER, nameUser);
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

    class FooterViewHolder extends RecyclerView.ViewHolder {
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


    class TopArtistsSection extends Section {

        public final String TAG = TopArtistsSection.class.getSimpleName();

        private String nameUser;

        private ImageLoader imageLoader;
        private ArrayList<Artist> topArtists;
        private final static String TITLE = Data.TOP_ARTISTS;

        public TopArtistsSection(int headerResourceId, int footerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, footerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            AppLog.log(TAG, "createTopArtistsSection");
            imageLoader = ImageLoader.getInstance();
        }

        private void setNameUser(String nameUser) {
            AppLog.log(TAG, "setNameUser");
            this.nameUser = nameUser;
        }

        public void setTopArtists(ArrayList<Artist> topArtists) {
            AppLog.log(TAG, "setTopArtists");
            this.topArtists = topArtists;
        }

        @Override
        public int getContentItemsTotal() {
            if (topArtists.size() < 5) {
                return topArtists.size();
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
            imageLoader.displayImage(topArtists.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgArtist);
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppLog.log(TAG, "onClickItem");
                    Intent intent = new Intent(getContext(), ArtistActivity.class);
                    intent.putExtra(ArtistActivity.ARTIST, topArtists.get(position).getName());
                    getActivity().startActivity(intent);
                }
            });
            itemHolder.artistName.setText(topArtists.get(position).getName());
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
            if (topArtists.size() < 5) {
                footerHolder.rootView.setVisibility(View.INVISIBLE);
            } else {
                footerHolder.rootView.setVisibility(View.VISIBLE);
                footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppLog.log(TAG, "onClickFooter");
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra(ListActivity.NAME_USER, nameUser);
                        intent.putExtra(ListActivity.TITLE, TITLE);
                        getActivity().startActivity(intent);
                    }
                });
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

        class FooterViewHolder extends RecyclerView.ViewHolder {
            View rootView;

            public FooterViewHolder(View view) {
                super(view);
                rootView = view;
            }
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.artist_photo)
            ImageView imgArtist;
            @BindView(R.id.artist_band)
            TextView artistName;
            @BindView(R.id.section_artist)
            LinearLayout cell;

            public ItemViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    class TopAlbumsSection extends Section {

        public final String TAG = TopAlbumsSection.class.getSimpleName();

        private String nameUser;

        private ArrayList<Album> topAlbums;
        private ImageLoader imageLoader;
        private final static String TOP_ALBUMS = Data.TOP_ALBUMS;

        public void setTopAlbums(ArrayList<Album> topAlbums) {
            AppLog.log(TAG, "setTopAlbums");
            this.topAlbums = topAlbums;
        }

        public void setNameUser(String nameUser) {
            AppLog.log(TAG, "setNameUser");
            this.nameUser = nameUser;
        }

        public TopAlbumsSection(int headerResourceId, int footerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, footerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            AppLog.log(TAG, "createTopAlbumsSection");
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getContentItemsTotal() {
            if (topAlbums.size() < 5) {
                return topAlbums.size();
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
            imageLoader.displayImage(topAlbums.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgAlbum);
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppLog.log(TAG, "onClickItem");
                    Intent intent = new Intent(getContext(), AlbumActivity.class);
                    intent.putExtra(AlbumActivity.ALBUM_NAME, topAlbums.get(position).getName());
                    intent.putExtra(AlbumActivity.ARTIST_NAME, topAlbums.get(position).getArtist());
                    getActivity().startActivity(intent);
                }
            });
            itemHolder.albumsName.setText(topAlbums.get(position).getName());
            itemHolder.artistName.setText(topAlbums.get(position).getArtist());
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.tvTitle.setText(TOP_ALBUMS);
        }

        @Override
        public RecyclerView.ViewHolder getFooterViewHolder(View view) {
            return new FooterViewHolder(view);
        }

        @Override
        public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            if (topAlbums.size() < 5) {
                footerHolder.rootView.setVisibility(View.INVISIBLE);
            } else {
                footerHolder.rootView.setVisibility(View.INVISIBLE);
                footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppLog.log(TAG, "onClickFooter");
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra(ListActivity.NAME_USER, nameUser);
                        intent.putExtra(ListActivity.TITLE, TOP_ALBUMS);
                        getActivity().startActivity(intent);
                    }
                });
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

        class FooterViewHolder extends RecyclerView.ViewHolder {
            View rootView;

            public FooterViewHolder(View view) {
                super(view);
                rootView = view;
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

    class TopTracksSection extends Section {

        public final String TAG = TopTracksSection.class.getSimpleName();

        private String nameUser;

        List<Track> topTracks;
        private ImageLoader imageLoader;
        private final static String TITLE = Data.TOP_TRACKS;

        public void setTopTracks(List<Track> topTracks) {
            AppLog.log(TAG, "setTopTracks");
            this.topTracks = topTracks;
        }

        private void setNameUser(String nameUser) {
            AppLog.log(TAG, "setNameUser");
            this.nameUser = nameUser;
        }


        public TopTracksSection(int headerResourceId, int footerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, footerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            AppLog.log(TAG, "createTopTracksSection");
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getContentItemsTotal() {
            if (topTracks.size() < 5) {
                return topTracks.size();
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
            imageLoader.displayImage(topTracks.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgAlbums);
            itemHolder.artistName.setText(topTracks.get(position).getArtist());
            itemHolder.songTitle.setText(topTracks.get(position).getName());
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppLog.log(TAG, "onClickItem");
                    Intent intent = new Intent(getContext(), TrackActivity.class);
                    intent.putExtra(TrackActivity.TRACK, topTracks.get(position));
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
            if (topTracks.size() < 5) {
                footerHolder.rootView.setVisibility(View.INVISIBLE);
            } else {
                footerHolder.rootView.setVisibility(View.VISIBLE);
                footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppLog.log(TAG, "onClickFooter");
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra(ListActivity.NAME_USER, nameUser);
                        intent.putExtra(ListActivity.TITLE, TITLE);
                        getActivity().startActivity(intent);
                    }
                });
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

        class FooterViewHolder extends RecyclerView.ViewHolder {
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

    }

    class LovedTracksSection extends Section {

        public final String TAG = LovedTracksSection.class.getSimpleName();

        private String nameUser;

        List<Track> lovedTracks;
        private ImageLoader imageLoader;
        private final static String TITLE = Data.LOVED_TRACKS;

        public void setLovedTracks(List<Track> lovedTracks) {
            AppLog.log(TAG, "setLovedTracks");
            this.lovedTracks = lovedTracks;
        }

        public void setNameUser(String nameUser) {
            AppLog.log(TAG, "setNameUser");
            this.nameUser = nameUser;
        }


        public LovedTracksSection(int headerResourceId, int footerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, footerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            AppLog.log(TAG, "createLovedTracksSection");
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getContentItemsTotal() {
            if (lovedTracks.size() < 5) {
                return lovedTracks.size();
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
            imageLoader.displayImage(lovedTracks.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgAlbums);
            itemHolder.artistName.setText(lovedTracks.get(position).getArtist());
            itemHolder.songTitle.setText(lovedTracks.get(position).getName());
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppLog.log(TAG, "onClickItem");
                    Intent intent = new Intent(getContext(), TrackActivity.class);
                    intent.putExtra(TrackActivity.TRACK, lovedTracks.get(position));
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
            if (lovedTracks.size() < 5) {
                footerHolder.rootView.setVisibility(View.INVISIBLE);
            } else {
                footerHolder.rootView.setVisibility(View.VISIBLE);
                footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppLog.log(TAG, "onClickFooter");
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtra(ListActivity.NAME_USER, nameUser);
                        intent.putExtra(ListActivity.TITLE, TITLE);
                        getActivity().startActivity(intent);
                    }
                });
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

        class FooterViewHolder extends RecyclerView.ViewHolder {
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
    }
}



