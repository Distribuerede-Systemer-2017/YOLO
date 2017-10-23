package server.endpoints;

import com.google.gson.Gson;
import server.authentication.Secured;
import server.controllers.StaffController;
import server.database.DBConnection;
import server.models.Order;
import server.utility.Encryption;
import server.utility.Globals;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

//Created by Nordmenn 19-10-2017 Gruppe YOLO
@Secured
@Path("/staff")
public class StaffEndpoint {
    private Encryption encryption = new Encryption();
    private StaffController staffController = new StaffController();

    /**
     *
     * @return Response ordersAsJson
     * Retrieves all orders in the system to be used for the staff side of a client.
     */
    @Secured
    @GET
    @Path("/getOrders")
    public Response getOrders() {
        ArrayList<Order> orders;
        int status = 500;
        orders = staffController.getOrders();

        if (!(orders == null)) {
            status = 200;
        }

        String ordersAsJson = new Gson().toJson(orders);

        return Response
                .status(status)
                .type("application/json")
                //encrypt response
                .entity(encryption.encryptXOR(ordersAsJson))
                .build();
    }

    /**
     *
     * @param orderID
     * @param jsonOrder
     * @return Response true or false
     * Changes the isReady boolean value in the database for the specified order (through the id) and returns a true or false entity in the response.
     */
    @Secured
    @POST
    @Path("/makeReady/{orderid}")
    public Response makeReady(@PathParam("orderid") int orderID, String jsonOrder) {
        jsonOrder = encryption.decryptXOR(jsonOrder);
        Order orderReady = new Gson().fromJson(jsonOrder, Order.class);
        int status = 500;
        Boolean isReady = staffController.makeReady(orderID);

        if (isReady) {
            status = 200;
            //Logging for order made ready
            Globals.log.writeLog(getClass().getName(), this, "Created order with id: " + orderReady.getOrderId(), 0);
        }
        return Response
                .status(status)
                .type("application/json")
                //encrypt response to client
                .entity(encryption.encryptXOR("{\"isReady\":\"" + isReady + "\"}"))
                .build();
    }
}
