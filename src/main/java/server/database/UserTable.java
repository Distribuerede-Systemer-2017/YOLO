package server.database;


import server.models.User;

import java.util.ArrayList;

public class UserTable {

    private ArrayList<User> users;
    private static UserTable instance = null;

    protected UserTable() {
        this.users = new ArrayList<>();

        User user1 = new User();
        user1.setId(1);
        user1.setAge(26);
        user1.setName("Jesper Bruun");

        this.users.add(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setAge(24);
        user2.setName("Daniel Franch");

        this.users.add(user2);

    }

    public static UserTable getInstance() {
        if (instance == null) {
            instance = new UserTable();
        }
        return instance;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public User findById(int id) {
        for (User user : this.users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
