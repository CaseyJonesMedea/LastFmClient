package by.viachaslau.kukhto.llikelastfm.UI.User.UserActivityFragments;

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
import by.viachaslau.kukhto.llikelastfm.Others.Model.AppLog;
import by.viachaslau.kukhto.llikelastfm.Others.Model.modelApp.ChartFragmentInformation;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.llikelastfm.R;
import by.viachaslau.kukhto.llikelastfm.UI.Track.TrackActivity;
import by.viachaslau.kukhto.llikelastfm.UI.WorldChard.WorldChartActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by kuhto on 23.02.2017.
 */

public class ChartFragmentUser extends Fragment {

    private static final String CHART_FRAGMENT_INFORMATION = "chartFragmentInformation";

    public static final String TAG = ChartFragmentUser.class.getSimpleName();


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    SectionedRecyclerViewAdapter sectionAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setRetainInstance(true);
    }

    public static ChartFragmentUser newInstance(ChartFragmentInformation chartFragmentInformation) {
        AppLog.log(TAG, "newInstance");
        ChartFragmentUser fragmentUser = new ChartFragmentUser();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CHART_FRAGMENT_INFORMATION, chartFragmentInformation);
        fragmentUser.setArguments(bundle);
        return fragmentUser;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.log(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_user_chart, container, false);
        ButterKnife.bind(this, view);
        ChartFragmentInformation chartFragmentInformation = (ChartFragmentInformation) getArguments().getSerializable(CHART_FRAGMENT_INFORMATION);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        ChartTracksSection chartTracksSection = new ChartTracksSection(R.layout.section_header, R.layout.section_button_world_chat, R.layout.section_tracks, R.layout.section_load_empty, R.layout.section_fail_empty);
        chartTracksSection.setChartTracks(chartFragmentInformation.getChartTracks());
        if(chartFragmentInformation.getChartTracks().size() == 0){
            chartTracksSection.setState(Section.State.FAILED);
        }
        sectionAdapter.addSection(chartTracksSection);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        return view;
    }


    class ChartTracksSection extends Section {

        public final String TAG = ChartTracksSection.class.getSimpleName();

        List<Track> chartTracks;
        private ImageLoader imageLoader;
        private final static String TITLE = "Chart Tracks";

        public void setChartTracks(List<Track> chartTracks) {
            AppLog.log(TAG, "setChartTracks");
            this.chartTracks = chartTracks;
        }


        public ChartTracksSection(int headerResourceId, int footerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, footerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            AppLog.log(TAG, "createChartTracksSection");
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
                    AppLog.log(TAG, "onClickFooter");
                    Intent intent = new Intent(getContext(), WorldChartActivity.class);
                    startActivity(intent);
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
