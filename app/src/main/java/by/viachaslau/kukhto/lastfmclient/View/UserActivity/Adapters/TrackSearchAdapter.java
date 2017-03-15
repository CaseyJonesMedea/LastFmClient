package by.viachaslau.kukhto.lastfmclient.View.UserActivity.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.R;

/**
 * Created by kuhto on 24.02.2017.
 */

public class TrackSearchAdapter extends SearchAdapter {

    private List<Track> tracks;
    private ImageLoader imageLoader;

    public TrackSearchAdapter(List<Track> tracks) {
        this.tracks = tracks;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.section_tracks, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        imageLoader.displayImage(tracks.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgAlbum);
        itemHolder.artistName.setText(tracks.get(position).getArtist());
        itemHolder.songTitle.setText(tracks.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgAlbum;
        private final TextView artistName;
        private final TextView songTitle;

        public ItemViewHolder(View view) {
            super(view);
            imgAlbum = (ImageView) view.findViewById(R.id.albums_photo);
            artistName = (TextView) view.findViewById(R.id.artist_name);
            songTitle = (TextView) view.findViewById(R.id.song_title);
        }
    }
}
