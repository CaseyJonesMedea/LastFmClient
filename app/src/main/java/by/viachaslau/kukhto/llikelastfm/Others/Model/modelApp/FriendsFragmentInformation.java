package by.viachaslau.kukhto.llikelastfm.Others.Model.modelApp;

import java.io.Serializable;
import java.util.List;

import by.viachaslau.kukhto.llikelastfm.Others.Model.umass.lastfm.User;

/**
 * Created by kuhto on 23.02.2017.
 */

public class FriendsFragmentInformation implements Serializable{

    List<User> friends;

    public FriendsFragmentInformation(List<User> friends) {
        this.friends = friends;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
