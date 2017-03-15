package by.viachaslau.kukhto.lastfmclient.View.UserActivity.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by kuhto on 24.02.2017.
 */

public class ArtistSearchAdapter extends SearchAdapter {

    private ImageLoader imageLoader;
    private List<Artist> artists;


    public ArtistSearchAdapter(List<Artist> artists) {
        this.artists = artists;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.section_artists, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        imageLoader.displayImage(artists.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgArtist);
        itemHolder.artistName.setText(artists.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgArtist;
        private final TextView artistName;

        public ItemViewHolder(View view) {
            super(view);
            imgArtist = (ImageView) view.findViewById(R.id.artist_photo);
            artistName = (TextView) view.findViewById(R.id.artist_band);
        }
    }

}
