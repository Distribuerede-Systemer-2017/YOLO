package server.endpoints;

import com.google.gson.Gson;
import server.database.DBConnection;
import server.models.Item;
import server.models.Order;
import server.models.User;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class UserEndpoint {
    DBConnection dbCon = new DBConnection();
    ArrayList<Item> items;

    @POST
    public Response createUser(String jsonUser){
        User userCreated = new Gson().fromJson(jsonUser, User.class);
        dbCon.addUser(userCreated);


        return Response
                .status(200)
                .type("application/json")
                .entity("{\"userCreated\":\"true\"}")
                .build();
    }

    @POST
    public Response createOrder(String jsonOrder){
        Order orderCreated = new Gson().fromJson(jsonOrder, Order.class);
        dbCon.addOrder(orderCreated);


        return Response
                .status(200)
                .type("application/json")
                .entity("{\"orderCreated\":\"true\"}")
                .build();
    }


    @GET
    @Path("{id}")
    public Response getOrdersById(@PathParam("id") int id){

        Order foundOrder = dbCon.findOrderById(id);
        String orderAsJson = new Gson().toJson(foundOrder, Order.class);

        return Response
                .status(200)
                .type("application/json")
                .entity(orderAsJson)
                .build();
    }

    @GET
    public Response getItems(){

        this.items = dbCon.getItems();
        String itemsAsJson = new Gson().toJson(items);

        return Response
                .status(200)
                .type("application/json")
                .entity(itemsAsJson)
                .build();
    }

    @GET
    @Path("{id}/{username}/{password}/{email}")

    public Response authorizeUser(
            @PathParam("username") String username,
            @PathParam("password") String password) {

        User user = dbCon.findUserById(id);
        String userAsJson = new Gson().toJson(user, User.class);

        return Response
                .status(200)
                .type("application/json")
                .entity(user)
                .build();
    }


}
