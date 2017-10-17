package server.endpoints;

import com.google.gson.Gson;
import server.database.DBConnection;
import server.models.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by Felix on 17-10-2017
 */

public class StaffEndpoint {
    DBConnection dbCon = new DBConnection();

    @PUT
    @Path("{orderId}")
    public Response isReady(@PathParam("orderId") int orderId) {

        dbCon.makeReady(orderId);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"orderUpdated\":\"true\"}")
                .build();
    }

    @GET
    public Response getOrders() {

        String ordersAsJson = new Gson().toJson(dbCon.getOrders());
        return Response
                .status(200)
                .type("application/json")
                .entity(ordersAsJson)
                .build();
    }
}
