package server.controllers;

import server.database.DBConnection;
import server.models.Order;

import javax.ws.rs.Path;
import java.util.ArrayList;

/**
 * Class responsible for all logic related to staff operations
 * @author Group YOLO
 */
public class StaffController {

    public StaffController() {

    }

    /**
     * Returns the list of all orders in the database
     * @return orders
     */
    public ArrayList<Order> getOrders() {
        DBConnection dbConnection = new DBConnection();
        ArrayList<Order> orders = dbConnection.getOrders();
        return orders;
    }

    /**
     * Method to make an order ready
     * @param orderID
     * @return boolean
     */
    public boolean makeReady(int orderID) {
        DBConnection dbConnection = new DBConnection();
        int result = dbConnection.makeReady(orderID);

        if(result>0){
            return true;
        }
        return false;
    }

}


