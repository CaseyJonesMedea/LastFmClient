package by.viachaslau.kukhto.lastfmclient.Activities.UserActivity.UserActivityFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import by.viachaslau.kukhto.lastfmclient.Others.Model.modelApp.FriendsFragmentInformation;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.ImageSize;
import by.viachaslau.kukhto.lastfmclient.Others.Model.umass.lastfm.User;
import by.viachaslau.kukhto.lastfmclient.R;
import by.viachaslau.kukhto.lastfmclient.Activities.UserActivity.UserActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by kuhto on 23.02.2017.
 */

public class FriendsFragmentUser extends Fragment {

    private static final String FRIENDS_FRAGMENT_INFORMATION = "friendsFragmentInformation";

    public static final String TAG = FriendsFragmentUser.class.getSimpleName();

    private RecyclerView recyclerView;
    SectionedRecyclerViewAdapter sectionAdapter;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public static FriendsFragmentUser newInstance(FriendsFragmentInformation chartFragmentInformation) {
        FriendsFragmentUser fragmentUser = new FriendsFragmentUser();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FRIENDS_FRAGMENT_INFORMATION, chartFragmentInformation);
        fragmentUser.setArguments(bundle);
        return fragmentUser;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_friends, container, false);
            initViews(view);
            FriendsFragmentInformation friendsFragmentInformation = (FriendsFragmentInformation) getArguments().getSerializable(FRIENDS_FRAGMENT_INFORMATION);
            sectionAdapter = new SectionedRecyclerViewAdapter();
            FriendsSection friendsSection = new FriendsSection(R.layout.section_header, R.layout.section_friends, R.layout.section_load_empty, R.layout.section_fail_empty);
            friendsSection.setFriends(friendsFragmentInformation.getFriends());
            sectionAdapter.addSection(friendsSection);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(sectionAdapter);
        }
        return view;
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }


    class FriendsSection extends Section {

        List<User> friends;
        private ImageLoader imageLoader;
        private final static String TITLE = "Friends";

        public void setFriends(List<User> friends) {
            this.friends = friends;
        }


        public FriendsSection(int headerResourceId, int itemResourceId, int loadingResourceId, int failedResourceId) {
            super(headerResourceId, itemResourceId, loadingResourceId, failedResourceId);
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getContentItemsTotal() {
            return friends.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            imageLoader.displayImage(friends.get(position).getImageURL(ImageSize.LARGE), itemHolder.imgFriend);
            itemHolder.nameFriend.setText(friends.get(position).getName());
            itemHolder.scrobblesFriend.setText(getContext().getResources().getString(R.string.scrobbles) + " " + friends.get(position).getPlaycount());
            itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), UserActivity.class);
                    intent.putExtra(UserActivity.USER_NAME, friends.get(position).getName());
                    startActivity(intent);
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
            headerHolder.tvTitle.setText(TITLE);
        }

    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imgFriend;
        private final TextView nameFriend;
        private final TextView scrobblesFriend;
        private final LinearLayout cell;


        public ItemViewHolder(View view) {
            super(view);
            cell = (LinearLayout) view.findViewById(R.id.section_friend);
            imgFriend = (CircleImageView) view.findViewById(R.id.image_friend);
            nameFriend = (TextView) view.findViewById(R.id.name_friend);
            scrobblesFriend = (TextView) view.findViewById(R.id.scrobbles_friend);
        }
    }

}
