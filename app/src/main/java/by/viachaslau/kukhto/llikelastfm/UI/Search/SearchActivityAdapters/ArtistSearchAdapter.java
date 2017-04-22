package by.viachaslau.kukhto.llikelastfm.UI.Search.SearchActivityAdapters;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import by.viachaslau.kukhto.llikelastfm.Others.Model.AppLog;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Artist;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.llikelastfm.R;
import by.viachaslau.kukhto.llikelastfm.UI.Artist.ArtistActivity;

/**
 * Created by kuhto on 24.02.2017.
 */

public class ArtistSearchAdapter extends SearchAdapter {

    public static final String TAG = ArtistSearchAdapter.class.getSimpleName();

    private ImageLoader imageLoader;
    private List<Artist> artists;
    private Context context;


    public ArtistSearchAdapter(Context context, List<Artist> artists) {
        AppLog.log(TAG, "createArtistSearchAdapter");
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
                AppLog.log(TAG, "onClick");
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
