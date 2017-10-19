package server.endpoints;

import com.google.gson.Gson;
import server.controllers.StaffController;
import server.database.DBConnection;
import server.models.Order;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

//Created by Nordmenn 19-10-2017 Gruppe YOLO

public class StaffEndpoint {
    private DBConnection dbCon = new DBConnection();
    private ArrayList<Order> orders;
    private boolean isReady = false;
    private StaffController staffController = new StaffController();

    @GET
    @Path("/viewOrders")
    public Response viewOrders() {
        int status = 500;

        this.orders = staffController.viewOrders();

        if(!(orders == null)){
            status = 200;
        }

        String ordersAsJson = new Gson().toJson(orders);
        return Response
                .status(status)
                .type("application/json")
                .entity(ordersAsJson)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response makeReady() {

        int status = 200;

        if (status == 200) {
            this.isReady = true;
        }

        String confirm = "Order ready";

        return Response
                .status(status)
                .type("application/json")
                .entity(confirm)
                .build();
    }
}
