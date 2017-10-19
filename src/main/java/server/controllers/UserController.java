package server.controllers;
import server.database.DBConnection;
import server.models.*;
import server.utility.Digester;

import java.util.ArrayList;

public class UserController {
    private User currentUser;
    private Digester dig;
    private DBConnection dbConnection;

    public UserController (DBConnection dbConnection){
        this.dbConnection = dbConnection;
        this.dig = new Digester();
    }

    public boolean addUser(User user){
        String hashedPassword = dig.hashWithSalt(user.getPassword());
        user.setPassword(hashedPassword);
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




}
