package server.controllers;

import java.util.ArrayList;
import server.endpoints.StaffEndpoint;
import server.models.Order;

/**
 * Created by Felix on 17-10-2017
 */

public class StaffController {
    public MainController mainController;

    public ArrayList<Order> getOrders(){
        return StaffEndpoint.getOrders();
    }

    public void changeOrderStatus(boolean isReady) {

    }

    public void logout(int id){
        mainController.logout(id);
    }
}
