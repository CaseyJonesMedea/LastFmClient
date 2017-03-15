package by.viachaslau.kukhto.lastfmclient.View.WorldChardActivity.Fragments;

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

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.View.TrackActivity.TrackActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by kuhto on 07.03.2017.
 */

public class WorldChartTracksFragment extends Fragment {

    public static final String TAG = WorldChartTracksFragment.class.getSimpleName();

    private static final String CHART_TRACKS = "ChartTracks";


    private RecyclerView recyclerView;
    SectionedRecyclerViewAdapter sectionAdapter;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static WorldChartTracksFragment newInstance(ArrayList<Track> list) {
        WorldChartTracksFragment fragment = new WorldChartTracksFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CHART_TRACKS, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_world_chart_tracks, container, false);
            initViews(view);
            List<Track> list = (List<Track>) getArguments().getSerializable(CHART_TRACKS);
            ChartTracksSection section = new ChartTracksSection(R.layout.section_header, R.layout.section_tracks, R.layout.section_load_empty, R.layout.section_fail_empty);
            section.setChartTracks(list);
            sectionAdapter = new SectionedRecyclerViewAdapter();
            sectionAdapter.addSection(section);
            LinearLayoutManager layotManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layotManager);
            recyclerView.setAdapter(sectionAdapter);
        }
        return view;
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }


    class ChartTracksSection extends Section {

        private ImageLoader imageLoader;
        private List<Track> chartTracks;
        private final static String TITLE = Data.CHART_TOP_TRACKS;

        public ChartTracksSection(int headerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            imageLoader = ImageLoader.getInstance();
        }

        public void setChartTracks(List<Track> chartTracks) {
            this.chartTracks = chartTracks;
        }

        @Override
        public int getContentItemsTotal() {
            return chartTracks.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            imageLoader.displayImage(chartTracks.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgAlbums);
            itemHolder.artistName.setText(chartTracks.get(position).getArtist());
            itemHolder.songTitle.setText(chartTracks.get(position).getName());
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), TrackActivity.class);
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
}
