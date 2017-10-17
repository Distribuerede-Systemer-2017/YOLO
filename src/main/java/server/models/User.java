package server.models;

import java.util.ArrayList;
import server.utility.Digester;

/**
 * Created by PK on 17-10-2017
 */

public class User {

    private int userId;
    private String username;
    private String password;
    private String email;
    private boolean isPersonel;
    private ArrayList<User> users;
    private Digester digester;

    public User(int userId, String username, String password, String email, boolean isPersonel) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isPersonel = isPersonel;
        this.users = new ArrayList<User>();
        this.digester = new Digester();

    }

    public User() {

    }

    public int getUserid() {
        return userId;
    }

    public void setUserid(int userId) {
        this.userId = userId;
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

        //Kalder metoden hashWithSalt fra Digester klassen, saa passwordet kan hashes
        this.password = digester.hashWithSalt(password);
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

    public void setPersonel(boolean isPersonel) {
        this.isPersonel = isPersonel;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
