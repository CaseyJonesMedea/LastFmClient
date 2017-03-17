package by.viachaslau.kukhto.lastfmclient.Activities.SearchActivity.SearchActivityAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.ArtistActivity.ArtistActivity;

/**
 * Created by kuhto on 24.02.2017.
 */

public class ArtistSearchAdapter extends SearchAdapter {

    private ImageLoader imageLoader;
    private List<Artist> artists;
    private Context context;


    public ArtistSearchAdapter(Context context, List<Artist> artists) {
        this.context = context;
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
        itemHolder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArtistActivity.class);
                intent.putExtra(ArtistActivity.ARTIST, artists.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.size();
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
