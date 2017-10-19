package server.endpoints;

import com.google.gson.Gson;
import server.controllers.StaffController;
import server.models.Order;
import server.utility.Globals;

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
    @Path("/makeReady/{orderid}")
    public Response makeReady(@PathParam("orderid") int orderID, String jsonOrder){
        Order orderReady = new Gson().fromJson(jsonOrder, Order.class);
        int status = 500;
        Boolean isReady = staffController.makeReady(orderID);

        if(isReady){
            status = 200;
            //Logging for order made ready
            Globals.log.writeLog(getClass().getName(), this, "Created order with id: " + orderReady.getOrderId(), 0 );


        }
        return Response
                .status(status)
                .type("application/json")
                .entity("{\"isReady\":\"" +isReady + "\"}")
                .build();
    }


}
