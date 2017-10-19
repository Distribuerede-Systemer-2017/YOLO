package server.endpoints;

import com.google.gson.Gson;
import server.authentication.Secured;
import server.controllers.StaffController;
import server.database.DBConnection;
import server.models.Order;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

//Created by Nordmenn 19-10-2017 Gruppe YOLO
@Secured
@Path("/staff")
public class StaffEndpoint {
    private DBConnection dbCon = new DBConnection();
    private ArrayList<Order> orders;
    private boolean isReady = false;
    private StaffController staffController = new StaffController(dbCon);

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

        int status = 0;

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
