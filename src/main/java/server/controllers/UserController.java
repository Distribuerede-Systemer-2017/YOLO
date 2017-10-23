package server.controllers;

import server.database.DBConnection;
import server.models.*;
import server.utility.Digester;

import java.util.ArrayList;

public class UserController {
    private Digester dig;

    public UserController() {
        this.dig = new Digester();
    }


    public boolean addUser(User user) {

        DBConnection dbConnection = new DBConnection();
        //sets a users password to a hashed with salt password and returns a boolean value if a user has been created

        String hashedPassword = dig.hashWithSalt(user.getPassword());

        user.setPassword(hashedPassword);
        int result = dbConnection.addUser(user);

        if(result>0){
            return true;
        }

        return false;
    }

    // Adds an item to the order list
    public boolean addOrder(int id, ArrayList<Item> items) {
        DBConnection dbConnection = new DBConnection();
        int result = dbConnection.addOrder(id, items);

        if(result == 1){
            return true;
        }
        return false;
    }

    public ArrayList<Order> findOrderById(int userId) {
        DBConnection dbConnection = new DBConnection();
        ArrayList<Order> orders = dbConnection.findOrderById(userId);
        return orders;
    }

    public ArrayList<Item> getItems() {
        DBConnection dbConnection = new DBConnection();
        ArrayList<Item> items = dbConnection.getItems();
        return items;
    }

}
