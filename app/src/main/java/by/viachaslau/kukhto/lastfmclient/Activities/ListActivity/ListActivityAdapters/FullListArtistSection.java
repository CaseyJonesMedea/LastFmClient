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


import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.ArtistActivity.ArtistActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;

/**
 * Created by VKukh on 04.03.2017.
 */

public class FullListArtistSection extends Section {

    public static final String TAG = FullListArtistSection.class.getSimpleName();


    private List<Artist> artistList;
    private ImageLoader imageLoader;

    private Context context;

    private String title;


    public FullListArtistSection(Context context, int headerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
        super(headerResourceId, itemResourceId, loadingResourceId, failedResourceId);
        AppLog.log(TAG, "createFullListArtistSection");
        imageLoader = ImageLoader.getInstance();
        this.context = context;
    }


    public void setArtistList(List<Artist> artistList) {
        AppLog.log(TAG, "setArtistList");
        this.artistList = artistList;
    }

    public void setTitle(String title) {
        AppLog.log(TAG, "setTitle");
        this.title = title;
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
                AppLog.log(TAG, "onItemClick");
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
