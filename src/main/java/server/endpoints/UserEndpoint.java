package server.endpoints;

import com.google.gson.Gson;
import server.authentication.Secured;
import server.controllers.UserController;
import server.models.Item;
import server.models.Order;
import server.models.User;
import server.utility.Encryption;
import server.utility.Globals;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by Tobias WR & Martin GS 17/10/2017
 */

@Path("/user")
public class UserEndpoint {

    private UserController ucontroller = new UserController();
    private Encryption encryption = new Encryption();


    /**
     *
     * @param jsonUser
     * @return Response true or false
     * Creates an user in the database and assigns a token through the AuthEndpoint class.
     * {"username":"****", "password":"****"}
     */
    @POST
    @Path("/createUser")
    public Response createUser(String jsonUser) {
        int status = 0;
        boolean result = false;
        try {
            //Parse Json with encrypted Json object to a String with the Encrypted Object thats no longer a Json ( {"rewqr"} => rewqr )
            //Then Decrypt the object and assign it as a Object in Json format ( rewqr => {"username":"..." }
            jsonUser = encryption.decryptXOR(jsonUser);
            User userCreated = new Gson().fromJson(jsonUser, User.class);
            result = ucontroller.addUser(userCreated);
            status = 200;
            //Logging for user created
            Globals.log.writeLog(getClass().getName(), this, "Creation of user" + userCreated.getUsername() + " successful", 0);

        } catch (Exception e) {
            if (e.getClass() == BadRequestException.class) {
                status = 400;
                //Logging for user not found
                Globals.log.writeLog(getClass().getName(), this, "Creation of user failed. Error code 400", 2);

            } else if (e.getClass() == InternalServerErrorException.class) {
                status = 500;
                //Logging for server failure
                Globals.log.writeLog(getClass().getName(), this, "Internal Server Error 500", 1);
            }
        }

        return Response
                .status(status)
                .type("application/json")
                //encrypt response to clien before sending
                .entity(encryption.encryptXOR("{\"userCreated\":\""+ result +"\"}"))
                .build();
    }


    /**
     *
     * @param jsonOrder
     * @return Response true or false
     * Creates an order in the database by receiving a json string of an order. ({"User_userId":"34","items":[{"itemName":"Kage","itemId":"1"}]}
     */
    @Secured
    @POST
    @Path("/createOrder")
    public Response createOrder(String jsonOrder) {
        //Parse Json with encrypted Json object to a String with the Encrypted Object thats no longer a Json ( {"rewqr"} => rewqr )
        //Then Decrypt the object and assign it as a Object in Json format ( rewqr => {"username":"..." }
        jsonOrder = encryption.decryptXOR(jsonOrder);
        // parse json object
        Order orderCreated = new Gson().fromJson(jsonOrder, Order.class);
        int status = 500;
        boolean result = ucontroller.addOrder(orderCreated.getUser_userId(), orderCreated.getItems());

        if (result) {
            status = 200;
            //Logging for order created
            Globals.log.writeLog(getClass().getName(), this, "Created order with id: " + orderCreated.getOrderId(), 0);

        } else if (!result) {
            status = 500;
            Globals.log.writeLog(getClass().getName(), this, "Internal Server Error 500", 1);

        }

        return Response
                .status(status)
                .type("application/json")
                //encrypt response to clien before sending
                .entity(encryption.encryptXOR("{\"orderCreated\":\"" + result + "\"}"))
                .build();
    }

    /**
     *
     * @param id
     * @return Response ordersAsJson
     * Retrieves all orders placed by the user by using the id as a parameter.
     */
    @Secured
    @GET
    @Path("{id}")
    public Response getOrdersById(@PathParam("id") int id) {
        int status = 500;
        ArrayList<Order> foundOrders;
        foundOrders = ucontroller.findOrderById(id);

        if (!(foundOrders == null)) {
            status = 200;

        } else if (foundOrders == null) {
            status = 500;
        }

        String ordersAsJson = new Gson().toJson(foundOrders, Order.class);

        return Response
                .status(status)
                .type("application/json")
                //encrypt response to clien before sending
                .entity(encryption.encryptXOR(ordersAsJson))
                .build();
    }

    /**
     *
     * @return Response itemsAsJson
     * Retrieves all items in the database to be used for creating a menu in the client.
     */
    @Secured
    @GET
    @Path("/getItems")
    public Response getItems() {
        ArrayList<Item> items;
        int status = 500;
        items = ucontroller.getItems();

        if (!(items == null)) {
            status = 200;
        }

        String itemsAsJson = new Gson().toJson(items);

        return Response
                .status(status)
                .type("application/json")
                //encrypt response to clien before sending
                .entity(encryption.encryptXOR(itemsAsJson))
                .build();
    }
}
