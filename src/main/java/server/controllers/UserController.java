package server.controllers;

import server.database.DBConnection;
import server.models.*;
import server.endpoints.UserEndpoint;

import java.util.ArrayList;

public class UserController {
    private User currentUser;
    private Digester dig;
    private MainController mainController;
    private DBConnection dbConnection;


    public UserController (User currentUser){
        this.currentUser = currentUser;
        this.mainController = new MainController();
        this.dbConnection = new DBConnection();
        this.dig = new Digester();
    }

    /**
     * Return the customer's orders
     * @param id id of the user
     * @return the ArrayList containing orders
     */
    public ArrayList<Order> myOrders(int id){

        //Sørg for at denn metode returnerer et ArrayList
        return dbConnection.getOrders(id);

    }

    public void logOut() {
        mainController.logout();
    }

    public boolean addUser(User user){
        boolean result = dbConnection.addUser(user);
        return result;
    }

    public boolean addOrder(int id, ArrayList<Item> items){
        boolean result = dbConnection.addOrder(id, items);
        return result;
    }

    public ArrayList<Order> getOrdersById(int id){
        ArrayList<Order> orders = dbConnection.findOrderById(id);
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
