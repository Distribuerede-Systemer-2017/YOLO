package server.models;

import java.util.ArrayList;

/**
 * Created by PK on 17-10-2017
 */

public class User {

    private int userid;
    private String username;
    private String password;
    private String email;
    private boolean isPersonel;
    private ArrayList<User> users;

    public User(int userid, String username, String password, String email, boolean isPersonel) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isPersonel = isPersonel;
        this.users = new ArrayList<User>();
    }

    public User() {

    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPersonel() {
        return isPersonel;
    }

    public void setPersonel(boolean personel) {
        isPersonel = personel;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
