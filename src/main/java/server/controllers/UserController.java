package server.controllers;
import server.database.DBConnection;
import server.models.*;
import server.utility.Digester;

import server.database.DBConnection;
import server.models.*;
import server.endpoints.UserEndpoint;
import server.utility.Globals;

import javax.ws.rs.core.Cookie;
import java.security.SecureRandom;
import java.util.ArrayList;

public class UserController {
    private User currentUser;
    private Digester dig;
    private DBConnection dbConnection;

    public UserController (){
        this.currentUser = currentUser;
        this.dbConnection = new DBConnection();
        this.dig = new Digester();
    }


    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public boolean addUser(User user){
        String hashedPassword = dig.hashWithSalt(user.getPassword());
        user.setPassword(hashedPassword);
        boolean result = dbConnection.addUser(user);

        //Conditional logging for creation of user
        if(result) {
            Globals.log.writeLog(getClass().getName(), this, "Creation of user" + user.getUsername() + " successful", 0);

        } else {
            Globals.log.writeLog(getClass().getName(), this, "Creation of user" + user.getUsername() + " failed", 2);
        }

        return result;
    }

    public boolean addOrder(int id, ArrayList<Item> items){
        boolean result = dbConnection.addOrder(id, items);

        //Conditional logging for adding an order
        if(result) {
            Globals.log.writeLog(getClass().getName(), this, "Added order id: " + id + " to " + currentUser.getUsername() + "'s orders successful", 0);
        } else {
            Globals.log.writeLog(getClass().getName(), this, "Adding order id: " + id + " to" + currentUser.getUsername() + "'s orders failed", 2);
        }
        return result;
    }

    public ArrayList<Order> findOrderById(int userId) {
        ArrayList<Order> orders = dbConnection.findOrderById(userId);
        return orders;
    }

    public ArrayList<Item> getItems(){
        ArrayList<Item> items = dbConnection.getItems();
        return items;
    }

    public User authorizeUser(User user){
        String hashedPassword = dig.hashWithSalt(user.getPassword());
        user.setPassword(hashedPassword);
        User userCheck = dbConnection.authorizeUser(user);
        return userCheck;
    }


}
