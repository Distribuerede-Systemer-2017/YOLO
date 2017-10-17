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

//Created by Tobias & Martin 17-10-2017 Gruppe YOLO

public class UserEndpoint {
    DBConnection dbCon = new DBConnection();
    ArrayList<Item> items;

    @POST
    public Response createUser(String jsonUser){
        User userCreated = new Gson().fromJson(jsonUser, User.class);
        int status = 500;
        boolean result = dbCon.addUser(userCreated);
        if (result){
            status = 200;
        }
        else if (!result){
            status = 500;
        }

        return Response
                .status(status)
                .type("application/json")
                .entity("{\"userCreated\":\"true\"}")
                .build();
    }

    @POST
    public Response createOrder(String jsonOrder){
        Order orderCreated = new Gson().fromJson(jsonOrder, Order.class);
        int status = 500;
        boolean result = dbCon.addOrder(orderCreated);
        if (result) {
            status = 200;
        } else if (!result){
            status = 500;
        }


        return Response
                .status(status)
                .type("application/json")
                .entity("{\"orderCreated\":\"true\"}")
                .build();
    }


    @GET
    @Path("{id}")
    public Response getOrdersById(@PathParam("id") int id){

        int status = 500;
        Order foundOrder;
        foundOrder = dbCon.findOrderById(id);

        if (!(foundOrder == null)){
            status = 200;
        }
        else if (foundOrder == null){
            status = 500;
        }

        String orderAsJson = new Gson().toJson(foundOrder, Order.class);

        return Response
                .status(status)
                .type("application/json")
                .entity(orderAsJson)
                .build();
    }

    @GET
    public Response getItems(){
        int status = 500;
        this.items = dbCon.getItems();

        if(!(items == null)){
            status = 200;
        }


        String itemsAsJson = new Gson().toJson(items);

        return Response
                .status(status)
                .type("application/json")
                .entity(itemsAsJson)
                .build();
    }

    @GET
    @Path("{username}/{password}")

    public Response authorizeUser(@PathParam("username") String username, @PathParam("password") String password) {

        User user = dbCon.authorizeUser(username, password);
        String userAsJson = new Gson().toJson(user, User.class);

        return Response
                .status(200)
                .type("application/json")
                .entity(userAsJson)
                .build();
    }


}
