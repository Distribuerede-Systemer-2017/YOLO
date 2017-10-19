package server.endpoints;

import com.google.gson.Gson;
import server.controllers.UserController;
import server.database.DBConnection;
import server.models.Item;
import server.models.Order;
import server.models.User;
import server.utility.Digester;
import server.utility.Globals;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

//Created by Tobias & Martin 17-10-2017 Gruppe YOLO

@Path("/user")
public class UserEndpoint {

    private DBConnection dbCon = new DBConnection();
    private Digester dig = new Digester();
    private ArrayList<Item> items;
    private UserController ucontroller = new UserController();

    @POST
    @Path("/createUser")
    public Response createUser(String jsonUser){
        int status = 0;
        try {
            User userCreated = new Gson().fromJson(jsonUser, User.class);
            boolean result = ucontroller.addUser(userCreated);
            status = 200;
            Globals.log.writeLog(getClass().getName(), this, "Oprettede bruger med navnet: " + userCreated.getUsername(), 0 );
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
    @Path("/createOrder")
    public Response createOrder(String jsonOrder){
        Order orderCreated = new Gson().fromJson(jsonOrder, Order.class);
        int status = 500;
        boolean result = ucontroller.addOrder(orderCreated.getUser_userId(), orderCreated.getItems());
        Globals.log.writeLog(getClass().getName(), this, "Oprettede ordrer med id: " + orderCreated.getOrderId(), 0 );
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
        foundOrders = ucontroller.getOrdersById(id);

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
    @Path("/getItems")
    public Response getItems(){
        int status = 500;
        this.items = ucontroller.getItems();

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

    @POST
    @Path("/login")
    public Response authorizeUser(String userAsJson) { //virker ikke nå fordi vi skal hashe på klient-siden også
        User user = new Gson().fromJson(userAsJson, User.class);
        User userCheck = ucontroller.authorizeUser(user);
        String userAsJson2 = new Gson().toJson(userCheck, User.class);

        Globals.log.writeLog(getClass().getName(), this, "Authorized bruger med navnet:" + user.getUsername(), 0 );

        return Response
                .status(200)
                .type("application/json")
                .entity(userAsJson2)
                .build();
    }
}
