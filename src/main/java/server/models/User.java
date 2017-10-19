package server.models;

import java.util.ArrayList;
import server.utility.Digester;

/**
 * Created by PK on 17-10-2017
 */

public class User {

    private int user_id;
    private String username;
    private String password;
    private boolean isPersonel;
    private ArrayList<User> users;
    private Digester digester;

    public User(int userId, String username, String password, boolean isPersonel, Digester digester) {
        this.user_id = userId;
        this.username = username;
        this.password = password;
        this.isPersonel = isPersonel;
        this.users = new ArrayList<User>();
        this.digester = digester;

    }

    public User() {

    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password; //skal ifølge Andy hashes her(han fikk vite det av Tidemann), DB authorizeUser fungerer ikke med det.
    }


    public boolean isPersonel() {
        return isPersonel;
    }

    public void setPersonel(boolean personel) {
        isPersonel = personel;
    }


}

