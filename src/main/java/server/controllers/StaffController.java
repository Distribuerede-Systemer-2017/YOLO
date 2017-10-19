package server.controllers;

import server.database.DBConnection;
import server.models.Order;
import com.google.gson.Gson;
import server.database.DBConnection;
import server.models.Order;
import server.endpoints.StaffEndpoint;
import server.utility.Globals;

import javax.ws.rs.Path;
import java.util.ArrayList;

/**
 * Class responsible for all logic related to staff operations
 */
public class StaffController {
    private DBConnection dbConnection;

    public StaffController(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Returns the list of all orders currently in the database
     */
    public ArrayList<Order> viewOrders() {
        ArrayList<Order> orders = dbConnection.getOrders();
        return orders;
    }

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


