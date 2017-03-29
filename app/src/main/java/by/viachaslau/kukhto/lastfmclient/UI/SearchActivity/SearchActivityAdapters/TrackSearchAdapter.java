package by.viachaslau.kukhto.lastfmclient.UI.SearchActivity.SearchActivityAdapters;

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
import by.viachaslau.kukhto.lastfmclient.Others.Model.AppLog;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.Track;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.UI.TrackActivity.TrackActivity;

/**
 * Created by kuhto on 24.02.2017.
 */

public class TrackSearchAdapter extends SearchAdapter {

    public static final String TAG = TrackSearchAdapter.class.getSimpleName();

    private List<Track> tracks;
    private ImageLoader imageLoader;
    private Context context;

    public TrackSearchAdapter(Context context, List<Track> tracks) {
        AppLog.log(TAG, "createTrackSearchAdapter");
        this.tracks = tracks;
        this.context = context;
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
        itemHolder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppLog.log(TAG, "onClick");
                Intent intent = new Intent(context, TrackActivity.class);
                intent.putExtra(TrackActivity.TRACK, tracks.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.albums_photo)
        ImageView imgAlbum;
        @BindView(R.id.artist_name)
        TextView artistName;
        @BindView(R.id.song_title)
        TextView songTitle;
        @BindView(R.id.section_track)
        LinearLayout cell;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
