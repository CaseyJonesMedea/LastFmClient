package by.viachaslau.kukhto.lastfmclient.UI.List.ListActivityAdapters;

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
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.Album.AlbumActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;

/**
 * Created by VKukh on 04.03.2017.
 */

public class FullListAlbumSection extends Section {

    public static final String TAG = FullListAlbumSection.class.getSimpleName();

    private List<Album> albumList;
    private ImageLoader imageLoader;

    private Context context;

    private String title;


    public FullListAlbumSection(Context context, int headerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
        super(headerResourceId, itemResourceId, loadingResourceId, failedResourceId);
        AppLog.log(TAG, "CreateFullListAlbumSection");
        imageLoader = ImageLoader.getInstance();
        this.context = context;
    }

    public void setAlbumList(List<Album> albumList) {
        AppLog.log(TAG, "setAlbumList");
        this.albumList = albumList;
    }

    public void setTitle(String title){
        AppLog.log(TAG, "setTitle");
        this.title = title;
    }

    @Override
    public int getContentItemsTotal() {
        return albumList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        imageLoader.displayImage(albumList.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgAlbum);
        itemHolder.albumsName.setText(albumList.get(position).getName());
        itemHolder.artistName.setText(albumList.get(position).getArtist());
        itemHolder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppLog.log(TAG, "onItemClick");
                Intent intent = new Intent(context, AlbumActivity.class);
                intent.putExtra(AlbumActivity.ALBUM_NAME, albumList.get(position).getName());
                intent.putExtra(AlbumActivity.ARTIST_NAME, albumList.get(position).getArtist());
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
