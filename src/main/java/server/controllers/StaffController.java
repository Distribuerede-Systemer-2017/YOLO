package server.controllers;

import server.database.DBConnection;
import server.models.Order;
<<<<<<< HEAD
import server.endpoints.StaffEndpoint;
=======
>>>>>>> origin/norwegians

import java.util.ArrayList;

/**
 * Class responsible for all logic related to staff operations
 */
public class StaffController {
    private DBConnection dbConnection;

<<<<<<< HEAD
    public StaffController(){
        this.dbConnection = new DBConnection();

    }
=======
    private DBConnection dbConnection;

    public StaffController () {
        this.dbConnection = new DBConnection();
    }

    /**
     * Returns the list of all orders currently in the database
     */
    public ArrayList<Order> viewOrders() {
        ArrayList<Order> orders = dbConnection.getOrders();
        return orders;
    }

    public void makeReady() {
>>>>>>> origin/norwegians

    /**
     * Returns the list of all orders in the database
     */
    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = dbConnection.getOrders();
        return orders;
    }

    public boolean makeReady(int orderID) {
        boolean result = dbConnection.makeReady(orderID);
        return result;
    }

}

