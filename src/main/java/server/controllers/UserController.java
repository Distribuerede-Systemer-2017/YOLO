package server.controllers;

import server.database.DBConnection;
import server.models.*;
import server.utility.Digester;
import server.database.DBConnection;
import server.models.*;
import server.endpoints.UserEndpoint;
import server.utility.Globals;

import java.util.ArrayList;

public class UserController {
    private Digester dig;
    private DBConnection dbConnection;

    public UserController(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.dig = new Digester();
    }

    public UserController() {

    }

    public boolean addUser(User user) {


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
        int result = dbConnection.addOrder(id, items);

        if(result == 1){
            return true;
        }
        return false;
    }

    public ArrayList<Order> findOrderById(int userId) {
        ArrayList<Order> orders = dbConnection.findOrderById(userId);
        return orders;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = dbConnection.getItems();
        return items;
    }

}
