package server.controllers;

import server.models.*;
import server.endpoints.UserEndpoint;

import java.util.ArrayList;

public class UserController {
    public int sessionId;
    public MainController mainController;


    public UserController (int id){
        setSessionId(id);
        this.mainController = new MainController();
    }



    public Order myOrders(int id){
        return UserEndpoint.getOrdersById(id);
    }

    public ArrayList<Item> getItems(){
        return UserEndpoint.getItems();
    }

    public void setSessionId(int sessionId){
        this.sessionId = sessionId;
    }

    public void logout(int id){
        mainController.logout(id);
    }



}
