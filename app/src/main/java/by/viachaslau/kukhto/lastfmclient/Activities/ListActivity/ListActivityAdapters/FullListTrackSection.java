package by.viachaslau.kukhto.lastfmclient.Activities.ListActivity.ListActivityAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.TrackActivity.TrackActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;

/**
 * Created by VKukh on 04.03.2017.
 */

public class FullListTrackSection extends Section {

    public static final String TAG = FullListTrackSection.class.getSimpleName();

    private List<Track> trackList;
    private ImageLoader imageLoader;

    private Context context;

    private String title;


    public FullListTrackSection(Context context, int headerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
        super(headerResourceId, itemResourceId, loadingResourceId, failedResourceId);
        AppLog.log(TAG, "createFullListTrackSection");
        imageLoader = ImageLoader.getInstance();
        this.context = context;
    }

    public void setTrackList(List<Track> trackList) {
        AppLog.log(TAG, "setTrackList");
        this.trackList = trackList;
    }

    public void setTitle(String title) {
        AppLog.log(TAG, "setTitle");
        this.title = title;
    }


    @Override
    public int getContentItemsTotal() {
        return trackList.size();
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        imageLoader.displayImage(trackList.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgAlbums);
        itemHolder.artistName.setText(trackList.get(position).getArtist());
        itemHolder.songTitle.setText(trackList.get(position).getName());
        itemHolder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppLog.log(TAG, "onClickItem");
                Intent intent = new Intent(context, TrackActivity.class);
                intent.putExtra(TrackActivity.TRACK, trackList.get(position));
                context.startActivity(intent);
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
        headerHolder.tvTitle.setText(title);
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
