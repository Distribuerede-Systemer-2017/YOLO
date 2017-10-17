package server.endpoints;

import com.google.gson.Gson;
import server.database.DBConnection;
import server.models.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import server.controllers.StaffController;

public class StaffEndpoint {
    DBConnection dbCon = new DBConnection();
    ArrayList<Order> orders = dbCon.getOrders();
    String ordersAsJson = new Gson().toJson(orders);

    StaffController staffController = new StaffController();

    @PUT
    @Path("{id}")
    public Response isReady(@PathParam("id") boolean isReady) {



        return Response
                .status(200)
                .type("application/json")
                .entity()
                .build();
    }

    @GET
    public Response getOrders() {
        return Response
                .status(200)
                .type("application/json")
                .entity(ordersAsJson)
                .build();
    }
}
