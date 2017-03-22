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

import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.ChartFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.TrackActivity.TrackActivity;
import by.viachaslau.kukhto.lastfmclient.Activities.WorldChardActivity.WorldChartActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by kuhto on 23.02.2017.
 */

public class ChartFragmentUser extends Fragment {

    private static final String CHART_FRAGMENT_INFORMATION = "chartFragmentInformation";

    public static final String TAG = ChartFragmentUser.class.getSimpleName();

    private RecyclerView recyclerView;
    SectionedRecyclerViewAdapter sectionAdapter;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public static ChartFragmentUser newInstance(ChartFragmentInformation chartFragmentInformation) {
        ChartFragmentUser fragmentUser = new ChartFragmentUser();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CHART_FRAGMENT_INFORMATION, chartFragmentInformation);
        fragmentUser.setArguments(bundle);
        return fragmentUser;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_chart, container, false);
            initViews(view);
            ChartFragmentInformation chartFragmentInformation = (ChartFragmentInformation) getArguments().getSerializable(CHART_FRAGMENT_INFORMATION);
            sectionAdapter = new SectionedRecyclerViewAdapter();
            ChartTracksSection chartTracksSection = new ChartTracksSection(R.layout.section_header, R.layout.section_button_world_chat, R.layout.section_tracks, R.layout.section_load_empty, R.layout.section_fail_empty);
            chartTracksSection.setChartTracks(chartFragmentInformation.getChartTracks());
            sectionAdapter.addSection(chartTracksSection);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(sectionAdapter);
        }
        return view;
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }


    class ChartTracksSection extends Section {

        List<Track> chartTracks;
        private ImageLoader imageLoader;
        private final static String TITLE = "Chart Tracks";

        public void setChartTracks(List<Track> chartTracks) {
            this.chartTracks = chartTracks;
        }


        public ChartTracksSection(int headerResourceId, int footerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, footerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            imageLoader = ImageLoader.getInstance();
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

        @Override
        public RecyclerView.ViewHolder getFooterViewHolder(View view) {
            return new FooterViewHolder(view);
        }

        @Override
        public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), WorldChartActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        private final View rootView;

        public FooterViewHolder(View view) {
            super(view);
            rootView = view;
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
