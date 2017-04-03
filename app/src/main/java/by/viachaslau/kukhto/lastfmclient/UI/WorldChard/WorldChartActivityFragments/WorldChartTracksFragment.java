package by.viachaslau.kukhto.lastfmclient.UI.WorldChard.WorldChartActivityFragments;

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
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.Track.TrackActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by kuhto on 07.03.2017.
 */

public class WorldChartTracksFragment extends Fragment {

    public static final String TAG = WorldChartTracksFragment.class.getSimpleName();

    private static final String CHART_TRACKS = "ChartTracks";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    SectionedRecyclerViewAdapter sectionAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setRetainInstance(true);
    }

    public static WorldChartTracksFragment newInstance(ArrayList<Track> list) {
        AppLog.log(TAG, "newInstance");
        WorldChartTracksFragment fragment = new WorldChartTracksFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CHART_TRACKS, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.log(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_world_chart_tracks, container, false);
        ButterKnife.bind(this, view);
        List<Track> list = (List<Track>) getArguments().getSerializable(CHART_TRACKS);
        ChartTracksSection section = new ChartTracksSection(R.layout.section_header, R.layout.section_tracks, R.layout.section_load_empty, R.layout.section_fail_empty);
        section.setChartTracks(list);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        sectionAdapter.addSection(section);
        LinearLayoutManager layotManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layotManager);
        recyclerView.setAdapter(sectionAdapter);
        return view;
    }


    class ChartTracksSection extends Section {

        public final String TAG = ChartTracksSection.class.getSimpleName();

        private ImageLoader imageLoader;
        private List<Track> chartTracks;
        private final static String TITLE = Data.CHART_TOP_TRACKS;

        public ChartTracksSection(int headerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            AppLog.log(TAG, "createChartTracksSection");
            imageLoader = ImageLoader.getInstance();
        }

        public void setChartTracks(List<Track> chartTracks) {
            AppLog.log(TAG, "setChartTracks");
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
                    AppLog.log(TAG, "onClickItem");
                    Intent intent = new Intent(getContext(), TrackActivity.class);
                    intent.putExtra(TrackActivity.TRACK, chartTracks.get(position));
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
}
