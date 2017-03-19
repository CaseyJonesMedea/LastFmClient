package by.viachaslau.kukhto.lastfmclient.Activities.ListActivity.ListActivityAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.ArtistActivity.ArtistActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;

/**
 * Created by VKukh on 04.03.2017.
 */

public class FullListArtistSection extends Section {


    private List<Artist> artistList;
    private ImageLoader imageLoader;

    private Context context;

    private String title;


    public FullListArtistSection(Context context ,int headerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
        super(headerResourceId, itemResourceId, loadingResourceId, failedResourceId);
        imageLoader = ImageLoader.getInstance();
        this.context = context;
    }


    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void addAlbums(List<Artist> artists){
        artistList.addAll(artists);
        //Pagination
    }

    @Override
    public int getContentItemsTotal() {
        return artistList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        imageLoader.displayImage(artistList.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgArtist);
        itemHolder.artistName.setText(artistList.get(position).getName());
        itemHolder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArtistActivity.class);
                intent.putExtra(ArtistActivity.ARTIST, artistList.get(position).getName());
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
        private final ImageView imgArtist;
        private final TextView artistName;
        private final LinearLayout cell;

        public ItemViewHolder(View view) {
            super(view);
            cell = (LinearLayout)view.findViewById(R.id.section_artist);
            imgArtist = (ImageView) view.findViewById(R.id.artist_photo);
            artistName = (TextView) view.findViewById(R.id.artist_band);
        }
    }
}
