package by.viachaslau.kukhto.lastfmclient.View.ListActivity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.View.AlbumActivity.AlbumActivity;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;

/**
 * Created by VKukh on 04.03.2017.
 */

public class FullListAlbumSection extends Section {

    private List<Album> albumList;
    private ImageLoader imageLoader;

    private Context context;

    private String title;


    public FullListAlbumSection(Context context, int headerResourceId, int footerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
        super(headerResourceId, footerResourceId, itemResourceId, loadingResourceId, failedResourceId);
        imageLoader = ImageLoader.getInstance();
        this.context = context;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void addAlbums(List<Album> albums){
        albumList.addAll(albums);
        //Pagination
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
        itemHolder.imgAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlbumActivity.class);
                intent.putExtra(AlbumActivity.ALBUM_NAME, albumList.get(position).getName());
                intent.putExtra(AlbumActivity.ARTIST_NAME, albumList.get(position).getArtist());
                context.startActivity(intent);
            }
        });
        itemHolder.albumsName.setText(albumList.get(position).getName());
        itemHolder.artistName.setText(albumList.get(position).getArtist());
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
                Toast.makeText(context, "Clicked on footer!", Toast.LENGTH_SHORT).show();
            }
        });
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
        private final ImageView imgAlbum;
        private final TextView albumsName;
        private final TextView artistName;

        public ItemViewHolder(View view) {
            super(view);
            imgAlbum = (ImageView) view.findViewById(R.id.albums_photo_main);
            albumsName = (TextView) view.findViewById(R.id.album_name);
            artistName = (TextView) view.findViewById(R.id.artist_band);
        }
    }
}
