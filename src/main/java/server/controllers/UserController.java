package server.controllers;

import server.database.DBConnection;
import server.models.*;
import server.endpoints.UserEndpoint;

import java.util.ArrayList;

public class UserController {
    private User currentUser;
    private MainController mainController;
    private DBConnection dbConnection;


    public UserController (User currentUser){
        this.currentUser = currentUser;
        this.mainController = new MainController();
        this.dbConnection = new DBConnection();
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

    public ArrayList<Item> getItems(){
        return UserEndpoint.getItems();
    }

    public void logOut() {
        mainController.logout();
    }



}
