package by.viachaslau.kukhto.lastfmclient.Model.modelApp;

import java.io.Serializable;

/**
 * Created by CaseyJones on 26.01.2017.
 */

public class UserInformation implements Serializable{

    private String name;
    private String password;

    public UserInformation(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
