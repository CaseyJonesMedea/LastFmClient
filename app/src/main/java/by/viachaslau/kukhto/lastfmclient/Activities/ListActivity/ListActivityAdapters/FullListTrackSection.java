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

import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.TrackActivity.TrackActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;

/**
 * Created by VKukh on 04.03.2017.
 */

public class FullListTrackSection extends Section{

    private List<Track> trackList;
    private ImageLoader imageLoader;

    private Context context;

    private String title;


    public FullListTrackSection(Context context, int headerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
        super(headerResourceId, itemResourceId, loadingResourceId, failedResourceId);
        imageLoader = ImageLoader.getInstance();
        this.context = context;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public void setTitle(String title){
        this.title = title;
    }


    public void addTracks(List<Track> tracks){
        trackList.addAll(tracks);
        // Pagination
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
            cell = (LinearLayout)view.findViewById(R.id.section_track);
            imgAlbums = (ImageView) view.findViewById(R.id.albums_photo);
            artistName = (TextView) view.findViewById(R.id.artist_name);
            songTitle = (TextView) view.findViewById(R.id.song_title);
        }
    }
}
