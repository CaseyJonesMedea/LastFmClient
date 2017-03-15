package by.viachaslau.kukhto.lastfmclient.View.UserActivity.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by kuhto on 24.02.2017.
 */

public class AlbumSearchAdapter extends SearchAdapter {

    List<Album> albums;
    private ImageLoader imageLoader;

    public AlbumSearchAdapter(List<Album> albums) {
        this.albums = albums;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.section_albums, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        imageLoader.displayImage(albums.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgAlbum);
        itemHolder.artistName.setText(albums.get(position).getArtist());
        itemHolder.albumsName.setText(albums.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return albums.size();
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
