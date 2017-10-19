package server.endpoints;

import com.google.gson.Gson;
import server.controllers.UserController;
import server.database.DBConnection;
import server.models.Item;
import server.models.Order;
import server.models.User;
import server.utility.Digester;

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

    /**
     *Try catch that returns one of three possible outcomes when trying to create a User
     *
     *@param jsonUser the User created described in a String
     * @return Returns Response status 200(succes), Status 400 (Bad Syntax) or Status 500(Server Error)based on the boolean value of result
     */
    @POST
    @Path("/createUser")
    public Response createUser(String jsonUser){
        int status = 0;
        try {
            User userCreated = new Gson().fromJson(jsonUser, User.class);

            //Call controller and see if the user is created
            boolean result = ucontroller.addUser(userCreated);
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

    /**
     * An if else statement that executes one of to possible outcomes when trying to create an order
     *
     * @param jsonOrder the order created by the User described in a String
     * @return Returns Response with status 200 (succes) or status 500 (Server Error) based on the boolean value of result
     */
    @POST
    @Path("/createOrder")
    public Response createOrder(String jsonOrder){


        Order orderCreated = new Gson().fromJson(jsonOrder, Order.class);
        int status = 500;

        //Call controller and get userID
        boolean result = ucontroller.addOrder(orderCreated.getUser_userId(), orderCreated.getItems());
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


    /**
     * An if else statement that executes one of to possible outcomes when trying to create an order
     *
     * @param id: An id to identify the unique Order
     * @return Returns Response with status 200 (succes) or status 500 (Server Error) based on the value of foundOrders
     */
    @GET
    @Path("{id}")
    public Response getOrdersById(@PathParam("id") int id){

        int status = 500;

        //Call controller and check ArrayList of Orders based on their ID
        ArrayList<Order> foundOrders;
        foundOrders = ucontroller.getOrdersById(id);

        if (!(foundOrders == null)){
            status = 200;
        }
        else if (foundOrders == null){
            status = 500;
        }

        //This method serializes the object foundOrders into its equivalent representation in Json.
        String ordersAsJson = new Gson().toJson(foundOrders, Order.class);


        return Response
                .status(status)
                .type("application/json")
                .entity(ordersAsJson)
                .build();
    }


    /**
     *
     * @return Returns Response with status 200 (succes) or status 500 (Server Error) based on the value of items
     */
    @GET
    @Path("/getItems")
    public Response getItems(){
        int status = 500;
        this.items = ucontroller.getItems();

        if(!(items == null)){
            status = 200;
        }

        //This method serializes the object itemsAsJson into its equivalent representation in Json.
        String itemsAsJson = new Gson().toJson(items);

        return Response
                .status(status)
                .type("application/json")
                .entity(itemsAsJson)
                .build();
    }

    /**
     * Skal kommenteres færdigt når den fungere
     * @param userAsJson
     * @return
     */
    @POST
    @Path("/login")
    public Response authorizeUser(String userAsJson) { //virker ikke nå fordi vi skal hashe på klient-siden også
        User user = new Gson().fromJson(userAsJson, User.class);
        User userCheck = ucontroller.authorizeUser(user);
        String userAsJson2 = new Gson().toJson(userCheck, User.class);

        return Response
                .status(200)
                .type("application/json")
                .entity(userAsJson2)
                .build();
    }
}


