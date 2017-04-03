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
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Data;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.Artist.ArtistActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by kuhto on 07.03.2017.
 */

public class WorldChartArtistsFragment extends Fragment {

    public static final String TAG = WorldChartArtistsFragment.class.getSimpleName();

    private static final String CHART_ARTISTS = "ChartArtist";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    SectionedRecyclerViewAdapter sectionAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log(TAG, "onCreate");
        setRetainInstance(true);
    }

    public static WorldChartArtistsFragment newInstance(ArrayList<Artist> list) {
        AppLog.log(TAG, "newInstance");
        WorldChartArtistsFragment fragment = new WorldChartArtistsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CHART_ARTISTS, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.log(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_world_chart_artists, container, false);
        ButterKnife.bind(this, view);
        List<Artist> list = (List<Artist>) getArguments().getSerializable(CHART_ARTISTS);
        ChartArtistsSection section = new ChartArtistsSection(R.layout.section_header, R.layout.section_artists, R.layout.section_load_empty, R.layout.section_fail_empty);
        section.setChartArtists(list);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        sectionAdapter.addSection(section);
        LinearLayoutManager layotManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layotManager);
        recyclerView.setAdapter(sectionAdapter);
        return view;
    }




    class ChartArtistsSection extends Section {

        public final String TAG = ChartArtistsSection.class.getSimpleName();

        private ImageLoader imageLoader;
        private List<Artist> chartArtists;
        private final static String TITLE = Data.CHART_TOP_ARTISTS;

        public ChartArtistsSection(int headerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            AppLog.log(TAG, "createChartArtistsSection");
            imageLoader = ImageLoader.getInstance();
        }


        public void setChartArtists(List<Artist> chartArtists) {
            AppLog.log(TAG, "setChartArtists");
            this.chartArtists = chartArtists;
        }

        @Override
        public int getContentItemsTotal() {
            return chartArtists.size();
        }


        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            imageLoader.displayImage(chartArtists.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgArtist);
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppLog.log(TAG, "onClickItem");
                    Intent intent = new Intent(getContext(), ArtistActivity.class);
                    intent.putExtra(ArtistActivity.ARTIST, chartArtists.get(position).getName());
                    getActivity().startActivity(intent);
                }
            });
            itemHolder.artistName.setText(chartArtists.get(position).getName());
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
}
