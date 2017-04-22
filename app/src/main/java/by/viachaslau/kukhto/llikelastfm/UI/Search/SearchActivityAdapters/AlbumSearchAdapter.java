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
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.Album;
import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.llikelastfm.R;
import by.viachaslau.kukhto.llikelastfm.UI.Album.AlbumActivity;

/**
 * Created by kuhto on 24.02.2017.
 */

public class AlbumSearchAdapter extends SearchAdapter {

    public static final String TAG = AlbumSearchAdapter.class.getSimpleName();

    List<Album> albums;
    private ImageLoader imageLoader;
    private Context context;

    public AlbumSearchAdapter(List<Album> albums, Context context) {
        AppLog.log(TAG, "createAlbumSearchAdapter");
        this.albums = albums;
        this.context = context;
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
        itemHolder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppLog.log(TAG, "onClick");
                Intent intent = new Intent(context, AlbumActivity.class);
                intent.putExtra(AlbumActivity.ALBUM_NAME, albums.get(position).getName());
                intent.putExtra(AlbumActivity.ARTIST_NAME, albums.get(position).getArtist());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
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
