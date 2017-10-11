package server.endpoints;

import com.google.gson.Gson;
import server.database.UserTable;
import server.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/users")
public class UserEndpoint {

    UserTable userTable = UserTable.getInstance();
    ArrayList<User> users = userTable.getUsers();

    @GET
    public Response getUsers(){
        //Laver vores ArrayList om til en Json streng
        String usersAsJson = new Gson().toJson(users);
        return Response
                .status(200)
                .type("application/json")
                .entity(usersAsJson)
                .build();
    }

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") int id){

        UserTable userTable = UserTable.getInstance();
        User foundUser = userTable.findById(id); //Finds the user according to id
        String foundUserToJson = new Gson().toJson(foundUser); //String to contain JSON

        return Response
                .status(200)
                .type("application/json")
                .entity(foundUserToJson)
                .build();
    }

    @POST
    public Response createUser(String jsonUser) {
        User newUser = new Gson().fromJson(jsonUser, User.class); //De-serializes from JSON to an object of the User class

        UserTable.getInstance().addUser(newUser); //Adds the object to the UserTable

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"userCreated\":\"true\"}")
                .build();
    }

}
