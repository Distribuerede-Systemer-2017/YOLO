package server.endpoints;

import com.google.gson.Gson;
import server.database.DBConnection;
import server.models.Item;
import server.models.Order;
import server.models.User;
import server.utility.Digester;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

//Created by Tobias & Martin 17-10-2017 Gruppe YOLO

@Path("/user")
public class UserEndpoint {
    DBConnection dbCon = new DBConnection();
    Digester dig = new Digester();
    ArrayList<Item> items;

    @POST
    @Path("{jsonUser}")
    public Response createUser(String jsonUser){
        int status = 0;
        try {
            User userCreated = new Gson().fromJson(jsonUser, User.class);
            boolean result = dbCon.addUser(userCreated);
            status = 200;
        } catch (Exception e){
            if(e.getClass() == BadRequestException.class){
                status = 400;
            }
            else if(e.getClass() == InternalServerErrorException.class){
                status = 500;
            }
        }
        return Response
                .status(status)
                .type("application/json")
                .entity("{\"userCreated\":\"true\"}")
                .build();
    }

    @POST
    @Path("{jsonOrder}")
    public Response createOrder(@PathParam("jsonOrder") String jsonOrder){
        Order orderCreated = new Gson().fromJson(jsonOrder, Order.class);
        int status = 500;
        boolean result = dbCon.addOrder(orderCreated.getUser_userId(), orderCreated.getItems());
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
        ArrayList<Order> foundOrders;
        foundOrders = dbCon.findOrderById(id);

        if (!(foundOrders == null)){
            status = 200;
        }
        else if (foundOrders == null){
            status = 500;
        }

        String ordersAsJson = new Gson().toJson(foundOrders, Order.class);

        return Response
                .status(status)
                .type("application/json")
                .entity(ordersAsJson)
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
    @Path("{userAsJson}")
    public Response authorizeUser(@PathParam("userAsJson") String userAsJson) {
        User user = new Gson().fromJson(userAsJson, User.class);
        String hashedPassword = dig.hashWithSalt(user.getPassword());
        user.setPassword(hashedPassword);
        User userCheck = dbCon.authorizeUser(user);
        String userAsJson2 = new Gson().toJson(userCheck, User.class);

        return Response
                .status(200)
                .type("application/json")
                .entity(userAsJson2)
                .build();
    }


}
