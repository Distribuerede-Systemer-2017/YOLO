package server.endpoints;

import com.google.gson.Gson;
import server.config.Config;
import server.controllers.UserController;
import server.models.Item;
import server.models.Order;
import server.models.User;
import server.utility.Encryption;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

//Created by Tobias & Martin 17-10-2017 Gruppe YOLO

@Path("/user")
public class UserEndpoint {

    private ArrayList<Item> items;
    private UserController userController = new UserController();
    private Encryption encryption = new Encryption();
    private Config config = new Config();


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

            boolean result = userController.addUser(userCreated);

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

        boolean result = userController.addOrder(orderCreated.getUser_userId(), orderCreated.getItems());

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


    @POST
    @Path("/findOrdersById/{userId}")
    public Response findOrderById(@PathParam("userId")int userId){
        ArrayList<Order> orders;
        int status = 500;
        orders = userController.findOrderById(userId);
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


    /**
     *
     * @return Returns Response with status 200 (succes) or status 500 (Server Error) based on the value of items
     */
    @GET
    @Path("/getItems")
    public Response getItems(){
        int status = 500;
        this.items = userController.getItems();

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

        if (config.getENCRYPTION()) {
        userAsJson = new Gson().fromJson(userAsJson, String.class);
        userAsJson = encryption.encryptDecryptXOR(userAsJson);
        User user = new Gson().fromJson(userAsJson, User.class);
        User userCheck = userController.authorizeUser(user);
        String userAsJson2 = new Gson().toJson(userCheck, User.class);
        String response = new Gson().toJson(encryption.encryptDecryptXOR(userAsJson2));
            return Response
                .status(200)
                .type("application/json")
                .entity(response)
                .build();
        } else{
            User user = new Gson().fromJson(userAsJson, User.class);
            User userCheck = userController.authorizeUser(user);
            String response = new Gson().toJson(userCheck, User.class);
            return Response
                    .status(200)
                    .type("application/json")
                    .entity(response)
                    .build();
        }

    }

    @POST
    @Path("/encrypt")
    public Response encrypt(String request) {
        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(encryption.encryptDecryptXOR(request)))
                .build();
    }
}
