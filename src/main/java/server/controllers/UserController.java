package server.controllers;
import server.database.DBConnection;
import server.models.*;
import server.endpoints.UserEndpoint;
import server.utility.Digester;

import server.database.DBConnection;
import server.models.*;
import server.endpoints.UserEndpoint;

import java.util.ArrayList;

public class UserController {
    private User currentUser;
    private Digester dig;
    private MainController mainController;
    private DBConnection dbConnection;

    public UserController (){
        this.currentUser = currentUser;
        this.dbConnection = new DBConnection();
        this.dig = new Digester();
    }

   // public void logOut() {
   //     mainController.logout();
   // }

     // sets the user to the coherent value of the current user
    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    /**
     *
     * @param user
     * hashes the password+salt of the user and sends it back to DBConnection
     */
    public boolean addUser(User user){

        //sets a users password to a hashed with salt password and returns a boolean value if a user has been created
        String hashedPassword = dig.hashWithSalt(user.getPassword());
        user.setPassword(hashedPassword);
        boolean result = dbConnection.addUser(user);
        return result;
    }

    // Adds an item to the order list
    public boolean addOrder(int id, ArrayList<Item> items){
        boolean result = dbConnection.addOrder(id, items);
        return result;
    }

    // Gets an order by their id
    public ArrayList<Order> getOrdersById(int id){
        ArrayList<Order> orders= dbConnection.findOrderById(id);
        return orders;
    }

    public ArrayList<Item> getItems(){
        ArrayList<Item> items = dbConnection.getItems();
        return items;
    }

    // Authorizes a user, by hashing the input password first, and comparing it to the stored hashed password
    public User authorizeUser(User user){
        String hashedPassword = dig.hashWithSalt(user.getPassword());
        user.setPassword(hashedPassword);
        User userCheck = dbConnection.authorizeUser(user);
        return userCheck;
    }



}
