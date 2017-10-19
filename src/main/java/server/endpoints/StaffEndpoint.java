package server.endpoints;

import com.google.gson.Gson;
import server.controllers.StaffController;
import server.models.Order;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/staff")
public class StaffEndpoint {

    StaffController staffController = new StaffController();

    @GET
    @Path("/getOrders")
    public Response getOrders(){
        ArrayList<Order> orders;
        int status = 500;
        orders = staffController.getOrders();

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

    @POST
    @Path("/makeReady?{orderid}")
    public Response makeReady(@PathParam("orderid") int orderID){
        int status = 500;
        Boolean isReady = staffController.makeReady(orderID);

        if(isReady){
            status = 200;
        }
        return Response
                .status(status)
                .type("application/json")
                .entity("{\"isReady\":\"true\"}")
                .build();
    }


}
